package com.androidkotlin.mynotes.viewmodel.note

import com.androidkotlin.mynotes.data.model.Note
import com.androidkotlin.mynotes.ui.base.BaseViewState

class NoteViewState (note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error)
