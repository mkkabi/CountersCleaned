package fxml;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Counter;

/*
AnchorPane pane to edit Counter fields
 */
public class EditCounterPane extends AnchorPane {

    private static EditCounterPane instance;

    private TextField rateField, nameField, fileNameField, serialNumberField;
    private Label rateLabel, nameLabel, fileNameLabel, serialNumberLabel;
    private Button submit;
    Counter counter;
    private double width = 250;
    private double height = 220;
    private double padding = 15;

    TranslateTransition translation;

    private EditCounterPane() {
        this.setMaxWidth(width);
        this.setPrefWidth(width);
        this.setPrefHeight(height);
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

    public void initBox(Counter counter) {
        initPane(counter);
        translation = TranslationController.translateObjTopDown(this, this.getWidth(), this.getHeight());

    }

    private void initPane(Counter counter) {
        this.counter = counter;
        rateField = new TextField(counter.getRate() + "");
        nameField = new TextField(counter.getName() + "");
        fileNameField = new TextField(counter.getFileName() + "");
        serialNumberField = new TextField(counter.getSerialNumber() + "");
        TextField[] textFields = {rateField, nameField, fileNameField, serialNumberField};

        rateLabel = new Label("Rate:");
        nameLabel = new Label("Name");
        fileNameLabel = new Label("File name");
        serialNumberLabel = new Label("Serial number");
        Label[] labels = {rateLabel, nameLabel, fileNameLabel, serialNumberLabel};

        seUpPane(textFields, labels, this);
    }

    private void seUpPane(TextField[] textFields, Label[] labels, AnchorPane pane) {
        for (int i = 0; i < textFields.length; i++) {
            pane.setLeftAnchor(textFields[i], pane.getPrefWidth() * 0.4);
            pane.setRightAnchor(textFields[i], padding);

            pane.setLeftAnchor(labels[i], padding);
            pane.setRightAnchor(labels[i], pane.getPrefWidth() * 0.38);

            textFields[i].setLayoutY(40 * i + textFields[i].getPrefHeight() + padding);
            labels[i].setLayoutY(40 * i + labels[i].getPrefHeight() + padding);

            pane.getChildren().addAll(textFields[i], labels[i]);
        }
        ImageView close = new ImageView("/resources/images/close.png");
        close.setFitHeight(10);
        close.setFitWidth(10);
        submit = new Button("Su.bmit");
        submit.setOnAction(t -> submitData());
        submit.setPrefWidth(120);
        submit.setAlignment(Pos.CENTER);
        pane.setBottomAnchor(submit, padding);
        pane.setLeftAnchor(submit, padding);
        pane.setRightAnchor(submit, padding);
        pane.getChildren().addAll(submit, close);

    }

    private void submitData() {
        // rateField, nameField, fileNameField, serialNumberField
        double rate = Double.parseDouble(rateField.getText());
        String name = nameField.getText();
        String fileName = fileNameField.getText();
        String serialNumber = serialNumberField.getText();
        
        counter.setRate(rate);
        counter.setName(name);
        counter.setFileName(fileName);
        counter.setSerialNumber(serialNumber);
        
        this.setVisible(false);
    }
}
