package com.camc.media.feature.note.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.camc.media.compoent.MessageSnackbar
import com.camc.media.data.model.Note
import com.camc.media.feature.note.presentation.add_edit_note.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
) {
    // 从ViewModel中获取标题和内容状态
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val recordNumbState = viewModel.recordNumb.value
    // 用于控制异常消息的显示
    var showErrorSnackbar by remember { mutableStateOf(false) }
    // 创建一个可动画化的背景颜色对象
    val scaffoldState = rememberScaffoldState()
    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }
    val scope = rememberCoroutineScope()

    // 创建一个可动画化的偏移量状态
    var fabTranslationY by remember { mutableStateOf(0.dp) }

    // 获取布局根视图
    val rootView = LocalView.current

    // 添加窗口插入监听器
    ViewCompat.setOnApplyWindowInsetsListener(rootView) { _, insets ->
        val isKeyboardVisible = insets.isVisible(WindowInsetsCompat.Type.systemBars())
        // 根据键盘的可见性来设置按钮的偏移量
        fabTranslationY = if (isKeyboardVisible) (-56).dp else 0.dp
        insets
    }
    // 使用LaunchedEffect来收集ViewModel中的事件流
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    // 显示Snackbar消息
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    // 保存完成后返回上一页
                    navController.navigateUp()
                }
            }
        }
    }
    // 当键盘可见时，将按钮浮动在键盘之上

    Scaffold(
        floatingActionButton = {
            // 保存按钮
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                },
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier
                    .offset(y = fabTranslationY)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "保存分类")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(16.dp)
        ) {
            // 颜色选择区域
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 遍历颜色列表
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                // 点击颜色块时触发颜色动画和ViewModel事件
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 标题输入框
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .background(Color.Gray)
            )

            // 内容输入框
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                singleLine = true
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .background(Color.Gray)
            )
            // 显示记录数量输入框
            TransparentHintTextField(
                text = recordNumbState.text,
                hint = recordNumbState.hint,
                onValueChange = {
                    try {
                        viewModel.onEvent(AddEditNoteEvent.EnteredRecordNumb(it.toInt()))
                    } catch (e: NumberFormatException) {
                        // 处理输入非法字符的异常情况
                        showErrorSnackbar = true
                    }
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeRecordNumbFocus(it))
                },
                isHintVisible = recordNumbState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                singleLine = true
            )
            Spacer(
                modifier = Modifier
                    .height(16.dp)
                    .background(Color.Gray)
            )
            if (showErrorSnackbar) {
                MessageSnackbar(
                    message = "请输入整数，此处为需要记录的多媒体数量",
                    modifier = Modifier.padding(16.dp),
                    duration = SnackbarDuration.Long,
                    onDismiss = { showErrorSnackbar = false }
                )
            }
        }
    }
}