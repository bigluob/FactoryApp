package com.camc.media.feature.note.domain.use_case.note

import com.camc.media.feature.note.domain.repository.NoteRepository
import com.camc.media.data.model.InvalidNoteException
import com.camc.media.data.model.Note


class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("分类标题不能为空。")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("记录内容描述不能为空。")
        }
        repository.insertNote(note)
    }
}