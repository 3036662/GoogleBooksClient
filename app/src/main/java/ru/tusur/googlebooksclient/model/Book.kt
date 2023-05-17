package ru.tusur.googlebooksclient.model

import kotlinx.serialization.Serializable

@Serializable
data class Book (
    val id:String,
    val title:String,
    val authors: List<String>,
    val thumbnail:String,
    val img:String?=null,
    val date:String?
)
