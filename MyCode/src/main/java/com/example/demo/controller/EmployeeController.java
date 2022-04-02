package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Employee;
import com.example.demo.repo.EmployeeRepository;

@RestController
@RequestMapping("v1/api/")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@GetMapping("getMyCode/{champ}")
	public String getMyCode(@PathVariable("champ") String champ) {
		return "Welcome to My Code " + champ;
	}

	/**
	 * Get all the employees
	 *
	 * @return ResponseEntity
	 */
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getEmployees() {
		try {
			return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Get the employee by id
	 *
	 * @param id
	 * @return ResponseEntity
	 */
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {
		try {
			// check if employee exist in database
			Employee empObj = getEmpRec(id);

			if (empObj != null) {
				return new ResponseEntity<>(empObj, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Create new employee
	 *
	 * @param employee
	 * @return ResponseEntity
	 */
	@PostMapping("/employee")
	public ResponseEntity<Employee> newEmployee(@RequestBody Employee employee) {
		Employee newEmployee = employeeRepository
				.save(employee);
		return new ResponseEntity<>(newEmployee, HttpStatus.OK);
	}

	/**
	 * Update Employee record by using it's id
	 *
	 * @param id
	 * @param employee
	 * @return
	 */
	@PutMapping("/employee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {

		// check if employee exist in database
		Employee empObj = getEmpRec(id);

		if (empObj != null) {
			empObj.setName(employee.getName());
			empObj.setRole(employee.getRole());
			return new ResponseEntity<>(employeeRepository.save(empObj), HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Delete Employee by Id
	 *
	 * @param id
	 * @return ResponseEntity
	 */
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<HttpStatus> deleteEmployeeById(@PathVariable("id") long id) {
		try {
			// check if employee exist in database
			Employee emp = getEmpRec(id);

			if (emp != null) {
				employeeRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Delete all employees
	 *
	 * @return ResponseEntity
	 */
	@DeleteMapping("/employees")
	public ResponseEntity<HttpStatus> deleteAllEmployees() {
		try {
			employeeRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Method to get the employee record by id
	 *
	 * @param id
	 * @return Employee
	 */
	private Employee getEmpRec(long id) {
		Optional<Employee> empObj = employeeRepository.findById(id);

		if (empObj.isPresent()) {
			return empObj.get();
		}
		return null;
	}

}
