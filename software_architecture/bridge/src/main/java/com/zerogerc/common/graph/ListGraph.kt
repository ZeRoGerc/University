package com.zerogerc.common.graph

import com.zerogerc.common.drawing.DrawingApi
import com.zerogerc.common.drawing.Point
import java.util.*

class ListGraph(
        drawingApi: DrawingApi
) : Graph(drawingApi) {

    val edges = mutableListOf<Edge>()

    override fun drawGraph() {
        val mainCenterX = (drawingApi.drawingAreaWidth / 2).toFloat()
        val mainCenterY = (drawingApi.drawingAreaHeight / 2).toFloat()
        val mainRadius = Math.min(drawingApi.drawingAreaHeight, drawingApi.drawingAreaWidth).toFloat() / 3

        val vertexes = calcVertexes(mainCenterX, mainCenterY, mainRadius)
        for ((_, vertex) in vertexes) {
            drawingApi.drawCircle(vertex.x, vertex.y, 10.0f)
        }

        for ((fromId, toId) in edges) {
            val from = vertexes[fromId]!!
            val to = vertexes[toId]!!
            drawingApi.drawLine(from.x, from.y, to.x, to.y)
        }
    }

    override fun readGraph() {
        with(Scanner(System.`in`)) {
            val n = nextInt()
            for (i in 1..n) {
                edges += Edge(nextInt(), nextInt())
            }
        }
    }

    private fun calcVertexes(x: Float, y: Float, r: Float): Map<Int, Vertex> {
        val vertexNumbers = mutableSetOf<Int>()
        for ((from, to) in this.edges) {
            vertexNumbers.add(from)
            vertexNumbers.add(to)
        }
        var p = Point(0.0f, -r)
        val step = 2 * Math.PI / vertexNumbers.size

        val result = mutableMapOf<Int, Vertex>()
        for (vertexNum in vertexNumbers) {
            result.put(vertexNum, Vertex(vertexNum, x + p.x, y + p.y))
            p = rotate(p, step)
        }
        return result
    }
}