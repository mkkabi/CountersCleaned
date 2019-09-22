package fxml;

import controller.AppLogger;
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
import model.DataModel;
import model.Counter;

public class ListController<T> {

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
                        AppLogger.getInstance().getLogger().info("edit canceled of ListController");
                        System.out.println("edit canceled of ListController");
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

                MenuItem sortAZ = new MenuItem();
                sortAZ.textProperty().bind(Bindings.format("Sort A-Z"));
                sortAZ.setOnAction(event -> {
                    sortAZ();
                });

                MenuItem sortZA = new MenuItem();
                sortZA.textProperty().bind(Bindings.format("Sort Z-A"));
                sortZA.setOnAction(event -> {
                    sortZA();
                });

                contextMenu.getItems().addAll(deleteItem, sortAZ, sortZA);

                if (empty) {
                    setContextMenu(null);
                    setText(null);
                    setGraphic(null);
                } else {
                    Image image = new Image(item.getImage());
                    imageView.setImage(image);
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
                AppLogger.getInstance().getLogger().info("ListController.commitEdit()");
                System.out.println("ListController.commitEdit()");
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
        AppLogger.getInstance().getLogger().info("removed from list " + t.toString());
        model.showInfoMessage("removed from list " + t.toString());
    }

    private void sortAZ() {
        itemsArrayList.sort((o1, o2) -> o1.toString().compareTo(o2.toString()));
        itemsObservableList.clear();
        initList();
    }

    private void sortZA() {
        itemsArrayList.sort((o1, o2) -> o2.toString().compareTo(o1.toString()));
        try {
            itemsObservableList.clear();
        } catch (Exception e) {
            AppLogger.getInstance().getLogger().info(e.toString());
            System.out.println(e.toString());
        }
        initList();
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
