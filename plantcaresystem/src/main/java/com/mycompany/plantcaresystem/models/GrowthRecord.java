package com.mycompany.plantcaresystem.models;

import java.time.LocalDate;

public class GrowthRecord {

    private int id;
    private int plantId;
    private double height;
    private String condition;
    private LocalDate date;

    public GrowthRecord(int id, int plantId, double height, String condition, LocalDate date) {
        this.id = id;
        this.plantId = plantId;
        this.height = height;
        this.condition = condition;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getPlantId() {
        return plantId;
    }

    public double getHeight() {
        return height;
    }

    public String getCondition() {
        return condition;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return date + ": " + height + "cm - " + condition;
    }
}
