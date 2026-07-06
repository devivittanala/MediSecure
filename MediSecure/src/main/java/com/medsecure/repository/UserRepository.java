package com.medsecure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medsecure.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}