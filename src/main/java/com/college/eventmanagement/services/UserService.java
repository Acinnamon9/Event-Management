package com.college.eventmanagement.services;

import com.college.eventmanagement.dao.UserDAO;
import com.college.eventmanagement.entities.User;
import com.college.eventmanagement.entities.Role;
import java.util.List;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public void createUser(User user) {
        userDAO.save(user);
        System.out.println("User created: " + user.getName());
    }

    public void createUser(String name, String email, Role role) {
        User user = new User(name, email, role);
        userDAO.save(user);
        System.out.println("User created: " + name);
    }

    public User getUserById(Long id) {
        return userDAO.findById(id);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public void updateUser(User user) {
        userDAO.update(user);
        System.out.println("User updated: " + user.getName());
    }

    public void deleteUser(Long id) {
        userDAO.delete(id);
        System.out.println("User deleted with ID: " + id);
    }
}