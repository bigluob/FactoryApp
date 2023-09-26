package com.camc.media.feature.note.presentation.notes

import com.camc.media.data.model.Note
import com.camc.media.feature.note.domain.util.NoteOrder
import com.camc.media.feature.note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
