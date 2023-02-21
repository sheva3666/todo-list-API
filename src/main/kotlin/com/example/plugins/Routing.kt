package com.example.plugins

import entities.AuthDraft
import entities.UserDraft
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import repository.*
import routes.authRoute
import routes.todosRoute
import routes.usersRoute

fun Application.configureRouting() {

    val authRepository: AuthRepository = InMemoryAuthRepository()

    routing {
        todosRoute()
        usersRoute(authRepository)
        authRoute(authRepository)
    }
}
