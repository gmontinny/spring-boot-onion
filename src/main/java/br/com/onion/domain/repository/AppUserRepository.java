package br.com.onion.domain.repository;

import br.com.onion.domain.entity.AppUser;

import java.util.Optional;

public interface AppUserRepository {
    Optional<AppUser> findByUsername(String username);
    AppUser save(AppUser user);
    boolean existsByUsername(String username);
}
