package com.example.UserDept.services;

import com.example.UserDept.exceptions.InvalidDataException;
import com.example.UserDept.repositories.specifications.EmployeeSpecification;
import com.example.UserDept.web.dto.employee.EmployeeUpdateDto;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.UserDept.entities.employee.Employee;
import com.example.UserDept.repositories.EmployeeRepository;
import com.example.UserDept.exceptions.DatabaseConflictException;
import com.example.UserDept.exceptions.ResourceNotFoundException;

import java.util.Objects;

@Slf4j
@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	@Autowired
	private DepartmentService deptService;

	@Autowired
	private AddressService addressService;

	@Transactional(readOnly = true)
	public Page<Employee> findAll(String name, String deptName, Pageable pageable) {
		Specification<Employee> spec = EmployeeSpecification.withFilters(name, deptName);
		return repository.findAll(spec, pageable);
	}

	@Transactional(readOnly = true)
	public Employee findById(Long id) {
		log.info("Buscando funcionário com ID {}", id);
		return repository.findById(id)
				.orElseThrow(() -> {
					log.error("Funcionário com ID {} não encontrado", id);
					return new ResourceNotFoundException(id);
				});
	}

	@Transactional
	public Employee insert(Employee emp) {
		emp.setDepartment(deptService.findById(emp.getDepartment().getId()));

		validateUniqueEmail(emp.getEmail());

		validateUniquePhone(emp.getPhoneNumber());

		emp.setAddress(addressService.getAddressByZipCode(emp.getAddress().getZipCode(), emp.getAddress().getHouseNumber()));

		emp.setPassword(new BCryptPasswordEncoder().encode(emp.getPassword()));

		return repository.save(emp);
	}

	@Transactional
	public void delete(Long id) {
		findById(id);
		repository.deleteById(id);
	}

	@Transactional
	public void update(Long id, EmployeeUpdateDto dto) {
		Employee emp = findById(id);

		if (StringUtils.isNotBlank(dto.getNewEmail())) {
			if (!dto.getNewEmail().equals(emp.getEmail())) {
				validateUniqueEmail(dto.getNewEmail());

				if (!emp.getEmail().equals(dto.getCurrentEmail())) {
					throw new InvalidDataException("Incorrect email!");
				}

				emp.setEmail(dto.getNewEmail());
			}
		}

		if (StringUtils.isNotBlank(dto.getNewPassword())) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			if (!passwordEncoder.matches(dto.getCurrentPassword(), emp.getPassword())) {
				throw new InvalidDataException("Incorrect password!");
			}

			emp.setPassword(passwordEncoder.encode(dto.getNewPassword()));
		}

		if (StringUtils.isNotBlank(dto.getPhoneNumber())) {
			validateUniquePhone(dto.getPhoneNumber());
			emp.setPhoneNumber(dto.getPhoneNumber());
		}

		if (dto.getDepartmentId() != null) {
			emp.setDepartment(deptService.findById(dto.getDepartmentId()));
		}

		if (StringUtils.isNotBlank(dto.getZipCode()) && dto.getHouseNumber() != null) {
			emp.setAddress(addressService.getAddressByZipCode(dto.getZipCode(), dto.getHouseNumber()));
		}

		repository.save(emp);
	}

	private void validateUniqueEmail(String email) {
		if (repository.findByEmail(email) != null) {
			throw new DatabaseConflictException(String.format("Email '%s' already exists", email));
		}
	}

	private void validateUniquePhone(String phone) {
		if (repository.findByPhoneNumber(phone) != null) {
			throw new DatabaseConflictException(String.format("Phone number : '%s' already exists", phone));
		}
	}

	protected boolean hasDependentEmployees(Long departmentId) {
		return repository.countByDepartmentId(departmentId) > 0;
	}
}
