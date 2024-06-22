package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.*;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;



//    @Override
//    public User findByEmail(String email) {
//        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = : e");
//        query.setParameter("e", email);
//        return (User) query.getSingleResult();
//    }
@Override
public User findByEmail(String email) throws EntityNotFoundException {
    Query query;
    User result;
    try {
        query = entityManager.createQuery("SELECT distinct u FROM User u JOIN FETCH u.roles WHERE u.email = : e");
        query.setParameter("e", email);
        result = (User) query.getSingleResult();
    } catch (NoResultException e) {
        throw new EntityNotFoundException("User with email " + email + " not found");
    }
    return result;
}

//    @Override
//    public List<User> getUsers() {
//        return entityManager.createQuery("SELECT u FROM User u").getResultList();
//    }

    @Override
    public List<User> getUsers() {
    List<User> users = entityManager.createQuery("SELECT distinct u FROM User u JOIN FETCH u.roles").getResultList();
        System.out.println(users);
        return users;
    }


    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

//    @Override
//    public void delete(int id) {
//        User user = entityManager.find(User.class, id);
//        entityManager.remove(user);
//    }

    @Override
    public void delete(int id) {
        User userToDelete = entityManager.find(User.class, id);
        if (userToDelete != null) {
            entityManager.remove(userToDelete);
        }
    }
//    @Override
//    public boolean exist(String email) {
//        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = : e");
//        query.setParameter("e", email);
//        if (((org.hibernate.query.Query)query).list().isEmpty()) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public boolean exist(String email) {
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = : e");
        query.setParameter("e", email);
        return !query.getResultList().isEmpty();
    }
}
