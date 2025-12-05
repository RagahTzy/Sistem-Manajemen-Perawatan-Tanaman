package com.mycompany.plantcaresystem.dao;

import com.mycompany.plantcaresystem.models.CareLog;
import com.mycompany.plantcaresystem.models.CareSchedule;
import com.mycompany.plantcaresystem.util.Database;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CareDAO {

    public static void addSchedule(int plantId, String action, int freq) throws SQLException {
        String sql = "INSERT INTO care_schedules (plant_id, action, frequency_days, next_due_date) VALUES (?, ?, ?, DATE('now'))";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, plantId);
            ps.setString(2, action);
            ps.setInt(3, freq);
            ps.executeUpdate();
        }
    }

    public static List<CareSchedule> getSchedulesByUser(int userId) {
        List<CareSchedule> list = new ArrayList<>();
        String sql = "SELECT cs.* FROM care_schedules cs JOIN plants p ON cs.plant_id = p.plant_id WHERE p.user_id = ?";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CareSchedule(
                        rs.getInt("schedule_id"),
                        rs.getInt("plant_id"),
                        rs.getString("action"),
                        rs.getInt("frequency_days"),
                        LocalDate.parse(rs.getString("next_due_date"))
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

            String logSql = "INSERT INTO care_logs (plant_id, action, date_performed, notes) VALUES (?, ?, DATE('now'), 'Selesai')";
            try (PreparedStatement ps = c.prepareStatement(logSql)) {
                ps.setInt(1, cs.getPlantId());
                ps.setString(2, cs.getAction());
                ps.executeUpdate();
            }

            String updateSql = "UPDATE care_schedules SET next_due_date = ? WHERE schedule_id = ?";
            try (PreparedStatement ps = c.prepareStatement(updateSql)) {
                LocalDate next = LocalDate.now().plusDays(cs.getFrequencyDays());
                ps.setString(1, next.toString());
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

    public static List<CareLog> getLogsByPlant(int plantId) {
        List<CareLog> list = new ArrayList<>();
        String sql = "SELECT * FROM care_logs WHERE plant_id = ? ORDER BY date_performed DESC";

        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, plantId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CareLog(
                        rs.getInt("logs_id"),
                        rs.getInt("plant_id"),
                        rs.getString("action"),
                        LocalDate.parse(rs.getString("date_performed"))
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
