package br.com.onion.domain.repository;

import br.com.onion.domain.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerRepository {
    Page<Customer> findAll(Pageable pageable);
    Optional<Customer> findById(String id);
    Customer save(Customer customer);
    void deleteById(String id);
    boolean existsById(String id);
}
