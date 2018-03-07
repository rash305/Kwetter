package model;

import javax.enterprise.inject.Model;
import javax.management.relation.Role;
import javax.persistence.*;
import javax.swing.plaf.synth.Region;
import java.util.*;

/**
 * Created by Sjoerd on 26-2-2018.
 */
@Entity
@Model
public  class User {

    //region Constructor

    public User() {
    }

    public User(String userName, String email, String encryptedPassword, String location, String bio, String avatarPath, String website, Set<USER_ROLE> roles) {
        this.userName = userName;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.location = location;
        this.bio = bio;
        this.avatarPath = avatarPath;
        this.website = website;
        this.roles = roles;
    }

    //endregion

    //region Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userName;
    private String email;
    private String encryptedPassword;
    private String location;
    private String bio;
    private String avatarPath;
    private String website;
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<USER_ROLE> roles;

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<Tweet> tweets = new HashSet<Tweet>();
    @OneToMany(fetch = FetchType.LAZY)
    @ManyToMany()
    private Collection<User> following = new HashSet<User>();
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable()
    private Collection<User> followers= new HashSet<User>();

    //endregion


    //region Properties

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getLocation() {
        return location;
    }

    public String getBio() {
        return bio;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public String getWebsite() {
        return website;
    }

    public Set<USER_ROLE> getRoles() {
        return roles;
    }

    public Collection<Tweet> getTweets() {
        return tweets;
    }

    public Collection<User> getFollowing() {
        return following;
    }

    public Collection<User> getFollowers() {
        return followers;
    }

    //endregion

    //region Setters


    public void setId(long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setTweets(Collection<Tweet> tweets) {
        this.tweets = tweets;
    }

    public void setFollowing(Collection<User> following) {
        this.following = following;
    }

    public void setRoles(Set<USER_ROLE> roles) {
        this.roles = roles;
    }

    public void setFollowers(Collection<User> followers) {
        this.followers = followers;
    }

    //

    //region Methods

    public boolean Follow(User user){
        int followCount = following.size();
        following.add(user);
        return (followCount != following.size());
    }

    public boolean UnFollow(User user){
        int followCount = following.size();
        following.remove(user);
        return (followCount != following.size());
    }

    public boolean AddFollower(User user){
        int followCount = followers.size();
        followers.add(user);
        return (followCount != followers.size());
    }

    public boolean LoseFollower(User user){
        int followCount = followers.size();
        followers.remove(user);
        return (followCount != followers.size());
    }

    public boolean AddTweet(Tweet tweet){
            int tweetCount = tweets.size();
            tweets.add(tweet);
            return (tweetCount != tweets.size());

    }

    public boolean DeleteTweet(Tweet tweet){
        int tweetCount = tweets.size();
        tweets.remove(tweet);
        return (tweetCount != tweets.size());
    }
    //endregion
}
