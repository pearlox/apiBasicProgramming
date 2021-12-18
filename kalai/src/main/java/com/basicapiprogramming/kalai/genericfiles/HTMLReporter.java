package com.basicapiprogramming.kalai.genericfiles;

import java.io.File;
import java.util.Objects;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class HTMLReporter {

	private static ExtentReports extent;
	private static ExtentTest test, node;
	private static final ThreadLocal<ExtentTest> tlTest = new ThreadLocal<>();
	private static final ThreadLocal<ExtentTest> tlNode = new ThreadLocal<>();
	private static String reportPath;
	private static final String USER_DIRECTORY = "user.dir";
	
	protected static void startReport() {
		if(Objects.isNull(extent)) {
			if(System.getProperty("ReportPath")!= null) {
				reportPath = System.getProperty("ReportPath");
			} else {
				if(prop().getProperty("DefaultReport").equalsIgnoreCase("Yes")) {
					reportPath = System.getProperty(USER_DIRECTORY)+ Util.getFileSeparator() + 
							"Results";
				} else {
					reportPath = createFolderWithTimeStamp() + Util.getFileSeparator() + "HTML Results";
				}
			}
			extent = new ExtentReports();
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath+ "/Dashboard.html");
			htmlReporter.loadConfig(System.getProperty(USER_DIRECTORY)+ Util.getFileSeparator()+"extent-config.xml");
			extent.attachReporter(htmlReporter);
		}
	}
	
	private static Properties prop() {
		return Settings.getInstance();
	}
	
	private static String createFolderWithTimeStamp() {
		String reportPathWithTimeStamp;
		String timeStamp = "Run_"+ Util.getCurrentFormattedTime(prop().getProperty("DateFormatString"))
			.replace(" ", "_").replace(":", "-");
		reportPathWithTimeStamp = System.getProperty(USER_DIRECTORY)+Util.getFileSeparator()+"Results"
			+Util.getFileSeparator()+timeStamp;
		(new File(reportPathWithTimeStamp)).mkdirs();
		return reportPathWithTimeStamp;
	}
	
	protected ExtentTest startTestCase(String testCaseName, String testDescription) {
		test = extent.createTest(testCaseName,testDescription);
		tlTest.set(test);
		return test;
	}
	
	protected ExtentTest startTestModule(String nodes, String category, String author) {
		startTestModule(nodes);
		assignAuthor(author);
		assignCategory(category);
		return node;
	}
	
	protected ExtentTest startTestModule(String nodes) {
		node = tlTest.get().createNode(nodes);
		tlNode.set(node);
		return node;
	}
	
	private void assignAuthor(String author) {
		tlNode.get().assignAuthor(author);
	}
	
	private void assignCategory(String category) {
		tlNode.get().assignCategory(category);
	}
	
	protected void endResult() {
		if(Objects.nonNull(extent))
			extent.flush();
		tlTest.remove();
		tlNode.remove();
	}
	
	protected static void reportLog(String desc, Status status) {
		switch(status) {
		case PASS : tlNode.get().pass(desc); break;
		case FAIL : tlNode.get().fail(desc);  break;
		case WARNING : tlNode.get().warning(desc); break;
		case SKIP : tlNode.get().skip(desc); break;
		default : tlNode.get().info(desc);
		}
	}
	
	protected static void reportLog(String desc, String json, Status status) {
		Markup markup = MarkupHelper.createCodeBlock(json, CodeLanguage.JSON);
		switch(status) {
		case PASS : tlNode.get().pass(markup); break;
		case FAIL : tlNode.get().fail(markup); break;
		case WARNING : tlNode.get().warning(markup); break;
		case SKIP : tlNode.get().skip(markup); break;
		default : tlNode.get().info(markup);
		}
	}
}
