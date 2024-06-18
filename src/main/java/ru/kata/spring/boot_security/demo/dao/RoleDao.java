package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleDao {
    Role findByRole (String role);
    void save (Role role);
    boolean exist(String role);

}
