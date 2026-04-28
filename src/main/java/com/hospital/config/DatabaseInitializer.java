package com.hospital.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
@Order(-1)
public class DatabaseInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        String dbUrl = "jdbc:postgresql://localhost:5432/hms";
        String username = "postgres";
        String password = "admin";
        
        try {
            Class.forName("org.postgresql.Driver");
            
            try (Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres", username, password)) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_database WHERE datname = 'hms'");
                if (!rs.next()) {
                    stmt.execute("CREATE DATABASE hms");
                    System.out.println("=== Database 'hms' created ===");
                }
            }
        } catch (Exception e) {
            System.out.println("Database init: " + e.getMessage());
        }
    }
}