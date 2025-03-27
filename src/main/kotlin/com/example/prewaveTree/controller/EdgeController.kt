package com.example.prewaveTree.controller

import com.example.prewaveTree.model.Edge
import com.example.prewaveTree.service.EdgeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/edges")
class EdgeController(private val edgeService: EdgeService) {
    @PostMapping
    fun createEdge(@RequestBody edge: Edge): Edge  {
        return edgeService.createEdge(edge)
    }
}