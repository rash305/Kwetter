package service;

import model.Tweet;
import model.User;
import repository.JPA;
import repository.UserRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sjoerd on 26-2-2018.
 */
@Stateless
public class UserService {
    @Inject @JPA
    UserRepository userRepository;


    //Region Methods.

    public User getUser(int id){
        return userRepository.getUser(id);
    }

    public List<User> getUserByName(String name){
        return userRepository.findUserByName(name);
    }

    public User createUser(User user){
        return userRepository.createUser(user);
    }

    public User updateUser(User user){
        return userRepository.updateUser(user);
    }

    public boolean removeUser(User user){
        return userRepository.removeUser(user);
    }

    public Collection<User> getFollowers(User user){
        return userRepository.getFollowers(user);
    }

    public Collection<User> getFollowing(User user){
        return userRepository.getFollowing(user);
    }
    //endregion

}
