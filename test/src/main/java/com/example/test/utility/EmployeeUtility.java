package com.example.test.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.example.test.model.Employee;

public class EmployeeUtility {

	public static Employee getEmployeeFields(String[] coloumns) {
		Employee emp = new Employee();
		String id = coloumns[0];
		String name = coloumns[1];
		int age = Integer.parseInt(coloumns[2]);
		int exp = Integer.parseInt(coloumns[3]);
		String dateOfJoin = coloumns[4];
		emp.setId(id);
		emp.setName(name);
		emp.setAge(age);
		emp.setExperience(exp);
		emp.setDoj(dateOfJoin);
		return emp;
	}

	public static int getColoumnIndex(String type) {
		int index = 0;
		switch (type) {
		case "name":
			index = 1;
			break;
		case "age":
			index = 2;
			break;
		case "experience":
			index = 3;
			break;
		case "doj":
			index = 4;
			break;
		}
		return index;
	}

	public static String getFormatedDateAsString(String strDate, String fromFormate, String toFormate)
			throws ParseException {
		String formatedDate = "";
			DateFormat formatter = new SimpleDateFormat(fromFormate);
			Date newdate = (Date) formatter.parse(strDate);
			SimpleDateFormat newFormat = new SimpleDateFormat(toFormate);
			formatedDate = newFormat.format(newdate);
		return formatedDate;
	}
}
