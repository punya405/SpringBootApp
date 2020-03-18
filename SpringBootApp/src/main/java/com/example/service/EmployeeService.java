package com.example.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Employee;

@Service
public interface EmployeeService {
	public List<Employee> getAllEmployees();
	public Employee createUpdateEmployee(Employee entity);
	public List<Employee> getAllEmployeesByPlace(String place);
	public List<String> getSupervisee(String sup);
	public BigDecimal findTotalSalaryByBU(String BU);
	public BigDecimal findTotalSalaryBySup(String tSalBysup);
	public BigDecimal findTotalSalaryByPlace(String tSalByPlace);
	public List<Object[]> findSalaryRangeByPlace(String salRangeByPlace);
}
