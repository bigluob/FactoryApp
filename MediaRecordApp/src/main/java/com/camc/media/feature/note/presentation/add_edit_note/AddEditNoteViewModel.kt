package com.camc.media.feature.note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camc.media.data.model.InvalidNoteException
import com.camc.media.data.model.Note
import com.camc.media.feature.note.domain.use_case.note.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // 用于存储笔记标题的状态
    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hint = "输入标题..."
        )
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    // 用于存储笔记内容的状态
    private val _noteContent = mutableStateOf(
        NoteTextFieldState(
            hint = "输入产品编码"
        )
    )

    // 用于存储笔记内容的状态
    private val _recordNumb = mutableStateOf(
        NoteTextFieldState(
            hint = "输入需记录的多媒体数量"
        )
    )

    val noteContent: State<NoteTextFieldState> = _noteContent
    val recordNumb: State<NoteTextFieldState> = _recordNumb


    // 用于存储笔记颜色的状态
    private val _noteColor = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    // 用于触发 UI 事件的 Flow
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // 当前笔记的 ID
    private var currentNoteId: Int? = null

    init {
        // 从 SavedStateHandle 中获取传递的笔记 ID
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    // 通过笔记 ID 获取笔记信息
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.id
                        // 更新笔记标题的状态
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        /* _recordNumb.value = _recordNumb.value.copy(
                             text = note.recordNumb.toString(),
                             isHintVisible = false
                         )*/
                        // 更新笔记内容的状态
                        _noteContent.value = _noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _recordNumb.value = _noteContent.value.copy(
                            text = note.recordNumb.toString(),
                            isHintVisible = false
                        )
                        // 更新笔记颜色的状态
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    // 处理 AddEditNoteEvent 的方法
    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = _noteContent.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredRecordNumb -> {
                _recordNumb.value = _recordNumb.value.copy(
                    text = event.value.toString()
                )
            }

            is AddEditNoteEvent.ChangeRecordNumbFocus -> {
                _recordNumb.value = _recordNumb.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _recordNumb.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        // 保存笔记
                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                recordNumb = recordNumb.value.text.toInt(),
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        // 发送保存笔记的 UI 事件
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        // 发送显示 Snackbar 的 UI 事件
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }

        }
    }

    // 定义 UI 事件的 sealed class
    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}