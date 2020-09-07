package com.androidkotlin.mynotes.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidkotlin.mynotes.Repository

class MainViewModel : ViewModel() {

    private val viewStateLiveData = MutableLiveData<MainViewState>()

    init {
        viewStateLiveData.value = MainViewState(Repository.notes)
    }

    fun getViewState(): LiveData<MainViewState> = viewStateLiveData

}