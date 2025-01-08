package com.cdamayab.flox.authenticationservice.repository;

import com.cdamayab.flox.authenticationservice.model.ActiveSessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ActiveSessionsRepository extends JpaRepository<ActiveSessions, Long> {
    void deleteByToken(String token);
    Optional<ActiveSessions> findByToken(String token);
}
