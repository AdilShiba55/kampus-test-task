package com.example.kampustesttask.service;

import com.example.kampustesttask.dto.ReminderSaveDTO;
import com.example.kampustesttask.entity.Reminder;
import com.example.kampustesttask.entity.User;
import com.example.kampustesttask.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Transactional
    public Reminder save(ReminderSaveDTO reminderSaveDTO, Long userId) {
        Reminder reminder = new Reminder();
        reminder.setId(reminderSaveDTO.getId());
        reminder.setTitle(reminderSaveDTO.getTitle());
        reminder.setDescription(reminderSaveDTO.getDescription());
        reminder.setDtReminder(reminderSaveDTO.getDtReminder());
        reminder.setUserId(userId);
        reminderRepository.save(reminder);
        return reminder;
    }

    @Transactional
    public void deleteById(Long reminderId) {
        reminderRepository.deleteById(reminderId);
    }

}
