package repository;

import model.USER_ROLE;
import model.User;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Sjoerd on 8-3-2018.
 */
@Stateless
public class UserCollectionRepository implements UserRepository {

    List<User> userList;
    Collection<USER_ROLE> rolesList;
    int userid = -1;
    int roleid = -1;

    public UserCollectionRepository() {
        userList = new ArrayList();
        rolesList = new HashSet<>();

    }

    private int getNewUserId(){
        return userid++;
    }
    private int getNewRoleId(){
        return roleid++;
    }

    public List<User> findUserByName(String name){
        List<User> foundUsers = new ArrayList<User>();
        for (User u: userList) {
            if(u.getUserName().startsWith(name)){
                foundUsers.add(u);
            }
        }
        return foundUsers;
    }

    public User getUser(int id) {
        for (User u: userList) {
            if(u.getId() == id){
                return u;
            }
        }
        return null;
    }

    @Override
    public List<User> getUsers(int page) {
        //ignore page
        return userList;
    }

    public Collection<User> getFollowers(User user) {

        for (User u: userList) {
            if(u.getId() == user.getId()){
                return u.getFollowers();
            }
        }
        return null;
    }

    public Collection<User> getFollowing(User user) {
        for (User u: userList) {
            if(u.getId() == user.getId()){
                return u.getFollowing();
            }
        }
        return null;
    }

    public User createUser(User user) {
        user.setId(getNewUserId());
        userList.add(user);

        return user;
    }

    public User updateUser(User user) {
        for (User u: userList) {
            if(u.getId() == user.getId()){
                userList.set(userList.indexOf(user), user );
                return user;
            }
        }
        return null;
    }

    public boolean removeUser(int id) {
        int index = 0;
        for (User u: userList) {
            if(u.getId() == id){
                userList.remove(index);
                return true;
            }
            index++;
        }
        return false;
    }

    public USER_ROLE getRole(int id) {
        for (USER_ROLE ur: rolesList) {
            if(ur.getId() == id){
                return ur;
            }
        }
        return null;
    }

    public List<USER_ROLE> getRoles() {
        List<USER_ROLE> roles = new ArrayList<USER_ROLE>();
        roles.addAll(rolesList);
        return roles;
    }

    @Override
    public Collection<USER_ROLE> createRoles(Collection<USER_ROLE> roles) {
        for (USER_ROLE role: roles) {
            role.setId(getNewRoleId());

        }
        this.rolesList.addAll(roles);

        return roles;
    }

    @Override
    public USER_ROLE createRole(USER_ROLE role) {
        role.setId(getNewRoleId());
        rolesList.add(role);

        return role;
    }

    @Override
    public boolean removeRole(USER_ROLE role) {
        boolean returnValue;
        if(returnValue = rolesList.contains(role))
            returnValue = true;
        rolesList.remove(role);
        return returnValue;
}


}
