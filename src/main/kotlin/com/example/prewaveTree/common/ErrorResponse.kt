package com.example.prewaveTree.common

data class ErrorResponse(
    val status: Int,
    val error: String,
    var message: String
)
