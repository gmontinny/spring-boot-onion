package br.com.onion.application.service;

import br.com.onion.application.dto.request.OrderReviewRequest;
import br.com.onion.application.dto.response.OrderReviewResponse;
import br.com.onion.application.mapper.OrderReviewMapper;
import br.com.onion.domain.entity.Order;
import br.com.onion.domain.entity.OrderReview;
import br.com.onion.domain.repository.OrderRepository;
import br.com.onion.domain.repository.OrderReviewRepository;
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
public class OrderReviewService {

    private final OrderReviewRepository orderReviewRepository;
    private final OrderRepository orderRepository;

    public Page<OrderReviewResponse> findAll(Pageable pageable) {
        return orderReviewRepository.findAll(pageable).map(OrderReviewMapper::toResponse);
    }

    public OrderReviewResponse findById(String id) {
        OrderReview review = orderReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order review not found: " + id));
        return OrderReviewMapper.toResponse(review);
    }

    public Page<OrderReviewResponse> findByOrderId(String orderId, Pageable pageable) {
        return orderReviewRepository.findByOrderId(orderId, pageable).map(OrderReviewMapper::toResponse);
    }

    @Transactional
    public OrderReviewResponse create(OrderReviewRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + request.getOrderId()));

        OrderReview review = OrderReview.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .order(order)
                .reviewScore(request.getReviewScore())
                .reviewCommentTitle(request.getReviewCommentTitle())
                .reviewCommentMessage(request.getReviewCommentMessage())
                .reviewCreationDate(LocalDateTime.now())
                .reviewAnswerTimestamp(LocalDateTime.now())
                .build();

        return OrderReviewMapper.toResponse(orderReviewRepository.save(review));
    }

    @Transactional
    public void delete(String id) {
        orderReviewRepository.deleteById(id);
    }
}
