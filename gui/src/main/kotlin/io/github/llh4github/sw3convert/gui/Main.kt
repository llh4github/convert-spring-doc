package io.github.llh4github.sw3convert.gui

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

/**
 *
 *
 * Created At 2023/12/29 14:24
 * @author llh
 */
class Main : Application() {
    override fun start(primaryStage: Stage) {
        val root = BorderPane()
        val scene = Scene(root, 400.0, 400.0)
        primaryStage.scene = scene
        primaryStage.title="JavaFx Demo"
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(Main::class.java, *args)
}
