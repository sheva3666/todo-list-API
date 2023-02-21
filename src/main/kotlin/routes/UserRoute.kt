package routes

import entities.AuthDraft
import entities.UserDraft
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import repository.AuthRepository
import repository.InMemoryAuthRepository
import repository.InMemoryUserRepository
import repository.UserRepository

fun Route.usersRoute(authRepository: AuthRepository) {
    val userRepository: UserRepository = InMemoryUserRepository()


        get("users/{id}/{password}") {
            val id = call.parameters["id"].toString()
            val password = call.parameters["password"].toString()


            val user = userRepository.getUser(id, password)

            if (user == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    "Email or password is incorrect"
                )
            } else {
                authRepository.addAuth(AuthDraft(user.id))
                call.respond(user)
            }
        }
        post ("users"){
            val userDraft = call.receive<UserDraft>()
            val userIsInList = userRepository.checkUser(userDraft.email)
            if (userIsInList == null) {
                val user = userRepository.addUser(userDraft)
                call.respond(
                    HttpStatusCode.OK,
                    "User with  email ${user.email} created"
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    "User with this email already registered"
                )
            }
        }
}