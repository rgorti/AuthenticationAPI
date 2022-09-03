package com.craft.authentication.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.craft.authentication.data.UserEntity;

/**
 * This is the interface to manage crud operations in the database
 * @author gorti
 *
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);

}
