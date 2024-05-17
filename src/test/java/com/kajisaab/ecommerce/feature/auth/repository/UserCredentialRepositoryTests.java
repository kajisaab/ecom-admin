package com.kajisaab.ecommerce.feature.auth.repository;

import com.kajisaab.ecommerce.constants.UserRoleConstantEnum;
import com.kajisaab.ecommerce.constants.UserTypeConstantEnum;
import com.kajisaab.ecommerce.feature.auth.entity.User;
import com.kajisaab.ecommerce.feature.auth.entity.UserCredential;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserCredentialRepositoryTests {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void shouldInsertUserCredential() {
        System.out.println("Test running");
        var user = User
                .builder()
                .firstName("Aman")
                .lastName("Khadka")
                .email("amankhadka102@gmail.com")
                .userName("kajisaab1")
                .role(UserRoleConstantEnum.ADMIN.getName())
                .userType(UserTypeConstantEnum.VENDOR.getName())
                .phoneNumber("9999999999")
                .build();

        User savedUser = userRepository.save(user);
        UserCredential userCredential = new UserCredential();
        userCredential.setUserDetails(savedUser);
        userCredential.setPasswordHistory(Collections.EMPTY_LIST);
        userCredential.setPassword("password");

        userCredentialRepository.save(userCredential);
    }

//    @Test
//    void shouldGetUserCredentialsByUsername() {
//        Optional<UserCredential> user = userCredentialRepository.findByUserId("kajisaab");
//        assertEquals(user,);
//    }

}
