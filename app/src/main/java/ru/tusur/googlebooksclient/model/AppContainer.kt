package ru.tusur.googlebooksclient.model

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


interface AppContainer {
    val  booksRepository:BooksRepository
}

class DefaultAppContainer:AppContainer{
    private val BASE_URL ="https://www.googleapis.com/"


    // This is just for logging all requests and responses to Logcat
    private val logging = HttpLoggingInterceptor()
    private val httpClient = OkHttpClient.Builder().addInterceptor(logging)
    init {
        logging.level = HttpLoggingInterceptor.Level.BODY
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(httpClient.build()) // this is just for logging all requests and responses to Logcat
        .build()

    private val booksApiServices:BooksApiServices by lazy<BooksApiServices>{
        retrofit.create(BooksApiServices::class.java)
    }
    override val booksRepository: BooksRepository by lazy{
        DefaultBooksRepository(booksApiServices)
    }


}