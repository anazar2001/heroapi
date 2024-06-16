package com.example.heroapi.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

@Service
public class DatabaseInfoService {

    private DataSource dataSource;

    public DatabaseInfoService(DataSource dataSource) {
        this.dataSource = dataSource;

        printDatabaseUrl();
    }

    public void printDatabaseUrl() {
        try (Connection connection = dataSource.getConnection()) {
            String url = connection.getMetaData().getURL();
            System.out.println("Database URL: " + url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
