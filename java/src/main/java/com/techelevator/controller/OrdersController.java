package com.techelevator.controller;

import com.techelevator.dao.login.UserDao;
import com.techelevator.dao.login.UserDataDao;
import com.techelevator.dao.orders.OrderDao;
import com.techelevator.model.login.UserData;
import com.techelevator.model.menu.MenuItem;
import com.techelevator.model.orders.Order;
import com.techelevator.model.orders.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
//@PreAuthorize("isAuthenticated()")
//@PreAuthorize("permitAll()")
@CrossOrigin
public class OrdersController {

    @Autowired
    private UserDataDao userDataDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;



    @RequestMapping(path = "/orders", method = RequestMethod.POST)
    public int addOrder(@RequestBody Order order, Principal principal){
//        if(principal == null){
            order.setDataId(userDataDao.createGuestData(order));
//        }else{
//            order.setDataId(userDataDao.getUserData(userDao.findIdByUsername(principal.getName())).getDataId());
//        }
        return orderDao.create(order);
    }

    @Secured({"ROLE_EMPLOYEE", "ROLE_ADMIN" })
    @RequestMapping(path = "/orders", method = RequestMethod.GET)
    public List<Order> getAllOrders(){
        return orderDao.getAllOrders();
    }

    @Secured({"ROLE_EMPLOYEE", "ROLE_ADMIN" })
    @RequestMapping(path = "/orders/status", method = RequestMethod.GET)
    public List<Order> getAllOrdersByStatus(@RequestParam(value = "status") String status){
        return orderDao.getAllOrdersByStatus(status);
    }

    @Secured({"ROLE_DEMO_EMPLOYEE"})
    @RequestMapping(path = "/demo-orders/status", method = RequestMethod.GET)
    public List<Order> getDemoOrdersByStatus(@RequestParam(value = "status") String status){
        return orderDao.getDemoOrdersByStatus(status);
    }

    @Secured({"ROLE_EMPLOYEE", "ROLE_ADMIN", "ROLE_USER" })
    @RequestMapping(path = "/orders/{id}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable int id, Authentication authentication, Principal principal){
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> roles = new HashSet<>();
        for(GrantedAuthority eachAuthority : authorities){
            if(eachAuthority.getAuthority().equals("ROLE_EMPLOYEE") || eachAuthority.getAuthority().equals("ROLE_ADMIN")){
                return orderDao.getOrderById(id);
            }
        }
        Order order = orderDao.getOrderById(id);
        UserData userData = userDataDao.getUserData(userDao.findIdByUsername(principal.getName()));
        if(userData.getDataId() == order.getDataId()){
            return orderDao.getOrderById(id);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Authorized");
    }


    @Secured({"ROLE_EMPLOYEE", "ROLE_ADMIN" })
    @RequestMapping(path = "/orders", method = RequestMethod.PUT)
    public void updateMenuItems(@RequestBody OrderStatus orderStatus){
        boolean updated = orderDao.updateStatus(orderStatus);
        if(!updated){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update");
        }
    }
}
