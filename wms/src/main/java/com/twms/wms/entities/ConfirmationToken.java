package com.twms.wms.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
public class ConfirmationToken {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @NotNull
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    @NotNull
    private LocalDateTime expiredAt;

    public ConfirmationToken(){

    }

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.user = user;
    }
}
