package br.com.onion.application.service;

import br.com.onion.application.dto.request.OrderRequest;
import br.com.onion.application.dto.response.OrderResponse;
import br.com.onion.application.mapper.OrderMapper;
import br.com.onion.domain.entity.Customer;
import br.com.onion.domain.entity.Order;
import br.com.onion.domain.repository.CustomerRepository;
import br.com.onion.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public Page<OrderResponse> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderMapper::toResponse);
    }

    public OrderResponse findById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + id));
        return OrderMapper.toResponse(order);
    }

    public Page<OrderResponse> findByCustomerId(String customerId, Pageable pageable) {
        return orderRepository.findByCustomerId(customerId, pageable).map(OrderMapper::toResponse);
    }

    @Transactional
    public OrderResponse create(OrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + request.getCustomerId()));

        Order order = Order.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .customer(customer)
                .status(request.getStatus())
                .purchaseTimestamp(LocalDateTime.now())
                .build();

        return OrderMapper.toResponse(orderRepository.save(order));
    }

    @Transactional
    public void delete(String id) {
        orderRepository.deleteById(id);
    }
}
