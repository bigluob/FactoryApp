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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.camc.factory.data.model.entity.RecordCategory
import com.camc.factory.ui.feature.viewmodel.CategoryViewModel

@Composable
fun TodoAddEditScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    // 用于保存用户输入的文本字段的状态
    val categoryName = remember { mutableStateOf("") }

    Scaffold(
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
        }, content = {
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
                                modifier = Modifier.weight(3f)
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
                                    value = viewModel.description,
                                    onValueChange = { newValue ->
                                        viewModel.description = newValue
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
                                    if (viewModel.name.isNotBlank() && viewModel.description.isNotBlank() && viewModel.demandRecords > 0) {
                                        val category = RecordCategory(
                                            name = viewModel.name,
                                            description = viewModel.description,
                                            demandRecords = viewModel.demandRecords
                                        )
                                        viewModel.addCategory(category)
                                        navController.popBackStack()
                                    } else {
                                        // Show error message indicating missing fields
                                        // You can display a Snackbar or any other appropriate error handling mechanism
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