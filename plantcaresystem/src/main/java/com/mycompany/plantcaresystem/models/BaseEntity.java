package com.mycompany.plantcaresystem.models;

public abstract class BaseEntity {

    protected int id;

    public int getId() {
        return id;
    }

    public abstract String getInfo(); // polymorphic method
}
