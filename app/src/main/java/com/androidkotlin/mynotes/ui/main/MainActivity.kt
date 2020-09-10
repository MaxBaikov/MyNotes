package com.androidkotlin.mynotes.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.androidkotlin.mynotes.R
import com.androidkotlin.mynotes.ui.note.NoteActivity
import com.androidkotlin.mynotes.viewmodel.main.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var adapter: NotesRVA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)//Не забудьте поменять в Стилях приложения тему на Theme.AppCompat.Light.NoActionBar - поменял

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVA{
            NoteActivity.start(this, it)
        }
        rv_notes.adapter = adapter

        viewModel.getViewState().observe(this, { t ->
            t?.let { adapter.notes = it.notes }
        })

        fab.setOnClickListener{
            NoteActivity.start(this)
        }
    }
}


