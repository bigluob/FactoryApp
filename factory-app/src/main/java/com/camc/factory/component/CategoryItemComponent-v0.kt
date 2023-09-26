package com.camc.factory.component

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun CategoryItemComponent(
    categoryName: String,
    requiredCount: Int,
    recordCount: Int,
    isUploaded: Boolean,
    onCameraClick: () -> Unit,
    onUploadClick: () -> Unit
) {
    var imageCount by remember { mutableStateOf(0) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var photoPath by remember { mutableStateOf<String?>(null) }
    val contentResolver = LocalContext.current.contentResolver // 初始化contentResolver

    fun getOrCreateFolder(): File? {
        val folder = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
            "Record"
        )
        if (!folder.exists()) {
            val created = folder.mkdirs()
            if (!created) {
                return null
            }
        }
        // 获取文件夹下包含categoryName的图片列表
        var imageList = folder.listFiles { file ->
            file.name.startsWith(categoryName)
        }?.toList() ?: emptyList()

        return folder
    }
    val folder = getOrCreateFolder()
    if (folder != null) {
        imageCount = folder.listFiles { file ->
            file.name.startsWith(categoryName)
        }?.size ?: 0
    }


    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isTaken ->
            if (isTaken) {
                // 在这里处理拍照成功的逻辑
                val bitmap = BitmapFactory.decodeFile(photoPath)
                imageBitmap = bitmap?.asImageBitmap()
                // 更新recordCount和文件夹下包含categoryName的图片数量
                // 更新 recordCount 的值

            } else {
                // 在这里处理拍照失败或取消的逻辑
            }
        }
    )
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    fun showToast(message: String) {
        coroutineScope.launch {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    var imageList by remember { mutableStateOf<List<File>>(emptyList()) }


    fun createSDCardFile(contentResolver: ContentResolver): Pair<Uri?, File?> {
        val folder = getOrCreateFolder()
        if (folder != null) {
            val fileName = "${categoryName}${imageCount}.jpg"
            val file = File(folder, fileName)
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.DATA, file.absolutePath)
            }
            val uri =
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            if (uri == null) {
                showToast("无法创建文件夹")
            } else {

            }
            return Pair(uri, file)
        } else {
            showToast("无法创建文件夹")
            return Pair(null, null)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 左侧展示名称、要求数量和记录数量是否已上传
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(6.dp)
            ) {
                Text(text = categoryName, fontWeight = FontWeight.Bold)
                Text(text = "要求数量: $requiredCount")
                Text(text = "记录数量: $imageCount")
                Text(text = "已上传: ${if (isUploaded) "是" else "否"}")
            }
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(2.dp)
            ) {
                IconButton(
                    onClick = {
                        val photoUri = createSDCardFile(contentResolver).first
                        if (photoUri != null) {
                            photoPath = photoUri.toString() // 保存照片路径
                            takePictureLauncher.launch(photoUri)
                        } else {
                            showToast("无法创建临时文件")
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = "拍照"
                    )
                }
            }

            Column(modifier = Modifier.weight(0.5f)) {
                IconButton(
                    onClick = { onUploadClick() },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudUpload,
                        contentDescription = "上传"
                    )
                }
            }
        }
    }
}
