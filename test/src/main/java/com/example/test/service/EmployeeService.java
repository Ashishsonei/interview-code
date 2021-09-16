package com.example.test.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.test.model.Employee;
import com.example.test.utility.EmployeeUtility;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

@Service
public class EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	@Value("${csv.file.path}")
	private String filePath;

	public ResponseEntity<Object> getAllEmployee() {
		ResponseEntity<Object> response;
		List<Employee> empList = new ArrayList<Employee>();
		Path pathFile = Paths.get(filePath);
		logger.info("Path of file::" + pathFile);
		try {
			BufferedReader br = Files.newBufferedReader(pathFile);
			String record = br.readLine();
			logger.info("Records Line value::" + record);
			while (record != null) {
				String[] coloumns = record.split(",");
				Employee emp = EmployeeUtility.getEmployeeFields(coloumns);
				empList.add(emp);
				record = br.readLine();
			}
			response = new ResponseEntity<Object>(empList, HttpStatus.OK);
		} catch (IOException e) {
			logger.info("Exception while getting Employee Records");
			response = new ResponseEntity<Object>("Error while reading data", HttpStatus.EXPECTATION_FAILED);
			e.printStackTrace();
		}
		return response;
	}

	public ResponseEntity<Object> getAllEmployeeByType(String type, String value) {
		ResponseEntity<Object> response;
		List<Employee> empList = new ArrayList<Employee>();
		if (type == null || type.isEmpty() || value == null || value.isEmpty()) {
			return new ResponseEntity<Object>("Please provide valid input type", HttpStatus.BAD_REQUEST);
		}
		int coloumnIndex = EmployeeUtility.getColoumnIndex(type);
		Path pathFile = Paths.get(filePath);
		logger.info("Path of file::" + pathFile);
		try {
			BufferedReader br = Files.newBufferedReader(pathFile);
			String record = br.readLine();
			logger.info("Records Line value::" + record);
			while (record != null) {
				String[] coloumns = record.split(",");
				if (coloumns[coloumnIndex].equalsIgnoreCase(value)) {
					Employee emp = EmployeeUtility.getEmployeeFields(coloumns);
					empList.add(emp);
				}
				record = br.readLine();
			}
			response = new ResponseEntity<Object>(empList, HttpStatus.OK);
		} catch (IOException e) {
			logger.info("Exception while getting Employee Records");
			response = new ResponseEntity<Object>("Error while reading data", HttpStatus.EXPECTATION_FAILED);
			e.printStackTrace();
		}

		return response;
	}

	public ResponseEntity<Object> getCountOfRecords(Integer count) {
		ResponseEntity<Object> response;
		List<Employee> empList = new ArrayList<Employee>();
		Path pathFile = Paths.get(filePath);
		logger.info("Path of file::" + pathFile);
		try {
			BufferedReader br = Files.newBufferedReader(pathFile);
			String record = br.readLine();
			int temp = 0;
			logger.info("Records Line value::" + record);
			while (record != null) {
				if (temp == count)
					break;
				String[] coloumns = record.split(",");
				Employee emp = EmployeeUtility.getEmployeeFields(coloumns);
				empList.add(emp);
				temp += 1;
				record = br.readLine();
			}
			response = new ResponseEntity<Object>(empList, HttpStatus.OK);
		} catch (IOException e) {
			logger.info("Exception while getting Employee Records");
			response = new ResponseEntity<Object>("Error while reading data", HttpStatus.EXPECTATION_FAILED);
			e.printStackTrace();
		}
		return response;
	}

	public ResponseEntity<Object> saveDataAtEnd(List<Employee> records) {
		logger.info("writing records in file");
		ResponseEntity<Object> response;
		// FileWriter fileWriter=null;
		if (records.size() == 0) {
			return new ResponseEntity<Object>("Please provide valid input type", HttpStatus.BAD_REQUEST);
		}

		try {
			CSVWriter writer = new CSVWriter(new FileWriter(filePath, true));
			for (Employee emp : records) {
				List<String> emplist = new ArrayList<String>();
				logger.info("records are" + emp.toString());
				emplist.add(emp.getId());
				emplist.add(emp.getName());
				emplist.add(String.valueOf(emp.getAge()));
				emplist.add(String.valueOf(emp.getExperience()));
				String doj = emp.getDoj();
				try {
					doj = EmployeeUtility.getFormatedDateAsString(doj, "DD-MM-YYYY", "YYYY-MM-DD");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				emplist.add(doj);
				String[] empdata = new String[emplist.size()];
				logger.info("employee data is::" + empdata.toString());
				empdata = emplist.toArray(empdata);
				writer.writeNext(empdata);
			}
			writer.close();
			response = new ResponseEntity<Object>(records, HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			response = new ResponseEntity<Object>("Error while writing  data", HttpStatus.EXPECTATION_FAILED);
			e.printStackTrace();
		}
		return response;
	}

	public ResponseEntity<Object> deleteRecords(String type, String value) {
		ResponseEntity<Object> response;
		List<Employee> responseList = new ArrayList<Employee>();
		if (type == null || type.isEmpty() || value == null || value.isEmpty()) {
			return new ResponseEntity<Object>("Please provide valid input type", HttpStatus.BAD_REQUEST);
		}
		int coloumnIndex = EmployeeUtility.getColoumnIndex(type);
		try {
			CSVReader reader = new CSVReader(new FileReader(filePath));
			List<String[]> allRecords = reader.readAll();
			logger.info("Number Of records before Deletion:" + allRecords.size());
			List<String[]> toRemove = new ArrayList<>();
			for (String[] result : allRecords) {
				String resultValue = result[coloumnIndex];
				if (resultValue.equalsIgnoreCase(value)) {
					Employee emp = EmployeeUtility.getEmployeeFields(result);
					responseList.add(emp);
					toRemove.add(result);
				}
			}
			logger.info("Number Of records has to Delete:" + toRemove.size());
			allRecords.removeAll(toRemove);
			logger.info("Number Of records after Deletion:" + allRecords.size());
			CSVWriter writer = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER);
			for (String[] row : allRecords) {
				writer.writeNext(row);
			}
			// writer.writeAll(allRecords);
			writer.close();
			response = new ResponseEntity<Object>(responseList, HttpStatus.OK);
		} catch (IOException e) {
			logger.info("Exception while deleting Employee Records");
			response = new ResponseEntity<Object>("Error while deleting  data", HttpStatus.EXPECTATION_FAILED);
			e.printStackTrace();
		}

		return response;
	}
}
