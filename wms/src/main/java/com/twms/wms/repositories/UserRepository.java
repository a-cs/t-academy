package com.twms.wms.repositories;

import com.twms.wms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    List<User> findByUsernameContainingIgnoreCase(String searchTerm);
    Optional<User> findUserByEmail(String username);

}
