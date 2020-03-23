package com.example;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import com.example.service.EmployeeService;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository empRepo;

	@InjectMocks
	private EmployeeService empSer;

	@Test
	public void getAllEmployeesTest() {
		// given
		List<Employee> mockEmp = new ArrayList<Employee>();

		mockEmp.add(new Employee(152388L, "Punyasloka", "Java Developer", "BFS", "Bangalore", "17625", "Java", 30000d));
		mockEmp.add(new Employee(152389L, "Shivashis", ".Net Developer", "Tel", "Delhi", "17621", ".Net", 40000d));
		mockEmp.add(new Employee(152382L, "suraj", "Automation Developer", "Retail", "Bangalore", "17625", "Automation",
				30000d));
		mockEmp.add(new Employee(152383L, "subrat", "Python Developer", "Ecomm", "Chennai", "13625", "Python", 20000d));
		mockEmp.add(new Employee(152323L, "anis", "SQL Developer", "healthcare", "mumbai", "17645", "SQl", 70000d));

		when(empRepo.findAll()).thenReturn(mockEmp);

		List<Employee> empList = empSer.getAllEmployees();

		assertEquals(5, empList.size());

	}
	
	@Test
	public void updateEmployeeTest() throws Exception {
		
		Employee updatedEmp=new Employee(152388L, "punyasloka", "senior java developer", "Tel", "Bangalore", "17452", "java", 80000d);
		Mockito.when(
				empRepo.save(Mockito.any(Employee.class))).thenReturn(updatedEmp);
		
       Employee actualUpdateEmp= empSer.createUpdateEmployee(updatedEmp);
		assertEquals(updatedEmp.getBunit(), actualUpdateEmp.getBunit());
		
	}
	
	@Test
	public void getEmployeeByPlaceTest() throws Exception {
		List<Employee> mockEmpByPlace=new ArrayList<Employee>() ;
		mockEmpByPlace.add(new Employee(152388L, "Punyasloka", "Java Developer", "BFS", "Bangalore", "17625", "Java", 30000d));
		mockEmpByPlace.add(new Employee(152382L, "suraj", "Automation Developer", "Retail", "Bangalore", "17625", "Automation", 30000d));
		
		Mockito.when(
				empRepo.findByPlace(Mockito.anyString())).thenReturn(mockEmpByPlace);
		List<Employee> empList = empSer.getAllEmployeesByPlace("Bangalore");
		System.out.println(empList);
		String expected = "[{empID:152388,empName:Punyasloka,title:Java Developer,bunit:BFS,place:Bangalore,sId:17625,competencies:Java,salary:30000},"
				+ "{empID:152382,empName:suraj,title:Automation Developer,bunit:Retail,place:Bangalore,sId:17625,competencies:Automation,salary:30000}]";
				

		JSONAssert.assertEquals(expected.toString(), empList.toString()
				, false);
		
	}
	
	@Test
	public void getSuperviseeTest() throws Exception {
		/*
		 * Mockito.when( empRepo.find(Mockito.anyString())).thenReturn(mockSupervisee);
		 * 
		 * RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
		 * "/employees/supervisee/17625").accept( MediaType.APPLICATION_JSON); MvcResult
		 * result = mockMvc.perform(requestBuilder).andReturn();
		 * System.out.println(result.getResponse()); String expected = "[17625]";
		 * 
		 * 
		 * JSONAssert.assertEquals(expected, result.getResponse() .getContentAsString(),
		 * false);
		 */
	}
	
	@Test
	public void getTotalSalaryByBUTest() throws Exception {
		BigDecimal mockSalByBU=new BigDecimal(30000);
		Mockito.when(
				empRepo.findTotalSalaryByBU(Mockito.anyString())).thenReturn(mockSalByBU);
		
		BigDecimal tsal = empSer.findTotalSalaryByBU("BFS");
		System.out.println("Actual Salary TotalSalaryByBU "+tsal);
		String expected = "30000";
				

		JSONAssert.assertEquals(expected, tsal.toString(), false);
		
	}
	
	@Test
	public void getTotalSalaryByPlaceTest() throws Exception {
		BigDecimal mockSalByPlace=new BigDecimal(60000);
		Mockito.when(
				empRepo.findTotalSalaryByPlace(Mockito.anyString())).thenReturn(mockSalByPlace);
		BigDecimal tsal = empSer.findTotalSalaryByPlace("Bangalore");
		System.out.println("Actual Salary TotalSalaryByPlace "+tsal);
		String expected = "60000";
				

		JSONAssert.assertEquals(expected, tsal.toString(), false);
		
	}
	
	@Test
	public void getTotalSalaryBySupTest() throws Exception {
		BigDecimal mockSalBySup=new BigDecimal(60000);
		Mockito.when(
				empRepo.findTotalSalaryBySupervisor(Mockito.anyString())).thenReturn(mockSalBySup);
		BigDecimal tsal = empSer.findTotalSalaryBySup("17625");
		System.out.println("Actual Salary TotalSalaryBySup "+tsal);
		String expected = "60000";
				

		JSONAssert.assertEquals(expected,tsal.toString(), false);
		
	}
	
	@Test
	public void getSalaryRangeByPlaceTest() throws Exception {
		List<Object[]> mockSalrangebyPlace=new ArrayList<Object[]>();
		Object[] salRange=new Object[] {30000,30000};
		mockSalrangebyPlace.add(salRange);
		
		Mockito.when(
				empRepo.findSalaryRangeByPlace(Mockito.anyString())).thenReturn(mockSalrangebyPlace);
		
		List<Object[]> actualSalRange = empSer.findSalaryRangeByPlace("Bangalore");
		System.out.println("Actual Salary RangeByPlace "+actualSalRange);
		String expected = "[30000,30000]";
				

		JSONAssert.assertEquals(expected,actualSalRange.toString(), false);
		
	}
}
