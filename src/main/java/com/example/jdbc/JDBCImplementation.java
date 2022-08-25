package com.example.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCImplementation {
    public static void main(String[] args) {
        System.out.println("Oh hi!");
        DatabaseConnectionManager connectionManager=new DatabaseConnectionManager("localhost",
                "hplussport", "postgres","password");
        try {
            Connection connection=connectionManager.getConnection();
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT COUNT (*) FROM CUSTOMER");
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
