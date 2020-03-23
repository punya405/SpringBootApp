package com.example.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.constants.AppCont;
import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import com.example.util.CSVDataLoader;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	
	Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepository repository;

	@Cacheable(cacheNames = "Emp-Cache",key = "'allEmps'")
	public List<Employee> getAllEmployees() {
		List<Employee> employeeList = repository.findAll();
		logger.info("getAllEmployees fetching from DB ++++++++++++++++==");

		if (employeeList.size() > 0) {
			return employeeList;
		} else {
			return new ArrayList<Employee>();
		}
	}
	
	@CacheEvict(cacheNames = "Emp-Cache",allEntries=true)
	@CachePut(cacheNames = "Emp-Cache",key="#entity.empId")
	public Employee createUpdateEmployee(Employee entity) {
		 Optional<Employee> employee = repository.findById(entity.getEmpId());
		 logger.info("createUpdateEmployee fetching from DB ++++++++++++++++==");
	        if(employee.isPresent()) 
	        {
	            Employee newEntity = employee.get();
	            newEntity.setEmpName(entity.getEmpName());
	            newEntity.setTitle(entity.getTitle());
	            newEntity.setBunit(entity.getBunit());
	            newEntity.setPlace(entity.getPlace());
	            newEntity.setSalary(entity.getSalary());
	            newEntity.setCompetencies(entity.getCompetencies());
	            newEntity.setsId(entity.getsId());
	 
	            newEntity = repository.save(newEntity);
	             
	            return newEntity;
	        } else {
	            entity = repository.save(entity);
	             
	            return entity;
	        }
	}
	@Override
	@CacheEvict(cacheNames = "Emp-Cache",allEntries=true)
	@CachePut(cacheNames = "Emp-Cache",key="{#place, #percentage}")
	public List<Employee> updateUserByPlaceNSalary(String place, double percentage) {
		List<Employee> allEmpsByPlace = repository.findByPlace(place);
		
		if(allEmpsByPlace!=null) {
			allEmpsByPlace = allEmpsByPlace.stream().map(e -> e.salaryIncreamentBy(percentage))
					.collect(Collectors.toList());

			allEmpsByPlace.stream().forEach(e -> repository.save(e));
		}
		return allEmpsByPlace;
	}
	
	@Cacheable(cacheNames = "Emp-Cache",key = "#place")
	public List<Employee> getAllEmployeesByPlace(String place) {
		List<Employee> employeeList = repository.findByPlace(place);
		 logger.info("getAllEmployeesByPlace fetching from DB ++++++++++++++++==");
		if (employeeList.size() > 0) {
			return employeeList;
		} else {
			return new ArrayList<Employee>();
		}
	}
	
	@Cacheable(cacheNames = "Emp-Cache",key = "#sup")
	public List<String> getSupervisee(String sup) {
		//List<String> supList = repository.findBySupervisor(sup);

		//if (supList.size() > 0) {
		//	return supList;
		//} else {
			return new ArrayList<String>();
		//}
	}
	
	@Cacheable(cacheNames = "Emp-Cache",key = "#BU")
	public BigDecimal findTotalSalaryByBU(String BU) {
		//return new BigDecimal(1000);
		logger.info("findTotalSalaryByBU fetching from DB ++++++++++++++++==");
		 return repository.findTotalSalaryByBU(BU);
	}
	
	@Cacheable(cacheNames = "Emp-Cache",key = "#tSalBysup")
	public BigDecimal findTotalSalaryBySup(String tSalBysup) {
		//return new BigDecimal(1000);
		 logger.info("findTotalSalaryBySup fetching from DB ++++++++++++++++==");
		 return repository.findTotalSalaryBySupervisor(tSalBysup);
	}
	
	
	@Cacheable(cacheNames = "Emp-Cache",key = "#tSalByPlace")
	public BigDecimal findTotalSalaryByPlace(String tSalByPlace) {
		//return new BigDecimal(1000);
		 logger.info("findTotalSalaryByPlace fetching from DB ++++++++++++++++==");
		 return repository.findTotalSalaryByPlace(tSalByPlace);
	}
	
	@Cacheable(cacheNames = "Emp-Cache",key = "#salRangeByPlace")
	public List<Object[]> findSalaryRangeByPlace(String salRangeByPlace) {
		//return new ArrayList<>();
		 logger.info("findSalaryRangeByPlace fetching from DB ++++++++++++++++==");
		 return repository.findSalaryRangeByPlace(salRangeByPlace);
	}

	
	@PostConstruct
	private void setupData() {
		logger.info("Inside setupData ----------- loading data from csv file to DB");
		setupUsers();
	}

	private void setupUsers() {

		List<Employee> empList = CSVDataLoader.loadObjectList(Employee.class, AppCont.EMP_CSV_FILE);

		if (empList != null && empList.size() > 0) {
			repository.saveAll(empList);
		}
	}

	

}
