package com.example.prewaveTree.controller

import com.example.prewaveTree.model.Edge
import com.example.prewaveTree.model.Tree
import com.example.prewaveTree.service.EdgeService
import org.springframework.web.bind.annotation.*

@RestController
class EdgeController(private val edgeService: EdgeService) {
    @PostMapping("/edges")
    fun createEdge(@RequestBody edge: Edge): Edge  {
        return edgeService.createEdge(edge)
    }

    @DeleteMapping("/edges")
    fun deleteEdge(@RequestBody edge: Edge) {
        return edgeService.deleteEdge(edge)
    }

    @GetMapping("/tree/{id}")
    fun getTreeFromRoot(@PathVariable id: Int): Tree {
        return edgeService.getTreeFromRoot(id)
    }
}