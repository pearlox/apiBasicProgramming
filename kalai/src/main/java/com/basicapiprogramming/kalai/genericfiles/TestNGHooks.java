package com.basicapiprogramming.kalai.genericfiles;

import java.util.Properties;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class TestNGHooks extends HTMLReporter {
	private String testCaseName;
	private String testDescription;
	private String nodes;
	private String author;
	private String category;
	public Properties properties;
	
	public String getTestCaseName() {
		return testCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	public String getTestDescription() {
		return testDescription;
	}
	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}
	public String getNodes() {
		return nodes;
	}
	public void setNodes(String nodes) {
		this.nodes = nodes;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@BeforeSuite
	public void beforeSuite() {
		startReport();
	}
	
	@AfterSuite
	public void afterSuite() {
		endResult();
	}
	
	@BeforeMethod
	public void beforeMethod() {
		startTestCase(this.getClass().getSimpleName(), testDescription);
		properties = Settings.getInstance();
	}
}
