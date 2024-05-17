package com.kajisaab.ecommerce.feature.auth.entity;

import com.kajisaab.ecommerce.common.DBEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserCredential extends DBEntity {

    @Column(nullable = false, columnDefinition = "int default 5")
    private int maxLoginAttempts;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int loginAttempts;

    @Column
    private String password;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> passwordHistory;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private User userDetails;

    @OneToOne(mappedBy = "userCredential", cascade = CascadeType.ALL)
    private OtpSetting otpSetting;
}
