package com.mycompany.plantcaresystem.controllers;

import com.mycompany.plantcaresystem.App;
import com.mycompany.plantcaresystem.dao.CareDAO;
import com.mycompany.plantcaresystem.dao.GrowthDAO;
import com.mycompany.plantcaresystem.dao.PlantDAO;
import com.mycompany.plantcaresystem.models.CareSchedule;
import com.mycompany.plantcaresystem.models.Plant;
import com.mycompany.plantcaresystem.models.User;
import com.mycompany.plantcaresystem.util.Session;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

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
    private Label imagePathLabel;
    @FXML
    private ImageView previewImage;
    private File selectedFile;

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
    private void chooseImage() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Pilih Foto Tanaman");
        // Filter agar cuma bisa pilih gambar
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File file = fc.showOpenDialog(null);
        if (file != null) {
            selectedFile = file;
            imagePathLabel.setText(file.getName());

            // Tampilkan preview
            Image img = new Image(file.toURI().toString());
            previewImage.setImage(img);
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
            String finalImagePath = "";

            if (selectedFile != null) {
                File folder = new File("user_images");
                if (!folder.exists()) {
                    folder.mkdir();
                }

                String ext = getFileExtension(selectedFile);
                String newFileName = "plant_" + selected.getId() + "_" + System.currentTimeMillis() + ext;
                File destFile = new File(folder, newFileName);

                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                finalImagePath = "user_images/" + newFileName;
            }

            GrowthDAO.add(selected.getId(), h, c, finalImagePath);

            showAlert("Laporan pertumbuhan disimpan!");
            heightField.clear();
            conditionField.clear();
            imagePathLabel.setText("Belum ada foto");
            previewImage.setImage(null);
            selectedFile = null;
        } catch (NumberFormatException e) {
            showAlert("Tinggi harus berupa angka!");
        } catch (Exception e) {
            showAlert("Error: " + e.getMessage());
        }
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf);
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
