package service;

import model.USER_ROLE;
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

    public boolean removeUser(int id){
        return userRepository.removeUser(id);
    }

    public Collection<User> getFollowers(int id){
        User user = userRepository.getUser(id);
        return user.getFollowers();    }

    public Collection<User> getFollowing(int id){
        User user = userRepository.getUser(id);
        return user.getFollowing();
    }


    public boolean addFollower(int id, int loggedinId) {
        User targetUser = userRepository.getUser(id);
        User loggedInUser = userRepository.getUser(loggedinId);
        if(loggedInUser == null ){
            throw new NotFoundException( String.format("Loggedin user can not be found"));
        }
        if(targetUser == null ){
            throw new NotFoundException( String.format("Target user can not be found"));
        }

        boolean returnValue = targetUser.addFollower(loggedInUser);
        userRepository.updateUser(targetUser);

        return returnValue;
    }

    public boolean removeFollower(int id, int loggedinId) {
        User targetUser = userRepository.getUser(id);
        User loggedInUser = userRepository.getUser(loggedinId);
        if(loggedInUser == null ){
            throw new NotFoundException( String.format("Loggedin user can not be found"));
        }
        if(targetUser == null ){
            throw new NotFoundException( String.format("Target user can not be found"));
        }

        boolean returnValue = targetUser.loseFollower(loggedInUser);
        userRepository.updateUser(targetUser);

        return returnValue;

    }

    public List<USER_ROLE> getRoles(){
        return userRepository.getRoles();
    }

    public boolean approveRole(int userid, USER_ROLE role){
        User updateUser = userRepository.getUser(userid);
        if(updateUser == null){
            throw new NotFoundException( String.format("User can not be found"));
        }
        if(updateUser.addRole(role)){
            userRepository.updateUser(updateUser);
            return true;
        }return false;
    }

    public boolean revokeRole(int userid, int roleid){
        User updateUser = userRepository.getUser(userid);
        USER_ROLE role = userRepository.getRole(roleid);
        if(updateUser == null){
            throw new NotFoundException( String.format("User can not be found"));
        }
        if(role== null){
            throw new NotFoundException( String.format("Role can not be found"));
        }
        if(updateUser.deleteRole(role)){
            userRepository.updateUser(updateUser);
            return true;
        }return false;
    }

    //endregion

}
