package fxml;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class InfoBox extends AnchorPane {

	private static Label label;

	public InfoBox() {
		label = new Label(" ");
		this.getChildren().add(label);
		this.setMaxWidth(350);
		this.setPrefWidth(350);
		this.setId("infoBox");
		initBox();
	}

	private static void initBox() {
		label.setAlignment(Pos.CENTER);
		InfoBox.setLeftAnchor(label, 15.0);
		InfoBox.setRightAnchor(label, 15.0);
		
	}

	public void setText(String message) {
		label.setText(message);
	}

}
