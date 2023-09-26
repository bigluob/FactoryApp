package com.camc.media.feature.media.camera;

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.camc.media.feature.note.domain.use_case.note.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

}