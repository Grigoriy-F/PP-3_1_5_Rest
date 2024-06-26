package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    void save(User user) throws EmailAlreadyExistsException;

    User findByEmail(String email);

    void update(User user) throws EmailAlreadyExistsException;

    void delete(int id);

}
