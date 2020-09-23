package com.androidkotlin.mynotes.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import com.androidkotlin.mynotes.R
import com.androidkotlin.mynotes.data.entity.Note

inline fun Note.Color.getColorInt(context: Context) = ContextCompat.getColor(context, getColorRes(context))

inline fun Note.Color.getColorRes(context: Context) = when (this) {
    Note.Color.WHITE -> R.color.white
    Note.Color.VIOLET -> R.color.violet
    Note.Color.YELLOW -> R.color.yello
    Note.Color.RED -> R.color.red
    Note.Color.PINK -> R.color.pink
    Note.Color.GREEN -> R.color.green
    Note.Color.BLUE -> R.color.blue
}