package com.example.capacityplanning.controller;

import com.example.capacityplanning.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class RootLayoutController {
    private MainApp mainApp;
    private BorderPane rootLayout;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.rootLayout = mainApp.getRootLayout();
    }
    @FXML
    public void handleShowProducts(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("products-overview.fxml"));
            AnchorPane smsHandyOverview = (AnchorPane) loader.load();

            ProductsOverviewController controller = loader.getController();
            controller.setProductData(mainApp.getProductData());

            rootLayout.setCenter(smsHandyOverview);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleShowCapacity(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("capacity.fxml"));
            AnchorPane smsHandyOverview = (AnchorPane) loader.load();

            CapacityController controller = loader.getController();
            controller.setUpData(mainApp);

            rootLayout.setCenter(smsHandyOverview);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleShowDiagram(ActionEvent actionEvent) {
    }
}
