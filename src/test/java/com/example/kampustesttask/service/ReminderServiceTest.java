package com.example.kampustesttask.service;

import com.example.kampustesttask.dto.ReminderSaveDTO;
import com.example.kampustesttask.entity.Reminder;
import com.example.kampustesttask.entity.User;
import com.example.kampustesttask.util.UtTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ReminderServiceTest {

    @Autowired
    private ReminderService reminderService;

    @Test
    void save() {
        ReminderSaveDTO reminderSaveDTO = getReminderSaveDTO();
        Reminder reminder = reminderService.save(reminderSaveDTO, UtTest.TEST_USER_ID);
        Assertions.assertNotNull(reminder.getId());
    }

    @Test
    void getById() {
        Reminder reminder = reminderService.getById(UtTest.TEST_REMINDER_ID);
        Assertions.assertNotNull(reminder.getId());
    }

    @Test
    void deleteById() {
        Assertions.assertDoesNotThrow(() -> reminderService.deleteById(UtTest.TEST_REMINDER_ID));
    }

    private ReminderSaveDTO getReminderSaveDTO() {
        ReminderSaveDTO reminderSaveDTO = new ReminderSaveDTO();
        reminderSaveDTO.setTitle("test title");
        reminderSaveDTO.setDescription("test description");
        OffsetDateTime dtRemind = OffsetDateTime.now().plusHours(1L);
        reminderSaveDTO.setDtRemind(dtRemind);
        return reminderSaveDTO;
    }
}
