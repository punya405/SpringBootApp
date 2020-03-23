package com.example;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {
	
	@Autowired
    private TestEntityManager testEntityManager;
	
	@Autowired
    private EmployeeRepository empRespository;
	
	 @Before
	    public void setUp(){
	        // given
		 List<Employee> mockEmp = new ArrayList<Employee>();

			mockEmp.add(new Employee(152388L, "Punyasloka", "Java Developer", "BFS", "Bangalore", "17625", "Java", 30000d));
			mockEmp.add(new Employee(152389L, "Shivashis", ".Net Developer", "Tel", "Delhi", "17621", ".Net", 40000d));
			mockEmp.add(new Employee(152382L, "suraj", "Automation Developer", "Retail", "Bangalore", "17625", "Automation",
					30000d));
			mockEmp.add(new Employee(152383L, "subrat", "Python Developer", "Ecomm", "Chennai", "13625", "Python", 20000d));
			mockEmp.add(new Employee(152323L, "anis", "SQL Developer", "healthcare", "mumbai", "17645", "SQl", 70000d));

	        testEntityManager.persist(mockEmp);
	    }

	 @Test
		public void getAllEmployeesTest() {


			List<Employee> empList = empRespository.findAll();

			assertEquals(5, empList.size());

		}
	 
	 @Test
		public void updateEmployeeTest() throws Exception {
			
			Employee updatedEmp=new Employee(152388L, "punyasloka", "senior java developer", "Tel", "Bangalore", "17452", "java", 80000d);
			
	       Employee actualUpdateEmp= empRespository.save(updatedEmp);
			assertEquals(updatedEmp.getBunit(), actualUpdateEmp.getBunit());
			
		}
	 
	 
	 @Test
		public void getEmployeeByPlaceTest() throws Exception {
			
			List<Employee> empList = empRespository.findByPlace("Bangalore");
			System.out.println(empList);
			String expected = "[{empID:152388,empName:Punyasloka,title:Java Developer,bunit:BFS,place:Bangalore,sId:17625,competencies:Java,salary:30000},"
					+ "{empID:152382,empName:suraj,title:Automation Developer,bunit:Retail,place:Bangalore,sId:17625,competencies:Automation,salary:30000}]";
					

			JSONAssert.assertEquals(expected.toString(), empList.toString()
					, false);
			
		}
	 @Test
		public void getTotalSalaryByBUTest() throws Exception {
			
			BigDecimal tsal = empRespository.findTotalSalaryByBU("BFS");
			System.out.println("Actual Salary TotalSalaryByBU "+tsal);
			String expected = "30000";
					

			JSONAssert.assertEquals(expected, tsal.toString(), false);
			
		}
		
		@Test
		public void getTotalSalaryByPlaceTest() throws Exception {
			BigDecimal tsal = empRespository.findTotalSalaryByPlace("Bangalore");
			System.out.println("Actual Salary TotalSalaryByPlace "+tsal);
			String expected = "60000";
					

			JSONAssert.assertEquals(expected, tsal.toString(), false);
			
		}
		
		@Test
		public void getTotalSalaryBySupTest() throws Exception {
			BigDecimal tsal = empRespository.findTotalSalaryBySupervisor("17625");
			System.out.println("Actual Salary TotalSalaryBySup "+tsal);
			String expected = "60000";
					

			JSONAssert.assertEquals(expected,tsal.toString(), false);
			
		}
		
		@Test
		public void getSalaryRangeByPlaceTest() throws Exception {
			List<Object[]> actualSalRange = empRespository.findSalaryRangeByPlace("Bangalore");
			System.out.println("Actual Salary RangeByPlace "+actualSalRange);
			String actual="["+actualSalRange.get(1)[0]+","+actualSalRange.get(1)[1]+"]";
			String expected = "[30000,30000]";
			JSONAssert.assertEquals(expected,actual, false);
			
		}
	 
}
