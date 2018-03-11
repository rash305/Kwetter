package model;

import javax.enterprise.inject.Model;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Sjoerd on 26-2-2018.
 */
@Model
@Entity
//Index published data because most of the queries will use a sort based on date
@Table(indexes = {@Index(name = "tweetIndex", columnList = "id,published")})
public class Tweet {

    //region Constructor


    public Tweet() {
    }

    public Tweet(String message, User tweetedBy) {
        this.message = message;
        this.tweetedBy = tweetedBy;
        tweetedBy.AddTweet(this);
    }

    //endregion

    //region Fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String message = null;


    private List<String> tags = null;
    @ManyToOne
    private User tweetedBy = null;
    @OneToMany
    private Set<User> likes  = new HashSet<User>();
    @OneToMany
    private List<User> mentions = new ArrayList<User>();


    @Basic(optional = false)
    @Column(insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date published = null;
    //endregion


    //region Properties

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getTags() {
        return tags;
    }

    public User getTweetedBy() {
        return tweetedBy;
    }

    public Set getLikes() {
        return likes;
    }

    public List<User> getMentions() {
        return mentions;
    }

    public Date getPublished() {
        return published;
    }

    //endregion

    //region Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setTweetedBy(User tweetedBy) {
        this.tweetedBy = tweetedBy;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public void setMentions(List<User> mentions) {
        this.mentions = mentions;
    }

    public void setPublished(Timestamp published) {
        this.published = published;
    }

    //endregion

    //region Methods
    public boolean AddMention(User user) {
        int mentionCount = mentions.size();
        mentions.add(user);
        return (mentionCount != mentions.size());
    }


    public boolean DeleteMention(User user) {
        int mentionsCount = mentions.size();
        mentions.remove(user);
        return (mentionsCount != mentions.size());
    }

    public boolean AddTag(String tag) {
        int tagCount = tags.size();
        tags.add(tag);
        return (tagCount != tags.size());
    }


    /**
     * Delete last tag
     *
     * @param tag
     * @return
     */
    public boolean DeleteTag(String tag) {
        int tagCount = tags.size();
        tags.remove(tag);
        return (tagCount != tags.size());
    }

    public boolean DeleteTag(int tag) {
        int tagCount = tags.size();
        tags.remove(tag);
        return (tagCount != tags.size());
    }


    public boolean AddLike(User user) {
        int likeCount = likes.size();
        likes.add(user);
        return (likeCount != likes.size());
    }


    public boolean DeleteLike(User user) {
        int likesCount = likes.size();
        likes.remove(user);
        return (likesCount != likes.size());
    }

    //endregion

    //region Overrides


    //endregion


}
