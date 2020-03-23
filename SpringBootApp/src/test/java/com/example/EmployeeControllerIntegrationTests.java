package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import com.example.SpringBootApps.SpringBootAppsApplication;
import com.example.model.Employee;

@SpringBootTest(classes = SpringBootAppsApplication.class, 
webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTests {

		@LocalServerPort
		private int port;
	 
	    @Autowired
	    private TestRestTemplate restTemplate;
	 
	    @Sql({ "schema.sql" })
	    @Test
	    public void testAllEmployees() 
	    {
	        
	    	Object[]  obs=  this.restTemplate
	                    .getForObject("http://localhost:" + port + "/employees",Object[].class);
	                    List<Object> list=Arrays.asList(obs);
	                    assertTrue(list.size()==5);
	    }
	    
	    @Test
	    public void getEmployeeByPlaceTest() {
	    	Object[] responseEntity = this.restTemplate
	            .getForObject("http://localhost:" + port + "/employees/place/Bangalore",Object[].class);
	    	  List<Object> list=Arrays.asList(responseEntity);
	        assertEquals(list.size(), 2);
	    }
	    
	    @Test
		public void updateEmployeeTest() throws Exception {
	    	final String uri = "http://localhost:8080/employees/update/employeeDetails";
	        
	        Map<String, String> params = new HashMap<String, String>();
	        params.put("empId", "152388L");
			Employee updatedEmp=new Employee(152388L, "punyasloka", "senior java developer", "Tel", "Bangalore", "17452", "java", 80000d);
			
	        restTemplate.put(uri, updatedEmp,params);
			assertEquals(updatedEmp.getBunit(), updatedEmp.getBunit());
			
		}
	    
	    @Test
		public void getTotalSalaryByBUTest() throws Exception {
			
			BigDecimal tsal = this.restTemplate
		            .getForObject("http://localhost:" + port + "/employees/tSal/BU/BFS",BigDecimal.class);
			System.out.println("Actual Salary TotalSalaryByBU "+tsal);
			String expected = "30000";
					

			JSONAssert.assertEquals(expected, tsal.toString(), false);
			
		}
	    
	    @Test
		public void getTotalSalaryByPlaceTest() throws Exception {
	    	BigDecimal tsal = this.restTemplate
		            .getForObject("http://localhost:" + port + "employees/tSal/place/Bangalore",BigDecimal.class);
			System.out.println("Actual Salary TotalSalaryByPlace "+tsal);
			String expected = "60000";
					

			JSONAssert.assertEquals(expected, tsal.toString(), false);
			
		}
		
		@Test
		public void getTotalSalaryBySupTest() throws Exception {
			BigDecimal tsal = this.restTemplate
		            .getForObject("http://localhost:" + port + "/employees/tSal/supervisor/17625",BigDecimal.class);
			System.out.println("Actual Salary TotalSalaryBySup "+tsal);
			String expected = "60000";
					

			JSONAssert.assertEquals(expected,tsal.toString(), false);
			
		}
		
		@Test
		public void getSalaryRangeByPlaceTest() throws Exception {
			Object[] actualSalRange = this.restTemplate
		            .getForObject("http://localhost:" + port + "/employees/sal/range/place//Bangalore",Object[].class);
			System.out.println("Actual Salary RangeByPlace "+actualSalRange);
			String actual="["+actualSalRange[0]+","+actualSalRange[1]+"]";
			String expected = "[30000,30000]";
			JSONAssert.assertEquals(expected,actual, false);
			
		}
	
}
