package repository;


import model.Account;
import model.Group;
import org.eclipse.persistence.annotations.ReturnInsert;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

@Stateless
@JPA
public class UserRepositoryImp implements UserRepository {

    @PersistenceContext(unitName = "kwetterpu")
    EntityManager em;

    @Override
    public Account getUser(int id) {

        Account returnAccount = em.find(Account.class, id);
        // Do a refresh so Following/followers gets updated
        if (returnAccount != null) {
            em.refresh(returnAccount);

        }
        return returnAccount;


    }

    @Override
    public Account getUser(String username) {
        Account returnAccount = em.createQuery("Select a from Account a where a.userName = :username", Account.class)
                .setParameter("username", username)
                .getSingleResult();
        // Do a refresh so Following/followers gets updated
      //  if (returnAccount != null) {
      //      em.refresh(returnAccount);
      //  }
        return returnAccount;
    }

    @Override
    public List<Account> getUsers(int page) {
        if (page < 0) {
            return em.createQuery("Select a from Account a order by a.userName", Account.class)
                    .getResultList();
        }
        int pagesize = 20;
        return em.createQuery("Select a from Account a order by a.userName", Account.class)
                .setFirstResult(pagesize * page).setMaxResults(page * pagesize + pagesize)
                .getResultList();
    }


    @Override
    public List<Account> findUserByName(String username) {
        return null;
    }


    @Override
    @ReturnInsert
    public Account createUser(Account account) {
        em.persist(account);
        return account;
    }

    @Override
    public Account updateUser(Account account) {
        Account accountExists = em.find(Account.class, account.getId());
        if (accountExists == null) {
            return null;
        }
        em.merge(account);
        em.flush();
        em.refresh(accountExists);

        return accountExists;
    }

    @Override
    public boolean removeUser(int id) {
        Account deleteAccount = getUser(id);
        if (deleteAccount != null) {
            em.remove(deleteAccount);
            return true;
        }
        return false;
    }

    @Override
    public Group getRole(String id) {

        return em.find(Group.class, id);
    }

    @Override
    public List<Group> getRoles() {

        return em.createQuery("Select a from Group a order by a.groupName", Group.class).getResultList();
    }

    @Override
    public Collection<Group> createRoles(Collection<Group> roles) {
        for (Group role : roles) {
            em.persist(role);
        }
        return roles;
    }

    @Override
    public Group createRole(Group role) {
        em.persist(role);
        return role;
    }

    @Override
    public boolean removeRole(Group role) {
        boolean returnValue = em.contains(role);
        em.remove(role);
        return returnValue;
    }
}
