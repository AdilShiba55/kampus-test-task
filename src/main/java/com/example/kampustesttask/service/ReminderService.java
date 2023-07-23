package com.example.kampustesttask.service;

import com.example.kampustesttask.config.quartz.QuartzTimeInfo;
import com.example.kampustesttask.config.quartz.ReminderEmailJob;
import com.example.kampustesttask.dto.ReminderSaveDTO;
import com.example.kampustesttask.entity.Reminder;
import com.example.kampustesttask.entity.User;
import com.example.kampustesttask.exception.RecordNotFoundException;
import com.example.kampustesttask.repository.ReminderRepository;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReminderService {

    private ReminderRepository reminderRepository;
    private UserService userService;
    private EmailService emailService;

    public ReminderService(ReminderRepository reminderRepository, UserService userService, EmailService emailService) {
        this.reminderRepository = reminderRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Transactional
    public Reminder save(ReminderSaveDTO reminderSaveDTO, Long userId) {
        User user = userService.getById(userId);
        Reminder reminder = new Reminder();
        reminder.setId(reminderSaveDTO.getId());
        reminder.setTitle(reminderSaveDTO.getTitle());
        reminder.setDescription(reminderSaveDTO.getDescription());
        reminder.setDtReminder(reminderSaveDTO.getDtReminder());
        reminder.setUser(user);
        reminderRepository.save(reminder);
        return reminder;
    }

    public Reminder getById(Long id) {
        return reminderRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    @Transactional
    public void deleteById(Long reminderId) {
        reminderRepository.deleteById(reminderId);
    }

    @Transactional
    public void sendRemindNotification(Reminder reminder) {

    }

}
