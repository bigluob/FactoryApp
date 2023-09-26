package com.camc.factory.data.local.datasource

import com.camc.factory.data.local.User
import com.camc.factory.data.local.UserStore
import com.camc.factory.data.local.mapper.UserMapper

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserDataSource(
    private val userStore: UserStore,
    private val mapper: UserMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    fun getUser(): Flow<User> = userStore.getUser().map { mapper.toDomain(it) }.flowOn(dispatcher)

    suspend fun updateUser(user: User) = withContext(dispatcher) {
        userStore.updateUser(mapper.toLocal(user))
    }
}
