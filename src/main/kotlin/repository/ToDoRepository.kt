package repository

import entities.ToDo
import entities.ToDoDraft

interface ToDoRepository {
    fun getAllTodos(): List<ToDo>

    fun getToDo(id: Int, userId: String): ToDo?

    fun getToDosForUser(userId: String): List<ToDo>?

    fun addToDo(draft: ToDoDraft): ToDo

    fun removeToDo(id: Int): Boolean

    fun updateToDo(id: Int, draft: ToDoDraft): Boolean
}