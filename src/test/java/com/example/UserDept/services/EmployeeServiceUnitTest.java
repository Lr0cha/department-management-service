package com.example.UserDept.services;

import com.example.UserDept.entities.department.Department;
import com.example.UserDept.entities.employee.Employee;
import com.example.UserDept.entities.employee.embedded.Address;
import com.example.UserDept.entities.enums.Role;
import com.example.UserDept.exceptions.DatabaseConflictException;
import com.example.UserDept.exceptions.InvalidDataException;
import com.example.UserDept.exceptions.ResourceNotFoundException;
import com.example.UserDept.repositories.EmployeeRepository;
import com.example.UserDept.web.dto.employee.EmployeeUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceUnitTest {

    @Mock
    EmployeeRepository repository;

    @Mock
    DepartmentService deptService;

    @Mock
    AddressService addressService;

    @InjectMocks
    EmployeeService service;

    //var
    Employee baseEmployee;
    Department baseDepartment;
    Address baseAddress;
    EmployeeUpdateDto updateDto = new EmployeeUpdateDto();
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setup(){
        baseDepartment = new Department(1L, "Enginnering");
        baseAddress = new Address();
        baseAddress.setZipCode("01001000");
        baseAddress.setStreet("Praça da Sé");
        baseAddress.setHouseNumber(120);
        baseEmployee = new Employee(1L, "Diego", "diego@email.com","test" ,"(11) 99999-0000", Role.COMMON,baseDepartment, baseAddress);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEmployee_returnEmployeeResponseDto_whenSuccess() {
        when(deptService.findById(baseDepartment.getId())).thenReturn(baseDepartment);
        when(repository.findByEmail(baseEmployee.getEmail())).thenReturn(null);
        when(repository.findByPhoneNumber(baseEmployee.getPhoneNumber())).thenReturn(null);
        when(addressService.getAddressByZipCode(baseAddress.getZipCode(), baseAddress.getHouseNumber())).thenReturn(baseAddress);
        when(repository.save(any(Employee.class))).thenReturn(baseEmployee);

        Employee result = service.insert(baseEmployee);

        assertNotNull(result);
        assertEquals("Diego", result.getName());
        assertEquals("diego@email.com", result.getEmail());
        assertEquals(baseDepartment, result.getDepartment());
        assertEquals(baseAddress, result.getAddress());
        assertNotNull(baseEmployee.getPassword());
        verify(repository, times(1)).save(baseEmployee);
    }

    @Test
    void createEmployee_throwDatabaseConflictException_whenEmailExists() {
        when(repository.findByEmail(baseEmployee.getEmail())).thenReturn(new Employee()); //validate unique email

        DatabaseConflictException exception = assertThrows(DatabaseConflictException.class, () -> service.insert(baseEmployee));

        assertEquals("Email 'diego@email.com' already exists", exception.getMessage());
    }

    @Test
    @DisplayName("should throw DatabaseConflictException when phone number already exists")
    void createEmployee_throwDatabaseConflictException_whenPhoneNumberExists() {
        when(repository.findByPhoneNumber(baseEmployee.getPhoneNumber())).thenReturn(new Employee()); //validate unique phoneNumber

        DatabaseConflictException exception = assertThrows(DatabaseConflictException.class, () -> service.insert(baseEmployee));

        assertEquals("Phone number : '(11) 99999-0000' already exists", exception.getMessage());
    }

    @Test
    void findEmployee_returnEmployeeResponseDto_whenSuccess() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(baseEmployee));

        Employee result = service.findById(id);

        assertNotNull(result);
        assertEquals("Diego", result.getName());

        verify(repository, times(1)).findById(id);
    }

    @Test
    void findEmployee_throwResourceNotFoundException_whenNotFound() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.findById(id)
        );

        assertEquals("Employee ID not found: 1", exception.getMessage());
    }

    @Test
    void deleteEmployee_returnVoid_whenSuccess() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(baseEmployee));
        service.delete(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("should update employee correctly when given valid input")
    void updateEmployee_returnEmployeeUpdateDto_whenSuccess() {
        updateDto.setCurrentEmail("diego@email.com");
        updateDto.setNewEmail("new@email.com");
        updateDto.setCurrentPassword("test");
        updateDto.setNewPassword("newpassword");
        updateDto.setPhoneNumber("(11) 00000-9999");
        updateDto.setDepartmentId(2L);
        updateDto.setZipCode("01310-100");
        updateDto.setHouseNumber(200);
        baseEmployee.setPassword(encoder.encode(baseEmployee.getPassword()));

        when(repository.findById(baseEmployee.getId())).thenReturn(Optional.of(baseEmployee));
        when(deptService.findById(2L)).thenReturn(new Department(2L, "IT"));
        when(addressService.getAddressByZipCode("01310-100", 200)).thenReturn(baseAddress);
        when(repository.save(any(Employee.class))).thenReturn(baseEmployee);

        service.update(baseEmployee.getId(), updateDto);

        assertEquals("new@email.com", baseEmployee.getEmail());
        assertTrue(encoder.matches("newpassword", baseEmployee.getPassword()));
        assertEquals("(11) 00000-9999", baseEmployee.getPhoneNumber());
        assertEquals(2L, baseEmployee.getDepartment().getId());
        assertEquals("Praça da Sé", baseEmployee.getAddress().getStreet());
    }

    @Test
    void updateEmployee_throwInvalidDataException_whenIncorrectCurrentEmail() {

        updateDto.setCurrentEmail("wrong@email.com");
        updateDto.setNewEmail("new@email.com");

        when(repository.findById(baseEmployee.getId())).thenReturn(Optional.of(baseEmployee));

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            service.update(baseEmployee.getId(), updateDto);
        });

        assertEquals("Incorrect email!", exception.getMessage());
    }

    @Test
    void updateEmployee_throwInvalidDataException_whenIncorrectCurrentPassword() {
        EmployeeUpdateDto dto = new EmployeeUpdateDto();
        dto.setCurrentEmail("diego@email.com");
        dto.setNewEmail("new@email.com");
        dto.setCurrentPassword("wrongpassword"); // wrong password
        dto.setNewPassword("newpassword");
        dto.setPhoneNumber("(11) 00000-9999");
        dto.setDepartmentId(2L);
        dto.setZipCode("01310-100");
        dto.setHouseNumber(200);

        when(repository.findById(baseEmployee.getId())).thenReturn(Optional.of(baseEmployee));

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            service.update(baseEmployee.getId(), dto);
        });

        assertEquals("Incorrect password!", exception.getMessage());
    }


    @Test
    void updateEmployee_throwDatabaseConflictException_whenEmailExists() {
        EmployeeUpdateDto dto = new EmployeeUpdateDto();
        dto.setCurrentEmail("diego@email.com");
        dto.setNewEmail("existing@email.com");

        when(repository.findById(baseEmployee.getId())).thenReturn(Optional.of(baseEmployee));
        when(repository.findByEmail("existing@email.com")).thenReturn(new Employee());

        DatabaseConflictException exception = assertThrows(DatabaseConflictException.class, () -> {
            service.update(baseEmployee.getId(), dto);
        });

        assertEquals("Email 'existing@email.com' already exists", exception.getMessage());
    }


    @Test
    @DisplayName("should throw DatabaseConflictException when phone number already exists during update")
    void updateEmployee_throwDatabaseConflictException_whenPhoneNumberExists() {
        EmployeeUpdateDto dto = new EmployeeUpdateDto();
        dto.setPhoneNumber("(11) 99999-0000");

        when(repository.findById(baseEmployee.getId())).thenReturn(Optional.of(baseEmployee));
        when(repository.findByPhoneNumber("(11) 99999-0000")).thenReturn(new Employee());

        DatabaseConflictException exception = assertThrows(DatabaseConflictException.class, () -> {
            service.update(baseEmployee.getId(), dto);
        });

        assertEquals("Phone number : '(11) 99999-0000' already exists", exception.getMessage());
    }

    @Test
    void updateEmployee_throwResourceNotFoundException_whenNotFound() {
        updateDto.setCurrentEmail("diego@email.com");
        updateDto.setNewEmail("new@email.com");

        when(repository.findById(baseEmployee.getId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.update(baseEmployee.getId(), updateDto);
        });

        assertEquals("Employee ID not found: 1", exception.getMessage());
    }
}
