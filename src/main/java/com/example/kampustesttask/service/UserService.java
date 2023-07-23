package com.example.kampustesttask.service;

import com.example.kampustesttask.entity.User;
import com.example.kampustesttask.exception.RecordNotFoundException;
import com.example.kampustesttask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void setTelegram(Long userId, String telegram) {
        userRepository.setTelegram(userId, telegram);
    }

    @Transactional
    public Long getByEmailAndSaveIfNotExists(String email) {
        User user;
        if(!existsByEmail(email)) {
            user = new User();
            user.setEmail(email);
            user.setDtCreate(OffsetDateTime.now());
            save(user);
        } else {
            user = getByEmail(email);
        }
        return user.getId();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(RecordNotFoundException::new);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
