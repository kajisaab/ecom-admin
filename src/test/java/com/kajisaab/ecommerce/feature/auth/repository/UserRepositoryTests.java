package com.kajisaab.ecommerce.feature.auth.repository;

import com.kajisaab.ecommerce.constants.UserRoleConstantEnum;
import com.kajisaab.ecommerce.constants.UserTypeConstantEnum;
import com.kajisaab.ecommerce.feature.auth.entity.User;
import io.jsonwebtoken.lang.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    User user = new User();

    @BeforeEach
    void shouldInsertUserDetails(){
        user.setFirstName("Aman");
        user.setLastName("Khadka");
        user.setEmail("amankhadka102@gmail.com");
        user.setUserName("kajisaab2");
        user.setRole(UserRoleConstantEnum.ADMIN.getName());
        user.setUserType(UserTypeConstantEnum.VENDOR.getName());
        user.setPhoneNumber("9999999999");

        userRepository.save(user);
    }

    @Test
    void shouldFindUserByEmail(){
        User userDetails = userRepository.findByEmailAndUserName("amankhadka102@gmail.com");
        assertEquals(userDetails, user);
    }

    @Test
    void shouldFindUserByUsername(){
        User userDetails = userRepository.findByEmailAndUserName("kajisaab2");
        assertEquals(userDetails, user);
    }

    @Test
    void shouldReturnNullIfUserDoesNotExist(){
        User userDetails = userRepository.findByEmailAndUserName("kajiasdfsaab");
        assertNull(userDetails);
    }
}
