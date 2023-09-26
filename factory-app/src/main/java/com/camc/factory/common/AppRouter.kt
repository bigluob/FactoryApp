package com.camc.factory.common

sealed class AppRouter(val route: String) {
    object Category: AppRouter(route = "category")
    object Login: AppRouter(route = "login")
    object TakePhoto: AppRouter(route = "takePhoto")
    // 其他路由...
   object  TodoAddEdit :  AppRouter(route = "todoAddEdit")
   object  TodoFileUpload:   AppRouter(route = "todoFileUpload")
   object  ServerSettings :  AppRouter(route = "serverSettings")

}
object AppRouterParams {
    const val PAR_CATEGORY_ID = "categoryId"
    const val PAR_TODO_TITLE = "todoTitle"
    // 其他参数...
}