package com.mycompany.plantcaresystem.dao;

import com.mycompany.plantcaresystem.models.CareSchedule;
import com.mycompany.plantcaresystem.util.Database;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CareDAO {

    public static void addSchedule(int plantId, String action, int freq) throws SQLException {
        String sql = "INSERT INTO care_schedules (plant_id, action, frequency_days, next_due_date) VALUES (?, ?, ?, CURDATE())";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, plantId);
            ps.setString(2, action);
            ps.setInt(3, freq);
            ps.executeUpdate();
        }
    }

    public static List<CareSchedule> getSchedulesByUser(int userId) {
        List<CareSchedule> list = new ArrayList<>();
        String sql = "SELECT cs.* FROM care_schedules cs JOIN plants p ON cs.plant_id = p.id WHERE p.user_id = ?";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CareSchedule(
                        rs.getInt("id"), rs.getInt("plant_id"),
                        rs.getString("action"), rs.getInt("frequency_days"),
                        rs.getDate("next_due_date").toLocalDate()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void completeTask(CareSchedule cs) throws SQLException {
        Connection c = Database.getConnection();
        try {
            c.setAutoCommit(false);

            String logSql = "INSERT INTO care_logs (plant_id, action, date_performed, notes) VALUES (?, ?, CURDATE(), 'Selesai')";
            try (PreparedStatement ps = c.prepareStatement(logSql)) {
                ps.setInt(1, cs.getPlantId());
                ps.setString(2, cs.getAction());
                ps.executeUpdate();
            }

            String updateSql = "UPDATE care_schedules SET next_due_date = ? WHERE id = ?";
            try (PreparedStatement ps = c.prepareStatement(updateSql)) {
                LocalDate next = LocalDate.now().plusDays(cs.getFrequencyDays());
                ps.setDate(1, java.sql.Date.valueOf(next));
                ps.setInt(2, cs.getId());
                ps.executeUpdate();
            }

            c.commit();
        } catch (Exception e) {
            c.rollback();
            throw e;
        } finally {
            c.setAutoCommit(true);
            c.close();
        }
    }
}
