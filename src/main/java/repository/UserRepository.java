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

    User createUser(User user);

    User updateUser(User user);

    boolean removeUser(int id);

    Collection<USER_ROLE> createRoles(Collection<USER_ROLE> roles);
    USER_ROLE getRole(int id);
    List<USER_ROLE> getRoles();

    //Not used outside this application (In service layer etc.) Maybe in future.
    //Roles are only created/verified when starting the application
    USER_ROLE createRole(USER_ROLE role);
    boolean removeRole(USER_ROLE role);

}
