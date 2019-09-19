package fxml;

import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class TranslationController {

    private static final int MESSAGE_DURATION = 2500;

    public static TranslateTransition translateFromLeftBottom(Node node, double nodeWidth, double nodeHeight) {
        TranslateTransition tr = new TranslateTransition(Duration.millis(500), node);
        tr.setFromX(node.getLayoutX());
        tr.setToX(node.getLayoutX() + nodeWidth);
        tr.setFromY(node.getLayoutY());
        tr.setToY(node.getLayoutY() - nodeHeight);
        return tr;
    }

    public static TranslateTransition translateObjBottomUp(Node node, double nodeWidth, double nodeHeight) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), node);
        node.setLayoutX((node.getParent().getScene().getWidth() - nodeWidth) / 2);
        node.setVisible(true);
        tt.setFromY(node.getParent().getScene().getHeight());
        tt.setToY(node.getParent().getScene().getHeight() - (node.getParent().getScene().getHeight() / 4) + nodeHeight);
        tt.setRate(1);
        tt.play();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                node.setVisible(false);
                timer.cancel();
            }
        }, MESSAGE_DURATION);
        return tt;
    }

    public static TranslateTransition translateObjTopDown(Node node, double nodeWidth, double nodeHeight) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), node);
        node.setLayoutX((node.getParent().getScene().getWidth() - nodeWidth) / 2);
        node.setVisible(true);
        tt.setFromY(0);
        tt.setToY(node.getParent().getScene().getHeight() / 4);
        tt.setRate(1);
        tt.play();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                node.setVisible(false);
                timer.cancel();
            }
        }, MESSAGE_DURATION);
        return tt;
    }
}
