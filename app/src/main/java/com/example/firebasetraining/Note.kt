package com.example.firebasetraining

data class Note(
    val id: String?,
    val noteTitle: String?,
    val noteBody: String?,
    val date : String?
){
    constructor() : this(null, null,null,null)
}
