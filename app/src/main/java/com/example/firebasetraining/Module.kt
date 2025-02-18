package com.example.firebasetraining

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Module {


    @Provides
    fun provideDataBaseReference():DatabaseReference{
        var firebaseRef : DatabaseReference
        return FirebaseDatabase.getInstance().getReference("Notes")
    }
    @Provides
    fun provideNoteRepo(
        databaseReference: DatabaseReference,
        auth : FirebaseAuth,
        firestore: CollectionReference,):NotesRepo{
        return NotesRepo(databaseReference,auth,firestore)
    }
    @Provides
    fun provideAuthRef():FirebaseAuth{
        val auth : FirebaseAuth = FirebaseAuth.getInstance()
        return auth
    }
    @Provides
    fun provideFireStoreRef(): CollectionReference {
        val firestore = Firebase.firestore.collection("Notes")
        return firestore
    }

}