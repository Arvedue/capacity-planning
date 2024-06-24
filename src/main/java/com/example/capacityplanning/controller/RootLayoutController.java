package com.example.capacityplanning.controller;

import com.example.capacityplanning.MainApp;
import com.example.capacityplanning.model.Machine;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        BarChart<String, Number> barChart = createBarChartDiagram();

        Scene scene = new Scene(barChart, 800, 600);
        Stage dialogStage = new Stage();
        dialogStage.setScene(scene);
        dialogStage.setTitle("Capacity Visualization");
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.show();
    }

    private BarChart<String, Number> createBarChartDiagram() {
        Map<String, Integer> capacitySupply = mainApp.getCapacitySupplyData();
        Map<String, Integer> capacityDemand = mainApp.getCapacityDemandData();
        ObservableList<Machine> machines = mainApp.getMachineData();

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Tage");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Kapazität (Stunden)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Fertigungskapazität");

        Map<String, XYChart.Series<String, Number>> seriesMap = new HashMap<>();
        for (String machine : capacityDemand.keySet()) {
            seriesMap.put(machine, new XYChart.Series<>());
            seriesMap.get(machine).setName(machine);
        }

        Map<String, Integer> remainingDemand = new HashMap<>(capacityDemand);
        int month = 1;

        while (remainingDemand.values().stream().anyMatch(d -> d > 0)) {
            String monthLabel = "Tag " + month;

            for (String machine : capacityDemand.keySet()) {
                int monthlySupply = capacitySupply.get(machine);
                int monthlyDemand = Math.min(remainingDemand.get(machine), monthlySupply);

                // Add to the series
                seriesMap.get(machine).getData().add(new XYChart.Data<>(monthLabel, monthlyDemand));

                // Update remaining demand
                remainingDemand.put(machine, remainingDemand.get(machine) - monthlyDemand);
            }

            ++month;
        }

        for (Machine machine : machines) {
            barChart.getData().add(seriesMap.get(machine.getName()));
        }

        barChart.setCategoryGap(30);

        return barChart;
    }
}
