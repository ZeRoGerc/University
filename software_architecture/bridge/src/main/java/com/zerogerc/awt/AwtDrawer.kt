package com.zerogerc.awt

import com.zerogerc.common.drawing.Circle
import com.zerogerc.common.drawing.DrawingApi
import com.zerogerc.common.drawing.Line
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D

class AwtDrawer : Frame(), DrawingApi {

    private val paddingHorizontal = 50

    private val paddingVertical = 50

    private val circles = mutableListOf<Circle>()

    private val lines = mutableListOf<Line>()

    override val drawingAreaWidth: Long
        get() = 600L

    override val drawingAreaHeight: Long
        get() = 400L

    override fun drawCircle(centerX: Float, centerY: Float, radius: Float) {
        circles += Circle(centerX, centerY, radius)
        repaint()
    }

    override fun drawLine(x1: Float, y1: Float, x2: Float, y2: Float) {
        lines += Line(x1, y1, x2, y2)
        repaint()
    }

    override fun paint(gInit: Graphics) {
        val g = gInit as Graphics2D
        setUp(g)

        for ((x1, y1, x2, y2) in lines) {
            g.draw(Line2D.Float(
                    x1 + paddingHorizontal,
                    y1 + paddingVertical,
                    x2 + paddingHorizontal,
                    y2 + paddingVertical
            ))
        }
        for ((x, y, r) in circles) {
            g.fill(Ellipse2D.Float(
                    x - r + paddingHorizontal,
                    y - r + paddingVertical,
                    2 * r,
                    2 * r
            ))
        }
    }

    fun showGraph() {
        this.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(we: WindowEvent) {
                System.exit(0)
            }
        })
        isVisible = true
        setSize(drawingAreaWidth.toInt() + paddingHorizontal, drawingAreaHeight.toInt() + paddingVertical)
    }

    private fun setUp(g: Graphics2D) {
        g.paint = Color.ORANGE
        g.stroke = BasicStroke(4.0f)
    }
}