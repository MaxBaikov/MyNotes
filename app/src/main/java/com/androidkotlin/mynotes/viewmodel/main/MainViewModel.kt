package com.androidkotlin.mynotes.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.androidkotlin.mynotes.data.model.Note
import com.androidkotlin.mynotes.data.model.NoteResult
import com.androidkotlin.mynotes.data.model.NotesRepository
import com.androidkotlin.mynotes.ui.base.BaseViewModel

class MainViewModel() : BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = Observer<NoteResult> { result ->
        result ?: return@Observer
        when (result) {
            is NoteResult.Success<*> -> viewStateLiveData.value = MainViewState(notes = result.data as? List<Note>)
            is NoteResult.Error -> viewStateLiveData.value = MainViewState(error = result.error)
        }
    }

    private val repositoryNotes = NotesRepository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
            repositoryNotes.observeForever(notesObserver)
    }

    override fun onCleared() {
        super.onCleared()
        repositoryNotes.removeObserver(notesObserver)
    }
}