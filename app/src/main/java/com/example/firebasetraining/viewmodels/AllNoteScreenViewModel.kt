package com.example.firebasetraining.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.firebasetraining.Note
import com.example.firebasetraining.NotesRepo
import com.example.firebasetraining.Screens
import com.example.firebasetraining.uistate.AllNotesScreenUiState
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class AllNoteScreenViewModel@Inject constructor(
    private val repo: NotesRepo,
    private val databaseReference: DatabaseReference
) : ViewModel() {

    val uiState = MutableStateFlow(AllNotesScreenUiState())
    init {
        fetchNotes()
        Log.d("nagy",getCurrentDate())
    }
    fun onTitleValueChange(value : String){
        uiState.update { it.copy(noteTitle = value) }
    }
    fun onBodyValueChange(value : String){
        uiState.update { it.copy(noteBody = value) }
    }
    fun onFabClicked(){
        uiState.update { it.copy(showDialog = true) }
    }
    fun onDismissRequest(){
        uiState.update { it.copy(showDialog = false) }
    }
    fun onBtnSaveClicked(context : Context){
        addNote(context)
        val id = databaseReference.push().key
        val noteTitle = uiState.value.noteTitle
        val noteBody = uiState.value.noteBody
        val note = Note(id = id!!, noteBody = noteBody, noteTitle = noteTitle, date = getCurrentDate())
        databaseReference.child(id).setValue(note)
        uiState.update { it.copy(showDialog = false, noteTitle = "", noteBody = "") }
    }
    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate():String{
        val cal = Calendar.getInstance()
        val dFormat = SimpleDateFormat("EEEE hh:mm a ")
        val date = dFormat.format(cal.time)
        return date
    }
    private fun addNote(context: Context){
        val note = Note(
            id = "",
            noteTitle = uiState.value.noteTitle,
            noteBody = uiState.value.noteBody,
            date = getCurrentDate())
        repo.addNote(note).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(context,"Saved",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show()

            }
        }
    }
    private fun fetchNotes(){
        repo.fetchNotes().addSnapshotListener{ documents,e->
            val notes = ArrayList<Note>()
            try {
                for (document in documents!!){
                    val body = document.getString("noteBody")
                    val title = document.getString("noteTitle")
                    val date = document.getString("date")
                    val id = document.id
                    val note = Note(noteTitle = title, noteBody = body, date = date,id = id)
                    notes.add(note)
                }
                uiState.update { it.copy(notesList = notes) }
            }catch (ex :Exception){
                Log.d("test",e?.message!!)
            }

        }
    }
    fun onSignOutClicked(navController: NavController){
        repo.signOut()
        navController.navigate(Screens.SignInScreen.route)
    }
    }

