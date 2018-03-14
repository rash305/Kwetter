package repository;

import model.USER_ROLE;
import model.User;

import java.util.Collection;
import java.util.List;

/**
 * Created by Sjoerd on 26-2-2018.
 */
public interface UserRepository {
    User getUser(int id);

    List<User> getUsers(int page);

    List<User> findUserByName(String username);

    Collection<User> getFollowers(User user);

    Collection<User> getFollowing(User user);

    User createUser(User user);

    User updateUser(User user);

    boolean removeUser(User user);

    Collection<USER_ROLE> createRoles(Collection<USER_ROLE> roles);
    USER_ROLE createRole(USER_ROLE role);
    boolean removeRole(USER_ROLE role);

}
