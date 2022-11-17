package ru.kata.spring.boot_security.demo.service.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public AccountDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> user = userDao.findByUsername(username);
        if (user.size() != 1) {
            throw new UsernameNotFoundException("User not found");
        }
        return new AccountDetails(user.get(0));
    }
}

