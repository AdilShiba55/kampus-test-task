package com.example.kampustesttask.service;

import com.example.kampustesttask.entity.User;
import com.example.kampustesttask.repository.UserRepository;
import com.example.kampustesttask.util.UtTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void save() {
        User user = getUser();
        userService.save(user);
        Assertions.assertNotNull(user.getId());
    }

    @Test
    void setTelegram() {
        Assertions.assertDoesNotThrow(() -> userService.setTelegram(UtTest.TEST_USER_ID, UtTest.TEST_USER_TELEGRAM));
    }

    @Test
    void getByEmail() {
        User user = userService.getByEmail(UtTest.TEST_USER_EMAIL);
        Assertions.assertNotNull(user.getId());
    }

    @Test
    void existsByEmail() {
        boolean exists = userService.existsByEmail(UtTest.TEST_USER_EMAIL);
        Assertions.assertTrue(exists);
    }

    private User getUser() {
        User user = new User();
        user.setDtCreate(OffsetDateTime.now());
        user.setEmail("someTest@gmail.com");
        user.setTelegram("some_telegram");
        return user;
    }
}
