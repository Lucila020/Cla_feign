package com.example.test.demo.impl;

import com.example.test.demo.repository.RoleRepository;
import com.example.test.demo.repository.UserRepository;
import com.example.test.demo.sto.PrivilegeSTO;
import com.example.test.demo.sto.RoleSTO;
import com.example.test.demo.sto.UserSTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service("userDetailsService")
@Transactional
public class AuthenticationUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Optional<UserSTO> user = userRepository.findByEmail(email);
        if (user == null) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true, true, true, true,
                    getAuthorities(Collections.singletonList(
                            roleRepository.findByName("ROLE_USER"))));
        }

        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(), user.get().getPassword(), user.get().isEnabled(), true, true,
                true, getAuthorities(user.get().getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<RoleSTO> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<RoleSTO> roles) {

        List<String> privileges = new ArrayList<>();
        List<PrivilegeSTO> collection = new ArrayList<>();
        for (RoleSTO role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (PrivilegeSTO item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
