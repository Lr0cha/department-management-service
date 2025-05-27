package com.example.UserDept.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UserDept.entities.employee.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByEmail(String email);
    Long countByDepartmentId(Long id);
}
