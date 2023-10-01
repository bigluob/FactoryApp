package com.camc.factory.common

import android.content.Context
import android.os.Build
import android.os.Environment
import java.io.File

object Constants {


    //md5 加密1的结果为：c4ca4238a0b923820dcc509a6f75849b
   // const val BASE_URL = "http://192.168.18.20/appku-api/public/api/"
    const val LOGIN_URL ="http://82.157.66.177:8888/api/Login/Login/"

    const val SINGLE_FILE_URL ="http://82.157.66.177:8888/api/DocLib/SingleFileUpload"

    const val MULTI_FILE_URL ="http://82.157.66.177:8888/api/DocLib/MultiFileUpload"

    const val Token_key ="eyJhbGciOiJub25lIiwidHlwIjoiQmVhcmVyIn0.eyJwcmltYXJ5c2lkIjoi" +
            "MTU3NjA2MjY2Mzk2MjI3NTg0MCIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUv" +
            "MDUvaWRlbnRpdHkvY2xhaW1zL3NpZCI6IkEyMTFcXHd1eGwxIiwidW5pcXVlX25hbWUiOiLlkL" +
            "TmlrDlvZUiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkv" +
            "Y2xhaW1zL3VzZXJkYXRhIjoie1wiVUlEXCI6XCIxNTc2MDYyNjYzOTYyMjc1ODQwXCIsXCJVc2VySURc" +
            "IjpcIkEyMTFcXFxcd3V4bDFcIixcIlVzZXJOYW1lXCI6XCLlkLTmlrDlvZVcIixcIlRlbmFudElEXCI6bn" +
            "VsbCxcIlRlbmFudE5hbWVcIjpudWxsLFwiRGVwdElEXCI6XCIxNTc2MjIyNjIxMDA5NzY4NDQ4XCIsXC" +
            "JEZXB0TmFtZVwiOlwiMui9pumXtFwiLFwiR3JvdXBJRFwiOlwiXCIsXCJHcm91cE5hbWVcIjpcIlwiLFw" +
            "iR3JhZGVsZXZlbFwiOlwi57uE6ZW_XCIsXCJTZWN1cml0eU5hbWVcIjpcIuWFrOW8gFwiLFwiU2VjdXJp" +
            "dHlWYWx1ZVwiOjAsXCJSb2xlSWRzXCI6bnVsbCxcIlJvbGVzXCI6bnVsbCxcIlBlcm1pc3Npb25zXCI6b" +
            "nVsbH0iLCJBdWRpZW5jZSI6IkRpWGluLlBETSIsIklzc3VlciI6IkRpWGluLlBETSIsIm5iZiI6MTY5NDk1N" +
            "zMyOCwiZXhwIjoxNjk1MDAwNTI4LCJpYXQiOjE2OTQ5NTczMjgsImlzcyI6IkRpWGluLlBETSIsImF1ZCI6IkRpWGluLlBETSJ9."


    const val IMAGE_URL = "http://192.168.18.20/appku-api/public/storage/images/"

   // const val LOGIN_URL = "login"
    const val REGISTER_URL = "register"
    const val LOGOUT_URL = "logout"
    const val UPDATE_DATA = "updateData/{id}"

    const val WEBINAR_DATA_LIST = "webinar"
    const val NEWS_DATA_LIST = "news"
    const val KELAS_DATA_LIST = "kelas"
    const val KATEGORI_KELAS_DATA_LIST = "kelasCategory"

    const val WEBINAR_IMAGES = "webinarImages"
    const val KELAS_IMAGES = "kelasImages/"
    const val NEWS_IMAGES = "newsImages/"
}