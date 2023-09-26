package com.camc.media.feature.note.domain.use_case.note

import com.camc.media.data.model.Note
import com.camc.media.feature.note.domain.repository.NoteRepository


class GetNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}