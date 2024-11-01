package software.gui;

import javafx.scene.Scene;
import javafx.scene.control.TabPane;

public class ProgramScene extends Scene {
    protected TabPane tabPane;

    public ProgramScene(TabPane tabPane) {
        super(tabPane, 1200, 800);
        this.tabPane = tabPane;
        this.tabPane.setPrefHeight(800);
        this.tabPane.setPrefWidth(1200);
        this.tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }
}
