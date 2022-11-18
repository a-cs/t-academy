package com.twms.wms.repositories;

import com.twms.wms.entities.Role;
import com.twms.wms.enums.AccessLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByAuthority(AccessLevel roleClient);
}
