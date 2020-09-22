package com.androidkotlin.mynotes.data

import com.androidkotlin.mynotes.data.entity.Note
import com.androidkotlin.mynotes.data.provider.DataProvider


class NotesRepository (val dataProvider: DataProvider) {

    fun getCurrentUser() = dataProvider.getCurrentUser()
    fun getNotes() = dataProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = dataProvider.saveNote(note)
    fun getNoteById(id: String) = dataProvider.getNoteById(id)
}
