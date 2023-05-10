package com.example.app.service;

import com.example.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Service
public class AdminService implements IAdminService{
    @Autowired
    private Environment env;

    @Override
    public void dropAll() {
        try {

            Connection conn = DriverManager.getConnection(
                    env.getRequiredProperty("spring.datasource.url"),
                    env.getRequiredProperty("spring.datasource.username"),
                    env.getRequiredProperty("spring.datasource.password"));
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

    @Override
    public void repopulateDb() {
        try {

            Connection conn = DriverManager.getConnection(
                    env.getRequiredProperty("spring.datasource.url"),
                    env.getRequiredProperty("spring.datasource.username"),
                    env.getRequiredProperty("spring.datasource.password"));
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(env.getRequiredProperty("sql_script.full_path"))));
            scanner.useDelimiter(";");

            while(scanner.hasNext()) {
                String statementString = scanner.next();
                Statement statement = conn.createStatement();
                statement.execute(statementString);
            }
            conn.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
