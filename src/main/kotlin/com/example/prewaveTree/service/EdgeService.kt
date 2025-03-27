package com.example.prewaveTree.service

import com.example.prewaveTree.model.Edge
import com.example.prewaveTree.repository.EdgeRepository
import org.springframework.stereotype.Service

@Service
class EdgeService(private val edgeRepository: EdgeRepository) {
    fun createEdge(edge: Edge): Edge {
        edgeRepository.createEdge(edge)
        return edge
    }
}