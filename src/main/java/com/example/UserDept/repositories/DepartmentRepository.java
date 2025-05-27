package com.example.UserDept.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UserDept.entities.department.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByName(String name);
}
