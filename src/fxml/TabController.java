package fxml;

import controller.DataController;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.DataModel;
import model.Household;
import model.AbstractCounter;
import model.CounterSolid;
import model.Counter;
import model.CounterType;

public class TabController<T extends Counter> extends Tab implements Initializable {

    @FXML
    private ListView countersList;
    @FXML
    private ComboBox counterTypes;
    @FXML
    private AnchorPane tabAnchorPane, addCounterPane;
    @FXML
    private Button calculateButton, addCounterButton;
    @FXML
    private Label tabName;
    @FXML
    private VBox contextMenu;
    @FXML
    TextField newCounterName, newCounterRate, currentDataTextField, previousDataTextField;
    private TranslateTransition tabContextMenuTranslation;
    private final Household house;
    private ObservableList<Counter> countersObservableList;
    private ListController<Counter> countersListController;
    private AbstractCounter currentCounter;
    TableViewCSVEditable tableCSV;

    @FXML
    private TableView<ObservableList<String>> tableView;

    public TabController(Household h) {
        house = h;
        countersObservableList = FXCollections.observableArrayList();
    }
    private DataModel model;

    public void initModel(DataModel model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

/////////////////////TABLEVIEW////////////////////////////////
        tableCSV = new TableViewCSVEditable(tableView, model);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//END TABLEVIEW////////////////////////////////

        tabName.setText(house.getName());

        tabContextMenuTranslation = TranslationController.translateFromLeftBottom(contextMenu, contextMenu.getPrefWidth(), contextMenu.getPrefHeight());

        countersListController = new ListController(countersList, house.getCounters(), model);
        countersListController.initList();
        application.NIO nio = new application.NIO();
        countersListController.setSelectionModel(t -> {
            if(t!=null){
            currentCounter = (AbstractCounter) t;
            previousDataTextField.setText(currentCounter.getPreviousData() + "");
            tableCSV.initialize(DataController.appHome + currentCounter.getFileName());
            System.out.println("Curret counter = "+currentCounter.getFileName());
            }else{
                System.out.println("counter = null");
            }
        });

        counterTypes.getItems().addAll("Water Counter", "Gas Counter", "Electricity Counter", "Other");

        addCounterButton.setOnMouseClicked(t -> {
            openAddCounterPane();
            closeContextMenu();
        });

    }

    @FXML
    public void deleteTab() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("do you really want to delete Household " + house.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            model.removeHousehold(house);
            closeTab();
            model.showInfoMessage("Household " + house.getName() + " removed");
        }
    }

    @FXML
    private void closeTab() {
        EventHandler<Event> handler = this.getOnClosed();
        if (null != handler) {
            handler.handle(null);
        } else {
            this.getTabPane().getTabs().remove(this);
        }
    }

    @FXML
    public void openEditCounterPopUp() {
        System.out.println(tabAnchorPane.getWidth());
        System.out.println(tabAnchorPane.getHeight());
    }

    @FXML
    private void openContextMenu() {
        tabContextMenuTranslation.setRate(1);
        tabContextMenuTranslation.play();
    }

    @FXML
    private void closeContextMenu() {
        tabContextMenuTranslation.setRate(-1);
        tabContextMenuTranslation.play();
    }

    @FXML
    private void openAddCounterPane() {
        addCounterPane.setLayoutX((tabAnchorPane.getWidth() - addCounterPane.getPrefWidth()) / 2);
        addCounterPane.setVisible(true);
    }

    @FXML
    private void closeAddCounterPane() {
        addCounterPane.setVisible(false);
    }

    @FXML
    private void addNewCounter() {
        Counter counter = new CounterSolid("counter");
        Double rate = null;

        if (newCounterName.getText() == null || newCounterName.getText().length() < 1) {
            model.showInfoMessage("enter Counter name");
            return;
        }
        if (newCounterRate.getText() == null || newCounterRate.getText().length() < 1) {
            model.showInfoMessage("enter Counter rate");
            return;
        }
        if (counterTypes.getSelectionModel().isEmpty()) {
            model.showInfoMessage("select Counter Type");
            return;
        }

        String name = Objects.requireNonNull(newCounterName.getText());
        try {
            rate = Objects.requireNonNull(Double.parseDouble(newCounterRate.getText()));
        } catch (NumberFormatException e) {
            model.showInfoMessage("only number in the rate field allowed");
        }

        String counterType = Objects.requireNonNull(counterTypes.getSelectionModel().getSelectedItem().toString());

        switch (counterType) {
            case "Water Counter":
                counter.setType(CounterType.WATER);
                break;
            case "Gas Counter":
                counter.setType(CounterType.GAS);
                break;
            case "Electricity Counter":
                counter.setType(CounterType.ELECTRICITY);
                break;
            case "Other":
                counter.setType(CounterType.CUSTOM);
                break;
            default:
                model.showInfoMessage("Select counter type!");
        }

        counter.setRate(rate);
        counter.setName(name);
        counter.setFileName(house.getName() + "_" + counter.getName() +"_"+counter.getSerialNumber()+ ".csv");
        
        application.NIO.createCounterFile(DataController.appHome + counter.getFileName());
        countersListController.addNewItem(counter);
        closeAddCounterPane();
        model.showInfoMessage("new Counter " + counter.getName());
    }

    @FXML
    private void calculate() {
        if (currentCounter == null) {
            model.showInfoMessage("Select counter first!");
            return;
        }
        Double current = Objects.requireNonNull(Double.parseDouble(currentDataTextField.getText()));
        Double previous = Objects.requireNonNull(Double.parseDouble(previousDataTextField.getText()));
        double difference = current - previous;
        double result = difference * currentCounter.getRate();
        currentCounter.setPreviousData(current);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(new Date());
        String textToSave = dateString + "," + previous + "," + current + "," + difference + "," + currentCounter.getRate() + "," + result;
        model.saveCalculation(DataController.appHome + currentCounter.getFileName(), textToSave);
        tableCSV.initialize(DataController.appHome + currentCounter.getFileName());
    }
}
