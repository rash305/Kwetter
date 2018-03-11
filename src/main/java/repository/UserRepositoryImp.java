package repository;


import model.User;

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
        return null;
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
    public User createUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public boolean removeUser(User user) {
        return false;
    }
}
