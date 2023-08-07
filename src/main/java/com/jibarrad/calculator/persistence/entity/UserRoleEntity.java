package com.jibarrad.calculator.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_role")
@IdClass(UserRoleId.class)
@Getter
@Setter
@NoArgsConstructor
public class UserRoleEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 20)
    private String role;

    @Column(name = "granted_date", nullable = false)
    private LocalDateTime grantedDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false)
    private UserEntity user;
}
