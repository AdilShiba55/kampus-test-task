package com.example.kampustesttask.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Data
public class ReminderSaveDTO {

    private Long id;
    @NotBlank
    private String title;
    private String description;
    @NotNull
    private OffsetDateTime dtRemind;

}
