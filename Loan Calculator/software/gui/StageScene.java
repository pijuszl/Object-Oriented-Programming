package software.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class StageScene extends Scene {
	public GridPane grid;
	
	public StageScene(GridPane grid) {
		super(grid, 1200, 800);
		this.grid = grid;
		this.grid.setAlignment(Pos.TOP_LEFT);
		this.grid.setHgap(10);
		this.grid.setVgap(10);
	}
}