package com.mycompany.plantcaresystem.models;

public class Plant extends BaseEntity {

    private final String name;
    private final int age;
    private final int userId;

    public Plant(int id, String name, int age, int userId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String getInfo() {
        return "Plant: " + name + " | Age: " + age + " weeks | OwnerId: " + userId;
    }

    @Override
    public String toString() {
        return name + " (" + age + " w)";
    }
}
