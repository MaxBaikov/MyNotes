package com.androidkotlin.mynotes.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

object Repository {

    private val notesLiveData = MutableLiveData<List<Note>>()

    private val notes = mutableListOf(
        Note(
            UUID.randomUUID().toString(),
            "Моя первая заметка",
            "Kotlin очень краткий, но при этом выразительный язык",
            Color.WHITE
        ),
        Note(
            UUID.randomUUID().toString(),
            "Моя первая заметка",
            "Kotlin очень краткий, но при этом выразительный язык",
            Color.YELLOW
        ),
        Note(
            UUID.randomUUID().toString(),
            "Моя первая заметка",
            "Kotlin очень краткий, но при этом выразительный язык",
            Color.GREEN
        ),
        Note(
            UUID.randomUUID().toString(),
            "Моя первая заметка",
            "Kotlin очень краткий, но при этом выразительный язык",
            Color.BLUE
        ),
        Note(
            UUID.randomUUID().toString(),
            "Моя первая заметка",
            "Kotlin очень краткий, но при этом выразительный язык",
            Color.VIOLET
        ),
        Note(
            UUID.randomUUID().toString(),
            "Моя первая заметка",
            "Kotlin очень краткий, но при этом выразительный язык",
            Color.PINK
        )
    )

    init {
        notesLiveData.value= notes
    }

    fun getNotes() : LiveData<List<Note>>{
        return notesLiveData
    }

    fun saveNote(note: Note){
        addOrReplace(note)
        notesLiveData.value = notes
    }

    private fun addOrReplace(note: Note){
        for(i in 0 until notes.size){
            if (notes[i] == note){
                notes[i] = note
                return
            }

        }
        notes.add(note)
    }

}
