package model;

import fxml.InfoBox;
import fxml.TabController;
import fxml.TranslationController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;

public class DataModel {

    private DataModel() {
    }

    private static volatile DataModel instance = null;

    public static DataModel getInstance() {
        if (instance == null) {
            synchronized (DataModel.class) {
                if (instance == null) {
                    instance = new DataModel();
                }
            }
        }
        return instance;
    }

    appUtils.Serializer ser = appUtils.Serializer.getInstance();
    InfoBox infoBox;
    TranslateTransition translation;

    public void addTab(TabPane tabPane, String s) {
        Household h = new Household(s);
        loadTab(tabPane, h);
    }

    public void loadTab(TabPane tabPane, Household hh) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/tab.fxml"));
        TabController tabController = new TabController(hh);
        tabController.initModel(this);
        loader.setController(tabController);
        tabController.setText(hh.getName());
        try {
            tabController.setContent(loader.load());
            tabPane.getTabs().add(tabController);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void restoreTabFromSave(TabPane tabPane) {
        ser.restoreObjects(Household.SAVE_FILE, t -> {
            Household house = (Household) t;
            loadTab(tabPane, (Household) t);
            Household.housholds.add((Household) t);
            Logger.getGlobal().log(Level.INFO, "loaded " + t.toString());
        });
    }

    public void removeHousehold(Household house) {
        Household.housholds.remove(house);
    }

    public void saveCurrentState() {
        ser.saveObjects(model.Household.SAVE_FILE, Household.housholds);
    }

    public void writeCalculationToFile() {

    }

    public void setInfoBox(InfoBox infoBox) {
        this.infoBox = infoBox;
    }

    public void showInfoMessage(String message) {
        infoBox.setText(message);
        translation = TranslationController.translateObjBottomUp(infoBox, infoBox.getWidth(), infoBox.getHeight());

    }

    public static void saveCalculation(String uri, String text) {
        appUtils.NIO.appendLine(uri, text);
    }
}
