package repository;

import model.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sjoerd on 8-3-2018.
 */
@JPA
@Stateless
public class UserCollectionRepository implements UserRepository {

    List<User> users;
    int userid = -1;

    public UserCollectionRepository() {
        users = new ArrayList();

    }

    private int getNewUserId(){
        return userid++;
    }

    public List<User> findUserByName(String name){
        List<User> foundUsers = new ArrayList<User>();
        for (User u:users) {
            if(u.getUserName().startsWith(name)){
                foundUsers.add(u);
            }
        }
        return foundUsers;
    }

    public User getUser(int id) {
        for (User u:users) {
            if(u.getId() == id){
                return u;
            }
        }
        return null;
    }

    public Collection<User> getFollowers(User user) {

        for (User u:users) {
            if(u.getId() == user.getId()){
                return u.getFollowers();
            }
        }
        return null;
    }

    public Collection<User> getFollowing(User user) {
        for (User u:users) {
            if(u.getId() == user.getId()){
                return u.getFollowing();
            }
        }
        return null;
    }

    public User createUser(User user) {
        user.setId(getNewUserId());
        users.add(user);

        return user;
    }

    public User updateUser(User user) {
        for (User u:users) {
            if(u.getId() == user.getId()){
                users.set(users.indexOf(user), user );
                return user;
            }
        }
        return null;
    }

    public boolean removeUser(User user) {
        return users.remove(user);
    }


}
