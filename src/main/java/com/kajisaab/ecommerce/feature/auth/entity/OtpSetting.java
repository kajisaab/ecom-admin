package com.kajisaab.ecommerce.feature.auth.entity;

import com.kajisaab.ecommerce.common.DBEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)

public class OtpSetting extends DBEntity {

    @Column
    private LocalDateTime expiryDateTime;

    @Column
    private int otp;

    @OneToOne()
    @JoinColumn(name = "user_credential_id")
    private UserCredential userCredential;
}
