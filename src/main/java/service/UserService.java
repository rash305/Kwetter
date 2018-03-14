package service;

import model.User;
import repository.JPA;
import repository.UserRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
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
        User returnUser = null;
        returnUser = userRepository.getUser(id);

        if(returnUser == null){
            throw new NotFoundException( String.format("User with id %d does not exists", id));
        }
        return returnUser;
    }

    public List<User> getUsers(int page){
        List<User> returnUser = null;
        returnUser = userRepository.getUsers(page);

        if(returnUser == null || returnUser.isEmpty()){
            throw new NotFoundException( String.format("Users can not be found"));
        }
        return returnUser;
    }

    public List<User> getUsers(){
        List<User> returnUser = null;
        returnUser = userRepository.getUsers(-1);
        if(returnUser == null || returnUser.isEmpty()){
            throw new NotFoundException( String.format("Users can not be found"));
        }
        return returnUser;
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
