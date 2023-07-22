package com.example.kampustesttask.repository;

import com.example.kampustesttask.entity.Reminder;
import org.springframework.data.repository.Repository;

import java.util.Map;

public interface ReminderSearchRepository extends Repository<Reminder, Long> {

    Map<String, Object> search();

}
