package com.example.fitfolio.interfaces

import com.example.fitfolio.data.User
interface IUsersProvider {
    suspend fun getUser(userId: String): User?

    suspend fun addUser(userId: String, user: User): Boolean
}