package com.camc.factory.ui.feature.page

/*import androidx.lifecycle.observeAsState*/
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.camc.factory.common.AppRouter
import com.camc.factory.ui.feature.component.CategoryItemComponent
import com.camc.factory.ui.feature.viewmodel.CategoryViewModel


@Composable
fun CategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val categoryList = viewModel.categoryList.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.List,
                            contentDescription = "List",
                            tint = Color.Blue,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "分类清单",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(AppRouter.Login.route) }
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "登录"
                        )
                    }
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "设置"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(AppRouter.TodoAddEdit.route) },
                modifier = Modifier.padding(bottom = 120.dp, end = 70.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        // 显示分类列表数据
        LazyColumn {
            itemsIndexed(categoryList) { indx, category ->
                CategoryItemComponent(
                    categoryName = category.name,
                    requiredCount = category.demandRecords,
                    recordCount = category.recordedCount,
                    isUploaded = category.isUploaded,
                    onCameraClick = { navController.navigate("${AppRouter.TakePhoto.route}/${category.name}") },
                    onUploadClick = { navController.navigate("${AppRouter.TodoFileUpload.route}/${category.name}") }
                )
            }
        }
    }
}