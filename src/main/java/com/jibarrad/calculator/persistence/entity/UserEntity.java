package com.jibarrad.calculator.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long idUser;

    @Column(unique = true, nullable = false)
    private String username; //email

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private Integer balance;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "last_top_up_time")
    private LocalDateTime lastTopUpTime;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<RecordEntity> records;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRoleEntity> roles;

    public enum Status {
        ACTIVE, INACTIVE
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active=" + status +
                ", balance=" + balance +
                ", roles=" + roles +
                '}';
    }
}
