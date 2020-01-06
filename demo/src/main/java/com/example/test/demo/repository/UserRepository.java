package com.example.test.demo.repository;

import com.example.test.demo.sto.UserSTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserSTO, Long> {

    Optional<UserSTO> findByEmail(String email);

    void deleteByEmail(String email);

}
