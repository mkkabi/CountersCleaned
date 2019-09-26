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
import fxml.EditCounterPane;
import java.util.logging.Logger;

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

        InfoBox infoBox = InfoBox.getInstance();
        infoBox.setVisible(false);
        root.getChildren().addAll(infoBox);
        
        EditCounterPane editCounter = EditCounterPane.getInstance();
        editCounter.setVisible(false);
        root.getChildren().addAll(editCounter);
        Logger.getGlobal().info("initialized Edit CounterPane");

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
