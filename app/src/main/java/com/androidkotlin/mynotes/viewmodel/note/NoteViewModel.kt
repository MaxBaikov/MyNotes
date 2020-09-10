package com.androidkotlin.mynotes.viewmodel.note

import androidx.lifecycle.ViewModel
import com.androidkotlin.mynotes.model.Note
import com.androidkotlin.mynotes.model.Repository

class NoteViewModel : ViewModel() {

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            Repository.saveNote(it)
        }
    }
}