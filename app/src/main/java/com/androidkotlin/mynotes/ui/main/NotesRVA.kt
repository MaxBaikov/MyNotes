package com.androidkotlin.mynotes.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.androidkotlin.mynotes.model.Note
import com.androidkotlin.mynotes.R
import com.androidkotlin.mynotes.model.Color
import kotlinx.android.synthetic.main.item_note.view.*

class NotesRVA(val onItemClick: ((Note) -> Unit)? = null) : RecyclerView.Adapter<NotesRVA.NotesVH>() {

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

            val color = when (note.color) {
                Color.WHITE -> R.color.white
                Color.VIOLET -> R.color.violet
                Color.YELLOW -> R.color.yello
                Color.RED -> R.color.red
                Color.PINK -> R.color.pink
                Color.GREEN -> R.color.green
                Color.BLUE -> R.color.blue
            }

            setCardBackgroundColor(ContextCompat.getColor(itemView.context,color))

            itemView.setOnClickListener {
                onItemClick?.invoke(note)
            }
        }
    }

}