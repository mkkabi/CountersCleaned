package controllers;

import fxml.ListController;
import java.util.Objects;
import javafx.scene.control.ListView;
import model.Counter;

public class AppController {

    private static ListController listController;
    private static ListView listView;
    private static Counter currentCounter;

    public static void setCurrent(ListController list) {
        listController = list;
    }

    public static void setCurrent(Counter c) {
        currentCounter = c;
    }

    public static void setCurrent(ListView listView) {
        listView = listView;
    }

    public static ListController getListController() {
        return listController;
    }

    public static ListView getListView() {
        ListView list = Objects.requireNonNull(listView);
        return list;
    }
}
