/** ******************************************************************
 *    NEWGEN SOFTWARE TECHNOLOGIES LIMITED
 *    Group                                     : Abu Dhabi Commercial Bank(ADCB)
 *    Product / Project                  	    : DMS REVAMP PROJECT
 *    Module                                  	: DOCUMENT EXTRACTION UTILITY
 *    File Name                               	: DocumentDetails.java
 *    Author                                    : Shivanshu Umrao
 *    Date written                          	: 14/Mar/2026
 *    (DD/MM/YYYY)
 *    Description                            	: Contains the methods for handling of DownloadDocument service
 *  CHANGE HISTORY
 ***********************************************************************************************
 * Date              ChangeID                  Change By                    Change Description (Bug No. (If Any))
 * (DD/MM/YYYY)
 *  07/03/2026       CHG00001                   Shivanshu Umrao              Contains the methods for parsing a XML file.
 *
 *********************************************************************************************** */


package com.newgen.beans;

import java.io.Serializable;


public class DownloadDocumentBean implements Serializable {
    private static final long serialVersionUID = 1L;
	private String documentIndex = null;
	private String base64String= null;
	private String operationStatus = null;
	private String operationMessage = null;
	private String operationCode = null;
	private String errorDescreption=null;
	private String documentName = null;
	private String iSIndex = null;
	private String createdByAppName = null;
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getBase64String() {
		return base64String;
	}
	public void setBase64String(String base64String) {
		this.base64String = base64String;
	}
	public String getiSIndex() {
		return iSIndex;
	}
	public void setiSIndex(String iSIndex) {
		this.iSIndex = iSIndex;
	}
	public String getCreatedByAppName() {
		return createdByAppName;
	}
	public void setCreatedByAppName(String createdByAppName) {
		this.createdByAppName = createdByAppName;
	}
	
	public String getErrorDescreption() {
		return errorDescreption;
	}
	public void setErrorDescreption(String errorDescreption) {
		this.errorDescreption = errorDescreption;
	}
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public String getOperationMessage() {
		return operationMessage;
	}
	public String getOperationStatus() {
		return operationStatus;
	}
	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}
	public void setOperationMessage(String operationMessage) {
		this.operationMessage = operationMessage;
	}

	public String getDocumentIndex() {
		return documentIndex;
	}
	public void setDocumentIndex(String documentIndex) {
		this.documentIndex = documentIndex;
	}
/*	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}*/
	public DownloadDocumentBean() {
		super();
	}
}
