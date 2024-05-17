package com.kajisaab.ecommerce.feature.auth.repository;

import com.kajisaab.ecommerce.feature.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT ud.*, uc.id as userCredId, uc.created_at as userCredCreatedAt, uc.is_deleted as userCredIsDeleted, uc.updated_at as userCredUpdatedAt, uc.login_attempts, uc.max_login_attempts, uc.password " +
            "FROM ecommerce.user_details ud " +
            "LEFT JOIN ecommerce.user_credential uc ON ud.id = uc.user_id " +
            "WHERE (ud.email = :emailOrUserName OR ud.user_name = :emailOrUserName) AND ud.is_deleted = false",
            nativeQuery = true)
    User findByEmailAndUserName(@Param("emailOrUserName") String emailOrUserName);

    @Query(value = "UPDATE ecommerce.user_details SET refresh_token = :refreshToken where id = :id", nativeQuery = true)
    void updateRefreshToken(String refreshToken, String id);
}
