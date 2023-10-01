package com.camc.factory.common

sealed class AppRouter(val route: String) {
    object Category: AppRouter(route = "category")
    object Login: AppRouter(route = "login")
    object TakePhoto: AppRouter(route = "takePhoto")
    // 其他路由...
   object  CategoryAddEdit :  AppRouter(route = "categoryAddEdit")
   object  ImageFileUpload:   AppRouter(route = "imageUpload")
   object  ServerSettings :  AppRouter(route = "serverSettings")

}
object AppRouterParams {
    const val PAR_CATEGORY_ID = "categoryId"
    const val PAR_CATEGORY_TITLE = "CategoryTitle"
    // 其他参数...
}