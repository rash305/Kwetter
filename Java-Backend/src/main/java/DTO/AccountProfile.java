package DTO;

import model.Account;

public class AccountProfile {
    private int id;
    private String username;
    private String location;
    private String bio;
    private String avatarPath;
    private String website;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public AccountProfile(Account account) {
        this.id = account.getId();
        this.username = account.getUserName();
        this.location = account.getLocation();
        this.bio = account.getBio();
        this.avatarPath = account.getAvatarPath();
        this.website = account.getWebsite();
    }

    public AccountProfile(){

    }
}
