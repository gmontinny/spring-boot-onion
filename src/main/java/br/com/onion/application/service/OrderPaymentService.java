package br.com.onion.application.service;

import br.com.onion.application.dto.request.OrderPaymentRequest;
import br.com.onion.application.dto.response.OrderPaymentResponse;
import br.com.onion.application.mapper.OrderPaymentMapper;
import br.com.onion.domain.entity.Order;
import br.com.onion.domain.entity.OrderPayment;
import br.com.onion.domain.repository.OrderPaymentRepository;
import br.com.onion.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderPaymentService {

    private final OrderPaymentRepository orderPaymentRepository;
    private final OrderRepository orderRepository;

    public Page<OrderPaymentResponse> findAll(Pageable pageable) {
        return orderPaymentRepository.findAll(pageable).map(OrderPaymentMapper::toResponse);
    }

    public OrderPaymentResponse findById(Long id) {
        OrderPayment payment = orderPaymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order payment not found: " + id));
        return OrderPaymentMapper.toResponse(payment);
    }

    public Page<OrderPaymentResponse> findByOrderId(String orderId, Pageable pageable) {
        return orderPaymentRepository.findByOrderId(orderId, pageable).map(OrderPaymentMapper::toResponse);
    }

    @Transactional
    public OrderPaymentResponse create(OrderPaymentRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + request.getOrderId()));

        OrderPayment payment = OrderPayment.builder()
                .order(order)
                .paymentSequential(request.getPaymentSequential())
                .paymentType(request.getPaymentType())
                .paymentInstallments(request.getPaymentInstallments())
                .paymentValue(request.getPaymentValue())
                .build();

        return OrderPaymentMapper.toResponse(orderPaymentRepository.save(payment));
    }

    @Transactional
    public void delete(Long id) {
        orderPaymentRepository.deleteById(id);
    }
}
