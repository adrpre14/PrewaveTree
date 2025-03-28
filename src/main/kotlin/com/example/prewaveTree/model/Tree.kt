package com.example.prewaveTree.model

data class Tree(val root: Int, var children: ArrayList<Tree> = arrayListOf()) {
    fun addChild(child: Tree) {
        children.add(child)
    }
}