package com.androidkotlin.mynotes.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidkotlin.mynotes.Note
import com.androidkotlin.mynotes.R
import kotlinx.android.synthetic.main.item_note.view.*

class NotesRVA : RecyclerView.Adapter<NotesRVA.NotesVH>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NotesVH(LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false))

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NotesVH, position: Int) = holder.bind(notes[position])

    class NotesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) = with(itemView) {
            note_title.text = note.title
            note_body.text = note.text
            setBackgroundColor(note.color)
        }
    }

}