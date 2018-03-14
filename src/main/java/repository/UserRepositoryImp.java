package repository;


import model.USER_ROLE;
import model.User;
import org.eclipse.persistence.annotations.ReturnInsert;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

@Stateless
@JPA
public class UserRepositoryImp implements UserRepository {

    @PersistenceContext(unitName = "kwetterpu")
    EntityManager em;

    @Override
    public User getUser(int id) {

        return em.find(User.class, id);
    }

    @Override
    public List<User> getUsers(int page) {
        if (page < 0) {
            return em.createQuery("Select a from User a order by a.userName", User.class)
                    .getResultList();
        }
        int pagesize = 20;
        return em.createQuery("Select a from User a order by a.userName", User.class)
                .setFirstResult(pagesize * page).setMaxResults(page * pagesize + pagesize)
                .getResultList();
    }


    @Override
    public List<User> findUserByName(String username) {
        return null;
    }

    @Override
    public Collection<User> getFollowers(User user) {
        return null;
    }

    @Override
    public Collection<User> getFollowing(User user) {
        return null;
    }

    @Override
    @ReturnInsert
    public User createUser(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public boolean removeUser(User user) {
        return false;
    }

    @Override
    public Collection<USER_ROLE> createRoles(Collection<USER_ROLE> roles) {
        for (USER_ROLE role : roles) {
            em.persist(role);
        }
        return roles;

    }

    @Override
    public USER_ROLE createRole(USER_ROLE role) {
        em.persist(role);
        return role;
    }

    @Override
    public boolean removeRole(USER_ROLE role) {
        boolean returnValue = em.contains(role);
        em.remove(role);
        return returnValue;
    }
}
