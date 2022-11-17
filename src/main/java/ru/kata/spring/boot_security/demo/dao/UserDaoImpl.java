package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final EntityManager entityManager;

    public UserDaoImpl (EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return entityManager.createQuery(" FROM User ", User.class).getResultList();
    }

    @Override
    public void update(long id, User user) {
        User update = entityManager.find(User.class, id);
        update.setUsername(user.getUsername());
        update.setPassword(user.getPassword());
        update.setFirstName(user.getFirstName());
        update.setLastName(user.getLastName());
        update.setAge(user.getAge());

    }

    @Override
    public void delete(long id) {
        entityManager.remove(getUser(id));
    }

    @Override
    public User getUser(long id) {
        System.out.println(entityManager.find(User.class, id));
        return entityManager.find(User.class, id);
    }

    @Override
    public List findByUsername(String username) {
        return entityManager.createQuery("select u from User u join fetch u.roles" +
                        " where u.username =:username")
                .setParameter("username", username).
                getResultList();
    }
}
