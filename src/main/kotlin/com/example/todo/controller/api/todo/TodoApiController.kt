package com.example.todo.controller.api.todo

import com.example.todo.model.http.TodoDto
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/todo")
class TodoApiController {

    // Read
    @GetMapping(path = [""])
    fun read(@RequestParam(required = false) index: Int?) {

    }

    // Create
    @PostMapping(path = [""])
    fun create(@Valid @RequestBody todoDto: TodoDto) {

    }

    // Update
    @PutMapping(path = [""])
    fun update(@Valid @RequestBody todoDto: TodoDto) {

    }

    // Delete
    @DeleteMapping(path = ["/{index}"])
    fun delete(@PathVariable(name = "index") _index: Int) {

    }
}