package com.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
public class HospitalApplication {
    
    public static void main(String[] args) {
        tryCreateDatabase();
        SpringApplication.run(HospitalApplication.class, args);
    }
    
    private static void createDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres", "postgres", "admin")) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_database WHERE datname = 'hms'");
                if (!rs.next()) {
                    stmt.execute("CREATE DATABASE hms");
                    System.out.println("=== Database 'hms' created ===");
                }
            }
        } catch (Exception e) {
            System.out.println("DB check: " + e.getMessage());
        }
    }
    
    private static boolean databaseExists = false;
    private static void tryCreateDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres", "postgres", "admin")) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT 1 FROM pg_database WHERE datname = 'hms'");
                databaseExists = rs.next();
                if (!databaseExists) {
                    stmt.execute("CREATE DATABASE hms");
                    System.out.println("=== Database 'hms' created ===");
                    databaseExists = true;
                }
            }
        } catch (Exception e) {
            System.out.println("DB check: " + e.getMessage());
        }
    }
}