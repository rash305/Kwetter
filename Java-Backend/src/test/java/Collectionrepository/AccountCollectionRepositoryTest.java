package Collectionrepository;

import model.Account;
import org.junit.Before;
import org.junit.Test;
import repository.UserCollectionRepository;
import repository.UserRepository;

import static org.junit.Assert.*;

/**
 * Created by Sjoerd on 7-3-2018.
 */
public class AccountCollectionRepositoryTest {

    private Account account;
    private Account account1;
    private Account account2;
    private UserRepository userRepository;


    @Before
    public void setUp() throws Exception {
        account = new Account();
        account1 = new Account();
        account2 = new Account();
        userRepository = new UserCollectionRepository();


    }

    @Test
    public void testCreateUser() {
        String username = "Usertest";
        int id = 99;
        Account account = new Account();
        account.setEmail("test@email.nl");
        account.setId(id);
        account.setUserName(username);
        Account repoAccount = userRepository.createUser(account);
        assertNotEquals(repoAccount.getId(), id);
        assertEquals(repoAccount.getUserName(), username);
    }


    @Test
    public void testGetUser() {
        String username = "Usertest";
        Account account = new Account();
        account.setEmail("test@email.nl");
        Account repoAccount = userRepository.createUser(account);
        Account getAccount = userRepository.getUser(repoAccount.getId());
        assertEquals(repoAccount, account);
        assertNull(userRepository.getUser(999));
    }

    @Test
    public void testFollowing() {

        /*Collection<Account> following = account.getFollowing();

        //Follow a account
        account.Follow(account1);

        Collection<Account> newFollowing = account.getFollowing();
        Account repoUser = userRepository.createUser(account);
        Collection<Account> repoFollowing = userRepository.getFollowing(repoUser);
        assertEquals(repoFollowing, newFollowing);

        assertNull(userRepository.getFollowing(new Account()));
   */ }


    @Test
    public void testFollowers() {
/*
        Collection<Account> followers = account.getFollowers();
        //Validate that the account does not follow account1
        Assert.assertFalse(followers.contains(account1));

        //Follow a account
        Assert.assertTrue(account.addFollower(account1));
        //Can't follow a account twice
        Collection<Account> newFollowers = account.getFollowers();

        Account repoUser = userRepository.createUser(account);
        Collection<Account> repoFollowers = userRepository.getFollowers(repoUser);
        Assert.assertEquals(repoFollowers, newFollowers);

        assertNull(userRepository.getFollowers(new Account()));*/

    }


    @Test
    public void updateUser() throws Exception {
        String oldUsername = "Test eerste username";
        Account account = new Account();
        account.setUserName(oldUsername);

        Account repoAccount = userRepository.createUser(account);
        assertEquals(userRepository.getUser(repoAccount.getId()).getUserName(), oldUsername);
        String newUserName = "Nieuwe username";
        repoAccount.setUserName(newUserName);

        userRepository.updateUser(repoAccount);
        assertEquals(userRepository.getUser(repoAccount.getId()).getUserName(), newUserName);

        assertNull(userRepository.updateUser(new Account()));
    }

    @Test
    public void removeUser() throws Exception {
        String oldUsername = "Test eerste username";
        Account account = new Account();
        account.setUserName(oldUsername);

        Account repoAccount = userRepository.createUser(account);
        assertEquals(userRepository.getUser(repoAccount.getId()).getUserName(), oldUsername);

        assertTrue(userRepository.removeUser(repoAccount.getId()));
        assertFalse(userRepository.removeUser(repoAccount.getId()));
    }


}