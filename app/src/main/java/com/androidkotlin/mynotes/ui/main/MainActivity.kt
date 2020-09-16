package com.androidkotlin.mynotes.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.androidkotlin.mynotes.R
import com.androidkotlin.mynotes.data.model.Note
import com.androidkotlin.mynotes.ui.base.BaseActivity
import com.androidkotlin.mynotes.ui.note.NoteActivity
import com.androidkotlin.mynotes.viewmodel.main.MainViewModel
import com.androidkotlin.mynotes.viewmodel.main.MainViewState
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override  val layoutRes: Int = R.layout.activity_main
    lateinit var adapter: NotesRVA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        setSupportActionBar(toolbar)//Не забудьте поменять в Стилях приложения тему на Theme.AppCompat.Light.NoActionBar - поменял

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVA{
            NoteActivity.start(this, it.id)
        }

        rv_notes.adapter = adapter

        fab.setOnClickListener{
            NoteActivity.start(this)
        }
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }
}


