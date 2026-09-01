package com.cdamayab.flox.authenticationservice.repository;

import com.cdamayab.flox.authenticationservice.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);
    Optional<Users> findByUserName(String userName);
}

