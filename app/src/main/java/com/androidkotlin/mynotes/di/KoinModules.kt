package com.androidkotlin.mynotes.di

import com.androidkotlin.mynotes.data.NotesRepository
import com.androidkotlin.mynotes.data.provider.DataProvider
import com.androidkotlin.mynotes.data.provider.FirestoreProvider
import com.androidkotlin.mynotes.ui.main.MainViewModel
import com.androidkotlin.mynotes.ui.note.NoteViewModel
import com.androidkotlin.mynotes.ui.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single<DataProvider> { FirestoreProvider(get(), get()) }
    single { NotesRepository(get()) }

}

val splashViewModel = module {
    viewModel{ SplashViewModel(get()) }

}

val mainModule = module {
    viewModel{ MainViewModel(get()) }

}

val noteModule = module {
    viewModel{ NoteViewModel(get()) }

}