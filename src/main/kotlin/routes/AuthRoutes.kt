package routes

import entities.AuthDraft
import entities.UserDraft
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import repository.AuthRepository

fun Route.authRoute(authRepository: AuthRepository) {

    get("auth/{id}") {
        val id = call.parameters["id"].toString()

        val todo = authRepository.getAuth(id)

        if (todo == null) {
            call.respond(
                HttpStatusCode.NotFound,
                "LOGOUT"
            )
        } else {
            call.respond(todo)
        }
    }

    delete("auth/{id}") {
        val authId = call.parameters["id"].toString()

        val removed = authRepository.removeAuth(authId)
        if (removed) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.NotFound,
                "found no todo with the id $authId")
        }
    }
}
