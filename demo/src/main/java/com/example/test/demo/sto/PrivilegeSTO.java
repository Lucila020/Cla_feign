package com.example.test.demo.sto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Entity(name = "Privilege")
public class PrivilegeSTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String name;

    /*@ManyToMany(mappedBy = "privileges")
    private Collection<RoleSTO> roles;*/

}
