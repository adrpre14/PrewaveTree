package com.example.prewaveTree.repository

import com.example.prewaveTree.model.Edge
import com.example.prewaveTree.routines.references.returnChildren
import com.example.prewaveTree.tables.references.EDGE
import org.jooq.DSLContext
import org.jooq.exception.DataAccessException
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException

@Repository
class EdgeRepository(private val dsl: DSLContext) {
    fun createEdge(edge: Edge) {
        try {
            dsl.insertInto(EDGE)
                .set(EDGE.FROM_ID, edge.fromId)
                .set(EDGE.TO_ID, edge.toId)
                .execute()
        }
        catch (e: DuplicateKeyException) {
            throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Node ID ${edge.toId} already has a parent!")
        }
        catch (e: DataAccessException) {
            throw ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Invalid connection!" +
                        " Node ID ${edge.toId} is already a node in the tree structure where ${edge.fromId} is."
            )
        }
        catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Something went wrong!", e)
        }
    }

    fun deleteEdge(edge: Edge) {
        val result: Int
        try {
            result = dsl.deleteFrom(EDGE)
                .where(EDGE.FROM_ID.eq(edge.fromId))
                .and(EDGE.TO_ID.eq(edge.toId))
                .execute()
        }
        catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Something went wrong!", e)
        }
        if (result == 0) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Edge does not exist! Node ID ${edge.toId} is not a child of node ID ${edge.fromId}."
            )
        }
    }

    fun getChildrenFromParent(parentId: Int): List<Edge> {
        val children = returnChildren(dsl.configuration(), parentId)
            .into(Edge::class.java)
        if (children.isEmpty()) {
            val result = dsl.select(EDGE.FROM_ID)
                .from(EDGE)
                .where(EDGE.TO_ID.eq(parentId))
                .fetch()
            if (result.isEmpty()) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Node ID $parentId has no children!")
            }
        }
        return children
    }
}