package com.androidkotlin.mynotes.data.provider


import kotlinx.coroutines.channels.ReceiveChannel
import com.androidkotlin.mynotes.data.entity.Note
import com.androidkotlin.mynotes.data.entity.User
import com.androidkotlin.mynotes.data.model.NoteResult

interface DataProvider {
    fun subscribeToAllNotes() : ReceiveChannel<NoteResult>

    suspend fun getCurrentUser() : User?
    suspend fun saveNote(note: Note) : Note
    suspend fun getNoteById(id: String) : Note?
    suspend fun deleteNote(id: String)
}