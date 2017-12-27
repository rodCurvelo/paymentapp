package com.jacana.paymmentApp.model;

public enum InvoiceStatus {
	
	PENDING("Pending"),
	RECEIVED("Received"),
	CANCELED("Canceled");
	
	
	private String description;
	
	InvoiceStatus(String description){
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

}
