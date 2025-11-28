package com.mycompany.plantcaresystem.controllers;

import com.mycompany.plantcaresystem.App;
import com.mycompany.plantcaresystem.dao.CareDAO;
import com.mycompany.plantcaresystem.models.CareSchedule;
import com.mycompany.plantcaresystem.models.Plant;
import com.mycompany.plantcaresystem.util.Context;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class PlantScheduleController {

    @FXML
    private Label headerLabel;
    @FXML
    private TextField actionField;
    @FXML
    private TextField freqField;
    @FXML
    private ListView<CareSchedule> scheduleList;

    private Plant currentPlant;

    @FXML
    public void initialize() {
        currentPlant = Context.selectedPlant;
        if (currentPlant == null) {
            goBack();
            return;
        }
        headerLabel.setText("Jadwal Perawatan: " + currentPlant.getName());
        loadData();
    }

    private void loadData() {
        List<CareSchedule> list = CareDAO.getSchedulesByUser(currentPlant.getUserId());
        scheduleList.getItems().clear();
        for (CareSchedule cs : list) {
            if (cs.getPlantId() == currentPlant.getId()) {
                scheduleList.getItems().add(cs);
            }
        }
    }

    @FXML
    private void addSchedule() {
        try {
            String action = actionField.getText();
            int freq = Integer.parseInt(freqField.getText());

            CareDAO.addSchedule(currentPlant.getId(), action, freq);

            actionField.clear();
            freqField.clear();
            loadData();
            new Alert(Alert.AlertType.INFORMATION, "Jadwal berhasil ditambahkan").show();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Frekuensi harus angka (hari)").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Gagal: " + e.getMessage()).show();
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
