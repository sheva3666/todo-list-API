package repository

import entities.Auth
import entities.AuthDraft

interface AuthRepository {
    fun getAuth(id: String): Auth?

    fun addAuth(draft: AuthDraft): Auth

    fun removeAuth(id: String): Boolean
}