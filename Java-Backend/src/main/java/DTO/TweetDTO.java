package DTO;

import model.Account;
import model.Tweet;

import java.util.Date;
import java.util.List;

public class TweetDTO {

    private int id;

    private String message;

    private Date tweetedDate;

    private AccountProfile tweetedBy;

    private int likedCount;

    private List<AccountProfile> liked;

    public List<AccountProfile> getLiked() {
        return liked;
    }

    public void setLiked(List<AccountProfile> liked) {
        this.liked = liked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTweetedDate() {
        return tweetedDate;
    }

    public void setTweetedDate(Date tweetedDate) {
        this.tweetedDate = tweetedDate;
    }

    public AccountProfile getTweetedBy() {
        return tweetedBy;
    }

    public void setTweetedBy(AccountProfile tweetedBy) {
        this.tweetedBy = tweetedBy;
    }

    public int getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(int likedCount) {
        this.likedCount = likedCount;
    }



    public TweetDTO(){}
}
