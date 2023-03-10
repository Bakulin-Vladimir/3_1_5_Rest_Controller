package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImp implements RoleService {
    private RoleDao roleDao;

    @Autowired
    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void saveRole(Role role) {
        roleDao.saveRole(role);
    }

    @Override
    public Role readRoleID(long id) {
        return roleDao.readRoleID(id);
    }

    @Override
    public Set<Role> readRoles() {
        return roleDao.readRoles();
    }

    @Override
    public void updateRole(Role role) {
        roleDao.updateRole(role);
    }

    @Override
    public void deleteRole(long id) {
        roleDao.deleteRole(id);

    }

    @Override
    public Set<Role> createRoleAdmin() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_ADMIN"));
        roles.add(new Role("ROLE_USER"));
        return roles;
    }

    @Override
    public Set<Role> createRoleUser() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        return roles;
    }
}
