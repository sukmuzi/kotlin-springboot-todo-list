package com.example.todo.repository

import com.example.todo.database.Todo
import com.example.todo.database.TodoDataBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TodoRepositoryImpl : TodoRepository {

    @Autowired
    lateinit var todoDataBase: TodoDataBase

    override fun save(todo: Todo): Todo? {

        // 1. 해당 index 에 data 있으면
        return todo.index?.let { index ->
            // update
            findOne(index)?.apply {
                this.title = todo.title
                this.description = todo.description
                this.schedule = todo.schedule
                this.updatedAt = LocalDateTime.now()
            }
        }?: kotlin.run {
            // 2. 없으면 insert
            todo.apply {
                this.index = ++todoDataBase.index
                this.createdAt = LocalDateTime.now()
                this.updatedAt = LocalDateTime.now()
            }.run {
                todoDataBase.todoList.add(todo)
                this
            }
        }
    }

    override fun saveAll(todoList: MutableList<Todo>): Boolean {
        return try {
            todoList.forEach {
                save(it)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun delete(index: Int): Boolean {
        return findOne(index)?.let {
            todoDataBase.todoList.remove(it)
            true
        }?: kotlin.run {
            false
        }
    }

    override fun findOne(index: Int): Todo? {
        return todoDataBase.todoList.first { it.index == index }
    }

    override fun findAll(): MutableList<Todo> {
        return todoDataBase.todoList
    }
}