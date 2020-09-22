package com.androidkotlin.mynotes.ui.splash

import com.androidkotlin.mynotes.data.NotesRepository
import com.androidkotlin.mynotes.data.errors.NoAuthException
import com.androidkotlin.mynotes.ui.base.BaseViewModel

class SplashViewModel(val notesRepository: NotesRepository) : BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        notesRepository.getCurrentUser().observeForever {
            viewStateLiveData.value = if (it != null) {
                SplashViewState(authenticated = true)
            } else {
                SplashViewState(error = NoAuthException())
            }
        }
//TODO убрать observer
    }
}