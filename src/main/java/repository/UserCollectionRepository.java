package repository;

import model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sjoerd on 8-3-2018.
 */
public class UserCollectionRepository implements UserRepository {

    List<User> users;
    int userid = -1;

    public UserCollectionRepository() {
        users = new ArrayList();

    }

    private int getNewUserId(){
        return userid++;
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
