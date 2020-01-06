package com.example.test.demo.sto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity(name = "User")
@JsonIgnoreProperties({"password"})
public class UserSTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean enabled;
    private boolean tokenExpired;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<RoleSTO> roles;

}
