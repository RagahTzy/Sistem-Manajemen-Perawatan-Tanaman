package com.mycompany.plantcaresystem.dao;

import com.mycompany.plantcaresystem.models.GrowthRecord;
import com.mycompany.plantcaresystem.util.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrowthDAO {

    public static void add(int plantId, double height, String condition) throws SQLException {
        String sql = "INSERT INTO growth_history (plant_id, height, condition_text, record_date) VALUES (?, ?, ?, CURDATE())";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, plantId);
            ps.setDouble(2, height);
            ps.setString(3, condition);
            ps.executeUpdate();
        }
    }

    public static List<GrowthRecord> getByPlant(int plantId) {
        List<GrowthRecord> list = new ArrayList<>();
        String sql = "SELECT * FROM growth_history WHERE plant_id = ? ORDER BY record_date DESC";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, plantId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new GrowthRecord(
                        rs.getInt("id"), rs.getInt("plant_id"),
                        rs.getDouble("height"), rs.getString("condition_text"),
                        rs.getDate("record_date").toLocalDate()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
