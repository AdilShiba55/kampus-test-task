package com.example.kampustesttask.controller;

import com.example.kampustesttask.dto.ReminderSaveDTO;
import com.example.kampustesttask.dto.ReminderSearchDTO;
import com.example.kampustesttask.entity.Reminder;
import com.example.kampustesttask.exception.CustomExceptionHandler;
import com.example.kampustesttask.repository.ReminderSearchRepository;
import com.example.kampustesttask.service.ReminderService;
import com.example.kampustesttask.util.UtAuthorization;
import com.example.kampustesttask.util.UtMap;
import com.example.kampustesttask.util.UtTest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reminder")
@CustomExceptionHandler
public class ReminderController {

    private ReminderService reminderService;
    private ReminderSearchRepository reminderSearchRepository;

    public ReminderController(ReminderService reminderService, ReminderSearchRepository reminderSearchRepository) {
        this.reminderService = reminderService;
        this.reminderSearchRepository = reminderSearchRepository;
    }

    @Operation(summary = "Добавление напоминания")
    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody @Valid ReminderSaveDTO reminderSaveDTO) {
        Long userId = UtAuthorization.getUserId();
        Reminder reminder = reminderService.save(reminderSaveDTO, userId);
        return ResponseEntity.ok(UtMap.toMap("id", reminder.getId()));
    }

    @Operation(summary = "Удаление напоминания")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        reminderService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Поиск напоминаний")
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(@RequestBody @Valid ReminderSearchDTO reminderSearchDTO) {
        Long userId = UtAuthorization.getUserId();
        Map<String, Object> result = reminderSearchRepository.search(reminderSearchDTO, userId);
        return ResponseEntity.ok(result);
    }

}
