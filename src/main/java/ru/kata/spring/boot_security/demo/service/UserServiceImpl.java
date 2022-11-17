package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.create(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    @Override
    public void update(long id, User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.update(id, user);
    }

    @Transactional
    @Override
    public void delete(long id) {
        userDao.delete(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(long id) {
        return userDao.getUser(id);
    }

}

