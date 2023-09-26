package com.camc.media.feature.note.presentation.notes

import com.camc.media.data.model.Note
import com.camc.media.feature.note.domain.util.NoteOrder



sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()

    data class CameraNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
