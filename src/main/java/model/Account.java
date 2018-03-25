package model;

import javax.enterprise.inject.Model;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import static javax.persistence.CascadeType.ALL;

/**
 * Created by Sjoerd on 26-2-2018.
 */
@Entity
@Model
public class Account implements Serializable {

    //region Constructor

    /**
     * Empty constructor of the user
     */
    public Account() {
    }

    /**
     * @param userName
     * @param email
     * @param encryptedPassword
     * @param location
     * @param bio
     * @param avatarPath
     * @param website
     * @param roles
     */
    public Account(String userName, String email, String encryptedPassword, String location, String bio, String avatarPath, String website, Set<Group> roles) {
        this.userName = userName;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.location = location;
        this.bio = bio;
        this.avatarPath = avatarPath;
        this.website = website;
        this.roles = roles;
        this.addFollower(this);

    }

    /**
     * @param userName
     * @param email
     * @param encryptedPassword
     * @param location
     * @param bio
     * @param avatarPath
     * @param website
     */
    public Account(String userName, String email, String encryptedPassword, String location, String bio, String avatarPath, String website) {
        this.userName = userName;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.location = location;
        this.bio = bio;
        this.avatarPath = avatarPath;
        this.website = website;
        this.roles = new HashSet<>();
        this.addFollower(this);
    }


    //endregion

    //region Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String userName;
    @Column(unique = true)
    private String email;
    private String encryptedPassword;
    private String location;
    private String bio;
    private String avatarPath;
    private String website;
    @ManyToMany(mappedBy = "users")
    private Set<Group> roles;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tweetedBy", cascade = ALL)
    private Collection<Tweet> tweets = new HashSet<Tweet>();
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            joinColumns = @JoinColumn(name = "id"),
//            inverseJoinColumns = @JoinColumn(name = "following_id"))

    @ManyToMany(mappedBy = "followers", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private Set<Account> following = new HashSet<Account>();
    //@ManyToMany(fetch = FetchType.LAZY, mappedBy="following")

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_by"))
    private Set<Account> followers = new HashSet<Account>();


    //endregion


    //region Properties

    public int getId() {
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


    public Set<Group> getRoleset() {
        return roles;
    }


    @JsonbTransient
    public Collection<Tweet> getTweets() {
        return tweets;
    }

    @JsonbTransient
    public Collection<Account> getFollowing() {
        return following;
    }

    @JsonbTransient
    public Collection<Account> getFollowers() {
        return followers;
    }

    //endregion

    //region Setters


    public void setId(int id) {
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

    public void setFollowing(Collection<Account> following) {
        for (Account account : following) {
            this.following.add(account);

        }
    }

    public void setRoles(Set<Group> roles) {
        this.roles = roles;

    }

    public void setFollowers(Collection<Account> followers) {

        for (Account account : followers) {
            this.followers.add(account);

        }
    }


    //

    //region Methods

/*
    public boolean Follow(Account user) {
        int followCount = following.size();
        following.add(user);
        return (followCount != following.size());
    }

    public boolean unFollow(Account user) {
        int followCount = following.size();
        following.remove(user);
        return (followCount != following.size());
    }
*/

    public boolean addFollower(Account account) {
        int followCount = followers.size();
        followers.add(account);
        return (followCount != followers.size());
    }

    public boolean loseFollower(Account account) {
        int followCount = followers.size();
        followers.remove(account);
        return (followCount != followers.size());
    }

    public boolean addTweet(Tweet tweet) {
        int tweetCount = tweets.size();
        tweets.add(tweet);
        return (tweetCount != tweets.size());

    }

    public boolean deleteTweet(Tweet tweet) {
        int tweetCount = tweets.size();
        tweets.remove(tweet);
        return (tweetCount != tweets.size());
    }

    public boolean addRole(Group role) {
        int roleCount = roles.size();
        roles.add(role);
        role.addUser(this);
        return (roleCount != roles.size());

    }

    public boolean deleteRole(Group role) {
        int roleCount = roles.size();
        roles.remove(role);
        role.removeUser(this);

        return (roleCount != roles.size());
    }
    //endregion
}
