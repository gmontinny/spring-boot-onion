package br.com.onion.application.service;

import br.com.onion.application.dto.request.OrderItemRequest;
import br.com.onion.application.dto.response.OrderItemResponse;
import br.com.onion.application.mapper.OrderItemMapper;
import br.com.onion.domain.entity.Order;
import br.com.onion.domain.entity.OrderItem;
import br.com.onion.domain.entity.Product;
import br.com.onion.domain.entity.Seller;
import br.com.onion.domain.repository.OrderItemRepository;
import br.com.onion.domain.repository.OrderRepository;
import br.com.onion.domain.repository.ProductRepository;
import br.com.onion.domain.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    public Page<OrderItemResponse> findAll(Pageable pageable) {
        return orderItemRepository.findAll(pageable).map(OrderItemMapper::toResponse);
    }

    public OrderItemResponse findById(Long id) {
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order item not found: " + id));
        return OrderItemMapper.toResponse(item);
    }

    public Page<OrderItemResponse> findByOrderId(String orderId, Pageable pageable) {
        return orderItemRepository.findByOrderId(orderId, pageable).map(OrderItemMapper::toResponse);
    }

    @Transactional
    public OrderItemResponse create(OrderItemRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + request.getOrderId()));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + request.getProductId()));
        Seller seller = sellerRepository.findById(request.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found: " + request.getSellerId()));

        OrderItem item = OrderItem.builder()
                .order(order)
                .orderItemId(request.getOrderItemId())
                .product(product)
                .seller(seller)
                .shippingLimitDate(LocalDateTime.now().plusDays(7))
                .price(request.getPrice())
                .freightValue(request.getFreightValue())
                .build();

        return OrderItemMapper.toResponse(orderItemRepository.save(item));
    }

    @Transactional
    public void delete(Long id) {
        orderItemRepository.deleteById(id);
    }
}
