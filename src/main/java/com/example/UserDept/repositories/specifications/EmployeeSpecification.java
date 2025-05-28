package com.example.UserDept.repositories.specifications;

import com.example.UserDept.entities.department.Department;
import com.example.UserDept.entities.employee.Employee;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    public static Specification<Employee> withFilters(String name, String deptName) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (name != null && !name.isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (deptName != null && !deptName.isEmpty()) {
                Join<Employee, Department> departmentJoin = root.join("department", JoinType.INNER);
                predicate = cb.and(predicate,
                        cb.like(cb.lower(departmentJoin.get("name")), "%" + deptName.toLowerCase() + "%"));
            }

            return predicate;
        };
    }
}
