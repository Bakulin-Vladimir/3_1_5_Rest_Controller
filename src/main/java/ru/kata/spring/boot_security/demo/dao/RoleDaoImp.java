package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@Transactional
public class RoleDaoImp implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Role readRoleID(long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Set<Role> readRoles() {
        List<Role> listRole = entityManager.createQuery("select role from Role role", Role.class).getResultList();
        Set<Role> role = listRole.stream().collect(Collectors.toSet());
        return role;
    }

    @Override
    public void updateRole(Role role) {
        entityManager.merge(role);

    }

    @Override
    public void deleteRole(long id) {
        Role role = entityManager.find(Role.class, id);
        entityManager.remove(role);

    }

}
