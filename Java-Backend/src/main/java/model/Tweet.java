package model;

import javax.enterprise.inject.Model;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Sjoerd on 26-2-2018.
 */
@Model
@Entity
@NamedQueries({
        @NamedQuery(name = "Tweet.getTweetsOfFollowing",
                query = "SELECT t\n"
                        + "FROM Account u, Tweet t\n"
                        + "INNER JOIN t.tweetedBy tb"
                        + "INNER JOIN u.following uf \n"
                        + "INNER JOIN Account u2 \n"
                        + "WHERE t.id = uf.id\n"
                        + "AND u2.id = uf.id \n"
                        + "AND u.id = :userid"
                        + " order by t.published desc "),
        @NamedQuery(name = "Tweet.getTweetsOfFollowing2",
                query = "SELECT t\n"
                        + "FROM Tweet t\n"
                        + "INNER JOIN t.tweetedBy.followers uf \n"
                        + "WHERE uf.id = :userid"
                        + " order by t.published desc ")
       })
@NamedNativeQueries(
        @NamedNativeQuery(name = "Tweet.getCurrentTrends",
                query = "SELECT `tweet_tags`.`TAGS`" +
                        " FROM `tweet_tags` " +
                        "LEFT JOIN `tweet` ON `tweet`.`ID` = `tweet_tags`.`Tweet_ID` " +
                        "WHERE `tweet`.`PUBLISHED`  >= now() - INTERVAL 1 DAY " +
                        "GROUP BY `tweet_tags`.`TAGS` " +
                        "ORDER BY COUNT(`tweet_tags`.`Tweet_ID`) " +
                        "DESC ")
)
//Index published data because most of the queries will use a sort based on date
@Table(indexes = {@Index(name = "tweetIndex", columnList = "id,published")})
public class Tweet implements Serializable      {

    //region Constructor


    public Tweet() {
    }

    public Tweet(String message, Account tweetedBy) {
        this.message = message;
        this.tweetedBy = tweetedBy;
        tweetedBy.addTweet(this);
    }

    //endregion

    //region Fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String message = null;

    @ElementCollection
    private List<String> tags = new ArrayList<String>();

    @ManyToOne (cascade = CascadeType.DETACH)
    private Account tweetedBy = null;
    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "tweet"),
            inverseJoinColumns = @JoinColumn(name = "person"))    private Set<Account> likes  = new HashSet<Account>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "mentions",
            joinColumns = @JoinColumn(name = "tweet"),
            inverseJoinColumns = @JoinColumn(name = "person"))
    private List<Account> mentions = new ArrayList<Account>();



    @Basic(optional = false)
    @Column(name =  "PUBLISHED",insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp published ;
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

    public Account getTweetedBy() {
        return tweetedBy;
    }

    public Set<Account> getLikes() {
        return likes;
    }

    public List<Account> getMentions() {
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

    public void setTweetedBy(Account tweetedBy) {
        this.tweetedBy = tweetedBy;
    }

    public void setLikes(Set<Account> likes) {
        this.likes = likes;
    }

    public void setMentions(List<Account> mentions) {
        this.mentions = mentions;
    }

    public void setPublished(Timestamp published) {
        this.published = published;
    }

    //endregion

    //region Methods
    public boolean AddMention(Account account) {
        int mentionCount = mentions.size();
        mentions.add(account);
        return (mentionCount != mentions.size());
    }


    public boolean DeleteMention(Account account) {
        int mentionsCount = mentions.size();
        mentions.remove(account);
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


    public boolean AddLike(Account account) {
        int likeCount = likes.size();
        likes.add(account);
        return (likeCount != likes.size());
    }


    public boolean DeleteLike(Account account) {
        int likesCount = likes.size();
        likes.remove(account);
        return (likesCount != likes.size());
    }

    //endregion

    //region Overrides


    //endregion


}
