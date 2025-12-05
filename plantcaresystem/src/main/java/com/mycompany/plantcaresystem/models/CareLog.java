package com.mycompany.plantcaresystem.models;

import java.time.LocalDate;

public class CareLog {

    private int id;
    private int plantId;
    private String action;
    private LocalDate date;

    public CareLog(int id, int plantId, String action, LocalDate date) {
        this.id = id;
        this.plantId = plantId;
        this.action = action;
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "✅ " + action + " - Dilakukan pada: " + date;
    }
}
