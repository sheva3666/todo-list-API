package repository

import entities.User
import entities.UserDraft

class  InMemoryUserRepository: UserRepository {

    private val users = mutableListOf<User>(
        User("admin@mail.com", "admin", "admin@mail.com", "admin")
    )

    override fun getUser(id: String, password: String): User? {
        return users.firstOrNull {it.id == id && it.password == password}
    }

    override fun checkUser(id: String): User? {
        return users.firstOrNull {it.id == id}
    }

    override fun addUser(draft: UserDraft): User {
        val user = User(
            id = draft.email,
            name = draft.name,
            email = draft.email,
            password = draft.password
        )
        users.add(user)
        return user
    }
}