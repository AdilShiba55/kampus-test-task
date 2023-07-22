package com.example.kampustesttask.controller;

import com.example.kampustesttask.dto.ReminderSaveDTO;
import com.example.kampustesttask.entity.Reminder;
import com.example.kampustesttask.exception.CustomExceptionHandler;
import com.example.kampustesttask.service.ReminderService;
import com.example.kampustesttask.util.UtAuthorization;
import com.example.kampustesttask.util.UtMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reminder")
@CustomExceptionHandler
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody @Valid ReminderSaveDTO reminderSaveDTO) {
        Long userId = UtAuthorization.getUserId();
        Reminder reminder = reminderService.save(reminderSaveDTO, userId);
        return ResponseEntity.ok(UtMap.toMap("id", reminder.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        reminderService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
