package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*

fun main() {
    embeddedServer(Netty, port = 3001, host = "localhost", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(CORS) {
    allowHost("*")
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Get)
    allowHeader(HttpHeaders.ContentType)
}
    configureSerialization()
    configureRouting()

}
