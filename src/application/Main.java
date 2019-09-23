package application;

import fxml.InfoBox;
import fxml.MainDocumentController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.DataModel;
import appUtils.AppLogger;
import appUtils.NIO;

public class Main extends Application {

    private AnchorPane root;

    @Override
    public void start(Stage primaryStage) throws Exception {
                
        //initializing loggers
        AppLogger.load();
        //initializing NIO class to create app home folder
        NIO.getInstance();

        FXMLLoader mainDocumentLoader = new FXMLLoader();
        mainDocumentLoader.setLocation(getClass().getResource("/fxml/mainDocument.fxml"));
        root = mainDocumentLoader.load();
        MainDocumentController mainDocumentController = mainDocumentLoader.getController();

        InfoBox infoBox = new InfoBox();

        infoBox.setVisible(false);
        root.getChildren().addAll(infoBox);

        DataModel model = DataModel.getInstance();
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
