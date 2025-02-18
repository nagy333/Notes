package com.example.firebasetraining

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotesRepo@Inject constructor(
    private val databaseReference: DatabaseReference,
    private val authRef : FirebaseAuth,
    private val firestore: CollectionReference,
) {

     fun getNoteById(noteId : String):DocumentReference?{
        return try {
            val db = FirebaseFirestore.getInstance()
            val userId = authRef.currentUser?.uid
            val document = db.collection("users")
                .document(userId!!)
                .collection("notes")
                .document(noteId)
            document
        }catch (e : Exception){
            null
        }
    }
    suspend fun updateNote(noteId : String,map : Map<String,String>){
        try {
            val db = FirebaseFirestore.getInstance()
            val userId = authRef.currentUser?.uid
            val document = db.collection("users")
                .document(userId!!)
                .collection("notes")
                .document(noteId)
            document.update(map).await()
        }catch (e : Exception){
            e.printStackTrace()
        }
    }
    fun addNote(note: Note): Task<DocumentReference> {
        val userId = authRef.currentUser?.uid

        val db = FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(userId!!)
            .collection("notes")

        return db.add(note)
    }
    fun fetchNotes(): CollectionReference {
        val db = FirebaseFirestore.getInstance()
        val userId = authRef.currentUser?.uid
        val noteRef = db.collection("users")
            .document(userId!!)
            .collection("notes")
       return noteRef
    }
     fun deleteNote(noteId : String): Task<Void>? {
         try {
            val db = FirebaseFirestore.getInstance()
            val userId = authRef.currentUser?.uid
            val document = db.collection("users")
                .document(userId!!)
                .collection("notes")
                .document(noteId)
             return document.delete()
        }catch (e :Exception){
            e.printStackTrace()
        }
         return null
    }
    fun signUp(email : String,pass : String): Task<AuthResult> {
        return authRef.createUserWithEmailAndPassword(email,pass)

    }
    fun signIn(email: String,pass: String): Task<AuthResult> {
        return authRef.signInWithEmailAndPassword(email,pass)
    }
    fun signOut(){
         authRef.signOut()
    }
}