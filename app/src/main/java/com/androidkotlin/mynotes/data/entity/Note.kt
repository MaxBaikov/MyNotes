package com.androidkotlin.mynotes.data.entity

import android.os.Parcelable
import com.androidkotlin.mynotes.data.entity.Note.Companion.random
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.random.Random


@Parcelize
data class Note(
    val id: String = "",
    val title: String = "",
    val text: String = "",
    var color: Color = random(),
    val lastChanged: Date = Date()
) : Parcelable {

    enum class Color {
        WHITE,
        YELLOW,
        GREEN,
        BLUE,
        RED,
        VIOLET,
        PINK;
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object {
        fun random() = Color.values()[Random.nextInt(Color.values().size)]
    }

}

