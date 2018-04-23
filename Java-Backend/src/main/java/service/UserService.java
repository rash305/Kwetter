package service;

import DTO.AccountProfile;
import DTO.PrivateAccountDetails;
import model.Account;
import model.Group;
import repository.JPA;
import repository.UserRepository;
import util.DTOMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.Collection;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by Sjoerd on 26-2-2018.
 */
@Stateless
public class UserService {
    @Inject @JPA
    UserRepository userRepository;


    //Region Methods.

    public Account getUser(int id){
        Account returnAccount = null;
        returnAccount = userRepository.getUser(id);

        if(returnAccount == null){
            throw new NotFoundException( String.format("Account with id %d does not exists", id));
        }
        return returnAccount;
    }

    public Account getUser(String username){
        Account returnAccount = null;
        returnAccount = userRepository.getUser(username);

        if(returnAccount == null){
            throw new NotFoundException( String.format("Account with id %d does not exists", username));
        }
        return returnAccount;
    }

    public List<Account> getUsers(int page){
        List<Account> returnAccount = null;
        returnAccount = userRepository.getUsers(page);

        if(returnAccount == null || returnAccount.isEmpty()){
            throw new NotFoundException( String.format("Users can not be found"));
        }
        return returnAccount;
    }

    public List<Account> getUsers(){
        List<Account> returnAccount = null;
        returnAccount = userRepository.getUsers(-1);
        if(returnAccount == null || returnAccount.isEmpty()){
            throw new NotFoundException( String.format("Users can not be found"));
        }
        return returnAccount;
    }


    public Account createUser(Account account){
        return userRepository.createUser(account);
    }

    public PrivateAccountDetails createUser(PrivateAccountDetails account){


        //Hash the password
        account.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt()));
        Account newAccount = DTOMapper.getAccount(account);
        newAccount.addFollower(newAccount);
        newAccount = userRepository.createUser(newAccount);
        return DTOMapper.getAccountDetails(newAccount);
    }

    public Account updateUser(Account account){

        return userRepository.updateUser(account);
    }

    public void updateUser(AccountProfile accountProfile){
        Account account = userRepository.getUser(accountProfile.getId());
        account.setWebsite(accountProfile.getWebsite());
        account.setLocation(accountProfile.getLocation());
        account.setBio(accountProfile.getBio());
        account.setAvatarPath(accountProfile.getAvatarPath());
        userRepository.updateUser(account);
    }

    public boolean removeUser(int id){
        return userRepository.removeUser(id);
    }

    public Collection<Account> getFollowers(int id){
        Account account = userRepository.getUser(id);
        return account.getFollowers();    }

    public Collection<Account> getFollowing(int id){
        Account account = userRepository.getUser(id);
        return account.getFollowing();
    }


    public boolean addFollower(int id, int loggedinId) {
        Account targetAccount = userRepository.getUser(id);
        Account loggedInAccount = userRepository.getUser(loggedinId);
        if(loggedInAccount == null ){
            throw new NotFoundException( String.format("Loggedin user can not be found"));
        }
        if(targetAccount == null ){
            throw new NotFoundException( String.format("Target user can not be found"));
        }

        boolean returnValue = targetAccount.addFollower(loggedInAccount);
        if(returnValue){
            loggedInAccount.getFollowing().add(targetAccount);
            userRepository.updateUser(loggedInAccount);
        }
        userRepository.updateUser(targetAccount);

        return returnValue;
    }

    public boolean removeFollower(int id, int loggedinId) {
        Account targetAccount = userRepository.getUser(id);
        Account loggedInAccount = userRepository.getUser(loggedinId);
        if(loggedInAccount == null ){
            throw new NotFoundException( String.format("Loggedin user can not be found"));
        }
        if(targetAccount == null ){
            throw new NotFoundException( String.format("Target user can not be found"));
        }

        boolean returnValue = targetAccount.loseFollower(loggedInAccount);
        if(returnValue){
            loggedInAccount.getFollowing().remove(targetAccount);
            userRepository.updateUser(loggedInAccount);
        }
        userRepository.updateUser(targetAccount);

        return returnValue;

    }

    public List<Group> getRoles(){
        return userRepository.getRoles();
    }

    public boolean approveRole(int userid, Group role){
        Account updateAccount = userRepository.getUser(userid);
        if(updateAccount == null){
            throw new NotFoundException( String.format("Account can not be found"));
        }
        if(updateAccount.addRole(role)){
            userRepository.updateUser(updateAccount);
            return true;
        }return false;
    }

    public boolean revokeRole(int userid, String roleid){
        Account updateAccount = userRepository.getUser(userid);
        Group role = userRepository.getRole(roleid);
        if(updateAccount == null){
            throw new NotFoundException( String.format("Account can not be found"));
        }
        if(role== null){
            throw new NotFoundException( String.format("Role can not be found"));
        }
        if(updateAccount.deleteRole(role)){
            userRepository.updateUser(updateAccount);
            return true;
        }return false;
    }

    //endregion

}
