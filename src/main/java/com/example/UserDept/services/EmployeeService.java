package com.example.UserDept.services;

import com.example.UserDept.exceptions.InvalidDataException;
import com.example.UserDept.repositories.specifications.EmployeeSpecification;
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
	public void updateEmail(Long id, String currentEmail, String newEmail) {
		Employee emp = findById(id);

		if (Objects.equals(emp.getEmail(), newEmail)) {
			return;
		}

		validateUniqueEmail(newEmail);

		if (!emp.getEmail().equals(currentEmail)) {
			throw new InvalidDataException("Email atual incorreto!");
		}

		log.info("Mudança do email {} para {}", emp.getEmail(), newEmail);
		emp.setEmail(newEmail);

		repository.save(emp);
	}


	@Transactional
	public void updatePhone(Long id, String newPhoneNumber) {
		Employee emp = findById(id);

		if(emp.getPhoneNumber().equals(newPhoneNumber)){
			return;
		}

		validateUniquePhone(newPhoneNumber);

		log.info("Mudança do telefone {} para {}", emp.getPhoneNumber(), newPhoneNumber);
		emp.setPhoneNumber(newPhoneNumber);

		repository.save(emp);
	}

	@Transactional
	public void updateAddress(Long id, String newZipCode, Integer houseNumber) {
		Employee emp = findById(id);

		emp.setAddress(addressService.getAddressByZipCode(newZipCode, houseNumber));
		repository.save(emp);
	}

	@Transactional
	public void updateDepartment(Long id, Long departmentId) {
		Employee emp = findById(id);

		emp.setDepartment(deptService.findById(departmentId));

		repository.save(emp);
	}

	private void validateUniqueEmail(String email) {
		if (repository.findByEmail(email) != null) {
			throw new DatabaseConflictException(String.format("Email '%s' já existe no sistema", email));
		}
	}

	private void validateUniquePhone(String phone) {
		if (repository.findByPhoneNumber(phone) != null) {
			throw new DatabaseConflictException(String.format("Número de celular: '%s' já existe no sistema", phone));
		}
	}

	protected boolean hasDependentEmployees(Long departmentId) {
		return repository.countByDepartmentId(departmentId) > 0;
	}
}
