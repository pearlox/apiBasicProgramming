package com.basicapiprogramming.kalai.testScripts;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.basicapiprogramming.kalai.genericfiles.FrameworkException;
import com.basicapiprogramming.kalai.genericfiles.HTMLReporter;
import com.basicapiprogramming.kalai.genericfiles.RestAssuredBaseClass;
import com.basicapiprogramming.kalai.restfulbooker.EndPoint;
import com.basicapiprogramming.kalai.restfulbooker.RestfulBookerHelperClass;

import io.restassured.response.Response;

public class RestfulBookerTestScripts extends RestAssuredBaseClass {

	private RestfulBookerHelperClass restfulBookerHelperClass = new RestfulBookerHelperClass();
	private int bookingID;

	private String payload = "{ \"firstname\" : \"{{FirstName}}\", \"lastname\" : \"{{LastName}}\","
			+ "\"totalprice\" : {{TotalPrice}}, \"depositpaid\" : {{DepositPaid}}, "
			+ "\"bookingdates\" : { \"checkin\" : \"{{CheckIn}}\", \"checkout\" : \"{{CheckOut}}\""
			+ "}, \"additionalneeds\" : \"{{AdditionalNeeds}}\" }";
	
	@Test(priority = 1)
	public void createBooking() {

		setNodes("Check if the user is able to create booking successfully or not");
		setAuthor(System.getProperty("user.name"));
		setCategory("Create");
		startTestModule(getNodes(),getCategory(),getAuthor());

		restfulBookerHelperClass.setFirstName("John");
		restfulBookerHelperClass.setLastName("Samuel");
		restfulBookerHelperClass.setTotalPrice(78945);
		restfulBookerHelperClass.setDepositPaid(true);
		restfulBookerHelperClass.setCheckInDate("2012-01-01");
		restfulBookerHelperClass.setCheckOutDate("2012-01-01");
		restfulBookerHelperClass.setAdditionalNeeds("None");		

		Response createBookingResponse = post(EndPoint.BASE_ENDPOINT,
				payload.replace("{{FirstName}}", restfulBookerHelperClass.getFirstName())
				.replace("{{LastName}}", restfulBookerHelperClass.getLastName())
				.replace("{{TotalPrice}}", String.valueOf(restfulBookerHelperClass.getTotalPrice()))
				.replace("{{DepositPaid}}", String.valueOf(restfulBookerHelperClass.isDepositPaid()))
				.replace("{{CheckIn}}", restfulBookerHelperClass.getCheckInDate())
				.replace("{{CheckOut}}", restfulBookerHelperClass.getCheckOutDate())
				.replace("{{AdditionalNeeds}}", restfulBookerHelperClass.getAdditionalNeeds()));

		bookingID = createBookingResponse.jsonPath().get("bookingid");
		HTMLReporter.reportLog("Booking ID : "+bookingID, Status.PASS);
	}

	@Test(priority = 2)
	public void getBooking() {

		setNodes("Check if the user is able to get the booking successfully or not");
		setAuthor(System.getProperty("user.name"));
		setCategory("Retrieve");

		startTestModule(getNodes(),getCategory(),getAuthor());

		Response getBookingIDResponse = get(EndPoint.BASE_ENDPOINT+"/"+bookingID);
		
		if(getBookingIDResponse.getStatusCode() == 200) {
			HTMLReporter.reportLog("GET Request!! Request URL : "+EndPoint.BASE_ENDPOINT+"/"+bookingID, Status.PASS);
			HTMLReporter.reportLog("Response : <br>"+getBookingIDResponse.asPrettyString(), Status.INFO);
		} else {
			HTMLReporter.reportLog("Error in GET Request!! <br> Request URL : </br> <br>"+EndPoint.BASE_ENDPOINT+"/"+bookingID+"</br>"
					+ "<br>Response : </br><br>"+getBookingIDResponse.asString()+"</br>", Status.FAIL);
			throw new FrameworkException("Error in GET Request!! Request URL : "+EndPoint.BASE_ENDPOINT+"/"+bookingID);
		}

		String firstName = getBookingIDResponse.jsonPath().get("firstname");
		String lastName = getBookingIDResponse.jsonPath().get("lastname");
		int price = getBookingIDResponse.jsonPath().get("totalprice");

		if(firstName.equals(restfulBookerHelperClass.getFirstName())) {
			HTMLReporter.reportLog("First Name matches! First Name : "+firstName, Status.PASS);
		} else {
			HTMLReporter.reportLog("First Name does not matches! First Name : "+firstName, Status.FAIL);
		}

		if(lastName.equals(restfulBookerHelperClass.getLastName())) {
			HTMLReporter.reportLog("Last Name matches! Last Name : "+lastName, Status.PASS);
		} else {
			HTMLReporter.reportLog("Last Name does not matches! Last Name : "+lastName, Status.FAIL);
		}

		if(price ==restfulBookerHelperClass.getTotalPrice()) {
			HTMLReporter.reportLog("Total Price matches! Total Price : "+price, Status.PASS);
		} else {
			HTMLReporter.reportLog("Total Price does not matches! Total Price : "+price, Status.FAIL);
		}

	}

