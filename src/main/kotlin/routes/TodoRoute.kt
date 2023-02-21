package routes

import entities.ToDoDraft
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import repository.InMemoryToDoRepository
import repository.ToDoRepository

fun Route.todosRoute() {

    val repository: ToDoRepository = InMemoryToDoRepository()

    get("todos/{userId}") {
        val userId = call.parameters["userId"].toString()

        val todos = repository.getToDosForUser(userId)

        if (todos == null) {
            call.respond(
                HttpStatusCode.NotFound,
                "Found no todo for $userId"
            )
        } else {
            call.respond(todos)
        }
    }

    get("todos/{userId}/{todoId}") {
        val todoId = call.parameters["todoId"]?.toIntOrNull()
        val userId = call.parameters["userId"].toString()


        if (todoId == null) {
            call.respond(
                HttpStatusCode.BadRequest,
                "id parameter of todo has to be a number"
            )
            return@get
        }

        val todo = repository.getToDo(todoId, userId)

        if (todo == null) {
            call.respond(
                HttpStatusCode.NotFound,
                "Found no todo for user $userId with provided id $todoId"
            )
        } else {
            call.respond(todo)
        }
    }
    post("todos") {
        val todoDraft = call.receive<ToDoDraft>()

        val todo = repository.addToDo(todoDraft)
        call.respond(todo)
    }
    put("todos/{id}") {
        val todoDraft = call.receive<ToDoDraft>()
        val todoId = call.parameters["id"]?.toIntOrNull()

        if(todoId == null) {
            call.respond(
                HttpStatusCode.BadRequest,
                "id parameter should be a number!")
            return@put
        }

        val updated = repository.updateToDo(todoId, todoDraft)
        if (updated) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(
                HttpStatusCode.NotFound,
                "found no todo with the id $todoId")
        }
    }
    delete("todos/{id}") {
        val todoId = call.parameters["id"]?.toIntOrNull()

        if(todoId == null) {
            call.respond(
                HttpStatusCode.BadRequest,
                "id parameter should be a number!")
            return@delete
        }

        val removed = repository.removeToDo(todoId)
        if (removed) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(
                HttpStatusCode.NotFound,
                "found no todo with the id $todoId")
        }
    }
}