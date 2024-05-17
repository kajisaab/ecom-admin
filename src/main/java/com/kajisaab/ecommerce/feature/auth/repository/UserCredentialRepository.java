package com.kajisaab.ecommerce.feature.auth.repository;

import com.kajisaab.ecommerce.feature.auth.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {
    @Query(value = "SELECT * from user_credential WHERE user_id = :userId AND is_deleted = false",nativeQuery = true)
    Optional<UserCredential> findByUserId(@Param("userId") String userId);


}