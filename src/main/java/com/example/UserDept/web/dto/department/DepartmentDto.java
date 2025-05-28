package com.example.UserDept.web.dto.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DepartmentDto {
    @NotBlank
    @Size(min=2, max=100)
    private String name;
}
