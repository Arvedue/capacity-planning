package com.example.capacityplanning.controller;

import com.example.capacityplanning.MainApp;
import com.example.capacityplanning.model.Machine;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Map;

public class CapacityController {
    @FXML
    public TableView<Machine> tableViewMachines;
    @FXML
    public TableColumn<Machine, String> tableColumnMachine;
    @FXML
    public TableColumn<Machine, Integer> tableColumnNumber;
    @FXML
    public TableColumn<Machine, Integer> tableColumnHours;
    @FXML
    public TableView<Machine> tableViewCapacity;
    @FXML
    public TableColumn<Machine, String> tableColumnCapMachines;
    @FXML
    public TableColumn<Machine, Integer> tableColumnSupply;
    @FXML
    public TableColumn<Machine, Integer> tableColumnDemand;

    private ObservableList<Machine> machineData;
    private Map<String, Integer> capacitySupplyData;
    private Map<String, Integer> capacityDemandData;

    public void setUpData(MainApp mainApp) {
        machineData = mainApp.getMachineData();
        capacitySupplyData = mainApp.getCapacitySupplyData();
        capacityDemandData = mainApp.getCapacityDemandData();

        tableViewMachines.setItems(machineData);
        tableViewCapacity.setItems(machineData);
    }

    @FXML
    private void initialize() {
        tableColumnMachine.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableColumnNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        tableColumnHours.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getHoursPerDay()).asObject());

        tableColumnCapMachines.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tableColumnSupply.setCellValueFactory(cellData -> new SimpleIntegerProperty(capacitySupplyData.get(cellData.getValue().getName())).asObject());
        tableColumnDemand.setCellValueFactory(cellData -> new SimpleIntegerProperty(capacityDemandData.get(cellData.getValue().getName())).asObject());
    }
}
