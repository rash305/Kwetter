package repository;

import model.Account;
import model.Group;

import java.util.Collection;
import java.util.List;

/**
 * Created by Sjoerd on 26-2-2018.
 */
public interface UserRepository {
    Account getUser(int id);
    Account getUser(String username);

    List<Account> getUsers(int page);


    Account createUser(Account account);

    Account updateUser(Account account);

    boolean removeUser(int id);

    Collection<Group> createRoles(Collection<Group> roles);
    Group getRole(String id);
    List<Group> getRoles();

    //Not used outside this application (In service layer etc.) Maybe in future.
    //Roles are only created/verified when starting the application
    Group createRole(Group role);
    boolean removeRole(Group role);

}
