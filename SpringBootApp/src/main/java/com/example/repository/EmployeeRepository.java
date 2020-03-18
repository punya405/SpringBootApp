package com.example.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	  @Query("select e from Employee e where e.place =:place")
	  List<Employee> findByPlace(@Param("place") String place);
	  
	  
	
	/*
	 * @Query("DECLARE @EmpID INT\r\n" + "SET @EmpID  = :SupervisorID \r\n" +
	 * ";WITH SubOrdinates AS\r\n" + "(\r\n" + "	SELECT	EmployeeID \r\n" +
	 * "	FROM	Employee	\r\n" + "	WHERE	EmployeeID = @EmpID\r\n" +
	 * "	UNION ALL\r\n" + "	SELECT	Employee.EmployeeID \r\n" +
	 * "	FROM	Employee\r\n" + "	JOIN	SubOrdinates\r\n" +
	 * "	ON		Employee.SupervisorID = SubOrdinates.EmployeeID\r\n" + ")\r\n" +
	 * "SELECT	EmployeeID\r\n" + "FROM	SubOrdinates\r\n" +
	 * "WHERE	EmployeeID<> @EmpID") List<String>
	 * findBySupervisor(@Param("SupervisorID")String SupervisorID);
	 */
	 
	  
	  
	  @Query("select sum(salary) from Employee where bunit =:BU")
	  BigDecimal findTotalSalaryByBU(@Param("BU") String BU);
	  
	  @Query("select sum(salary) from Employee where sId =:sup")
	  BigDecimal findTotalSalaryBySupervisor(@Param("sup") String sup);
	  
	  @Query("select sum(salary) from Employee where place=:place") BigDecimal
	  findTotalSalaryByPlace(@Param("place") String place);
	  
	  @Query("select min(salary) as min,max(salary) as max from Employee where place=:place") 
	  List<Object[]> findSalaryRangeByPlace(@Param("place") String place);
	 

}
