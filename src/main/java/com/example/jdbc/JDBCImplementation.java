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
           /* Connection connection=connectionManager.getConnection();
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT COUNT (*) FROM CUSTOMER");
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1));
            }*/

            Connection connection=connectionManager.getConnection();
            CustomerDAO customerDAO=new CustomerDAO(connection);

            // Create a customer record
            /*Customer customer=new Customer();
            customer.setFirstName("Name1");
            customer.setLastName("Surname1");
            customer.setEmail("person1@gmail.com");
            customer.setPhone("5846548654");
            customer.setAddress("25th Street CA");
            customer.setCity("Monterey");
            customer.setState("CA");
            customer.setZipCode("13133");*/

            // Get a customer by id using CustomerDAO
            Customer customer=customerDAO.findById(1000);
            System.out.println(customer);

            customerDAO.create(customer);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
