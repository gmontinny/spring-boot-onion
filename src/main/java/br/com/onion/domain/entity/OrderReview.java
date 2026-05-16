package br.com.onion.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_reviews")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReview {

    @Id
    @Column(name = "review_id", length = 32)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "review_score")
    private Integer reviewScore;

    @Column(name = "review_comment_title")
    private String reviewCommentTitle;

    @Column(name = "review_comment_message", columnDefinition = "TEXT")
    private String reviewCommentMessage;

    @Column(name = "review_creation_date")
    private LocalDateTime reviewCreationDate;

    @Column(name = "review_answer_timestamp")
    private LocalDateTime reviewAnswerTimestamp;
}
