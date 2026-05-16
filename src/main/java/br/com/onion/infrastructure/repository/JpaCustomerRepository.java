package br.com.onion.infrastructure.repository;

import br.com.onion.domain.entity.Customer;
import br.com.onion.domain.repository.CustomerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCustomerRepository extends JpaRepository<Customer, String>, CustomerRepository {
}
