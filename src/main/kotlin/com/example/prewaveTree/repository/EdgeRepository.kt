package com.example.prewaveTree.repository

import com.example.prewaveTree.model.Edge
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
}