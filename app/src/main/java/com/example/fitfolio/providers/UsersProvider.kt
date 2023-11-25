package com.example.fitfolio.providers

import com.example.fitfolio.data.User
import com.example.fitfolio.interfaces.IUsersProvider

class UsersProvider : IUsersProvider {
    override fun getUser(username: String, password: String): User {
        TODO("Not yet implemented")
    }

    override fun addUser(user: User): Boolean {
        TODO("Not yet implemented")
    }

}