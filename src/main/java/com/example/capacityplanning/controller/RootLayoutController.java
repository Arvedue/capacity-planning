package com.example.capacityplanning.controller;

import com.example.capacityplanning.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class RootLayoutController {
    private MainApp mainApp;
    private BorderPane rootLayout;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.rootLayout = mainApp.getRootLayout();
    }
    @FXML
    public void handleShowProducts(ActionEvent actionEvent) {
    }
    @FXML
    public void handleShowMachines(ActionEvent actionEvent) {
    }
    @FXML
    public void handleShowCapacity(ActionEvent actionEvent) {
    }
    @FXML
    public void handleShowDiagram(ActionEvent actionEvent) {
    }
}
