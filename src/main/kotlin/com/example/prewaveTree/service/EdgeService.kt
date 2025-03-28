package com.example.prewaveTree.service

import com.example.prewaveTree.model.Tree
import com.example.prewaveTree.model.Edge
import com.example.prewaveTree.repository.EdgeRepository
import org.springframework.stereotype.Service

@Service
class EdgeService(private val edgeRepository: EdgeRepository) {
    fun createEdge(edge: Edge): Edge {
        edgeRepository.createEdge(edge)
        return edge
    }

    fun deleteEdge(edge: Edge) {
        return edgeRepository.deleteEdge(edge)
    }

    fun getTreeFromRoot(id: Int): Tree {
        val children = edgeRepository.getChildrenFromParent(id)
        val groupedByRoot = children.groupBy { it.fromId }
        return createTreeRecursively(groupedByRoot, id)
    }

    private fun createTreeRecursively(grouped: Map<Int, List<Edge>>, root: Int): Tree {
        val tree = Tree(root)
        grouped[root]?.forEach { edge -> tree.addChild(createTreeRecursively(grouped, edge.toId)) }
        return tree
    }
}