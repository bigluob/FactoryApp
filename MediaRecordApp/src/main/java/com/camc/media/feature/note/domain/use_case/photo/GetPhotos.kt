package com.camc.media.feature.note.domain.use_case.photo

import com.camc.media.data.model.InvalidNoteException

import com.camc.media.data.model.Note
import com.camc.media.feature.note.domain.repository.NoteRepository

class GetPhotos(  private val repository: NoteRepository
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
