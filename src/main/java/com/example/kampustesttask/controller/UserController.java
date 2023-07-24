package com.example.kampustesttask.controller;

import com.example.kampustesttask.dto.TelegramSaveDTO;
import com.example.kampustesttask.exception.CustomExceptionHandler;
import com.example.kampustesttask.service.UserService;
import com.example.kampustesttask.util.UtAuthorization;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@CustomExceptionHandler
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Проставление телеграма пользователя")
    @PostMapping("/telegram")
    public ResponseEntity<Object> setTelegram(@RequestBody @Valid TelegramSaveDTO telegramSaveDTO) {
        Long userId = UtAuthorization.getUserId();
        userService.setTelegram(userId, telegramSaveDTO.getTelegram());
        return ResponseEntity.ok().build();
    }

}
