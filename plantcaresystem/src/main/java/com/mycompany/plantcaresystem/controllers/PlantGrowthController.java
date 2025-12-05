package com.mycompany.plantcaresystem.controllers;

import com.mycompany.plantcaresystem.App;
import com.mycompany.plantcaresystem.dao.GrowthDAO;
import com.mycompany.plantcaresystem.models.GrowthRecord;
import com.mycompany.plantcaresystem.models.Plant;
import com.mycompany.plantcaresystem.util.Context;
import java.io.File;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

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
        growthList.setCellFactory(param -> new ListCell<GrowthRecord>() {
            private final ImageView imageView = new ImageView();
            private final Label dateLabel = new Label();
            private final Label detailLabel = new Label();
            private final VBox textContainer = new VBox(5, dateLabel, detailLabel);
            private final HBox root = new HBox(15, textContainer, imageView);

            {
                imageView.setFitHeight(100);
                imageView.setPreserveRatio(true);

                textContainer.setAlignment(Pos.CENTER_LEFT);
                HBox.setHgrow(textContainer, Priority.ALWAYS);

                root.setAlignment(Pos.CENTER_LEFT);
                root.setPadding(new Insets(10));
                root.setStyle("-fx-border-color: #333; -fx-border-width: 0 0 1 0;");
            }

            @Override
            protected void updateItem(GrowthRecord item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    dateLabel.setText("📅 " + item.getDate());
                    dateLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #39ff14; -fx-font-size: 14px;");

                    detailLabel.setText("Tinggi: " + item.getHeight() + " cm\nKondisi: " + item.getCondition());
                    detailLabel.setStyle("-fx-text-fill: #e0e0e0;");

                    imageView.setImage(null);
                    if (item.getImagePath() != null && !item.getImagePath().isEmpty()) {
                        File imgFile = new File(item.getImagePath());
                        if (imgFile.exists()) {
                            imageView.setImage(new Image(imgFile.toURI().toString()));
                        }
                    }
                    setGraphic(root);
                }
            }
        });
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
