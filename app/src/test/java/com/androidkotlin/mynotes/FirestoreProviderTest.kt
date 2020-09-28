package com.androidkotlin.mynotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.androidkotlin.mynotes.data.entity.Note
import com.androidkotlin.mynotes.data.errors.NoAuthException
import com.androidkotlin.mynotes.data.model.NoteResult
import com.androidkotlin.mynotes.data.provider.FirestoreProvider
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.mockk.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import java.lang.Error

class FirestoreProviderTest {

    @get:Rule
    val taskExecutionRule = InstantTaskExecutorRule()

    private val mockDB = mockk<FirebaseFirestore>()
    private val mockAuth = mockk<FirebaseAuth>()
    private val mockUser = mockk<FirebaseUser>()
    private val mockResultCollection = mockk<CollectionReference>()

    private val provider: FirestoreProvider = FirestoreProvider(mockAuth, mockDB)

    private val mockDocument1 = mockk<DocumentSnapshot>()
    private val mockDocument2 = mockk<DocumentSnapshot>()
    private val mockDocument3 = mockk<DocumentSnapshot>()

    private val testNotes = listOf(Note("1"), Note("2"), Note("3"))

    @Before
    fun setup() {
        clearAllMocks()
        every { mockUser.uid } returns ""
        every { mockAuth.currentUser } returns mockUser
        every { mockDB.collection(any()).document(any()).collection(any()) } returns mockResultCollection

        every { mockDocument1.toObject(Note::class.java) } returns testNotes[0]
        every { mockDocument2.toObject(Note::class.java) } returns testNotes[1]
        every { mockDocument3.toObject(Note::class.java) } returns testNotes[2]
    }

    @Test
    fun `should throw NoAuthException if not auth`() {
        var result: Any? = null
        every { mockAuth.currentUser } returns null
        provider.subscribeToAllNotes().observeForever {
            result = (it as? NoteResult.Error)?.error
        }
        assertTrue(result is NoAuthException)
    }

    @Test
    fun `saveNote calls set`() {
        val mockDocumentReference = mockk<DocumentReference>()
        every { mockResultCollection.document(testNotes[0].id) } returns  mockDocumentReference
        provider.saveNote(testNotes[0])
        verify (exactly = 1) { mockDocumentReference.set(testNotes[0])}
    }

    @Test
    fun `saveNote return success note`() {
        var result: Note? = null
        val mockDocumentReference = mockk<DocumentReference>()
        val slot = slot<OnSuccessListener<Void>>()

        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
        every { mockDocumentReference.set(testNotes[0]).addOnSuccessListener(capture(slot)) } returns mockk()

        provider.saveNote(testNotes[0]).observeForever {
            result = (it as? NoteResult.Success<Note>)?.data
        }

        slot.captured.onSuccess(null)
        assertEquals(testNotes[0],result)
    }

    @Test
    fun`subscribeToAllNotes returns notes`(){
        var result: List<Note>? = null
        val mockSnapshot = mockk<QuerySnapshot>()
        val slot = slot<EventListener<QuerySnapshot>>()
        every {mockSnapshot.documents} returns listOf(mockDocument1, mockDocument2, mockDocument3)
        every { mockResultCollection.orderBy(any() as String, any()).addSnapshotListener(capture(slot)) } returns mockk()

        provider.subscribeToAllNotes().observeForever{
            result = (it as? NoteResult.Success<List<Note>>)?.data
        }

        slot.captured.onEvent(mockSnapshot, null)
        assertEquals(testNotes, result)
    }

    @Test
    fun`subscribeToAllNotes returns error`(){
        var result: Throwable? = null
        val testError = mockk<FirebaseFirestoreException>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockResultCollection.orderBy(any() as String, any()).addSnapshotListener(capture(slot)) } returns mockk()

        provider.subscribeToAllNotes().observeForever{
            result = (it as? NoteResult.Error)?.error
        }

        slot.captured.onEvent(null, testError)
        assertEquals(testError, result)
    }

    //TODO написать 2 теста для delete

    @Test
    fun `delete Note calls error`() {
        var result: Throwable? = null
        val testError = java.lang.Exception()
        val mockDocumentReference = mockk<DocumentReference>()
        val slot = slot<OnFailureListener>()

        every { mockResultCollection.document(testNotes[0].id) } returns  mockDocumentReference
        every { mockDocumentReference.delete().addOnSuccessListener(any()).addOnFailureListener(capture(slot)) } returns mockk()

        provider.deleteNote(testNotes[0].id).observeForever{
            result = (it as? NoteResult.Error)?.error
        }

        slot.captured.onFailure(testError)
        assertEquals(testError, result)
    }

    @Test
    fun `deleteNote return success`() {
        var result: NoteResult? = null
        val mockDocumentReference = mockk<DocumentReference>()
        val slot = slot<OnSuccessListener<Void>>()

        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
        every { mockDocumentReference.delete().addOnSuccessListener(capture(slot)) } returns mockk()

        provider.deleteNote(testNotes[0].id).observeForever {
            result = it
        }

        slot.captured.onSuccess(null)
        assertTrue(result is NoteResult.Success<*>)
    }

    //TODO написать 2 теста для getNoteById

    @Test
    fun `getNoteByID calls error`() {
        var result: Throwable? = null
        val testError = java.lang.Exception()
        val mockDocumentReference = mockk<DocumentReference>()
        val slot = slot<OnFailureListener>()

        every { mockResultCollection.document(testNotes[0].id) } returns  mockDocumentReference
        every { mockDocumentReference.get().addOnSuccessListener(any()).addOnFailureListener(capture(slot)) } returns mockk()

        provider.getNoteById(testNotes[0].id).observeForever{
            result = (it as? NoteResult.Error)?.error
        }

        slot.captured.onFailure(testError)
        assertEquals(testError, result)
    }

    @Test
    fun `getNoteByID return success`() {
        var result: Note? = null
        val mockDocumentReference = mockk<DocumentReference>()
        val slot = slot<OnSuccessListener<DocumentSnapshot>>()

        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
        every { mockDocumentReference.get().addOnSuccessListener(capture(slot)) } returns mockk()

        provider.getNoteById(testNotes[0].id).observeForever {
            result = (it as? NoteResult.Success<Note>)?.data
        }

        slot.captured.onSuccess(mockDocument1)
        assertEquals(result, testNotes[0])
    }
}