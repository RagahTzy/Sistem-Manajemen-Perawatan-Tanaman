package com.mycompany.plantcaresystem.models;

import java.time.LocalDate;

public class CareSchedule {

    private int id;
    private int plantId;
    private String action;
    private int frequencyDays;
    private LocalDate nextDueDate;

    public CareSchedule(int id, int plantId, String action, int frequencyDays, LocalDate nextDueDate) {
        this.id = id;
        this.plantId = plantId;
        this.action = action;
        this.frequencyDays = frequencyDays;
        this.nextDueDate = nextDueDate;
    }

    public int getId() {
        return id;
    }

    public int getPlantId() {
        return plantId;
    }

    public String getAction() {
        return action;
    }

    public int getFrequencyDays() {
        return frequencyDays;
    }

    public LocalDate getNextDueDate() {
        return nextDueDate;
    }

    @Override
    public String toString() {
        return action + " (Tiap " + frequencyDays + " hari). Next: " + nextDueDate;
    }
}
