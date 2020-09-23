package com.androidkotlin.mynotes.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidkotlin.mynotes.data.entity.Note
import com.androidkotlin.mynotes.data.model.NoteResult
import com.androidkotlin.mynotes.data.entity.User
import com.androidkotlin.mynotes.data.errors.NoAuthException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreProvider(val firebaseAuth: FirebaseAuth, val store: FirebaseFirestore) : DataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }

    private val currentUser
        get() = firebaseAuth.currentUser

    private val notesReference
            get() = currentUser?.let {
        store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    override fun getCurrentUser(): LiveData<User?> = MutableLiveData<User?>().apply {
        value = currentUser?.let {
            User(it.displayName ?: "", it.email ?: "")
        }
    }

    override fun subscribeToAllNotes(): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        try {
            notesReference.addSnapshotListener { snapshot, e ->
                e?.let {

                } ?: snapshot?.let {
                    val notes: List<Note> =
                        snapshot.documents.mapNotNull { it.toObject(Note::class.java) }
                    value = NoteResult.Success(notes)
                }
            }
        } catch (t: Throwable) {
            value = NoteResult.Error(t)
        }
    }

    override fun saveNote(note: Note): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {

        try {
            notesReference.document(note.id).set(note)
                .addOnSuccessListener { snapshot ->
                    value = NoteResult.Success(note)
                }.addOnFailureListener {
                    value = NoteResult.Error(it)
                }
        } catch (t: Throwable) {
            value = NoteResult.Error(t)
        }

    }

    override fun getNoteById(id: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            try {
                notesReference.document(id).get()
                    .addOnSuccessListener { snapshot ->
                        val note: Note? = snapshot.toObject(Note::class.java)
                        value = NoteResult.Success(note)
                    }.addOnFailureListener {
                        value = NoteResult.Error(it)
                    }
            } catch (t: Throwable) {
                value = NoteResult.Error(t)
            }
        }

    override fun deleteNote(id: String): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        try {
            notesReference.document(id).delete()
                .addOnSuccessListener { snapshot ->
                    value = NoteResult.Success(null)
                }.addOnFailureListener {
                    value = NoteResult.Error(it)
                }
        } catch (t: Throwable) {
            value = NoteResult.Error(t)
        }
    }
}