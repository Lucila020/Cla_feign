package com.example.test.demo.impl;

import com.example.test.demo.repository.PrivilegeRepository;
import com.example.test.demo.repository.RoleRepository;
import com.example.test.demo.repository.UserRepository;
import com.example.test.demo.sto.PrivilegeSTO;
import com.example.test.demo.sto.RoleSTO;
import com.example.test.demo.sto.UserSTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class InitialDataLoaderImpl implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        PrivilegeSTO readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        PrivilegeSTO writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<PrivilegeSTO> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Collections.singletonList(readPrivilege));

        RoleSTO adminRole = roleRepository.findByName("ROLE_ADMIN");
        UserSTO userSto = new UserSTO();
        userSto.setFirstName("Test");
        userSto.setLastName("Test");
        userSto.setPassword(passwordEncoder.encode("test"));
        userSto.setEmail("test@test.com");
        userSto.setRoles(Collections.singletonList(adminRole));
        userSto.setEnabled(true);
        userRepository.saveAndFlush(userSto);

        alreadySetup = true;
    }

    @Transactional
    private PrivilegeSTO createPrivilegeIfNotFound(String name) {

        PrivilegeSTO privilegeSto = privilegeRepository.findByName(name);
        if (privilegeSto == null) {
            privilegeSto = new PrivilegeSTO(name);
            privilegeRepository.saveAndFlush(privilegeSto);
        }
        return privilegeSto;
    }

    @Transactional
    private RoleSTO createRoleIfNotFound(
            String name, Collection<PrivilegeSTO> privileges) {

        RoleSTO roleSto = roleRepository.findByName(name);
        if (roleSto == null) {
            roleSto = new RoleSTO(name);
            roleSto.setPrivileges(privileges);
            roleRepository.saveAndFlush(roleSto);
        }
        return roleSto;
    }

}
