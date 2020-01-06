package com.example.test.demo.repository;

import com.example.test.demo.sto.RoleSTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleSTO, Long> {

    RoleSTO findByName(String name);
}
