package com.androidkotlin.mynotes.ui.splash

import com.androidkotlin.mynotes.data.NotesRepository
import com.androidkotlin.mynotes.data.errors.NoAuthException
import com.androidkotlin.mynotes.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel(val notesRepository: NotesRepository) : BaseViewModel<Boolean?>() {

    fun requestUser() = launch {
        notesRepository.getCurrentUser()?.let {
            setData(true)
        } ?: setError(NoAuthException())
    }

}