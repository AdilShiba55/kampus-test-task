package com.example.kampustesttask.repository;

import com.example.kampustesttask.dto.ReminderSearchDTO;
import com.example.kampustesttask.util.UtTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReminderSearchRepositoryTest {

    @Autowired
    private ReminderSearchRepository reminderSearchRepository;

    @Test
    void search() {
        ReminderSearchDTO filter = getFilter();
        Map<String, Object> result = reminderSearchRepository.search(filter, UtTest.TEST_USER_ID);
        Long totalCount = (Long) result.get("totalCount");
        Assertions.assertTrue(totalCount > 0);
    }

    private ReminderSearchDTO getFilter() {
        ReminderSearchDTO filter = new ReminderSearchDTO();
        filter.setTitle("test");
        return filter;
    }
}
