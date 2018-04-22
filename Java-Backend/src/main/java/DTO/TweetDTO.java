package DTO;

import model.Account;
import model.Tweet;

import java.util.Date;

public class TweetDTO {

    private int id;

    private String message;

    private Date tweetedDate;

    private Account tweetedBy;

    private int likedCount;

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

    public Account getTweetedBy() {
        return tweetedBy;
    }

    public void setTweetedBy(Account tweetedBy) {
        this.tweetedBy = tweetedBy;
    }

    public int getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(int likedCount) {
        this.likedCount = likedCount;
    }

    public TweetDTO(Tweet tweet) {
        this.id = tweet.getId();
        this.message = tweet.getMessage();
        this.likedCount = tweet.getLikes().size();
        this.tweetedBy =tweet.getTweetedBy();
        this.tweetedDate = tweet.getPublished();
    }

    public TweetDTO(){}
}
