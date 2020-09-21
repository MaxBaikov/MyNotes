package com.androidkotlin.mynotes.ui.note

import androidx.lifecycle.Observer
import com.androidkotlin.mynotes.data.entity.Note
import com.androidkotlin.mynotes.data.model.NoteResult
import com.androidkotlin.mynotes.data.NotesRepository
import com.androidkotlin.mynotes.ui.base.BaseViewModel

class NoteViewModel : BaseViewModel<Note?, NoteViewState>() {

    init {
        viewStateLiveData.value = NoteViewState()
    }

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    fun loadNote(noteId: String) {

//        NotesRepository.getNoteById(noteId).observeForever{result ->
//            result ?: return@observeForever
//            when(result){
//                is NoteResult.Success<*> -> viewStateLiveData.value =
//                    NoteViewState(note = result.data as? Note)
//                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
//
//            }
//        }

//TODO убрать обзервер после использования

        val noteLiveData = NotesRepository.getNoteById(noteId)

        val observer = object : Observer<NoteResult> {

            override fun onChanged(t: NoteResult?) {
                when (t) {
                    is NoteResult.Success<*> -> viewStateLiveData.value =
                        NoteViewState(note = t.data as? Note)
                    is NoteResult.Error -> viewStateLiveData.value =
                        NoteViewState(error = t.error)
                }
                noteLiveData.removeObserver(this)
            }
        }

        noteLiveData.observeForever(observer)

    }

    override fun onCleared() {
        pendingNote?.let {
            NotesRepository.saveNote(it)
        }
    }
}