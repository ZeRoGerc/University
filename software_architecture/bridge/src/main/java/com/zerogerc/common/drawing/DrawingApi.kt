package com.zerogerc.common.drawing

interface DrawingApi {

    val drawingAreaWidth: Long

    val drawingAreaHeight: Long

    fun drawCircle(centerX: Float, centerY: Float, radius: Float)

    fun drawLine(x1: Float, y1: Float, x2: Float, y2: Float)
}