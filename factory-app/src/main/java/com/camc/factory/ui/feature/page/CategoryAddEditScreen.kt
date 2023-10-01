package com.camc.factory.ui.feature.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.camc.factory.data.model.entity.RecordCategory
import com.camc.factory.ui.feature.viewmodel.CategoryViewModel
import kotlinx.coroutines.launch

@Composable
fun CategoryAddEditScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    // 用于保存用户输入的文本字段的状态
    val categoryName = remember { mutableStateOf("") }
    val descriptionState = remember { mutableStateOf("多媒体记录") }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    // 获取要编辑的分类数据
    val editCategory = viewModel.getEditCategory()

    // 初始化分类数据
    if (editCategory != null) {
        categoryName.value = editCategory.name
        descriptionState.value = editCategory.description
        // 其他字段的初始化
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("添加新分类") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = Color.White,
                ) {
                    Box(modifier = Modifier.padding(bottom = 20.dp)) {
                        Row(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(3f).padding(end = 32.dp)
                            ) {
                                // Name input field
                                TextField(
                                    value = viewModel.name,
                                    onValueChange = { newValue ->
                                        viewModel.name = newValue
                                    },
                                    label = { Text("记录产品分类名") },
                                    modifier = Modifier.fillMaxWidth()
                                    // Other TextField attributes...
                                )

                                // Description input field
                                TextField(
                                    value = descriptionState.value,
                                    onValueChange = { newValue ->
                                        descriptionState.value = newValue
                                    },
                                    label = { Text("记录内容描述") },
                                    modifier = Modifier.fillMaxWidth()
                                    // Other TextField attributes...
                                )

                                // Demand Records input field
                                TextField(
                                    value = viewModel.demandRecords.toString(),
                                    onValueChange = { newValue ->
                                        viewModel.demandRecords = newValue.toIntOrNull() ?: 0
                                    },
                                    label = { Text("需要记录的数据条数") },
                                    modifier = Modifier.fillMaxWidth()
                                    // Other TextField attributes...
                                )
                            }

                            FloatingActionButton(
                                onClick = {
                                    if (viewModel.name.isNotBlank() && descriptionState.value.isNotBlank() && viewModel.demandRecords > 0) {
                                        val category = RecordCategory(
                                            name = viewModel.name,
                                            description = descriptionState.value,
                                            demandRecords = viewModel.demandRecords
                                        )
                                        viewModel.addCategory(category)
                                        navController.popBackStack()
                                    } else {
                                        coroutineScope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("请填写完整的表单信息")
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(top = 16.dp, bottom = 16.dp, end = 16.dp)
                            ) {
                                Icon(Icons.Default.Save, contentDescription = "Save")
                            }
                        }
                    }
                }
            }
        }
    )
}