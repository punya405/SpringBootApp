package com.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.controller.EmployeeController;
import com.example.model.Employee;
import com.example.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)
class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService empService;
	
	List<Employee> mockEmp=new ArrayList<Employee>() ;
	List<Employee> mockEmpByPlace=new ArrayList<Employee>() ;
	
	List<String> mockSupervisee=new ArrayList<>();
	List<Employee> mockUpdatedEmpPlaceNSal=new ArrayList<Employee>();
	
	Employee updatedEmp=new Employee(152388L, "punyasloka", "senior java developer", "Tel", "Bangalore", "17452", "java", 80000d);
	
	
	
	@Before
	public void setup() {
		mockEmp.add(new Employee(152388L, "Punyasloka", "Java Developer", "BFS", "Bangalore", "17625", "Java", 30000d));
		mockEmp.add(new Employee(152389L, "Shivashis", ".Net Developer", "Tel", "Delhi", "17621", ".Net", 40000d));
		mockEmp.add(new Employee(152382L, "suraj", "Automation Developer", "Retail", "Bangalore", "17625", "Automation", 30000d));
		mockEmp.add(new Employee(152383L, "subrat", "Python Developer", "Ecomm", "Chennai", "13625", "Python", 20000d));
		mockEmp.add(new Employee(152323L, "anis", "SQL Developer", "healthcare", "mumbai", "17645", "SQl", 70000d));
		
		
		mockEmpByPlace.add(new Employee(152388L, "Punyasloka", "Java Developer", "BFS", "Bangalore", "17625", "Java", 30000d));
		mockEmpByPlace.add(new Employee(152382L, "suraj", "Automation Developer", "Retail", "Bangalore", "17625", "Automation", 30000d));
		
		mockSupervisee.add("17625");
		
		mockUpdatedEmpPlaceNSal.add(new Employee(152388L, "Punyasloka", "Java Developer", "BFS", "Bangalore", "17625", "Java", 33000d));
		mockUpdatedEmpPlaceNSal.add(new Employee(152382L, "suraj", "Automation Developer", "Retail", "Bangalore", "17625", "Automation", 33000d));
	}
	
	@Test
	public void getAllEmployeesTest() throws Exception {
		Mockito.when(
				empService.getAllEmployees()).thenReturn(mockEmp);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/employees").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "[{empID:152388,empName:Punyasloka,title:Java Developer,bunit:BFS,place:Bangalore,sId:17625,competencies:Java,salary:30000},"
				+ "{empID:152389,empName:Shivashis,title:.NET Developer,bunit:Tel,place:Delhi,sId:17621,competencies:.NET,salary:40000}"
				+ "{empID:152382,empName:suraj,title:Automation Developer,bunit:Retail,place:Bangalore,sId:17625,competencies:Automation,salary:30000}"
				+ "{empID:152383,empName:subrat,title:Python Developer,bunit:Ecomm,place:Chennai,sId:13625,competencies:Python,salary:20000}"
				+"{empID:152323,empName:anis,title:SQL Developer,bunit:healthcare,place:mumbai,sId:17645,competencies:SQL,salary:70000}"
				+ "]";

		// {"id":"Course1","name":"Spring","description":"10 Steps, 25 Examples and 10K Students","steps":["Learn Maven","Import Project","First Example","Second Example"]}

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		
	}

	
	@Test
	public void updateEmployeeTest() throws Exception {
		Mockito.when(
				empService.createUpdateEmployee(Mockito.any(Employee.class))).thenReturn(updatedEmp);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
				"/employees/update/employeeDetails").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "{empID:152388,empName:Punyasloka,title:senior java developer,bunit:Tel,place:Bangalore,sId:17452,competencies:Java,salary:80000}";

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		
	}
	
	@Test
	public void updateUserByPlaceNSalaryTest() throws Exception {
		Mockito.when(
				empService.getAllEmployeesByPlace(Mockito.anyString())).thenReturn(mockUpdatedEmpPlaceNSal);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
				"/employees/employee/place/Bangalore/salary/10").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "[{empID:152388,empName:Punyasloka,title:Java Developer,bunit:BFS,place:Bangalore,sId:17625,competencies:Java,salary:33000},"
				+ "{empID:152382,empName:suraj,title:Automation Developer,bunit:Retail,place:Bangalore,sId:17625,competencies:Automation,salary:33000}";
			
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		
	}
	
	@Test
	public void getEmployeeByPlaceTest() throws Exception {
		Mockito.when(
				empService.getAllEmployeesByPlace(Mockito.anyString())).thenReturn(mockEmpByPlace);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/employees/place/Bangalore").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "[{empID:152388,empName:Punyasloka,title:Java Developer,bunit:BFS,place:Bangalore,sId:17625,competencies:Java,salary:30000},"
				+ "{empID:152382,empName:suraj,title:Automation Developer,bunit:Retail,place:Bangalore,sId:17625,competencies:Automation,salary:30000}";
				

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		
	}
	
	
	@Test
	public void getSuperviseeTest() throws Exception {
		Mockito.when(
				empService.getSupervisee(Mockito.anyString())).thenReturn(mockSupervisee);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/employees/supervisee/17625").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "[17625]";
				

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		
	}
	
	@Test
	public void getTotalSalaryByBUTest() throws Exception {
		BigDecimal mockSalByBU=new BigDecimal(30000);
		Mockito.when(
				empService.findTotalSalaryByBU(Mockito.anyString())).thenReturn(mockSalByBU);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/employees/tSal/BU/BFS").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "30000";
				

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		
	}
	
	@Test
	public void getTotalSalaryByPlaceTest() throws Exception {
		BigDecimal mockSalByPlace=new BigDecimal(60000);
		Mockito.when(
				empService.findTotalSalaryByPlace(Mockito.anyString())).thenReturn(mockSalByPlace);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/employees/tSal/place/bangalore").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "60000";
				

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		
	}
	
	@Test
	public void getTotalSalaryBySupTest() throws Exception {
		BigDecimal mockSalBySup=new BigDecimal(60000);
		Mockito.when(
				empService.findTotalSalaryBySup(Mockito.anyString())).thenReturn(mockSalBySup);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/employees/tSal/supervisor/17625").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "60000";
				

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		
	}
	
	@Test
	public void getSalaryRangeByPlaceTest() throws Exception {
		List<Object[]> mockSalrangebyPlace=new ArrayList<Object[]>();
		Object[] salRange=new Object[] {30000,30000};
		mockSalrangebyPlace.add(salRange);
		
		Mockito.when(
				empService.findSalaryRangeByPlace(Mockito.anyString())).thenReturn(mockSalrangebyPlace);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/employees/sal/range/place//Bangalore").accept(
				MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "[30000,30000]";
				

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
		
	}
}
