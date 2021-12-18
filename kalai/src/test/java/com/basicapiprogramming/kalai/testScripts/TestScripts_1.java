package com.basicapiprogramming.kalai.testScripts;

import org.testng.annotations.*;

import com.basicapiprogramming.kalai.genericfiles.RestAssuredBaseClass;

import io.restassured.response.Response;

public class TestScripts_1 extends RestAssuredBaseClass {
	
	@BeforeTest
	public void setUp() {
		setTestDescription("Test Description");
		setNodes("Nodes");
		setAuthor(System.getProperty("user.name"));
		setCategory("Category");
	}
	
	@Test
	public void test() {
		Response response = get("http://dummy.restapiexample.com/api/v1/employees");
		System.out.println(response.asPrettyString());
	}
}
