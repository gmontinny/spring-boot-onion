package br.com.onion.infrastructure.repository;

import br.com.onion.domain.entity.Seller;
import br.com.onion.domain.repository.SellerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSellerRepository extends JpaRepository<Seller, String>, SellerRepository {
}
