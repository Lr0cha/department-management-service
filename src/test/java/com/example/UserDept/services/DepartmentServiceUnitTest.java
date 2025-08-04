package com.example.UserDept.services;

import com.example.UserDept.entities.department.Department;
import com.example.UserDept.exceptions.DatabaseConflictException;
import com.example.UserDept.exceptions.ResourceNotFoundException;
import com.example.UserDept.repositories.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceUnitTest {

    @Mock
    DepartmentRepository repository;

    @Mock
    EmployeeService empService;

    @InjectMocks
    DepartmentService service;

    @BeforeEach
     void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_returnDepartmentDto_WhenSuccess(){
        Department department = new Department(1L,"New Department");
        when(repository.findByName(department.getName())).thenReturn(null);
        when(repository.save(any(Department.class))).thenReturn(department);

        Department result = service.insert(department);

        assertNotNull(result);
        assertEquals("New Department", result.getName());
        verify(repository, times(1)).save(department);
    }

    @Test
    void create_throwDatabaseConflictException_whenDepartmentExists(){
        Department department = new Department(1L,"Existing Department");
        when(repository.findByName(department.getName())).thenReturn(new Department());

        DatabaseConflictException exception = assertThrows(
                DatabaseConflictException.class,
                () -> service.insert(department)
        );


        assertEquals("The department 'Existing Department' already exists", exception.getMessage());
    }

    @Test
    void findById_returnDepartmentDto_whenSuccess() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(new Department(id, "Existing Department")));

        Department result = service.findById(id);

        assertNotNull(result);
        assertEquals("Existing Department", result.getName());

        verify(repository, times(1)).findById(id);
    }

    @Test
    void findById_ThrowResourceNotFoundException_whenDepartmentNotFound() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.findById(id)
        );

        assertEquals("Department ID not found: 1", exception.getMessage());
    }

    @Test
    void delete_returnVoid_WhenSuccess() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(new Department(id, "Existing Department")));
        when(empService.hasDependentEmployees(1L)).thenReturn(false);

        service.delete(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void delete_ThrowDatabaseConflictException_whenHasDependentEmployees(){
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(new Department(id, "Existing Department")));
        when(empService.hasDependentEmployees(1L)).thenReturn(true);

        DatabaseConflictException exception = assertThrows(
                DatabaseConflictException.class,
                () -> service.delete(id)
        );
        assertEquals("The department cannot be deleted because it has associated employees.", exception.getMessage());
    }
}
