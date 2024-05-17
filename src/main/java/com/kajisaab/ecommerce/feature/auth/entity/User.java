package com.kajisaab.ecommerce.feature.auth.entity;

import com.kajisaab.ecommerce.common.DBEntity;
import com.kajisaab.ecommerce.constants.UserRoleConstantEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name= "user_details")
public class User extends DBEntity implements UserDetails {

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Getter
    @Column
    private String email;

    @Column
    private String userName;

    @Column
    private String phoneNumber;

    @Column(nullable = false, columnDefinition = "BOOLEAN default false")
    private boolean isBlocked;

    @Column(nullable = false, columnDefinition = "BOOLEAN default false")
    private boolean isActive;

    @Column
    private String role;

    @Column
    private String userType;

    @Column
    private String refreshToken;

    @OneToOne(mappedBy = "userDetails", cascade = CascadeType.ALL)
    private UserCredential userCredential;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority superAdminAuthority = new SimpleGrantedAuthority(UserRoleConstantEnum.SUPER_ADMIN.getName());
        SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority(UserRoleConstantEnum.ADMIN.getName());

        return List.of(superAdminAuthority, adminAuthority);
    }

    @Override
    public String getPassword() {
        if(userCredential != null) {
            return userCredential.getPassword();
        };

        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
