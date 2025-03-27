package com.example.prewaveTree.common

import com.example.prewaveTree.controller.EdgeController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException

@RestControllerAdvice(assignableTypes = [EdgeController::class])
class EdgeExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = "Invalid properties for Edge. Please make sure fromId and toId are integers and greater than 0"
        )

        if (ex.message?.contains("GREATER_THAN_ZERO_CHECK") == true) {
            errorResponse.message = "fromId and toId must be greater than 0"
        } else if (ex.message?.contains("EQUALITY_CHECK") == true) {
            errorResponse.message = "fromId and toId must be different"
        }

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(ex: ResponseStatusException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = ex.statusCode.value(),
            error = HttpStatus.resolve(ex.statusCode.value())?.reasonPhrase ?: "Unknown",
            message = ex.reason ?: "Something went wrong!"
        )
        return ResponseEntity(errorResponse, ex.statusCode)
    }
}
