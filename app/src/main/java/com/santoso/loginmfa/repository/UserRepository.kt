package com.santoso.loginmfa.repository

import com.santoso.loginmfa.data.local.UserDao
import com.santoso.loginmfa.data.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(user: User) = userDao.insert(user)
    suspend fun getUser(username: String) = userDao.getUserByUsername(username)
}