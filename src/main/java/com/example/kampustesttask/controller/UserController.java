package com.example.kampustesttask.controller;

import com.example.kampustesttask.dto.TelegramSaveDTO;
import com.example.kampustesttask.dto.UserPrincipal;
import com.example.kampustesttask.exception.CustomExceptionHandler;
import com.example.kampustesttask.exception.RecordNotFoundException;
import com.example.kampustesttask.service.UserService;
import com.example.kampustesttask.util.UtAuthorization;
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

    @PostMapping("/telegram")
    public ResponseEntity<?> setTelegram(@RequestBody @Valid TelegramSaveDTO telegramSaveDTO) {
        Long userId = UtAuthorization.getUserId();
        userService.setTelegram(userId, telegramSaveDTO.getTelegram());
        return ResponseEntity.ok().build();
    }

}
