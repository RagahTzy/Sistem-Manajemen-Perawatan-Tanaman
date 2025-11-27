package com.mycompany.plantcaresystem.controllers;

import com.mycompany.plantcaresystem.App;
import com.mycompany.plantcaresystem.dao.PlantDAO;
import com.mycompany.plantcaresystem.util.FileHelper;
import com.mycompany.plantcaresystem.models.Plant;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.List;

public class AdminDashboardController {

    @FXML
    private void openPlantManagement() {
        try {
            App.setRoot("plantManagement");
        } catch (Exception e) {
            alert("Gagal buka halaman: " + e.getMessage());
        }
    }

    @FXML
    private void exportPlants() {
        try {
            List<Plant> all = PlantDAO.getAll();
            FileHelper.exportPlants(all, "plants_export.txt");
            alertInfo("Data diexport ke plants_export.txt");
        } catch (Exception e) {
            alert("Export gagal: " + e.getMessage());
        }
    }

    @FXML
    private void logout() {
        try {
            App.setRoot("login");
        } catch (Exception e) {
            alert("Logout error: " + e.getMessage());
        }
    }

    private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(msg);
        a.show();
    }

    private void alertInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(msg);
        a.show();
    }
}
