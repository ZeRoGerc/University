package com.zerogerc.common.graph

import com.zerogerc.common.drawing.DrawingApi
import com.zerogerc.common.drawing.Point

abstract class Graph(val drawingApi: DrawingApi) {
    abstract fun drawGraph()

    abstract fun readGraph()

    companion object {
        fun rotate(p: Point, angle: Double): Point {
            val rx = p.x * Math.cos(angle) - p.y * Math.sin(angle)
            val ry = p.x * Math.sin(angle) + p.y * Math.cos(angle)
            return Point(rx.toFloat(), ry.toFloat())
        }
    }
}

fun getGraphByArg(api: DrawingApi, arg: String) : Graph {
    if ("list" == arg) {
        return ListGraph(api)
    } else if ("matrix" == arg) {
        return MatrixGraph(api)
    } else {
        throw IllegalArgumentException("Graph must be either list or matrix")
    }
}