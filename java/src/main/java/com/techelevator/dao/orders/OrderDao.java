package com.techelevator.dao.orders;

import com.techelevator.model.orders.Order;
import com.techelevator.model.orders.OrderStatus;

import java.util.List;

public interface OrderDao {
    int create(Order order);
    Order getOrderById(int orderId);
    List<Order> getAllOrders();
    boolean updateStatus(OrderStatus orderStatus);
    List<Order> getAllOrdersByStatus(String status);
    List<Order> getDemoOrdersByStatus(String status);
}
