package repository;

import model.Account;
import model.Group;

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

    List<Account> accountList;
    Collection<Group> rolesList;
    int userid = -1;
    int roleid = -1;

    public UserCollectionRepository() {
        accountList = new ArrayList();
        rolesList = new HashSet<>();

    }

    private int getNewUserId(){
        return userid++;
    }
    private int getNewRoleId(){
        return roleid++;
    }

    public List<Account> findUserByName(String name){
        List<Account> foundAccounts = new ArrayList<Account>();
        for (Account u: accountList) {
            if(u.getUserName().startsWith(name)){
                foundAccounts.add(u);
            }
        }
        return foundAccounts;
    }

    public Account getUser(int id) {
        for (Account u: accountList) {
            if(u.getId() == id){
                return u;
            }
        }
        return null;
    }

    @Override
    public Account getUser(String username) {
        for (Account u: accountList) {
            if(u.getUserName().equals(username)){
                return u;
            }
        }
        return null;    }

    @Override
    public List<Account> getUsers(int page) {
        //ignore page
        return accountList;
    }

    public Collection<Account> getFollowers(Account account) {

        for (Account u: accountList) {
            if(u.getId() == account.getId()){
                return u.getFollowers();
            }
        }
        return null;
    }

    public Collection<Account> getFollowing(Account account) {
        for (Account u: accountList) {
            if(u.getId() == account.getId()){
                return u.getFollowing();
            }
        }
        return null;
    }

    public Account createUser(Account account) {
        account.setId(getNewUserId());
        accountList.add(account);

        return account;
    }

    public Account updateUser(Account account) {
        for (Account u: accountList) {
            if(u.getId() == account.getId()){
                accountList.set(accountList.indexOf(account), account);
                return account;
            }
        }
        return null;
    }

    public boolean removeUser(int id) {
        int index = 0;
        for (Account u: accountList) {
            if(u.getId() == id){
                accountList.remove(index);
                return true;
            }
            index++;
        }
        return false;
    }

    public Group getRole(String id) {
        for (Group ur: rolesList) {
            if(ur.getName() == id){
                return ur;
            }
        }
        return null;
    }

    public List<Group> getRoles() {
        List<Group> roles = new ArrayList<Group>();
        roles.addAll(rolesList);
        return roles;
    }

    @Override
    public Collection<Group> createRoles(Collection<Group> roles) {
        for (Group role: roles) {
     //       role.setId(getNewRoleId());

        }
        this.rolesList.addAll(roles);

        return roles;
    }

    @Override
    public Group createRole(Group role) {
 //       role.setId(getNewRoleId());
        rolesList.add(role);

        return role;
    }

    @Override
    public boolean removeRole(Group role) {
        boolean returnValue;
        if(returnValue = rolesList.contains(role))
            returnValue = true;
        rolesList.remove(role);
        return returnValue;
}


}
