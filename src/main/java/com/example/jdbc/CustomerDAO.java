package com.example.jdbc;

import com.example.jdbc.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends DataAccessObject<Customer> {
    private static final String INSERT= "INSERT INTO customer (first_name, last_name, email," +
            "phone, address, city, state, zipcode) VALUES(?,?,?,?,?,?,?,?)";
    private static final String GET_BY_ID="SELECT customer_id, first_name, last_name, email," +
            "phone, address, city, state, zipcode FROM customer WHERE customer_id = ?";
    private static final String UPDATE="UPDATE customer SET first_name = ?, last_name = ?, email = ?," +
            " phone = ?, address = ?, city = ?, state = ?, zipcode = ? WHERE customer_id = ?";
    private static final String DELETE="DELETE FROM customer where customer_id = ?";
    private static final String GET_SORTED_LIMITED="SELECT customer_id, first_name, last_name, email," +
            "phone, address, city, state, zipcode FROM customer ORDER BY first_name, last_name LIMIT ?";
    private static final String GET_SORTED_LIMITED_PAGED="SELECT customer_id, first_name, last_name, email," +
            "phone, address, city, state, zipcode FROM customer ORDER BY first_name, last_name LIMIT ? OFFSET ?";


    public CustomerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Customer findById(long id) {
        Customer customer=new Customer();
        try(PreparedStatement statement=this.connection.prepareStatement(GET_BY_ID)){
            statement.setLong(1,id);
            ResultSet rs=statement.executeQuery();
            while (rs.next()){
                customer.setId(rs.getLong("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setPhone(rs.getString("phone"));
                customer.setEmail(rs.getString("email"));
                customer.setAddress(rs.getString("address"));
                customer.setState(rs.getString("city"));
                customer.setCity(rs.getString("state"));
                customer.setZipCode(rs.getString("zipcode"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer update(Customer dto) {
        Customer customer=null;
        // Disable auto-commit
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try(PreparedStatement statement=this.connection.prepareStatement(UPDATE)) {
            statement.setString(1, dto.getFirstName());
            statement.setString(2, dto.getLastName());
            statement.setString(3, dto.getEmail());
            statement.setString(4, dto.getPhone());
            statement.setString(5, dto.getAddress());
            statement.setString(6, dto.getCity());
            statement.setString(7, dto.getState());
            statement.setString(8, dto.getZipCode());
            statement.setLong(9, dto.getId());
            statement.execute();
            // Commit before the data read operation
            connection.commit();
            customer=findById(dto.getId());
        } catch (SQLException e) {
            // Rollback (can be called any time, not in the catch block particularly)
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public Customer create(Customer dto) {
        try(PreparedStatement statement=this.connection.prepareStatement(INSERT)){
            statement.setString(1, dto.getFirstName());
            statement.setString(2, dto.getLastName());
            statement.setString(3, dto.getEmail());
            statement.setString(4, dto.getPhone());
            statement.setString(5, dto.getAddress());
            statement.setString(6, dto.getCity());
            statement.setString(7, dto.getState());
            statement.setString(8, dto.getZipCode());
            statement.execute();

            int id=getLastVal(CUSTOMER_SEQUENCE);
            return this.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement statement=connection.prepareStatement(DELETE)) {
            statement.setLong(1,id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Customer> getCustomersSortedLimited(int limit){
        List<Customer> customers=new ArrayList<>();
        Customer customer=null;
        try(PreparedStatement preparedStatement=connection.prepareStatement(GET_SORTED_LIMITED)) {
            preparedStatement.setInt(1, limit);
            ResultSet rs=preparedStatement.executeQuery();
            while (rs.next()){
                customer=new Customer();
                customer.setId(rs.getLong("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setPhone(rs.getString("phone"));
                customer.setEmail(rs.getString("email"));
                customer.setAddress(rs.getString("address"));
                customer.setState(rs.getString("city"));
                customer.setCity(rs.getString("state"));
                customer.setZipCode(rs.getString("zipcode"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return customers;
    }

    public List<Customer> getCustomersSortedLimitedPaged(int limit, int pageNumber){
        List<Customer> customers=new ArrayList<>();
        Customer customer=null;
        int offset=(pageNumber-1)*limit;
        try(PreparedStatement preparedStatement=connection.prepareStatement(GET_SORTED_LIMITED_PAGED)) {
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet rs=preparedStatement.executeQuery();
            while (rs.next()){
                customer=new Customer();
                customer.setId(rs.getLong("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setPhone(rs.getString("phone"));
                customer.setEmail(rs.getString("email"));
                customer.setAddress(rs.getString("address"));
                customer.setState(rs.getString("city"));
                customer.setCity(rs.getString("state"));
                customer.setZipCode(rs.getString("zipcode"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return customers;
    }
}