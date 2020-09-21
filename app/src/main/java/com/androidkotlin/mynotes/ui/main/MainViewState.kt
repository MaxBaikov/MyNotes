package com.androidkotlin.mynotes.ui.main

import com.androidkotlin.mynotes.data.entity.Note
import com.androidkotlin.mynotes.ui.base.BaseViewState

class MainViewState(val notes: List<Note>? = null, error: Throwable? = null): BaseViewState<List<Note>?>(notes,error)