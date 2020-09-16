package com.androidkotlin.mynotes.data.provider

import androidx.lifecycle.LiveData
import com.androidkotlin.mynotes.data.model.Note
import com.androidkotlin.mynotes.data.model.NoteResult

interface DataProvider {

    fun subscribeToAllNotes() : LiveData<NoteResult>
    fun saveNote(note: Note) : LiveData<NoteResult>
    fun getNoteById(id: String) : LiveData<NoteResult>
}