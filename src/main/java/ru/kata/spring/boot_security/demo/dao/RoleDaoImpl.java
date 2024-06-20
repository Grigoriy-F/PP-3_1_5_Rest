
package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.*;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

//    @Override
//    public Role findByRole(String role) {
//        Query query = entityManager.createQuery("SELECT r FROM Role r WHERE r.role = : role");
//        query.setParameter("role", role);
//        return (Role) query.getSingleResult();
//    }
@Override
public Role findByRole(String role) throws EntityNotFoundException {
    Query query;
    Role result;
    try {
        query = entityManager.createQuery("SELECT r FROM Role r WHERE r.role = : role");
        query.setParameter("role", role);
        result = (Role) query.getSingleResult();
    } catch (NoResultException e) {
        throw new EntityNotFoundException("Role " + role + " not found");
    }
    return result;
}

    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }
    @Override
    public boolean exist(String role) {
        Query query = entityManager.createQuery("SELECT r FROM Role r WHERE r.role = : role");
        query.setParameter("role", role);
        if (((org.hibernate.query.Query)query).list().isEmpty()) {
            return false;
        }
        return true;
    }
}
