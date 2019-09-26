package fxml;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Counter;

public class EditCounterPane extends AnchorPane {

    private static EditCounterPane instance;
    private static Label label;

    private TextField rateField, nameField, fileNameField, serialNumberField;
    private Label rateLabel, nameLabel, fileNameLabel, serialNumberLabel;
//    TextField[] textFields;

    private Counter counter;
    TranslateTransition translation;

    private EditCounterPane() {
        
        rateField = new TextField();
        nameField = new TextField();
        fileNameField = new TextField();
        serialNumberField = new TextField();
        TextField[] textFields = {rateField, nameField, fileNameField, serialNumberField};
        
        setUpFields(textFields, this);

        rateLabel = new Label("rate");

        this.getChildren().add(rateLabel);
        this.setMaxWidth(350);
        this.setPrefWidth(350);
        this.setPrefHeight(200);
        this.setId("editCounter");
    }

    public static EditCounterPane getInstance() {
        if (instance == null) {
            synchronized (EditCounterPane.class) {
                if (instance == null) {
                    instance = new EditCounterPane();
                }
            }
        }
        return instance;
    }

    public void initBox() {
//        label.setAlignment(Pos.CENTER);
//        this.setLeftAnchor(label, 15.0);
//        this.setRightAnchor(label, 15.0);
        translation = TranslationController.translateObjTopDown(this, this.getWidth(), this.getHeight());

    }

    private void setUpFields(TextField[] textFields, AnchorPane pane) {

        for (int i = 0; i < textFields.length; i++) {
            System.out.println(i);
            pane.setLeftAnchor(textFields[i], 15.00);
            pane.setRightAnchor(textFields[i], 15.00);
            textFields[i].setLayoutY(40 * i + textFields[i].getPrefHeight());
            pane.getChildren().add(textFields[i]);

        }
    }
}
