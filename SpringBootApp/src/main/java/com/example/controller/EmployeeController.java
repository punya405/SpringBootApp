package com.example.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Exception.ResourceNotFoundException;
import com.example.model.Employee;
import com.example.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Swagger2EmployeeRestController", description = "REST APIs related to Employee Entity!!!!")
@RestController
@RequestMapping("/employees")
public class EmployeeController {
	Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService service;

	@ApiOperation(value = "Get list of Employee in the System ", response = Iterable.class, tags = "getAllEmployees")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), @ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> list = service.getAllEmployees();
		logger.info("inside getAllEmployees ");
		return new ResponseEntity<List<Employee>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@ApiOperation(value = "update the given employee details ", response = Employee.class, tags = "updateEmployee")
	@PutMapping(value = "/update/employeeDetails")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee emp) {

		logger.info("inside updateEmployee ");
		Employee updatedEmp = service.createUpdateEmployee(emp);

		if (updatedEmp == null) {
					new ResourceNotFoundException(
							"Unable to update. employee with id " + emp.getEmpId() + " not found.");
		}
		return new ResponseEntity<>(updatedEmp, HttpStatus.OK);
	}

	@ApiOperation(value = "update each record by increasing their salary by percentage(percentage path param) for employees that have place matching with place ", response = Iterable.class, tags = "updateUserByPlaceNSalary")
	@PutMapping(value = "/employee/place/{place}/salary/{percentage}")
	public ResponseEntity<List<Employee>> updateUserByPlaceNSalary(@PathVariable("place") String place,
			@PathVariable("percentage") double percentage) {

		logger.info("inside updateUserByPlaceNSalary ");
		List<Employee> allEmpsByPlace = service.getAllEmployeesByPlace(place);

		if (allEmpsByPlace.size() == 0) {
					new ResourceNotFoundException("Unable to upate. employee with place " + place + " not found.");
		}
		allEmpsByPlace = allEmpsByPlace.stream().map(e -> e.salaryIncreamentBy(percentage))
				.collect(Collectors.toList());

		allEmpsByPlace.stream().forEach(e -> service.createUpdateEmployee(e));
		return new ResponseEntity<>(allEmpsByPlace, HttpStatus.OK);
	}

	@ApiOperation(value = "Get list of Employee by a place name ", response = Iterable.class, tags = "getEmployeeByPlace")
	@GetMapping("/place/{place}")
	public ResponseEntity<List<Employee>> getEmployeeByPlace(@PathVariable("place") String place) {

		logger.info("inside getEmployeeByPlace ");
		List<Employee> allEmpsByPlace = service.getAllEmployeesByPlace(place);
		if (allEmpsByPlace == null) {
			new ResourceNotFoundException("Employee with place " + place + " not found");
		}
		return new ResponseEntity<>(allEmpsByPlace, HttpStatus.OK);
	}

	@ApiOperation(value = "get the nested list of all supervisees of a given supervisor  ", response = Iterable.class, tags = "getSupervisee")
	@GetMapping("/supervisee/{supervisee}")
	public ResponseEntity<List<String>> getSupervisee(@PathVariable("supervisee") String supervisee) {

		logger.info("inside getSupervisee ");
		List<String> allEmpsByPlace = service.getSupervisee(supervisee);
		if (allEmpsByPlace == null) {
					new ResourceNotFoundException("Employee with supervisee " + supervisee + " not found");
		}
		return new ResponseEntity<>(allEmpsByPlace, HttpStatus.OK);
	}

	@ApiOperation(value = "get total salary of given BU  ", response = BigDecimal.class, tags = "getTotalSalaryByBU")
	@GetMapping("/tSal/BU/{BU}")
	public ResponseEntity<BigDecimal> getTotalSalaryByBU(@PathVariable("BU") String BU) {

		logger.info("inside getTotalSalaryByBU ");
		BigDecimal tSal = service.findTotalSalaryByBU(BU);
		if (tSal == null) {
		new ResourceNotFoundException("Employee with BU " + BU + " not found");
		}
		return new ResponseEntity<>(tSal, HttpStatus.OK);
	}

	@ApiOperation(value = "get total salary of given supervisor  ", response = BigDecimal.class, tags = "getTotalSalaryBySupervisor")
	@GetMapping("/tSal/supervisor/{supervisor}")
	public ResponseEntity<BigDecimal> getTotalSalaryBySupervisor(@PathVariable("supervisor") String supervisor) {

		logger.info("inside getTotalSalaryBySupervisor ");
		BigDecimal tSal = service.findTotalSalaryBySup(supervisor);
		if (tSal == null) {
					new ResourceNotFoundException("Employee with supervisor " + supervisor + " not found");
		}
		return new ResponseEntity<>(tSal, HttpStatus.OK);
	}

	@ApiOperation(value = "get total salary of given place  ", response = BigDecimal.class, tags = "getTotalSalaryByPlace")
	@GetMapping("/tSal/place/{place}")
	public ResponseEntity<BigDecimal> getTotalSalaryByPlace(@PathVariable("place") String place) {

		logger.info("inside getTotalSalaryByPlace ");
		BigDecimal tSal = service.findTotalSalaryByPlace(place);
		if (tSal == null) {
			new ResourceNotFoundException("Employee with place " + place + " not found");
		}
		return new ResponseEntity<>(tSal, HttpStatus.OK);
	}

	@ApiOperation(value = "get salary range of given place  ", response = Iterable.class, tags = "getSalaryRangeByPlace")
	@GetMapping("/sal/range/place/{place}")
	public ResponseEntity<List<String>> getSalaryRangeByPlace(@PathVariable("place") String place) {

		logger.info("inside getSalaryRangeByPlace ");

		List<Object[]> salRan = service.findSalaryRangeByPlace(place);
		if (salRan == null) {
			new ResourceNotFoundException("Employee with place " + place + " not found");
		}

		List<String> salR = new ArrayList<String>();

		salRan.stream().forEach(obj -> {
			salR.add(obj[0].toString());
			salR.add(obj[1].toString());

		});
		return new ResponseEntity<>(salR, HttpStatus.OK);
	}

	@GetMapping("/hello")
	public String greeting() {
		return "hello world";
	}

}
