package com.example.app.service;

import com.example.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

@Service
public class AdminService implements IAdminService{

    @Override
    public void dropAll() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/main", "postgres", "password");
            List<String> statementsStrings = Arrays.asList(
                    "TRUNCATE TABLE \"review\" RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE \"manufacturer\" RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE \"product\" RESTART IDENTITY CASCADE"
            );
            statementsStrings.forEach(statementString -> {
                try {
                    Statement statement = conn.createStatement();
                    statement.execute(statementString);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
