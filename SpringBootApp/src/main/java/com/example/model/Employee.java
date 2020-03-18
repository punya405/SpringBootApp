package com.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="EMPLOYEES")
@ApiModel(description = "All details about the Employee. ")
public class Employee implements Serializable {

	private static final long serialVersionUID = -5591946702257140852L;

	@Id
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated employee ID")
	private Long empId;
	
	@ApiModelProperty(notes = "The employee name")
	@Column(name="emp_Name")
	private String empName;
	
	@ApiModelProperty(notes = "The employee Title")
	@Column(name="title")
	private String title;
	
	@ApiModelProperty(notes = "The employee's Business unit")
	@Column(name="bunit")
	private String bunit;
	
	@ApiModelProperty(notes = "The employee place")
	@Column(name="place")
	private String place;
	
	@ApiModelProperty(notes = "The employee Supervisor ID")
	@Column(name="sId")
	private String sId;
	
	
	@ApiModelProperty(notes = "The employee competencies")
	@Column(name="competencies")
	private String competencies;
	
	@ApiModelProperty(notes = "The employee salary")
	@Column(name="salary")
	private Double salary;
	
	public Employee() {
		
	}
	
	public Employee(Long empId, String empName, String title, String bunit, String place, String sId,
			String competencies, Double salary) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.title = title;
		this.bunit = bunit;
		this.place = place;
		this.sId = sId;
		this.competencies = competencies;
		this.salary = salary;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBunit() {
		return bunit;
	}
	public void setBunit(String bunit) {
		this.bunit = bunit;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
	}
	public String getCompetencies() {
		return competencies;
	}
	public void setCompetencies(String competencies) {
		this.competencies = competencies;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public Employee salaryIncreamentBy(Double per) {
		 this.setSalary(this.getSalary()+this.getSalary()*per*0.01);
		 return this;
	}
	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", title=" + title + ", bunit=" + bunit
				+ ", place=" + place + ", sId=" + sId + ", competencies=" + competencies + ", salary=" + salary + "]";
	}
	
	
}
