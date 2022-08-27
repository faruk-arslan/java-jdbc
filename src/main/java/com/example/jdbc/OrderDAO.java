package com.example.jdbc;

import com.example.jdbc.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DataAccessObject<Order> {

    private static final String GET_BY_ID="SELECT c.first_name as \"customer_first_name\", c.last_name as \"customer_last_name\", c.email as \"customer_email\",  o.order_id, o.creation_date, o.total_due, o.status,  s.first_name as \"salesperson_first_name\", s.last_name as \"salesperson_last_name\", s.email as \"salesperson_email\", oi.quantity as \"order_item_quantity\", p.code as \"product_code\", p.name as \"product_name\", p.size as \"product_size\", p.variety as \"product_variety\", p.price as \"product_price\"  FROM orders o  join customer c on o.customer_id=c.customer_id join salesperson s on o.salesperson_id=s.salesperson_id join order_item oi on oi.order_id=o.order_id join product p on oi.product_id=p.product_id  where o.order_id=?";

    public OrderDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Order findById(long id) {
        Order order=new Order();
        try(PreparedStatement statement=this.connection.prepareStatement(GET_BY_ID)){
            statement.setLong(1,id);
            ResultSet rs=statement.executeQuery();
            List<OrderItem> orderItems=new ArrayList<>();
            // There will be multiple rows for different order_item records
            // Insert them to a list and add to the main order object
            long orderId=0;
            while (rs.next()){
                // Assign once the common fields
                if(orderId==0){
                    order.setId(rs.getLong("order_id"));
                    order.setCreation_date(rs.getTimestamp("creation_date"));
                    order.setStatus(rs.getString("status"));
                    order.setTotalDue(rs.getBigDecimal("total_due"));
                    order.setCustomerFirstName(rs.getString("customer_first_name"));
                    order.setCustomerLastName(rs.getString("customer_last_name"));
                    order.setCustomerEmail(rs.getString("customer_email"));
                    order.setSalespersonFirstName(rs.getString("salesperson_first_name"));
                    order.setSalespersonLastName(rs.getString("salesperson_last_name"));
                    order.setSalespersonEmail(rs.getString("salesperson_email"));
                    orderId=order.getId();
                }
                OrderItem orderItem=new OrderItem();
                orderItem.setProductName(rs.getString("product_name"));
                orderItem.setProductCode(rs.getString("product_code"));
                orderItem.setProductVariety(rs.getString("product_variety"));
                orderItem.setProductSize(rs.getInt("product_size"));
                orderItem.getProductPrice(rs.getBigDecimal("product_price"));
                orderItems.add(orderItem);
            }
            order.setOrderItems(orderItems);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Order update(Order dto) {
        return null;
    }

    @Override
    public Order create(Order dto) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
