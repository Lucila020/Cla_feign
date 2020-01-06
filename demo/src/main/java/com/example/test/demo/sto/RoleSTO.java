package com.example.test.demo.sto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name = "Role")
public class RoleSTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String name;

    /*@ManyToMany(mappedBy = "roles")
    private Collection<UserSTO> users;*/

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<PrivilegeSTO> privileges;

}
