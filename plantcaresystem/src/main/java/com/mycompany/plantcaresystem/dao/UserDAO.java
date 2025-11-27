package com.mycompany.plantcaresystem.dao;

import com.mycompany.plantcaresystem.models.User;
import com.mycompany.plantcaresystem.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public static User login(String username, String password) {
        String sql = "SELECT id, username, role FROM users WHERE username = ? AND password = ?";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("role"));
            }
        } catch (Exception e) {
            System.out.println("UserDAO.login Error: " + e.getMessage());
        }
        return null;
    }
}
