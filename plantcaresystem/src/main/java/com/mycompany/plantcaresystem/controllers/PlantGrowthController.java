package com.mycompany.plantcaresystem.controllers;

import com.mycompany.plantcaresystem.App;
import com.mycompany.plantcaresystem.dao.GrowthDAO;
import com.mycompany.plantcaresystem.models.GrowthRecord;
import com.mycompany.plantcaresystem.models.Plant;
import com.mycompany.plantcaresystem.util.Context;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class PlantGrowthController {

    @FXML
    private Label headerLabel;
    @FXML
    private ListView<GrowthRecord> growthList;

    private Plant currentPlant;

    @FXML
    public void initialize() {
        currentPlant = Context.selectedPlant;
        if (currentPlant == null) {
            goBack();
            return;
        }
        headerLabel.setText("Laporan Pertumbuhan: " + currentPlant.getName());
        loadData();
    }

    private void loadData() {
        List<GrowthRecord> list = GrowthDAO.getByPlant(currentPlant.getId());
        growthList.getItems().setAll(list);

        if (list.isEmpty()) {
            growthList.setPlaceholder(new Label("Belum ada data pertumbuhan dari user."));
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
