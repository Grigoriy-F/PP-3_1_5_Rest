package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.EmailAlreadyExistsException;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.constraints.Email;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class RestAdminController {
    private final UserService userService;

    @Autowired
    public RestAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> allUsersRest() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> navBar(Principal principal) {
        return new ResponseEntity<>(userService.findByEmail(principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (EmailAlreadyExistsException e) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users")
    public ResponseEntity<User> update(@RequestBody User user) {
        try {
            userService.update(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (EmailAlreadyExistsException e) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") int id) {
        userService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }
}