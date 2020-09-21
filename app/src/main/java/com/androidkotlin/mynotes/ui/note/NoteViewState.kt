package com.androidkotlin.mynotes.ui.note

import com.androidkotlin.mynotes.data.entity.Note
import com.androidkotlin.mynotes.ui.base.BaseViewState

class NoteViewState (note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error)
