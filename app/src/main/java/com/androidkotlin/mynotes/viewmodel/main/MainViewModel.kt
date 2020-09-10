package com.androidkotlin.mynotes.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidkotlin.mynotes.model.Repository

class MainViewModel : ViewModel() {

    private val viewStateLiveData = MutableLiveData<MainViewState>()

    init {
        Repository.getNotes().observeForever{
            viewStateLiveData.value  = viewStateLiveData.value?.copy(notes = it) ?: MainViewState(it)
        }
    }

    fun getViewState(): LiveData<MainViewState> = viewStateLiveData

}