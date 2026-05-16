package br.com.onion.application.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class OrderReviewResponse extends RepresentationModel<OrderReviewResponse> {
    private String id;
    private String orderId;
    private Integer reviewScore;
    private String reviewCommentTitle;
    private String reviewCommentMessage;
    private LocalDateTime reviewCreationDate;
    private LocalDateTime reviewAnswerTimestamp;
}
