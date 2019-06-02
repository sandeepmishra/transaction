package com.sapient.feecalculator.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author sandeep
 *
 */
public class TransactionModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1896144506417930360L;

	private String externalTransactionID;
	private String clientId;
	private String securityId;
	private String transactionType;
	private Date transactionDate;
	private Double marketValue;
	private Boolean priority;
	private Double transactionFees;
	
	public TransactionModel(String externalTransactionID,String clientId,String securityId,
	String transactionType,
	Date transactionDate,
	Double marketValue,
	Boolean priority,
	Double transactionFees) {
		// TODO Auto-generated constructor stub
		this.externalTransactionID = externalTransactionID;
		this.clientId = clientId;
		this.securityId = securityId;
		this.transactionType = transactionType;
		this.transactionDate  =transactionDate;
		this.marketValue = marketValue;
		this.priority = priority;
		this.transactionFees  = transactionFees;
	}
	
	TransactionModel(){
		
	}
	
	public String getExternalTransactionID() {
		return externalTransactionID;
	}
	public void setExternalTransactionID(String externalTransactionID) {
		this.externalTransactionID = externalTransactionID;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getSecurityId() {
		return securityId;
	}
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}
	
	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public java.util.Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(java.util.Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Double getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}
	public Boolean getPriority() {
		return priority;
	}
	public void setPriority(Boolean priority) {
		this.priority = priority;
	}
	public Double getTransactionFees() {
		return transactionFees;
	}
	public void setTransactionFees(Double transactionFees) {
		this.transactionFees = transactionFees;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getClientId()+" "+this.getTransactionType()+" "+this.getTransactionDate()+" "+this.getPriority()+" "+this.getTransactionFees();
	}
}
