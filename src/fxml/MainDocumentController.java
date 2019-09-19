package fxml;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.DataModel;

public class MainDocumentController {

	@FXML
	private Label dialogBoxLabel;
	@FXML
	private CheckBox waterCheckBox, gasCheckBox, electricityCheckBox;
	@FXML
	private Button closeAddHousePopUp;
	@FXML
	private AnchorPane addHousePopUp, mainPane;
	@FXML
	private TextField newHouseholdName;
	@FXML
	private TabPane tabPane;
	private DataModel model;

	public void initModel(DataModel model) {
		if (this.model != null) {
			System.out.println("Model is no NULL");
			throw new IllegalStateException("Model can only be initialized once");
		}

		this.model = model;
		model.restoreTabFromSave(tabPane);

		/*== Add Household popUP ==*/
		addHousePopUp.setVisible(false);
		closeAddHousePopUp.setOnMouseClicked(t -> addHousePopUp.setVisible(false));

	}

	@FXML
	public void saveHousehold() {
		if (newHouseholdName.getText().length() > 2) {
			model.addTab(tabPane, newHouseholdName.getText());
			addHousePopUp.setVisible(false);
//			if(waterCheckBox.isSelected())
			model.showInfoMessage("new household " + newHouseholdName.getText() + " created");
		} else {
			model.showInfoMessage("House name is too short");
		}
	}

	@FXML
	public void openHouseholdPopUp() {
		addHousePopUp.setLayoutX(mainPane.getWidth() / 2 - addHousePopUp.getWidth() / 2);
		addHousePopUp.setLayoutY((mainPane.getHeight() / 2 - addHousePopUp.getHeight() / 2));
		addHousePopUp.setVisible(true);
	}

	
}
