package fxml;

import appUtils.NIO;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class InfoBox extends AnchorPane {

    private static Label label;
    private static InfoBox instance;

    private InfoBox() {
        label = new Label(" ");
        this.getChildren().add(label);
        this.setMaxWidth(350);
        this.setPrefWidth(350);
        this.setId("infoBox");
        
        initBox();
    }
    
    public static InfoBox getInstance() {
        if (instance == null) {
            synchronized (InfoBox.class) {
                if (instance == null) {
                    instance = new InfoBox();
                }
            }
        }
        return instance;
    }

    private static void initBox() {
        label.setAlignment(Pos.CENTER);
        InfoBox.setLeftAnchor(label, 15.0);
        InfoBox.setRightAnchor(label, 15.0);
    }

    public void setText(String message) {
        label.setText(message);
        Logger.getGlobal().info(message);
    }
}
