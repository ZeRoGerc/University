package com.zerogerc

import com.zerogerc.awt.AwtDrawer
import com.zerogerc.common.graph.getGraphByArg
import com.zerogerc.fx.JavaFxDrawer
import javafx.application.Application

class Main {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            when (args[0]) {
                "awt" -> {
                    val drawer = AwtDrawer()
                    val graph = getGraphByArg(drawer, args[1])
                    graph.readGraph()
                    graph.drawGraph()

                    drawer.showGraph()
                }
                "fx" -> {
                    Application.launch(JavaFxDrawer::class.java, args[1])
                }
                else -> throw IllegalArgumentException("Drawer must be either awt or fx")
            }
        }
    }
}