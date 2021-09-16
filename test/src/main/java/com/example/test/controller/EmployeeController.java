package com.example.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.model.Employee;
import com.example.test.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@GetMapping(value="/getAllEmployee")
	public ResponseEntity<Object> getAllEmployee(){
		return employeeService.getAllEmployee();
	}
	
	@GetMapping(value="/getAllEmployeeByType")
	public ResponseEntity<Object> getAllEmployeeByType(@RequestParam(required=false) String type,@RequestParam(required=false) String value){
		return employeeService.getAllEmployeeByType(type, value);
	}
	
	@GetMapping(value="/getRecordsByCount/{count}")
	public ResponseEntity<Object> getAllEmployee(@PathVariable Integer count){
		return employeeService.getAllEmployee();
	}
	
	@PostMapping(value="/insertRecordAtEnd")
	public ResponseEntity<Object> saveDataAtEnd(@RequestBody List<Employee> records){
		return employeeService.saveDataAtEnd(records);
	}
	
	@DeleteMapping(value="/deleteRecords")
	public ResponseEntity<Object> deleteRecords(@RequestParam String type,@RequestParam String value){
		
		return employeeService.deleteRecords(type, value);
	}
	
}
