package com.camc.factory.component


import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.camc.factory.utils.CardColorUtil
import com.camc.factory.utils.FileHelper
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CategoryItemComponentV2(
categoryName: String,
requiredCount: Int,
recordCount: Int,
isUploaded: Boolean,
onCameraClick: () -> Unit,
onUploadClick: () -> Unit
) {
    val context = LocalContext.current
    val folder = remember { FileHelper.getOrCreateFolder(context, categoryName) }

    var imageCount by remember { mutableStateOf(folder?.listFiles()?.size ?: 0) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val contentResolver = LocalContext.current.contentResolver
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val currentPhotoPath = remember { mutableStateOf<String?>(null) }
    // 根据屏幕大小调整图标的大小
    val iconSize = if (screenWidth > 600.dp) {
        72.dp
    } else {
        48.dp
    }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isTaken ->
            if (isTaken) {
                val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
                val files = FileHelper.listFilesByCategory(folder?.path ?: "", categoryName)
                val matchingFiles = files.filter { it.name.contains(categoryName) }
                val existingImageCount = matchingFiles.size
                val newPhotoFileName = "$categoryName$currentDate${existingImageCount + 1}.jpg"
                val newPhotoPath = File(folder, newPhotoFileName).path
                val newPhotoUri = FileProvider.getUriForFile(
                    context,
                    context.packageName + ".provider",
                    File(newPhotoPath)
                )
                val bitmap = BitmapFactory.decodeFile(newPhotoPath)
                imageBitmap = bitmap?.asImageBitmap()
                imageCount = existingImageCount + 1
            } else {
                // 处理拍照失败或取消的逻辑
            }
        }
    )

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        backgroundColor = CardColorUtil.getCardColor(imageCount, requiredCount)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(8.dp)
            ) {
                Text(text = categoryName, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "要求数量: $requiredCount")
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "记录数量: $imageCount")
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "已上传: ${if (isUploaded) "是" else "否"}")
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                IconButton(
                    onClick = {
                        val photoUri = FileHelper.createImageFile(
                            categoryName,
                            contentResolver
                        )
                        if (photoUri != null) {
                            currentPhotoPath.value = photoUri.path
                            takePictureLauncher.launch(photoUri)
                        } else {
                            showToast("无法创建临时文件")
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.PhotoCamera,
                        contentDescription = "拍照",
                        modifier = Modifier
                            .size(iconSize)
                            .padding(8.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                IconButton(
                    onClick = onUploadClick,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.FileUpload,
                        contentDescription = "文件上传",
                        modifier = Modifier
                            .size(iconSize)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}
