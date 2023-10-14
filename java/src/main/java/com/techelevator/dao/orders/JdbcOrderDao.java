package com.techelevator.dao.orders;

import com.techelevator.dao.menu.MenuItemDao;
import com.techelevator.dao.menu.PizzaDao;
import com.techelevator.model.menu.MenuItem;
import com.techelevator.model.menu.Pizza;
import com.techelevator.model.orders.Order;
import com.techelevator.model.orders.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcOrderDao implements OrderDao{

    @Autowired
    private PizzaDao pizzaDao;

    @Autowired
    private MenuItemDao menuItemDao;
    private final JdbcTemplate jdbcTemplate;
    public JdbcOrderDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public int create(Order order) {

        String sql = "INSERT INTO orders (status, data_id, delivery, subtotal, tax) VALUES (?, ?, ?, ?, ?) RETURNING order_id;";
        String sql2 = "INSERT INTO orders_to_menu_items (order_id, item_id, quantity) VALUES (?, ?, ?);";
        int newId = -1;
        try{
            order.setStatus("pending");
            newId = jdbcTemplate.queryForObject(sql, Integer.class, order.getStatus(), order.getDataId(), order.isIsDelivery(), order.getSubtotal(), order.getTax());
            if(newId == -1){throw new Exception();}
            System.out.println(order.getCustomPizzas());
            System.out.println(order.getMenuItems());
            for(Pizza pizza : order.getCustomPizzas()){
                int pizzaId = -1;
                pizzaId = pizzaDao.create(pizza);
                if(pizzaId == -1){throw new Exception();}
                if(jdbcTemplate.update(sql2, newId, pizzaId, pizza.getQuantity()) != 1){
                    throw new Exception();
                }
            }
            for(MenuItem menuItem : order.getMenuItems()){
                if(jdbcTemplate.update(sql2, newId, menuItem.getItemId(), menuItem.getQuantity()) != 1){
                    throw new Exception();
                }
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return newId;

    }

    @Override
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders JOIN user_data ON orders.data_id = user_data.data_id WHERE order_id = ?;";
        Order order = new Order();
        try {

            SqlRowSet row = jdbcTemplate.queryForRowSet(sql, orderId);
            while(row.next()){
                order = mapRowToOrder(row);
                Map<Integer, Integer> items = getItemsByOrderId(order.getOrderId());
                List<MenuItem> menuItems = new ArrayList<>();
                List<Pizza> pizzas = new ArrayList<>();
                for(Map.Entry<Integer, Integer> currentItem : items.entrySet()){
                    if(currentItem.getKey() >= 1001){
                        Pizza pizza = pizzaDao.getPizzaById(currentItem.getKey());
                        pizza.setQuantity(currentItem.getValue());
                        pizzas.add(pizza);
                    }else if(currentItem.getKey() > 0){
                        MenuItem menuItem = menuItemDao.getMenuItemById(currentItem.getKey());
                        menuItem.setQuantity(currentItem.getValue());
                        menuItems.add(menuItem);
                    }
                }
                order.setCustomPizzas(pizzas);
                order.setMenuItems(menuItems);
            }
        } catch (ResourceAccessException | DataAccessException e){
            System.out.println(e.getMessage());
        }
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders JOIN user_data ON orders.data_id = user_data.data_id;";
        List<Order> orderList = new ArrayList<>();
        try {
            Order order = new Order();
            SqlRowSet row = jdbcTemplate.queryForRowSet(sql);
            while(row.next()){
                order = mapRowToOrder(row);
                Map<Integer, Integer> items = getItemsByOrderId(order.getOrderId());
                List<MenuItem> menuItems = new ArrayList<>();
                List<Pizza> pizzas = new ArrayList<>();
                for(Map.Entry<Integer, Integer> currentItem : items.entrySet()){
                    if(currentItem.getKey() >= 1001){
                        Pizza pizza = pizzaDao.getPizzaById(currentItem.getKey());
                        pizza.setQuantity(currentItem.getValue());
                        pizzas.add(pizza);
                    }else if(currentItem.getKey() > 0){
                        MenuItem menuItem = menuItemDao.getMenuItemById(currentItem.getKey());
                        menuItem.setQuantity(currentItem.getValue());
                        menuItems.add(menuItem);
                    }
                }
                order.setCustomPizzas(pizzas);
                order.setMenuItems(menuItems);
                orderList.add(order);
            }
        } catch (ResourceAccessException | DataAccessException e){
            System.out.println(e.getMessage());
        }
        return orderList;
    }

    @Override
    public List<Order> getAllOrdersByStatus(String status) {
        String sql = "SELECT * FROM orders JOIN user_data ON orders.data_id = user_data.data_id WHERE status = ?;";
        List<Order> orderList = new ArrayList<>();
        try {
            Order order = new Order();
            SqlRowSet row = jdbcTemplate.queryForRowSet(sql, status);
            while(row.next()){
                order = mapRowToOrder(row);
                Map<Integer, Integer> items = getItemsByOrderId(order.getOrderId());
                List<MenuItem> menuItems = new ArrayList<>();
                List<Pizza> pizzas = new ArrayList<>();
                for(Map.Entry<Integer, Integer> currentItem : items.entrySet()){
                    if(currentItem.getKey() >= 1001){
                        Pizza pizza = pizzaDao.getPizzaById(currentItem.getKey());
                        pizza.setQuantity(currentItem.getValue());
                        pizzas.add(pizza);
                    }else if(currentItem.getKey() > 0){
                        MenuItem menuItem = menuItemDao.getMenuItemById(currentItem.getKey());
                        menuItem.setQuantity(currentItem.getValue());
                        menuItems.add(menuItem);
                    }
                }
                order.setCustomPizzas(pizzas);
                order.setMenuItems(menuItems);
                orderList.add(order);
            }
        } catch (ResourceAccessException | DataAccessException e){
            System.out.println(e.getMessage());
        }
        return orderList;
    }

    @Override
    public List<Order> getDemoOrdersByStatus(String status) {
        String sql = "SELECT * FROM orders JOIN user_data ON orders.data_id = user_data.data_id WHERE status = ? AND orders.order_id BETWEEN 1 AND 3;";
        List<Order> orderList = new ArrayList<>();
        try {
            Order order = new Order();
            SqlRowSet row = jdbcTemplate.queryForRowSet(sql, status);
            while(row.next()){
                order = mapRowToOrder(row);
                Map<Integer, Integer> items = getItemsByOrderId(order.getOrderId());
                List<MenuItem> menuItems = new ArrayList<>();
                List<Pizza> pizzas = new ArrayList<>();
                for(Map.Entry<Integer, Integer> currentItem : items.entrySet()){
                    if(currentItem.getKey() >= 1001){
                        Pizza pizza = pizzaDao.getPizzaById(currentItem.getKey());
                        pizza.setQuantity(currentItem.getValue());
                        pizzas.add(pizza);
                    }else if(currentItem.getKey() > 0){
                        MenuItem menuItem = menuItemDao.getMenuItemById(currentItem.getKey());
                        menuItem.setQuantity(currentItem.getValue());
                        menuItems.add(menuItem);
                    }
                }
                order.setCustomPizzas(pizzas);
                order.setMenuItems(menuItems);
                orderList.add(order);
            }
        } catch (ResourceAccessException | DataAccessException e){
            System.out.println(e.getMessage());
        }
        return orderList;
    }

    @Override
    public boolean updateStatus(OrderStatus orderStatus) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?;";
        int numOfRows = 0;
        try {
            numOfRows = jdbcTemplate.update(sql, orderStatus.getStatus(), orderStatus.getOrderId());
            if(numOfRows == 0){
                return false;
            }
        } catch (ResourceAccessException | DataAccessException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private Order mapRowToOrder(SqlRowSet row){
        Order order = new Order();

        order.setOrderId(row.getInt("order_id"));
        order.setDataId(row.getInt("data_id"));
        order.setStatus(row.getString("status"));
        order.setAddress(row.getString("address"));
        order.setPhone(row.getString("phone"));
        order.setSubtotal(row.getBigDecimal("subtotal"));
        order.setTax(row.getBigDecimal("tax"));
        order.setEmail(row.getString("email"));
        order.setName(row.getString("name"));
        order.setIsDelivery(row.getBoolean("delivery"));

        return order;
    }

    private Map<Integer, Integer> getItemsByOrderId(int orderId){
        Map<Integer, Integer> itemIds = new HashMap<>();
        String sql = "SELECT item_id, quantity FROM orders_to_menu_items WHERE order_id = ?;";
        try {
            SqlRowSet row = jdbcTemplate.queryForRowSet(sql, orderId);
            while(row.next()){
                itemIds.put(row.getInt("item_id"), row.getInt("quantity"));
            }
        }catch (ResourceAccessException | DataAccessException e){
            System.out.println(e.getMessage());
        }
        return itemIds;
    }

}
