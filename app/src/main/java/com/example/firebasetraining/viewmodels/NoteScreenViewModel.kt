package com.example.firebasetraining.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasetraining.uistate.NoteScreenUiState
import com.example.firebasetraining.NotesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteScreenViewModel@Inject constructor(
    private val repo: NotesRepo
) :ViewModel()  {
    val state = MutableStateFlow(NoteScreenUiState())
    fun getNoteById(id : String){
        repo.getNoteById(id)!!.get().addOnSuccessListener { document->
            if (document.exists()){
                val title = document.getString("noteTitle")
                val body = document.getString("noteBody")
                state.update { it.copy(title = title, body = body) }
            }
        }
    }
    private fun updateNote(noteId : String){
        val map = mutableMapOf<String,String>()
        map["noteBody"] = state.value.body!!
        map["noteTitle"] = state.value.title!!
        viewModelScope.launch {
            repo.updateNote(noteId,map)
        }
    }
    fun onEditClicked(){
        state.update { it.copy(isEditClicked = true, showEdit = false, showDel = false, showDone = true) }
    }
    fun onDeleteClicked(
        noteId: String,context: Context){
        state.update { it.copy(isDeleteCLicked = true) }
        viewModelScope.launch {
            repo.deleteNote(noteId)?.addOnSuccessListener {
                Toast.makeText(context,"Note Deleted",Toast.LENGTH_LONG).show()
            }
        }
    }
    fun onTitleChange(value : String){
        state.update { it.copy(title = value) }
    }
    fun onBodyChange(value : String){
        state.update { it.copy(body = value) }
    }
    fun onDoneClicked(noteId : String){
        state.update { it.copy(isDoneClicked = true, showDone = false, showDel = true, showEdit = true, isEditClicked = false) }
        updateNote(noteId)
    }

}