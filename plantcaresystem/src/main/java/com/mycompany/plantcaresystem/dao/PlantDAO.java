package com.mycompany.plantcaresystem.dao;

import com.mycompany.plantcaresystem.models.Plant;
import com.mycompany.plantcaresystem.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlantDAO {

    public static void add(Plant p) throws SQLException {
        String sql = "INSERT INTO plants (name, age, user_id) VALUES (?, ?, ?)";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setInt(2, p.getAge());
            ps.setInt(3, p.getUserId());
            ps.executeUpdate();
        }
    }

    public static void update(Plant p) throws SQLException {
        String sql = "UPDATE plants SET name=?, age=?, user_id=? WHERE plant_id=?";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setInt(2, p.getAge());
            ps.setInt(3, p.getUserId());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        }
    }

    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM plants WHERE plant_id=?";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public static List<Plant> getAll() {
        List<Plant> list = new ArrayList<>();
        String sql = "SELECT * FROM plants";
        try (Connection c = Database.getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Plant(
                        rs.getInt("plant_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getInt("user_id")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Plant> getByUser(int userId) {
        List<Plant> list = new ArrayList<>();
        String sql = "SELECT * FROM plants WHERE user_id = ?";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Plant(
                            rs.getInt("plant_id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getInt("user_id")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
