package repository;


import model.USER_ROLE;
import model.User;
import org.eclipse.persistence.annotations.ReturnInsert;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Stateless
@JPA
public class UserRepositoryImp implements UserRepository {

    @PersistenceContext(unitName = "kwetterpu")
    EntityManager em;

    @Override
    public User getUser(int id) {

        User returnUser =  em.find(User.class, id);
        // Do a refresh so Following/followers gets updated
        em.refresh(returnUser);
        return returnUser;


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
        em.refresh(user);
        return user.getFollowing();
    }

    @Override
    @ReturnInsert
    public User createUser(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        User userExists = em.find(User.class, user.getId());
        if(userExists == null){
            return null;
        }
        em.merge(user);
        em.flush();
        em.refresh(userExists);

        return userExists;
    }

    @Override
    public boolean removeUser(int id) {
        User deleteUser = getUser(id);
        if(deleteUser != null){
            em.remove(deleteUser);
            return true;
        }
        return false;
    }

    @Override
    public USER_ROLE getRole(int id) {

        return em.find(USER_ROLE.class, id);
    }

    @Override
    public List<USER_ROLE> getRoles() {

        return em.createQuery("Select a from USER_ROLE a order by a.id",USER_ROLE.class).getResultList();
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
