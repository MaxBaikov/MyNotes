package com.androidkotlin.mynotes.ui

import android.app.Application
import com.androidkotlin.mynotes.di.appModule
import com.androidkotlin.mynotes.di.mainModule
import com.androidkotlin.mynotes.di.noteModule
import com.androidkotlin.mynotes.di.splashModel

import org.koin.android.ext.android.startKoin

class App: Application(){

    companion object{
        lateinit var instance: App
        private set
    }

    override fun onCreate(){
        super.onCreate()
        instance = this

        startKoin(this, listOf(appModule, mainModule, noteModule, splashModel))
    }
}

