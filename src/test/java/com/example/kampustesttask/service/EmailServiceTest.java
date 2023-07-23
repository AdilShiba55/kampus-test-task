package com.example.kampustesttask.service;

import com.example.kampustesttask.util.UtTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSendException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    void should_not_throw_exception_when_provided_correct_email() {
        Assertions.assertDoesNotThrow(() -> emailService.sendMessage(UtTest.TEST_USER_EMAIL, "test", "test"));
    }

    @Test
    void should_throw_exception_when_provided_incorrect_email() {
        Assertions.assertThrows(MailSendException.class, () -> emailService.sendMessage("djkashdkjshjfhdskjfhds", "test", "test"));
    }
}
