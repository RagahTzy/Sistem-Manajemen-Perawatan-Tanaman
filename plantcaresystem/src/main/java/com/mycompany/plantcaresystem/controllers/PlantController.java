package com.mycompany.plantcaresystem.controllers;

import com.mycompany.plantcaresystem.App;
import com.mycompany.plantcaresystem.dao.PlantDAO;
import com.mycompany.plantcaresystem.models.Plant;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;
import com.mycompany.plantcaresystem.util.Context;

public class PlantController {

    @FXML
    private void viewGrowth() {
        Plant sel = listPlants.getSelectionModel().getSelectedItem();
        if (sel == null) {
            error("Pilih tanaman dulu untuk melihat laporan.");
            return;
        }
        try {
            Context.selectedPlant = sel; // Simpan tanaman ke Context
            App.setRoot("plantGrowth");  // Pindah ke halaman growth
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    @FXML
    private void manageSchedule() {
        Plant sel = listPlants.getSelectionModel().getSelectedItem();
        if (sel == null) {
            error("Pilih tanaman dulu untuk mengatur jadwal.");
            return;
        }
        try {
            Context.selectedPlant = sel; // Simpan tanaman ke Context
            App.setRoot("plantSchedule"); // Pindah ke halaman schedule
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField userIdField;
    @FXML
    private ListView<Plant> listPlants;

    @FXML
    public void initialize() {
        load();
    }

    private void load() {
        List<Plant> all = PlantDAO.getAll();
        listPlants.getItems().setAll(all);
    }

    @FXML
    private void addPlant() {
        try {
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            int uid = Integer.parseInt(userIdField.getText().trim());
            Plant p = new Plant(0, name, age, uid);
            PlantDAO.add(p);
            clearForm();
            load();
            info("Tanaman ditambahkan");
        } catch (NumberFormatException nfe) {
            error("Age dan UserID harus angka");
        } catch (Exception e) {
            error("Gagal tambah: " + e.getMessage());
        }
    }

    @FXML
    private void deleteSelected() {
        Plant sel = listPlants.getSelectionModel().getSelectedItem();
        if (sel == null) {
            error("Pilih tanaman dulu");
            return;
        }
        try {
            PlantDAO.delete(sel.getId());
            load();
            info("Terhapus");
        } catch (Exception e) {
            error("Gagal hapus: " + e.getMessage());
        }
    }

    private void clearForm() {
        nameField.clear();
        ageField.clear();
        userIdField.clear();
    }

    private void error(String m) {
        new Alert(Alert.AlertType.ERROR, m).show();
    }

    private void info(String m) {
        new Alert(Alert.AlertType.INFORMATION, m).show();
    }

    @FXML
    private void goBack() {
        try {
            App.setRoot("adminDashboard");
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
