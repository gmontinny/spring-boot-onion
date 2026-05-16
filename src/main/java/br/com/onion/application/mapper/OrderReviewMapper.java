package br.com.onion.application.mapper;

import br.com.onion.application.dto.response.OrderReviewResponse;
import br.com.onion.domain.entity.OrderReview;

public final class OrderReviewMapper {

    private OrderReviewMapper() {}

    public static OrderReviewResponse toResponse(OrderReview entity) {
        return OrderReviewResponse.builder()
                .id(entity.getId())
                .orderId(entity.getOrder().getId())
                .reviewScore(entity.getReviewScore())
                .reviewCommentTitle(entity.getReviewCommentTitle())
                .reviewCommentMessage(entity.getReviewCommentMessage())
                .reviewCreationDate(entity.getReviewCreationDate())
                .reviewAnswerTimestamp(entity.getReviewAnswerTimestamp())
                .build();
    }
}
