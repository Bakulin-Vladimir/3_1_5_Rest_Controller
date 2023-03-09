package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<User> readUsers() {
        List<User> userList = entityManager.createQuery("select u from User u left join fetch u.roles").getResultList();
        Set<User> collect = userList.stream().collect(Collectors.toSet());
        return collect;
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User readUserID(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public User findUserByEmail(String email) {
        TypedQuery<User> paramemail = entityManager.createQuery("select u from User u left join fetch u.roles where u.email=:paramemail", User.class)
                .setParameter("paramemail", email);
        User user = paramemail.getResultList().stream().findFirst().orElse(null);
        return user;
    }
}
