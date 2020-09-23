package com.androidkotlin.mynotes.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.androidkotlin.mynotes.data.entity.Note
import com.androidkotlin.mynotes.R
import com.androidkotlin.mynotes.extensions.getColorInt
import kotlinx.android.synthetic.main.item_note.view.*

class NotesRVA(val onItemClick: ((Note) -> Unit)? = null) :
    RecyclerView.Adapter<NotesRVA.NotesVH>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NotesVH(LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false))

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NotesVH, position: Int) = holder.bind(notes[position])

    inner class NotesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) = with(itemView as CardView) {
            note_title.text = note.title
            note_body.text = note.text

            setCardBackgroundColor(note.color.getColorInt(context))

            itemView.setOnClickListener {
                onItemClick?.invoke(note)
            }
        }
    }

}