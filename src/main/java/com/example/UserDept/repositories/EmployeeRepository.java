package com.example.UserDept.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UserDept.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
