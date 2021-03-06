package model;

import javax.enterprise.inject.Model;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sjoerd on 26-2-2018.
 */
@Model
@Entity
@Table(name = "UserGroup")
public class Group implements Serializable {

    @Id
    private String groupName;


    public String getName() {
        return groupName;
    }

    public void setName(String name) {
        this.groupName = name;
    }

    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(name="USER_USERGROUP",
            joinColumns = @JoinColumn(name = "groupname",
                    referencedColumnName = "groupName"),
            inverseJoinColumns = @JoinColumn(name = "USERNAME",
                    referencedColumnName = "USERNAME"))
    @JsonbTransient
    private List<Account> users = new ArrayList<>();


    public Group(){

    }

    public Group(String name) {
        this.groupName = name;
    }

    public List<Account> getAllUsers(){
        return this.users;
    }

    public void addUser(Account account){
        users.add(account);
    }

    public void removeUser(Account account){
        users.remove(account);
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Group){
            Group temp = (Group)o;
            if (this.getName().equals(temp.getName()))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
}

