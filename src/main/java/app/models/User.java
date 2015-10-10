package app.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Mihkel on 10.10.2015.
 */
@Entity
@Table(name = "user")
public class User {

    // Entity's fields (private)

    // An autogenerated id (unique for each user in the db)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // The user's email
    @NotNull
    private String email;

    // The user's name
    @NotNull
    private String name;


    // Public methods

    public User() { }

    public User(long id) {
        this.id = id;
    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}