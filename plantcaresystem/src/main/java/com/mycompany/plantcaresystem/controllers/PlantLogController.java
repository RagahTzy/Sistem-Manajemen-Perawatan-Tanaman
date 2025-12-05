package com.mycompany.plantcaresystem.controllers;

import com.mycompany.plantcaresystem.App;
import com.mycompany.plantcaresystem.dao.CareDAO;
import com.mycompany.plantcaresystem.models.CareLog;
import com.mycompany.plantcaresystem.models.Plant;
import com.mycompany.plantcaresystem.util.Context;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class PlantLogController {

    @FXML
    private Label headerLabel;
    @FXML
    private ListView<CareLog> logList;

    private Plant currentPlant;

    @FXML
    public void initialize() {
        currentPlant = Context.selectedPlant;
        if (currentPlant == null) {
            goBack();
            return;
        }
        headerLabel.setText("Riwayat Perawatan: " + currentPlant.getName());
        loadData();
    }

    private void loadData() {
        List<CareLog> list = CareDAO.getLogsByPlant(currentPlant.getId());
        logList.getItems().setAll(list);

        if (list.isEmpty()) {
            logList.setPlaceholder(new Label("Belum ada riwayat perawatan (User malas/belum update)."));
        }
    }

    @FXML
    private void goBack() {
        try {
            App.setRoot("plantManagement");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
