package util;

import DTO.AccountProfile;
import DTO.PrivateAccountDetails;
import DTO.TweetDTO;
import model.Account;
import model.Tweet;

import java.util.ArrayList;
import java.util.List;

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

    public static Account getAccount(AccountProfile account){
        Account returnAccount = new Account();
        returnAccount.setUserName(account.getUsername());
        returnAccount.setBio(account.getBio());
        returnAccount.setAvatarPath(account.getAvatarPath());
        returnAccount.setLocation(account.getLocation());
        returnAccount.setWebsite(account.getWebsite());
        return returnAccount;
    }

    public static TweetDTO getTweetDTO(Tweet tweet){
        TweetDTO dto = new TweetDTO();
        dto.setId(tweet.getId());
        dto.setLikedCount(tweet.getLikes().size());
        dto.setMessage(tweet.getMessage());
        dto.setTweetedDate(tweet.getPublished());
        List<AccountProfile> likedList = new ArrayList<>();
        for(Account a: tweet.getLikes()){
            likedList.add(new AccountProfile(a) );
        }
        dto.setTweetedBy(new AccountProfile(tweet.getTweetedBy()));
        dto.setLiked(likedList);
        return dto;
    }

    public static List<TweetDTO> getTweetDTOList(List<Tweet> tweetsWithTag) {
        List<TweetDTO> tweetDTOS = new ArrayList<>(tweetsWithTag.size());
        for(Tweet t: tweetsWithTag){
            tweetDTOS.add(DTOMapper.getTweetDTO(t) );
        }
        return tweetDTOS;
    }
}
