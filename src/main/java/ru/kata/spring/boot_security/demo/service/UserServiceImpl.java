package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

@Transactional
@Override
public void save(User user) throws EmailAlreadyExistsException {
    if (userDao.findByEmail(user.getEmail()).isPresent()) {
        throw new EmailAlreadyExistsException("Email " + user.getEmail() + " already exists.");
    }
    String pass = user.getPassword();
    user.setPassword(passwordEncoder.encode(pass));
    userDao.save(user);
}

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email).orElse(null);
    }

@Transactional
@Override
public void update(User user) throws EmailAlreadyExistsException {
    User existingUser = userDao.findByEmail(user.getEmail()).orElse(null);
    if (existingUser != null && existingUser.getId() != user.getId()) {
        throw new EmailAlreadyExistsException("Email " + user.getEmail() + " already exists.");
    }
    String pass = user.getPassword();
    user.setPassword(passwordEncoder.encode(pass));
    userDao.save(user);
}

    @Transactional
    @Override
    public void delete(int id) {
        userDao.deleteById(id);
    }
}