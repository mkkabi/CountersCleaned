package fxml;

import java.util.ArrayList;
import java.util.function.Consumer;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.AbstractCounter;
import model.DataModel;
import model.CounterSolid;
import model.Counter;
import model.GasCounter;
import model.WaterCounter;


public class ListController<T> {

    private final Image IMAGE_WATER = new Image("/resources/images/water.png");
    private final Image IMAGE_GAS = new Image("/resources/images/gas.png");
    private final Image IMAGE_ELECTRICITY = new Image("/resources/images/electricity.png");

    private ObservableList itemsObservableList;
    private final ListView listView;
    private ArrayList itemsArrayList;
    private final DataModel model;

    public ListController(ListView c, ArrayList arr, DataModel dm) {
        model = dm;
        itemsObservableList = FXCollections.observableArrayList();
        itemsArrayList = arr;
        listView = c;
    }

    public void initList() {
        itemsObservableList.addAll(itemsArrayList);
        listView.setItems(itemsObservableList);

        listView.setCellFactory(listView -> new ListCell<Counter>() {
            private TextField textField = new TextField();

            {
                textField.setOnAction(e -> {
                    commitEdit(getItem());
                });
                textField.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
                    if (e.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                });
            }

            private ImageView imageView = new ImageView();

            @Override
            public void updateItem(Counter item, boolean empty) {
                super.updateItem(item, empty);
                imageView.setFitHeight(30.0);
                imageView.setFitWidth(30.0);

                ContextMenu contextMenu = new ContextMenu();
                MenuItem deleteItem = new MenuItem();
                deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", item));
                deleteItem.setOnAction(event -> {
                    removeItem(item);
                });
                contextMenu.getItems().addAll(deleteItem);

                if (empty) {
                    setContextMenu(null);
                    setText(null);
                    setGraphic(null);
                } else {
                    if (item instanceof WaterCounter) {
                        imageView.setImage(IMAGE_WATER);
                    }
                    if (item instanceof GasCounter) {
                        imageView.setImage(IMAGE_GAS);
                    }
                    if (item instanceof CounterSolid) {
                        imageView.setImage(IMAGE_ELECTRICITY);
                    }

                    setContextMenu(contextMenu);

                    imageView.setId("counterImage");
                    setText(item.toString());
                    setGraphic(imageView);
                }
            }

            @Override
            public void startEdit() {
                super.startEdit();
                textField.setText(getItem().toString());
                setText(null);
                setGraphic(textField);
                textField.selectAll();
                textField.requestFocus();
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setText(getItem().getName());
                setGraphic(imageView);
            }

            @Override
            public void commitEdit(Counter t) {
                super.commitEdit(t);
                t.setName(textField.getText());
                setText(textField.getText());
                setGraphic(imageView);
            }

        });
    }

    public void removeItem(Counter t) {
        itemsObservableList.remove(t);
        itemsArrayList.remove(t);
        listView.refresh();
        model.showInfoMessage("removed from list " + t.toString());
    }

    public void addNewItem(Counter t) {
        itemsArrayList.add(t);
        itemsObservableList.add(t);
        listView.refresh();
    }

    public void setSelectionModel(Consumer c) {
        listView.getSelectionModel().selectedItemProperty().addListener((ov, old_val, new_val) -> {
            c.accept(new_val);
        });
    }
}
