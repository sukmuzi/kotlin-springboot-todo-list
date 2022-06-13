package com.example.todo.controller.api.todo

import com.example.todo.model.http.TodoDto
import com.example.todo.service.TodoService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Api(description = "일정관리")
@RestController
@RequestMapping("/api/todo")
class TodoApiController(
    val todoService: TodoService
) {

    /**
     *  @Api 클래스를 스웨거 리소스로 표시
     *  @ApiModel 스웨거 모델의 추가 정보 제공
     *  @ApiModelProperty 모델 속성의 데이터를 추가하고 조작
     *  @ApiOperation 특정 경로의 오퍼레이션이나 HTTP 메소드 설명
     *  @ApiParam 오퍼레이션 파라미터에 추가 메타 데이터 추가
     *  @ApiResponse 오퍼레이션 응답 예 설명
     *  @ApiResponses 여러 ApiResponse 객체 리스트를 허용하는 객체
     *  @Authorization 리소스나 오퍼레이션에 사용되는 권한 부여체계
     *  @AuthorizationScope Oauth2 인증 범위를 설명
     *  @ResponseHeader 응답의 일부로 제공될 수 있는 헤더를 표시
     */

    // Read
    @ApiOperation(value = "일정확인", notes = "일정 확인 GET API")
    @GetMapping(path = [""])
    fun read(
        @ApiParam(name = "index")
        @RequestParam(required = false) index: Int?
    ): ResponseEntity<Any?> {
        return index?.let {
            todoService.read(it)
        }?.let {
            return ResponseEntity.ok(it)
        }?: kotlin.run {
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, "/api/todo/all").build()
        }
    }

    // Read All
    @ApiOperation(value = "전체일정확인", notes = "전체 일정 확인 GET API")
    @GetMapping(path = ["/all"])
    fun readAll(): MutableList<TodoDto> {
        return todoService.readAll()
    }

    // Create
    @ApiOperation(value = "일정추가", notes = "일정 추가 POST API")
    @PostMapping(path = [""])
    fun create(@Valid @RequestBody todoDto: TodoDto): TodoDto? {
        return todoService.create(todoDto)
    }

    // Update
    @ApiOperation(value = "일정수정", notes = "일정 수정 PUT API")
    @PutMapping(path = [""]) // create = 201, update = 200
    fun update(@Valid @RequestBody todoDto: TodoDto): TodoDto? {
        return todoService.create(todoDto)
    }

    // Delete
    @ApiOperation(value = "일정삭제", notes = "일정 삭제 DELETE API")
    @DeleteMapping(path = ["/{index}"])
    fun delete(@PathVariable(name = "index") _index: Int): ResponseEntity<Any> {

        if (!todoService.delete(_index)) {
            return ResponseEntity.status(500).build()
        }

        return ResponseEntity.ok().build()
    }
}