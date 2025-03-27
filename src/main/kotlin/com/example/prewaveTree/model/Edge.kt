package com.example.prewaveTree.model

data class Edge (
    val fromId: Int,
    val toId: Int
) {
    init {
        require(fromId > 0 && toId > 0) { "GREATER_THAN_ZERO_CHECK" }
        require(fromId != toId) { "EQUALITY_CHECK" }
    }
}