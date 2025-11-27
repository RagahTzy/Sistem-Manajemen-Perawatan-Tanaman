package com.mycompany.plantcaresystem.controllers;

import com.mycompany.plantcaresystem.App;
import com.mycompany.plantcaresystem.dao.UserDAO;
import com.mycompany.plantcaresystem.models.User;
import com.mycompany.plantcaresystem.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void login() {
        try {
            String u = usernameField.getText().trim();
            String p = passwordField.getText().trim();
            if (u.isEmpty() || p.isEmpty()) {
                showAlert("Isi username dan password");
                return;
            }

            User user = UserDAO.login(u, p);
            if (user == null) {
                showAlert("Username atau password salah");
                return;
            }

            Session.setCurrentUser(user);
            if ("admin".equalsIgnoreCase(user.getRole())) {
                App.setRoot("adminDashboard");
            } else {
                App.setRoot("userDashboard");
            }
        } catch (Exception e) {
            showAlert("Login error: " + e.getMessage());
        }
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(msg);
        a.show();
    }
}
