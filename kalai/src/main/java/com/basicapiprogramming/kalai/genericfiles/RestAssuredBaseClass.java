package com.basicapiprogramming.kalai.genericfiles;

import com.aventstack.extentreports.Status;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredBaseClass extends TestNGHooks{

	public Response get(String url) {
		RequestSpecification requestSpecification = getRequestSpecification("JSON");
		requestSpecification = setAuthentication(requestSpecification, "No");
		
		Response response = requestSpecification.get(url);
		if(response.getStatusCode() == 200) {
			HTMLReporter.reportLog("GET Request!! Request URL : "+url, Status.PASS);
			return response;
		} else {
			HTMLReporter.reportLog("Error in GET Request!! <br> Request URL : </br> <br>"+url+"</br>"
					+ "<br>Response : </br><br>"+response.asString()+"</br>", Status.FAIL);
			throw new FrameworkException("Error in GET Request!! Request URL : "+url);
		}
	}
	
	public Response post(String url,String payload) {
		RequestSpecification requestSpecification = getRequestSpecification("JSON");
		requestSpecification = setAuthentication(requestSpecification, "No");
		
		if(requestSpecification!=null) {
			requestSpecification = setBody(requestSpecification,payload);
			Response response = requestSpecification.post(url);
			if(response.getStatusCode() == 200) {
				HTMLReporter.reportLog("POST Request!! Request URL : "+url, payload, Status.PASS);
				return response;
			} else {
				HTMLReporter.reportLog("Error in POST Request!! <br> Request URL : </br> <br>"+url+"</br>"
						+ "<br>Response : </br><br>"+response.asString()+"</br>", Status.FAIL);
				throw new FrameworkException("Error in POST Request!! Request URL : "+url);
			}
		} else {
			HTMLReporter.reportLog("Error in setting POST Request!! <br> Request URL : </br> <br>"+url+"</br>", Status.FAIL);
			throw new FrameworkException("Error in setting POST Request!! Request URL : "+url);
		}
		
	}
	
	public Response put(String url,String payload) {
		RequestSpecification requestSpecification = getRequestSpecification("JSON");
		requestSpecification = setAuthentication(requestSpecification, "No");
		
		if(requestSpecification!=null) {
			requestSpecification = setBody(requestSpecification,payload);
			Response response = requestSpecification.put(url);
			if(response.getStatusCode() == 200) {
				HTMLReporter.reportLog("PUT Request!! Request URL : "+url, payload, Status.PASS);
				return response;
			} else {
				HTMLReporter.reportLog("Error in PUT Request!! <br> Request URL : </br> <br>"+url+"</br>"
						+ "<br>Response : </br><br>"+response.asString()+"</br>", Status.FAIL);
				throw new FrameworkException("Error in PUT Request!! Request URL : "+url);
			}
		} else {
			HTMLReporter.reportLog("Error in setting PUT Request!! <br> Request URL : </br> <br>"+url+"</br>", Status.FAIL);
			throw new FrameworkException("Error in setting PUT Request!! Request URL : "+url);
		}
		
	}
	
	public Response delete(String url,String payload) {
		RequestSpecification requestSpecification = getRequestSpecification("JSON");
		requestSpecification = setAuthentication(requestSpecification, "No");
		
		if(requestSpecification!=null) {
			requestSpecification = setBody(requestSpecification,payload);
			Response response = requestSpecification.delete(url);
			if(response.getStatusCode() == 200) {
				HTMLReporter.reportLog("DELETE Request!! Request URL : "+url, payload, Status.PASS);
				return response;
			} else {
				HTMLReporter.reportLog("Error in DELETE Request!! <br> Request URL : </br> <br>"+url+"</br>"
						+ "<br>Response : </br><br>"+response.asString()+"</br>", Status.FAIL);
				throw new FrameworkException("Error in DELETE Request!! Request URL : "+url);
			}
		} else {
			HTMLReporter.reportLog("Error in setting DELETE Request!! <br> Request URL : </br> <br>"+url+"</br>", Status.FAIL);
			throw new FrameworkException("Error in setting DELETE Request!! Request URL : "+url);
		}
		
	}

	private RequestSpecification setBody(RequestSpecification requestSpecification, String payload) {
		return requestSpecification.body(payload);
	}

	private RequestSpecification getRequestSpecification(String contentType) {
		RequestSpecification requestSpecification = new RestAssured().given();		

		switch(contentType) {
		case "JSON" : requestSpecification.accept(ContentType.JSON); break;
		case "XML" : requestSpecification.accept(ContentType.XML); break;
		}

		return requestSpecification;
	}
	
	private RequestSpecification setAuthentication(RequestSpecification requestSpecification,String authenticationType) {
		
		switch(authenticationType) {
		case "Basic Authentication" : requestSpecification.auth().preemptive().basic("username", "password"); break;
		case "Outh2" : requestSpecification.headers("",""); break;
		}
		
		return requestSpecification;
	}

}
