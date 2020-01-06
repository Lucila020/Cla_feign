package com.example.test.demo.repository;

import com.example.test.demo.sto.PrivilegeSTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<PrivilegeSTO, Long> {

    PrivilegeSTO findByName(String name);

}
