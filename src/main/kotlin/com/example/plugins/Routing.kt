package com.example.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
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
