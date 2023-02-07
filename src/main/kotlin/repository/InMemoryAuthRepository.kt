package repository

import entities.Auth
import entities.AuthDraft

class InMemoryAuthRepository: AuthRepository {

    private val authsUsers = mutableListOf<Auth>()

    override fun getAuth(id: String): Auth? {
        return authsUsers.firstOrNull {it.id == id }
    }

    override fun addAuth(draft: AuthDraft): Auth {
        val authUser = Auth(
            id = draft.id,
        )
        authsUsers.add(authUser)
        return authUser
    }

    override fun removeAuth(id: String): Boolean {
        return authsUsers.removeIf {it.id == id}
    }
}