package com.basicapiprogramming.kalai.genericfiles;

import java.util.Properties;

import com.aventstack.extentreports.Status;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestAssuredBaseClass extends TestNGHooks {

	private String username;
	private String password;
	private String token;
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public RestAssuredBaseClass() {
		Properties prop = Settings.getInstance();
		setUsername(prop.getProperty("username"));
		setPassword(prop.getProperty("password"));
		getTokenCookie();
	}

	public Response get(String url) {

		return RestAssured.given()
				.auth().preemptive().basic(username, password)
				.header("Cookie","token="+token)
				.when()
				.get(url);
	}

	public Response post(String url,String payload) {

		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
				.cookie("token="+getToken())
				.body(payload)
				.when()
				.post(url);

		if(response.getStatusCode() == 200) {
			HTMLReporter.reportLog("POST Request!! Request URL : "+url, payload, Status.PASS);
			HTMLReporter.reportLog("Response : <br>"+response.asPrettyString(), Status.INFO);
			return response;
		} else {
			HTMLReporter.reportLog("Error in POST Request!! "
					+ "<br> Request URL : "+url+"</br>"
					+"<br> Response Code : "+response.getStatusCode()+"</br>"
					+ "<br>Response : </br><br>"+response.asString()+"</br>", Status.FAIL);
			HTMLReporter.reportLog(payload, Status.INFO);
			throw new FrameworkException("Error in POST Request!! Request URL : "+url);
		}


	}

	public Response put(String url,String payload) {

		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
				.cookie("token="+getToken())
				.when()
				.body(payload)
				.put(url);
		
		if(response.getStatusCode() == 200) {
			HTMLReporter.reportLog("PUT Request!! Request URL : "+url, payload, Status.PASS);
			HTMLReporter.reportLog("Response : <br>"+response.asPrettyString(), Status.INFO);
			return response;
		} else {
			HTMLReporter.reportLog("Error in PUT Request!! "
					+ "<br> Request URL : "+url+"</br>"
					+"<br> Response Code : "+response.getStatusCode()+"</br>"
					+ "<br>Response : </br><br>"+response.asString()+"</br>", Status.FAIL);
			HTMLReporter.reportLog(payload, Status.INFO);
			throw new FrameworkException("Error in PUT Request!! Request URL : "+url);
		}

	}

	public Response delete(String url) {

		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=")
				.cookie("token="+getToken())
				.when()
				.delete(url);
		if(response.getStatusCode() == 200 || response.getStatusCode()==201) {
			HTMLReporter.reportLog("DELETE Request!! Request URL : "+url, Status.PASS);
			HTMLReporter.reportLog("Response : <br>"+response.asPrettyString(), Status.INFO);
			return response;
		} else {
			HTMLReporter.reportLog("Error in DELETE Request!! "
					+ "<br> Request URL : "+url+"</br>"
					+"<br> Response Code : "+response.getStatusCode()+"</br>"
					+ "<br>Response : </br><br>"+response.asString()+"</br>", Status.FAIL);
			throw new FrameworkException("Error in DELETE Request!! Request URL : "+url);
		}
	}
	
	protected void getTokenCookie(){
		
		String payload = "{\"username\" : \""+getUsername()+"\",\"password\" : \""+getPassword()+"\"}";
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(payload)
				.when()
				.post("https://restful-booker.herokuapp.com/auth");
		
		setToken(response.jsonPath().getString("token"));
	}

}
