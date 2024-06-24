package com.example.capacityplanning.controller;

import com.example.capacityplanning.MainApp;
import com.example.capacityplanning.model.Product;
import com.example.capacityplanning.model.Step;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ProductsOverviewController {
    @FXML
    public TableView<Product> tableViewProducts;
    @FXML
    public TableColumn<Product, String> tableColumnProducts;
    @FXML
    public TableView<Step> tableViewSteps;
    @FXML
    public TableColumn<Step, String> tableColumnMachines;
    @FXML
    public TableColumn<Step, Integer> tableColumnDurations;

    public void setProductData(ObservableList<Product> products) {
        tableViewProducts.setItems(products);
    }

    @FXML
    private void initialize() {
        tableColumnProducts.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableViewProducts.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(Product product) {
        tableColumnMachines.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMachine()));
        tableColumnDurations.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDuration()).asObject());

        ObservableList<Step> stepsData = FXCollections.observableArrayList();
        stepsData.addAll(product.getSteps());
        tableViewSteps.setItems(stepsData);
    }
}
