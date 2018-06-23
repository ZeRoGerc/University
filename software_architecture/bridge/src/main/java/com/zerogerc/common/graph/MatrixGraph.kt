package com.zerogerc.common.graph

import com.zerogerc.common.drawing.DrawingApi
import com.zerogerc.common.drawing.Point
import java.util.*


class MatrixGraph(
        drawingApi: DrawingApi
) : Graph(drawingApi) {

    var matrix : Array<Array<Int>> = arrayOf()

    override fun drawGraph() {
        val mainCenterX = (drawingApi.drawingAreaWidth / 2).toFloat()
        val mainCenterY = (drawingApi.drawingAreaHeight / 2).toFloat()
        val mainRadius = Math.min(drawingApi.drawingAreaHeight, drawingApi.drawingAreaWidth).toFloat() / 3

        val vertexes = calcVertexes(mainCenterX, mainCenterY, mainRadius)
        for ((_, vertex) in vertexes) {
            drawingApi.drawCircle(vertex.x, vertex.y, 10.0f)
        }

        for (i in 0 .. matrix.size - 1) {
            for (j in 0 .. matrix[0].size - 1) {
                if (matrix[i][j] > 0) {
                    val from = vertexes[i]!!
                    val to = vertexes[j]!!
                    drawingApi.drawLine(from.x, from.y, to.x, to.y)
                }
            }
        }
    }

    override fun readGraph() {
        with(Scanner(System.`in`)) {
            val n = nextInt()

            val temp = mutableListOf<MutableList<Int>>()
            for (i in 0 .. n - 1) {
                temp += mutableListOf<Int>()
                for (j in 0 .. n - 1) {
                    temp[i].add(nextInt())
                }
            }
            matrix = temp.map { it.toTypedArray() }.toTypedArray()
        }
    }

    private fun calcVertexes(x: Float, y: Float, r: Float): Map<Int, Vertex> {
        var p = Point(0.0f, -r)
        val step = 2 * Math.PI / matrix.size

        val result = mutableMapOf<Int, Vertex>()
        for (vertexNum in 0 .. matrix.size - 1) {
            result.put(vertexNum, Vertex(vertexNum, x + p.x, y + p.y))
            p = rotate(p, step)
        }
        return result
    }

    companion object {
        fun rotate(p: Point, angle: Double): Point {
            val rx = p.x * Math.cos(angle) - p.y * Math.sin(angle)
            val ry = p.x * Math.sin(angle) + p.y * Math.cos(angle)
            return Point(rx.toFloat(), ry.toFloat())
        }

        @JvmStatic fun main(args: Array<String>) {
            val p = Point(100.0f, 0.0f)
            System.out.println(rotate(p, Math.PI))
        }
    }
}