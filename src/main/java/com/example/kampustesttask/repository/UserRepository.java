package com.example.kampustesttask.repository;

import com.example.kampustesttask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    @Modifying
    @Query(value = "update table user set telegram = :telegram where id = :userId", nativeQuery = true)
    void setTelegram(Long userId, String telegram);

}
