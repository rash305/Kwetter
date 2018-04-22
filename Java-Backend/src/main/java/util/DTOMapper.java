package util;

import DTO.PrivateAccountDetails;
import model.Account;

public abstract class DTOMapper {

    public static PrivateAccountDetails getAccountDetails(Account account){
        PrivateAccountDetails accountDetails = new PrivateAccountDetails(account.getEmail(), account.getUserName());
        return accountDetails;
    }
    public static Account getAccount(PrivateAccountDetails account){
        Account returnAccount = new Account();
        returnAccount.setEmail(account.getEmail());
        returnAccount.setUserName(account.getUsername());
        returnAccount.setEncryptedPassword(account.getPassword());
        return returnAccount;
    }
}
