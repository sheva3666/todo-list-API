package com.example.plugins

import entities.AuthDraft
import entities.ToDoDraft
import entities.UserDraft
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import repository.*

fun Application.configureRouting() {

    val repository: ToDoRepository = InMemoryToDoRepository()
    val userRepository: UserRepository = InMemoryUserRepository()
    val authRepository: AuthRepository = InMemoryAuthRepository()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        route("/todos") {
            get {
                if (repository.getAllTodos().isNotEmpty()) {
                    call.respond(repository.getAllTodos())
                } else {
                    call.respondText("No todos created yet")
                }
            }
            get("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()

                if (id == null) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        "id parameter has to be a number"
                    )
                    return@get
                }

                val todo = repository.getToDo(id)

                if (todo == null) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        "Found no todo for the provided id $id"
                    )
                } else {
                    call.respond(todo)
                }
            }
            post {
                val todoDraft = call.receive<ToDoDraft>()

                val todo = repository.addToDo(todoDraft)
                call.respond(todo)
            }
            put("/{id}") {
                val todoDraft = call.receive<ToDoDraft>()
                val todoId = call.parameters["id"]?.toIntOrNull()

                if(todoId == null) {
                    call.respond(HttpStatusCode.BadRequest,
                        "id parameter should be a number!")
                    return@put
                }

                val updated = repository.updateToDo(todoId, todoDraft)
                if (updated) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.NotFound,
                        "found no todo with the id $todoId")
                }
            }
            delete("/{id}") {
                val todoId = call.parameters["id"]?.toIntOrNull()

                if(todoId == null) {
                    call.respond(HttpStatusCode.BadRequest,
                        "id parameter should be a number!")
                    return@delete
                }

                val removed = repository.removeToDo(todoId)
                if (removed) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.NotFound,
                        "found no todo with the id $todoId")
                }
            }
        }


        route("/users") {
            get("/{id}/{password}") {
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
            post {
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

        route("/auth") {
            get("/{id}") {
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
            post {
                val authDraft = call.receive<AuthDraft>()

                val todo = authRepository.addAuth(authDraft)
                call.respond(todo)
            }

            delete("/{id}") {
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
    }
}
