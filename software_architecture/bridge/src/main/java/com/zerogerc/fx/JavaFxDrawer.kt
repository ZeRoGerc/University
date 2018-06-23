package com.zerogerc.fx

import com.zerogerc.common.drawing.DrawingApi
import com.zerogerc.common.graph.getGraphByArg
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.stage.Stage

class JavaFxDrawer : Application(), DrawingApi {

    val root = Group()

    override fun start(primaryStage: Stage) {
        val graph = getGraphByArg(this, parameters.raw[0])
        graph.readGraph()
        graph.drawGraph()

        primaryStage.scene = Scene(root, drawingAreaWidth.toDouble(), drawingAreaHeight.toDouble(), Color.WHITE)
        primaryStage.show()
    }

    override val drawingAreaWidth = 600L

    override val drawingAreaHeight = 400L

    override fun drawCircle(centerX: Float, centerY: Float, radius: Float) {
        val circle = Circle(centerX.toDouble(), centerY.toDouble(), radius.toDouble())
        circle.fill = Color.BLACK

        root.children.add(circle)
    }

    override fun drawLine(x1: Float, y1: Float, x2: Float, y2: Float) {
        val line = Line(x1.toDouble(), y1.toDouble(), x2.toDouble(), y2.toDouble())
        line.stroke = Color.BLACK
        line.strokeWidth = 5.0

        root.children.add(line)
    }
}