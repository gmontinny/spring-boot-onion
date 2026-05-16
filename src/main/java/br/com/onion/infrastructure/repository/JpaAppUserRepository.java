package br.com.onion.infrastructure.repository;

import br.com.onion.domain.entity.AppUser;
import br.com.onion.domain.repository.AppUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaAppUserRepository extends JpaRepository<AppUser, Long>, AppUserRepository {
    Optional<AppUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
