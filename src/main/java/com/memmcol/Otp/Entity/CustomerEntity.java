package com.memmcol.Otp.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity
@NoArgsConstructor
@Component
public class CustomerEntity {

    @Id
    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "customer_sequence"
    )
    private Long Id;
    @Column(unique = true)
    private String email;
    @Column
    private int Otp;

    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime expiresAt;
    @Column
    private LocalDateTime confirmedAt;
    @Column
    private boolean status;



    public CustomerEntity(String email, int Otp, LocalDateTime createdAt, LocalDateTime expiresAt, boolean status){
        this.email = email;
        this.Otp = Otp;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.status = status;
    }


}
