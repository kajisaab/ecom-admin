package com.kajisaab.ecommerce.feature.auth.repository;

import com.kajisaab.ecommerce.feature.auth.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT ud.*, uc.id as userCredId, uc.created_at as userCredCreatedAt, uc.is_deleted as userCredIsDeleted, uc.updated_at as userCredUpdatedAt, uc.login_attempts, uc.max_login_attempts, uc.password, uc.password_history " +
            "FROM ecommerce.user_details ud " +
            "LEFT JOIN ecommerce.user_credential uc ON ud.id = uc.user_id " +
            "WHERE  (LOWER(ud.email) = LOWER(:userId) OR LOWER(ud.user_name) = LOWER(:userId))  AND ud.is_deleted = false",
            nativeQuery = true)
    User findByEmailAndUserName(@Param("userId") String userId);

    @Query(value = "SELECT CASE WHEN (LOWER(u.email) = LOWER(:userId) OR LOWER(u.user_name) = LOWER(:userId)) AND u.is_deleted = false THEN true ELSE false END FROM ecommerce.user_details u", nativeQuery = true)
    boolean isUserPresent(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ecommerce.user_details SET refresh_token = :refreshToken where (LOWER(email) = LOWER(:userId) OR LOWER(user_name) = LOWER(:userId)) AND is_deleted = false", nativeQuery = true)
    void updateRefreshToken(@Param("refreshToken") String refreshToken, @Param("userId") String userId);

    @Query(value = "UPDATE ecommerce.user_details SET is_active = true where (LOWER(email) = LOWER(:userId) OR LOWER(user_name) = LOWER(:userId)) AND is_deleted = false", nativeQuery = true)
    void updateUserStatus(@Param("userId") String userId);
}
