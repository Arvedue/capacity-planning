package com.example.capacityplanning.controller;

import com.example.capacityplanning.MainApp;
import com.example.capacityplanning.model.Machine;
import com.example.capacityplanning.model.Product;
import com.example.capacityplanning.model.Step;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

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
    @FXML
    public ChoiceBox<Machine> choiceBoxMachine;
    @FXML
    public TextField textFieldDuration;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        tableViewProducts.setItems(mainApp.getProductData());
        choiceBoxMachine.setItems(mainApp.getMachineData());
    }

    @FXML
    private void initialize() {
        tableColumnProducts.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableViewProducts.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
    }

    private void showDetails(Product product) {
        choiceBoxMachine.setValue(null);
        textFieldDuration.setText(null);

        tableColumnMachines.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMachine()));
        tableColumnDurations.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDuration()).asObject());
        tableViewSteps.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showUpdateFields(newValue)
        );

        ObservableList<Step> stepsData = FXCollections.observableArrayList();
        stepsData.addAll(product.getSteps());
        tableViewSteps.setItems(stepsData);
    }

    private void showUpdateFields(Step step) {
        if (step == null) return;
        choiceBoxMachine.setValue(mainApp.getMachine(step.getMachine()));
        textFieldDuration.setText(String.valueOf(step.getDuration()));
    }

    private boolean isStepSelected() {
        return tableViewSteps.getSelectionModel().getSelectedItem() != null;
    }

    private boolean isProductSelected() {
        return tableViewProducts.getSelectionModel().getSelectedItem() != null;
    }

    private Alert getAlert(String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);

        return alert;
    }

    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();
        if (choiceBoxMachine.getValue() == null) {
            errorMessage.append("Please select a machine!\n");
        }
        if (textFieldDuration.getText() != null && !textFieldDuration.getText().isEmpty()) {
            if (!isInteger(textFieldDuration.getText(), 10))
                errorMessage.append("Duration must be numeric value!");
        } else {
            errorMessage.append("Please enter a valid duration!\n");
        }

        if (errorMessage.isEmpty()) return true;

        getAlert("Input is not valid!", errorMessage.toString()).showAndWait();
        return false;
    }

    private boolean isStepContainsMachine(String machine) {
        for (Step step : tableViewSteps.getItems()) {
            if (step.getMachine().equals(machine))
                return true;
        }

        return false;
    }

    public static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) return false;

        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i),radix) < 0) return false;
        }

        return true;
    }

    public void handleNewStep(ActionEvent actionEvent) {
        if (!isProductSelected()) {
            getAlert("Product is not selected!", "Please select the desired Product.").showAndWait();
            return;
        }
        if (tableViewSteps.getItems().size() >= 5) {
            getAlert("Steps limit exceeded.", "There can be maximum 5 steps!").showAndWait();
            return;
        }
        if (!isInputValid()) return;
        if (isStepContainsMachine(choiceBoxMachine.getValue().getName())) {
            getAlert("Machine can't be used.", "The chosen product already uses this machine!").showAndWait();
            return;
        }

        Step step = new Step();
        step.setMachine(choiceBoxMachine.getValue().getName());
        step.setDuration(Integer.parseInt(textFieldDuration.getText()));

        Product currProduct = tableViewProducts.getSelectionModel().getSelectedItem();
        currProduct.addStep(step);
        showDetails(currProduct);
    }

    public void handleUpdateStep(ActionEvent actionEvent) {
        if (!isProductSelected()) {
            getAlert("Product is not selected!", "Please select the desired Product.").showAndWait();
            return;
        }
        if (!isStepSelected()) {
            getAlert("Step is not selected!", "Please select the desired Step.").showAndWait();
            return;
        }

        if (!isInputValid()) return;

        Step step = tableViewSteps.getSelectionModel().getSelectedItem();
        step.setMachine(choiceBoxMachine.getValue().getName());
        step.setDuration(Integer.parseInt(textFieldDuration.getText()));

        showDetails(tableViewProducts.getSelectionModel().getSelectedItem());
    }

    public void handleDeleteStep(ActionEvent actionEvent) {
        if (!isProductSelected()) {
            getAlert("Product is not selected!", "Please select the desired Product.").showAndWait();
            return;
        }
        if (!isStepSelected()) {
            getAlert("Item is not selected!", "Please select the desired Step.").showAndWait();
            return;
        }
        if (tableViewSteps.getItems().size() <= 3) {
            getAlert("Steps limit.", "There must be minimum 5 steps!").showAndWait();
            return;
        }

        Product currProduct = tableViewProducts.getSelectionModel().getSelectedItem();
        currProduct.removeStep(tableViewSteps.getSelectionModel().getSelectedItem());
        showDetails(currProduct);
    }
}
