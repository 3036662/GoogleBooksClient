package ru.tusur.googlebooksclient.model

import android.util.Log

interface BooksRepository{
    suspend fun searchBooks(searchString:String):List<Book>
}

class DefaultBooksRepository(
    private val booksApiService:BooksApiServices
):BooksRepository {
    override suspend fun searchBooks(searchString: String): List<Book> {
        val response= booksApiService.searchBooks(searchString,"full")
        return response.items?.mapNotNull {
            if (it.id!=null && it.volumeInfo?.title!=null && it.volumeInfo.authors!==null && it.volumeInfo.imageLinks!=null){
              //  val bookById:BookByIdResponse=booksApiService.getVolume(it.id)
              //  Log.d("BooksLog",it.id)
               // val img=(bookById.volumeInfo.imageLinks?.medium ?:   bookById.volumeInfo.imageLinks?.thumbnail)?.replace("http://","https://")
                Book(it.id,
                    it.volumeInfo.title?:"",
                    it.volumeInfo.authors?: listOf(),
                    thumbnail = it.volumeInfo.imageLinks.thumbnail.replace("http://","https://"),   //wierd http links are accepted
                    //=it.volumeInfo.imageLinks.medium?.replace("http://","https://"),
                    date=it.volumeInfo.publishedDate)
            }
            else{
                null
            }
        } ?: listOf()

    }
}