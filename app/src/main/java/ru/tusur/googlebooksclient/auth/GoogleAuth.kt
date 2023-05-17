package ru.tusur.googlebooksclient.auth


import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
//import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*


const val MY_CLIENT_ID="189284822864-i5gehoqcvqesloriuccem7im2p3hlare.apps.googleusercontent.com"
const val MY_REDIRECT_URI="ru.tusur.googlebooksclient"
const val BOOKS_SCOPE="https://www.googleapis.com/auth/books"
const val authlink="https://accounts.google.com/o/oauth2/v2/auth"
const val tokenBaseURL="https://oauth2.googleapis.com"

const val  STRING_LENGTH=32


class GoogleAuth(private val tokenStorage:SharedPreferences ) {

    private  var codeVerifier:String=""
    private  var codeChallenge:String=""
    private var tokens:Tokens?=
        // read tokens from local storage
        tokenStorage.let {
            Log.d("BooksLog","Reading tokens from local storage")
            if (it.contains("accessToken") &&
                it.contains("tokenType") &&
                it.contains("expirationTime") &&
                it.contains("scope") &&
                it.contains("refreshToken")) {
                Tokens(it.getString("accessToken","")!! ,
                              it.getLong("expirationTime",0),
                              it.getString("tokenType","")!!,
                              it.getString("scope","")!!,
                              it.getString("refreshToken","")!!)
            }
            else{
                 null
            }
        }

    public val isAuthorized:Boolean
            get()=tokens!=null


    // This is just for logging all requests and responses to Logcat
    private val logging = HttpLoggingInterceptor()
    private val httpClient = OkHttpClient.Builder().addInterceptor(logging)
    init {
        logging.level = HttpLoggingInterceptor.Level.BODY
    }
    // retrofit
    @OptIn(ExperimentalSerializationApi::class)
    private val  retrofit= Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(tokenBaseURL)
    .client(httpClient.build()) // this is just for logging all requests and responses to Logcat
    .build()
    private val  googleAuthApi : GoogleAuthInterface by lazy {
        retrofit.create(GoogleAuthInterface::class.java)
    }

    // generate random string for codeVerifier
    private  fun codeVerifier() :Unit{
        val  charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val secureRandom = SecureRandom()
        val str= (1..STRING_LENGTH).map {
            secureRandom.nextInt(charPool.size).let { charPool[it] }
        }.joinToString("")
       // Log.d("BooksLog","rand_str= $str")
        codeVerifier= Base64.getUrlEncoder().withoutPadding().encodeToString(str.toByteArray())
    }

    // generate codeChallenge from codeVerifier
    private fun codeChallenge():Unit{
        val md=MessageDigest.getInstance("SHA-256")
        codeChallenge=  Base64.getUrlEncoder().withoutPadding().encodeToString(md.digest(codeVerifier.toByteArray()))
    }

    // initial authorization
    fun authorization(curContext:Context) {
        codeVerifier()
        codeChallenge()
        val authUrl="$authlink?client_id=$MY_CLIENT_ID&redirect_uri=$MY_REDIRECT_URI:/" +
                "&scope=$BOOKS_SCOPE&code_challenge=$codeChallenge&code_challenge_method=S256&response_type=code"
        val builder = CustomTabsIntent.Builder( )
        builder.setShowTitle(true)
        builder.setInstantAppsEnabled(true)
        val customBuilder = builder.build()
        customBuilder.launchUrl(curContext, Uri.parse(authUrl))
        Log.d("BooksLog","code_verifier=$codeVerifier")
        Log.d("BooksLog","code_challenge=$codeChallenge")
        Log.d("BooksLog","auth_url:$authUrl")
    }

    // change code to token
   suspend fun getTokenWithCode(code: String) {
        logout() //just in case
       try {
                val res:TokenResponse = googleAuthApi.requestTokenFromCode(
                  code=code,
                  redirectUri="$MY_REDIRECT_URI:/",
                  clientId=MY_CLIENT_ID,
                  codeVerifier=codeVerifier,
                  grantType = "authorization_code",
                )
                Log.d("BooksLog", res.toString())
                tokens= Tokens(
                    accessToken = res.accessToken,
                    expirationTime=  System.currentTimeMillis()+res.expiresIn*1000-500,
                    tokenType = res.tokenType,
                    scope = res.scope,
                    refreshToken = res.refreshToken
                )
                saveToken()
            }
            catch (e:java.lang.Exception) {
                tokenStorage.edit().clear().apply()
                tokens=null
                Log.d("BooksLog", e.toString())
            }
    }

    // save token to local storage
    private fun saveToken(){
        // may be some super crypto encoding needed
        val editor=tokenStorage.edit()
        tokens?.let {
            editor.clear()
            editor.putString("accessToken",it.accessToken)
            editor.putLong("expirationTime",it.expirationTime)
            editor.putString("tokenType",it.tokenType)
            editor.putString("scope",it.scope)
            editor.putString("refreshToken",it.refreshToken)
            editor.commit()
        }
    }

    suspend fun getAccessToken():String?{
      if (tokens==null) return null
      if (tokens!!.expirationTime<System.currentTimeMillis()){
          updateToken()
      }
      return tokens!!.accessToken
    }

    suspend fun updateToken() {
       try {
           val res:UpdatedTokenResponse= googleAuthApi.updateToken(
                MY_CLIENT_ID,
                tokens!!.refreshToken,
                "refresh_token"
           )
           tokens=tokens!!.copy(
               accessToken = res.accessToken,
               expirationTime=  System.currentTimeMillis()+res.expiresIn*1000-500,
           )
       }
       catch (e:java.lang.Exception) {
           tokenStorage.edit().clear().apply()
           tokens=null
           Log.d("BooksLog", e.toString())
       }

    }

    suspend fun logout() {
        try {
            googleAuthApi.revokeToken(tokens!!.accessToken)
            tokenStorage.edit().clear().apply()
            tokens=null
        }
        catch (e:java.lang.Exception) {
             Log.d("BooksLog", e.toString())
        }
    }




}



