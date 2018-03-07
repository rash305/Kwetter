package model;

import javax.annotation.security.DeclareRoles;
import javax.persistence.*;
import java.util.Set;

/**
 * Created by Sjoerd on 26-2-2018.
 */
@Entity
@Table(name = "user_role")
public class USER_ROLE {
    private int id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public USER_ROLE(String name) {
        this.name = name;
    }
}
