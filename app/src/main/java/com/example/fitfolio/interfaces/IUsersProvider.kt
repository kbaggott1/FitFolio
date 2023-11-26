package com.example.fitfolio.interfaces

import com.example.fitfolio.data.User
interface IUsersProvider {
    suspend fun getUser(): User?

    suspend fun addUser(user: User): Boolean
}