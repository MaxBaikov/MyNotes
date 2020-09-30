package com.androidkotlin.mynotes.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import com.androidkotlin.mynotes.data.entity.Note
import com.androidkotlin.mynotes.data.model.NoteResult
import com.androidkotlin.mynotes.data.NotesRepository
import com.androidkotlin.mynotes.ui.base.BaseViewModel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class MainViewModel(notesRepository: NotesRepository) : BaseViewModel<List<Note>?>() {
    private val notesChannel = notesRepository.getNotes()

    init {
        launch {
            notesChannel.consumeEach {
                when (it) {
                    is NoteResult.Success<*> -> setData(it.data as? List<Note>)
                    is NoteResult.Error -> setError(it.error)
                }
            }
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        super.onCleared()
        notesChannel.cancel()
    }
}