package com.example.fitfolio.interfaces

import com.example.fitfolio.data.User
interface IUsersProvider {
    fun getUser(): User?

    fun addUser(user: User): Boolean
}