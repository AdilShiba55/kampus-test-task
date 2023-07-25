package com.example.kampustesttask.service;

import com.example.kampustesttask.dto.ReminderSaveDTO;
import com.example.kampustesttask.entity.Reminder;
import com.example.kampustesttask.entity.User;
import com.example.kampustesttask.exception.RecordNotFoundException;
import com.example.kampustesttask.repository.ReminderRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReminderService {

    private ReminderService self;
    private ReminderRepository reminderRepository;
    private UserService userService;
    private EmailService emailService;

    public ReminderService(@Lazy ReminderService self, ReminderRepository reminderRepository, UserService userService, EmailService emailService) {
        this.self = self;
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
        reminder.setDtRemind(reminderSaveDTO.getDtRemind());
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

    public List<Reminder> getAllExpired() {
        return reminderRepository.getAllExpired();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendRemindNotification(Reminder reminder) {
        String email = reminder.getUser().getEmail();
        // отправляем сообщение на почту
        emailService.sendMessage(email, reminder.getTitle(), reminder.getDescription());
        // после отправки, удаляем запись с БД
        deleteById(reminder.getId());
    }

    @Transactional
    public void sendRemindNotifications() {
        List<Reminder> expired = getAllExpired();
        expired.forEach(item -> self.sendRemindNotification(item));
    }

}
