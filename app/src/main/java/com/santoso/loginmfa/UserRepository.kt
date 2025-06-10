package com.santoso.loginmfa

class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(user: User) = userDao.insert(user)
    suspend fun getUser(username: String) = userDao.getUserByUsername(username)
}