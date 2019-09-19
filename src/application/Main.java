package application;

import fxml.InfoBox;
import fxml.MainDocumentController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.DataModel;

public class Main extends Application {

    private AnchorPane root;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader mainDocumentLoader = new FXMLLoader();
        mainDocumentLoader.setLocation(getClass().getResource("/fxml/mainDocument.fxml"));
        root = mainDocumentLoader.load();
        MainDocumentController mainDocumentController = mainDocumentLoader.getController();

        InfoBox infoBox = new InfoBox();

        infoBox.setVisible(false);
        root.getChildren().addAll(infoBox);

        DataModel model = new DataModel();
        mainDocumentController.initModel(model);
        model.setInfoBox(infoBox);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(w -> model.saveCurrentState());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
