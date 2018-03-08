package repository;

import model.User;

import java.util.Collection;
import java.util.List;

/**
 * Created by Sjoerd on 26-2-2018.
 */
public interface UserRepository {
    User getUser(int id);

    Collection<User> getFollowers(User user);

    Collection<User> getFollowing(User user);

    User createUser(User user);

    User updateUser(User user);

    boolean removeUser(User user);

}
