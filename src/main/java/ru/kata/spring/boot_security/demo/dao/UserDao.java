package ru.kata.spring.boot_security.demo.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    @Query("SELECT distinct u FROM User u JOIN FETCH u.roles WHERE u.email = :email")
    Optional<User> findByEmail (String email);
    @Query("SELECT distinct u FROM User u JOIN FETCH u.roles")
    List<User> getUsers();


}