package com.se.auth.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.se.auth.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByUsername(String username);

    @Query("SELECT u.balance FROM UserModel u WHERE u.id = :userId")
    Optional<Double> findBalanceByUserId(@Param("userId") Long userId);
}