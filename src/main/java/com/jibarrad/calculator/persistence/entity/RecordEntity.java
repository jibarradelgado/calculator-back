package com.jibarrad.calculator.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "record")
@Getter
@Setter
@NoArgsConstructor
public class RecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_id", nullable = false)
    private OperationEntity operation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "balance_before_operation", nullable = false)
    private Integer balanceBeforeOperation;

    @Column(name = "balance_after_operation", nullable = false)
    private Integer balanceAfterOperation;

    @Column(name = "operation_response")
    private String operationResponse;

    @Column(nullable = false)
    private Boolean deleted;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;


}
