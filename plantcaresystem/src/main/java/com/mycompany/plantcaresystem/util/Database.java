package com.mycompany.plantcaresystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // sesuaikan host, port, dan nama database jika perlu
    private static final String URL = "jdbc:mysql://localhost:3306/plantcare?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = ""; // isi password kalau ada

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
