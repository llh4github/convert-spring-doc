package io.github.llh4github.sw3convert.gui

import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import org.kordamp.bootstrapfx.BootstrapFX
import org.kordamp.bootstrapfx.scene.layout.Panel

/**
 *
 *
 * Created At 2023/12/29 14:24
 * @author llh
 */
class Main : Application() {
    override fun start(primaryStage: Stage) {
        val panel = Panel("This is A title")
        panel.styleClass.add("panel-primary")
        val root = BorderPane()
        root.padding = Insets(20.0)
        val btn = Button("Hello Btn")
        btn.styleClass.setAll("btn", "btn-danger")
        root.center = btn
        panel.body = root
        val scene = Scene(panel, 400.0, 400.0)
        scene.stylesheets.add(BootstrapFX.bootstrapFXStylesheet())
        primaryStage.scene = scene
        primaryStage.title = "JavaFx Demo"
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(Main::class.java, *args)
}
