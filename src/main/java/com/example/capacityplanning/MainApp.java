package com.example.capacityplanning;

import com.example.capacityplanning.controller.RootLayoutController;
import com.example.capacityplanning.model.CapacityCalculator;
import com.example.capacityplanning.model.Machine;
import com.example.capacityplanning.model.Product;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Product> productData = FXCollections.observableArrayList();
    private ObservableList<Machine> machineData = FXCollections.observableArrayList();
    Map<String, Integer> capacitySupplyData;
    Map<String, Integer> capacityDemandData;
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setTitle("Capacity planning App");

        setUpData();
        initRootLayout();
    }
    public static void main(String[] args) {
        launch();
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("root-layout.fxml"));
            this.rootLayout = (BorderPane) loader.load();

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public ObservableList<Product> getProductData() {
        return productData;
    }

    public ObservableList<Machine> getMachineData() {
        return machineData;
    }

    public Map<String, Integer> getCapacitySupplyData() {
        return capacitySupplyData;
    }

    public Map<String, Integer> getCapacityDemandData() {
        return capacityDemandData;
    }

    private void setUpData() {
        CapacityCalculator capacityCalculator = new CapacityCalculator();
        productData.addAll(capacityCalculator.getProducts());
        machineData.addAll(capacityCalculator.getMachines());
        capacitySupplyData = capacityCalculator.getCapacitySupply();
        capacityDemandData = capacityCalculator.getCapacityDemand();
    }
}
