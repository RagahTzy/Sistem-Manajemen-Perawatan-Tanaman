package com.mycompany.plantcaresystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        setRoot("login");
        primaryStage.setTitle("Plant Care System");
        primaryStage.show();
    }

    public static void setRoot(String fxmlName) throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/mycompany/plantcaresystem/" + fxmlName + ".fxml"));
        Parent root = loader.load(); // Load root dulu

        Scene scene = new Scene(root);

        // --- TAMBAHAN: Load CSS Cyberpunk ---
        String css = App.class.getResource("/com/mycompany/plantcaresystem/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        // ------------------------------------

        stage.setScene(scene);
    }
}
