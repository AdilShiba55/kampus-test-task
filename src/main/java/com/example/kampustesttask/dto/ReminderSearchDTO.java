package com.example.kampustesttask.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ReminderSearchDTO {

    private String title;
    private String description;
    private OffsetDateTime dt_remind;

    private Integer pageNum = 1;
    private Integer pageSize = 15;
    private Integer sort = 4;

    public boolean isTitlePresent() {
        return title != null && !title.isEmpty();
    }
    public boolean isDescriptionPresent() {
        return description != null && !description.isEmpty();
    }

    public boolean isTitleAscPagination() {
        return sort == 1;
    }
    public boolean isTitleDescPagination() {
        return sort == 2;
    }
    public boolean isDtRemindAscPagination() {
        return sort == 3;
    }
    public boolean isDtRemindDescPagination() {
        return sort == 4;
    }

}
