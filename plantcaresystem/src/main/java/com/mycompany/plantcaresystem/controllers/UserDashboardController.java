package com.mycompany.plantcaresystem.controllers;

import com.mycompany.plantcaresystem.App;
import com.mycompany.plantcaresystem.dao.CareDAO;
import com.mycompany.plantcaresystem.dao.GrowthDAO;
import com.mycompany.plantcaresystem.dao.PlantDAO;
import com.mycompany.plantcaresystem.models.CareSchedule;
import com.mycompany.plantcaresystem.models.Plant;
import com.mycompany.plantcaresystem.models.User;
import com.mycompany.plantcaresystem.util.Session;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UserDashboardController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private ListView<Plant> userPlantList;

    @FXML
    private ListView<CareSchedule> scheduleList;
    @FXML
    private TextField heightField;
    @FXML
    private TextField conditionField;

    @FXML
    public void initialize() {
        refreshData();
    }

    private void refreshData() {
        User u = Session.getCurrentUser();
        if (u != null) {
            if (welcomeLabel != null) {
                welcomeLabel.setText("Welcome, " + u.getUsername());
            }

            List<Plant> plants = PlantDAO.getByUser(u.getId());
            userPlantList.getItems().setAll(plants);

            List<CareSchedule> schedules = CareDAO.getSchedulesByUser(u.getId());
            scheduleList.getItems().setAll(schedules);
        }
    }

    @FXML
    private void saveGrowthRecord() {
        Plant selected = userPlantList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Pilih tanaman dulu di list sebelah kiri!");
            return;
        }
        try {
            if (heightField.getText().isEmpty() || conditionField.getText().isEmpty()) {
                showAlert("Isi tinggi dan kondisi tanaman!");
                return;
            }

            double h = Double.parseDouble(heightField.getText());
            String c = conditionField.getText();
            GrowthDAO.add(selected.getId(), h, c);

            showAlert("Laporan pertumbuhan disimpan!");
            heightField.clear();
            conditionField.clear();
        } catch (NumberFormatException e) {
            showAlert("Tinggi harus berupa angka!");
        } catch (Exception e) {
            showAlert("Error: " + e.getMessage());
        }
    }

    @FXML
    private void completeTask() {
        CareSchedule sel = scheduleList.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert("Pilih jadwal perawatan yang sudah dikerjakan!");
            return;
        }
        try {
            CareDAO.completeTask(sel);
            sel.setDone(true);
            scheduleList.refresh();
            scheduleList.getSelectionModel().clearSelection();
        } catch (Exception e) {
            showAlert("Gagal: " + e.getMessage());
        }
    }

    @FXML
    private void logout() {
        try {
            Session.clear();
            App.setRoot("login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.show();
    }
}
