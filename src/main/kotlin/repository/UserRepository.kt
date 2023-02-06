package repository

import entities.User
import entities.UserDraft

interface UserRepository {
    fun getUser(id: String, password: String): User?

    fun checkUser(id: String): User?

    fun addUser(draft: UserDraft): User
}