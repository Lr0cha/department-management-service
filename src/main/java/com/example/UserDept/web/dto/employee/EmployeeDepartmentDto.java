package com.example.UserDept.web.dto.employee;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EmployeeDepartmentDto {
    @NotNull
    private Long departmentId;
}
