package com.example.kampustesttask.repository;

import com.example.kampustesttask.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    @Query(value = "select * from reminder where dt_remind <= current_date", nativeQuery = true)
    List<Reminder> getAllExpired();

}
