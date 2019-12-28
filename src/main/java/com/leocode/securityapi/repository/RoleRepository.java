package com.leocode.securityapi.repository;

import com.leocode.securityapi.models.Role;
import com.leocode.securityapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> getRolesByUsers(User user);
}
