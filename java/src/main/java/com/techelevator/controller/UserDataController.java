package com.techelevator.controller;

import com.techelevator.dao.login.UserDao;
import com.techelevator.dao.login.UserDataDao;
import com.techelevator.model.login.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
//@PreAuthorize("permitAll()")
@CrossOrigin
public class UserDataController {

    @Autowired
    private UserDataDao userDataDao;

    @Autowired
    private UserDao userDao;


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/userdata", method = RequestMethod.GET)
    public UserData getUserDataById(Principal principal) {
        System.out.println(principal.getName());
        return userDataDao.getUserData(userDao.findIdByUsername(principal.getName()));
    }

    @Secured({"ROLE_EMPLOYEE", "ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping(path = "/userdata", method = RequestMethod.PUT)
    public UserData updateUserData(@RequestBody UserData userData, Principal principal) {
        int pId = userDao.findIdByUsername(principal.getName());
        if (userData.getUserId() == pId) {
            return userDataDao.updateUserData(userData);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not your account!");
    }
}
