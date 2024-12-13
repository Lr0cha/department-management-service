package com.example.UserDept.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UserDept.entities.User;

public interface UserRepository extends JpaRepository<User,Long> {

}
