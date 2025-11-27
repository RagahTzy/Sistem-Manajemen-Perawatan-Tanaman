package com.mycompany.plantcaresystem.controllers;

import com.mycompany.plantcaresystem.App;
import com.mycompany.plantcaresystem.dao.PlantDAO;
import com.mycompany.plantcaresystem.models.Plant;
import com.mycompany.plantcaresystem.models.User;
import com.mycompany.plantcaresystem.util.Session;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class UserDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private ListView<Plant> userPlantList;

    @FXML
    public void initialize() {
        User u = Session.getCurrentUser();
        if (u != null) {
            welcomeLabel.setText("Welcome, " + u.getUsername());

            // tampilkan tanaman miliknya
            List<Plant> plants = PlantDAO.getByUser(u.getId());
            userPlantList.getItems().setAll(plants);
        }
    }

    @FXML
    private void logout() {
        try {
            Session.clear();
            App.setRoot("login");
        } catch (Exception e) {
            System.out.println("Logout Error: " + e.getMessage());
        }
    }
}
