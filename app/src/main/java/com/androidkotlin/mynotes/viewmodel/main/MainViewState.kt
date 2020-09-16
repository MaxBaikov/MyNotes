package com.androidkotlin.mynotes.viewmodel.main

import com.androidkotlin.mynotes.data.model.Note
import com.androidkotlin.mynotes.ui.base.BaseViewState

class MainViewState(val notes: List<Note>? = null, error: Throwable? = null): BaseViewState<List<Note>?>(notes,error)