	@Test(priority = 3)
	public void updateBooking() {

		setNodes("Check if the user is able to update booking successfully or not");
		setAuthor(System.getProperty("user.name"));
		setCategory("Update");
		startTestModule(getNodes(),getCategory(),getAuthor());

		restfulBookerHelperClass.setFirstName("Updated First Name");
		restfulBookerHelperClass.setLastName("Updated Last Name");
		restfulBookerHelperClass.setTotalPrice(78945);
		restfulBookerHelperClass.setDepositPaid(true);
		restfulBookerHelperClass.setCheckInDate("2012-01-01");
		restfulBookerHelperClass.setCheckOutDate("2012-01-01");
		restfulBookerHelperClass.setAdditionalNeeds("None");

		put(EndPoint.BASE_ENDPOINT+"/"+bookingID, 
				payload.replace("{{FirstName}}", restfulBookerHelperClass.getFirstName())
				.replace("{{LastName}}", restfulBookerHelperClass.getLastName())
				.replace("{{TotalPrice}}", String.valueOf(restfulBookerHelperClass.getTotalPrice()))
				.replace("{{DepositPaid}}", String.valueOf(restfulBookerHelperClass.isDepositPaid()))
				.replace("{{CheckIn}}", restfulBookerHelperClass.getCheckInDate())
				.replace("{{CheckOut}}", restfulBookerHelperClass.getCheckOutDate())
				.replace("{{AdditionalNeeds}}", restfulBookerHelperClass.getAdditionalNeeds()));

		Response getBookingIDResponse = get(EndPoint.BASE_ENDPOINT+"/"+bookingID);
		
		if(getBookingIDResponse.getStatusCode() == 200) {
			HTMLReporter.reportLog("GET Request!! Request URL : "+EndPoint.BASE_ENDPOINT+"/"+bookingID, Status.PASS);
			HTMLReporter.reportLog("Response : <br>"+getBookingIDResponse.asPrettyString(), Status.INFO);
			
			String firstName = getBookingIDResponse.jsonPath().get("firstname");
			String lastName = getBookingIDResponse.jsonPath().get("lastname");

			if(firstName.equals(restfulBookerHelperClass.getFirstName())) {
				HTMLReporter.reportLog("First Name matches! First Name : "+firstName, Status.PASS);
			} else {
				HTMLReporter.reportLog("First Name does not matches! First Name : "+firstName, Status.FAIL);
			}

			if(lastName.equals(restfulBookerHelperClass.getLastName())) {
				HTMLReporter.reportLog("Last Name matches! Last Name : "+lastName, Status.PASS);
			} else {
				HTMLReporter.reportLog("Last Name does not matches! Last Name : "+lastName, Status.FAIL);
			}
			
		} else {
			HTMLReporter.reportLog("Error in GET Request!! <br> Request URL : </br> <br>"+EndPoint.BASE_ENDPOINT+"/"+bookingID+"</br>"
					+ "<br>Response : </br><br>"+getBookingIDResponse.asString()+"</br>", Status.FAIL);
			throw new FrameworkException("Error in GET Request!! Request URL : "+EndPoint.BASE_ENDPOINT+"/"+bookingID);
		}
	}

	@Test(priority = 4)
	public void deleteBooking() {

		setNodes("Check if the user is able to delete booking successfully or not");
		setAuthor(System.getProperty("user.name"));
		setCategory("Delete");
		startTestModule(getNodes(),getCategory(),getAuthor());

		delete(EndPoint.BASE_ENDPOINT+"/"+bookingID);
		HTMLReporter.reportLog("Booking ID deleted successfully!!", Status.PASS);
		
		Response getBookingIDResponse = get(EndPoint.BASE_ENDPOINT+"/"+bookingID);
		
		if(getBookingIDResponse.getStatusCode() != 200) {
			HTMLReporter.reportLog("Booking ID : '"+bookingID+"' is not found!", Status.PASS);
			HTMLReporter.reportLog("Response : <br>"+getBookingIDResponse.asPrettyString(), Status.INFO);
		} else {
			HTMLReporter.reportLog("Error in Deleting the Booking ID Request!! "
					+ "<br> Request URL : </br> <br>"+EndPoint.BASE_ENDPOINT+"/"+bookingID+"</br>"
					+ "<br>Response : </br><br>"+getBookingIDResponse.asString()+"</br>", Status.FAIL);
			throw new FrameworkException("Error in GET Request!! Request URL : "+EndPoint.BASE_ENDPOINT);
		}
	}
}
