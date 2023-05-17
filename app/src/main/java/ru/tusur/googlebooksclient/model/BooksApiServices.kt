package ru.tusur.googlebooksclient.model

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApiServices {
    @GET("/books/v1/volumes")
    suspend fun searchBooks(
        @Query("q") searchString:String,
        @Query("filter") filter:String
    ):BooksResponse

    @GET("books/v1/volumes/{volumeId}")
    suspend fun getVolume(@Path("volumeId") volId:String):BookByIdResponse
}
