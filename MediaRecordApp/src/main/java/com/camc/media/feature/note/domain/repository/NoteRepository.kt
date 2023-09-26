package com.camc.media.feature.note.domain.repository


import com.camc.media.data.model.Note

interface NoteRepository {

    fun getNotes(): kotlinx.coroutines.flow.Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}