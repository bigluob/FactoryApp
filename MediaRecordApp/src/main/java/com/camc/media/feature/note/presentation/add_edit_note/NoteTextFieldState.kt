package com.camc.media.feature.note.presentation.add_edit_note

import com.google.android.material.textfield.TextInputLayout.BoxBackgroundMode

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
/*,
    val backgroundMode: BoxBackgroundMode*/

)
