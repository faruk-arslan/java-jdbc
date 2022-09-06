package com.example.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
            OrderDAO orderDAO=new OrderDAO(connection);

            // Create a customer record
            /*Customer customer=new Customer();
            customer.setFirstName("Name1");
            customer.setLastName("Surname1");
            customer.setEmail("person1@gmail.com");
            customer.setPhone("5846548654");
            customer.setAddress("25th Street CA");
            customer.setCity("Monterey");
            customer.setState("CA");
            customer.setZipCode("13133");
            customerDAO.create(customer);*/

            // Get a customer by id using CustomerDAO
            /*Customer customer=customerDAO.findById(1000);
            System.out.println(customer);*/

            // Update a record by id
            /*Customer customer=customerDAO.findById(1000);
            System.out.println(customer);
            customer.setEmail("modified_email@gmail.com");
            customer=customerDAO.update(customer);
            System.out.println(customer);*/

            // Get a customer by id using CustomerDAO
            /*Customer customer=customerDAO.findById(1000);
            System.out.println(customer.getFirstName());
            System.out.println(customer.getId());
            System.out.println(customer.getId());*/

            // Create, update and delete a record
            Customer customer=new Customer();
            customer.setFirstName("Name1");
            customer.setLastName("Surname1");
            customer.setEmail("person1@gmail.com");
            customer.setPhone("5846548654");
            customer.setAddress("25th Street CA");
            customer.setCity("Monterey");
            customer.setState("CA");
            customer.setZipCode("13133");

            Customer newCustomer=customerDAO.create(customer);
            System.out.println(newCustomer);
            newCustomer=customerDAO.findById(newCustomer.getId());
            System.out.println(newCustomer);
            newCustomer.setEmail("modified_email@gmail.com");
            newCustomer=customerDAO.update(newCustomer);
            System.out.println(newCustomer);
            customerDAO.delete(newCustomer.getId());

            // Get order by id using OrderDAO
            /*Order order=orderDAO.findById(1197);
            System.out.println(order);*/

            // Run the stored procedure
            /*List<Order> orders=orderDAO.findOrdersForCustomer(789);
            orders.forEach(System.out::println);*/

            // Get customers sorted and limited
            /*List<Customer> customers=customerDAO.getCustomersSortedLimited(20);
            customers.forEach(System.out::println);*/

            // Get customers sorted, limited and paged
            /*for (int i = 1; i < 10; i++) {
                System.out.println("Page: "+i);
                List<Customer> customers=customerDAO.getCustomersSortedLimitedPaged(20,i);
                customers.forEach(System.out::println);
                System.out.println();
            }*/

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
