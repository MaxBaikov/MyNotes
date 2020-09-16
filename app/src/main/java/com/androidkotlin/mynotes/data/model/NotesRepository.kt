package com.androidkotlin.mynotes.data.model

import com.androidkotlin.mynotes.data.provider.DataProvider
import com.androidkotlin.mynotes.data.provider.FirestoreProvider


object NotesRepository {

    private val dataProvider: DataProvider = FirestoreProvider()

    fun getNotes() = dataProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = dataProvider.saveNote(note)
    fun getNoteById(id: String) = dataProvider.getNoteById(id)
}
