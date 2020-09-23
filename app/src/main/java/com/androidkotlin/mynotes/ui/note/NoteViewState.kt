package com.androidkotlin.mynotes.ui.note

import com.androidkotlin.mynotes.data.entity.Note
import com.androidkotlin.mynotes.ui.base.BaseViewState

class NoteViewState (data: Data = Data(), error: Throwable? = null) : BaseViewState<NoteViewState.Data>(data, error){
    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}
