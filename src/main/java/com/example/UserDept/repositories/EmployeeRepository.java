package com.example.UserDept.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UserDept.entities.employee.Employee;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepository extends JpaRepository<Employee,Long>, JpaSpecificationExecutor<Employee> {
    Employee findByEmail(String email);
    Employee findByPhoneNumber(String phoneNumber);
    Long countByDepartmentId(Long id);
}
