package com.basicapiprogramming.kalai.restfulbooker;

public class RestfulBookerHelperClass {
	
	private String firstName;
	private String lastName;
	private int totalPrice;
	private boolean depositPaid;
	private String checkInDate;
	private String checkOutDate;
	private String additionalNeeds;
	
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public int getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public boolean isDepositPaid() {
		return depositPaid;
	}
	
	public void setDepositPaid(boolean depositPaid) {
		this.depositPaid = depositPaid;
	}
	
	public String getCheckInDate() {
		return checkInDate;
	}
	
	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}
	
	public String getCheckOutDate() {
		return checkOutDate;
	}
	
	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	
	public String getAdditionalNeeds() {
		return additionalNeeds;
	}
	
	public void setAdditionalNeeds(String additionalNeeds) {
		this.additionalNeeds = additionalNeeds;
	}

}
