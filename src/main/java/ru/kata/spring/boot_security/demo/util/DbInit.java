package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DbInit {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public DbInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void postConstruct() {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");
        if(roleService.findByRole("ROLE_ADMIN") == null) {
            roleService.save(adminRole);
        }

        if(roleService.findByRole("ROLE_USER") == null) {
            roleService.save(userRole);
        }

        if (userService.findByEmail("admin@mail.ru") == null) {
            List<Role> adminRolesList = new ArrayList<>();
            adminRolesList.add(adminRole);
            adminRolesList.add(userRole);
            User admin = new User("admin", "admin", 28, "admin@mail.com",
                    "admin",adminRolesList);
            userService.save(admin);
        }

        if (userService.findByEmail("user@mail.ru") == null) {
            List<Role> userRolesList = new ArrayList<>();
            userRolesList.add(userRole);
            User user = new User("user", "user", 28, "user@mail.com",
                    "user", userRolesList);
            userService.save(user);
        }
    }
}
