package ru.kata.spring.boot_security.demo.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@Transactional
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    public UserDaoImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findUserByEmail(String email) {
        TypedQuery<User> paramemail = entityManager.createQuery("select u from User u join fetch u.roles where u.email=:paramemail", User.class)
                .setParameter("paramemail", email);
        User user = paramemail.getResultList().stream().findFirst().orElse(null);
        return user;
    }

    @Override
    public Set<User> findAllUsers() {
        List<User> userList = entityManager.createQuery("select user from User user join fetch user.roles",User.class).getResultList();
        return userList.stream().collect(Collectors.toSet());
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }
}
