package com.example.UserDept.web.dto.mapper;

import com.example.UserDept.entities.Department;
import com.example.UserDept.web.dto.department.DepartmentDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

@Slf4j
public class DepartmentMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Department toDepartment(DepartmentDto dto){
        return modelMapper.map(dto, Department.class);
    }

    public static DepartmentDto toDto(Department dept) {
        return modelMapper.map(dept, DepartmentDto.class);
    }

    public static Page<DepartmentDto> toDtos(Page<Department> departments){
        return departments.map(DepartmentMapper::toDto);
    }
}
