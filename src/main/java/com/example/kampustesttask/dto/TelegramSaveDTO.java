package com.example.kampustesttask.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TelegramSaveDTO {

    @NotBlank
    private String telegram;

}
