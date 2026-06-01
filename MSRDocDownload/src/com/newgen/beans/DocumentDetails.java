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


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocumentDetails {

    String CUSTOMER_CIF;
    String CUSTOMER_CATEGORY;
    String TRAY_NAME;
    String TOTAL_RECORDS;
    String DOCUMENT_INDEX;
    String DOCUMENT_NAME;
    String DOCUMENT_CREATEDDATETIME;
    String DOCUMENT_REVISEDDATETIME;
    String AUTHOR;
    String APPLICATION_ID;
    String DOCUMENT_ID;
    String DOCUMENT_CODE;
    String ACCOUNT_NUMBER;
    String SOURCE_CHANNEL;
    String DOCUMENT_TYPE;
    String NOOFPAGES;
    String DOC_STATUS;
    String OUTPUT_FILENAME;
    

    // Constructor
    public DocumentDetails(String CUSTOMER_CIF, String CUSTOMER_CATEGORY, String TRAY_NAME, String TOTAL_RECORDS, String DOCUMENT_INDEX, String DOCUMENT_NAME, String DOCUMENT_CREATEDDATETIME, String DOCUMENT_REVISEDDATETIME, String AUTHOR, String APPLICATION_ID, String DOCUMENT_ID, String DOCUMENT_CODE, String ACCOUNT_NUMBER, String SOURCE_CHANNEL, String DOCUMENT_TYPE, String NOOFPAGES, String DOC_STATUS, String OUTPUT_FILENAME) {
        this.CUSTOMER_CIF = CUSTOMER_CIF;
        this.CUSTOMER_CATEGORY = CUSTOMER_CATEGORY;
        this.TRAY_NAME = TRAY_NAME;
        this.TOTAL_RECORDS = TOTAL_RECORDS;
        this.DOCUMENT_INDEX = DOCUMENT_INDEX;
        this.DOCUMENT_NAME = DOCUMENT_NAME;
        this.DOCUMENT_CREATEDDATETIME = DOCUMENT_CREATEDDATETIME;
        this.DOCUMENT_REVISEDDATETIME = DOCUMENT_REVISEDDATETIME;
        this.AUTHOR = AUTHOR;
        this.APPLICATION_ID = APPLICATION_ID;
        this.DOCUMENT_ID = DOCUMENT_ID;
        this.DOCUMENT_CODE = DOCUMENT_CODE;
        this.ACCOUNT_NUMBER = ACCOUNT_NUMBER;
        this.SOURCE_CHANNEL = SOURCE_CHANNEL;
        this.DOCUMENT_TYPE = DOCUMENT_TYPE;
        this.NOOFPAGES = NOOFPAGES;
        this.DOC_STATUS = DOC_STATUS;
        this.OUTPUT_FILENAME = OUTPUT_FILENAME;
    }

    public DocumentDetails() {
        super();
    }

    // Getters & Setters

    public String getCUSTOMER_CIF() {
        return CUSTOMER_CIF;
    }

    public void setCUSTOMER_CIF(String CUSTOMER_CIF) {
        this.CUSTOMER_CIF = CUSTOMER_CIF;
    }

    public String getCUSTOMER_CATEGORY() {
        return CUSTOMER_CATEGORY;
    }

    public void setCUSTOMER_CATEGORY(String CUSTOMER_CATEGORY) {
        this.CUSTOMER_CATEGORY = CUSTOMER_CATEGORY;
    }

    public String getTRAY_NAME() {
        return TRAY_NAME;
    }

    public void setTRAY_NAME(String TRAY_NAME) {
        this.TRAY_NAME = TRAY_NAME;
    }

    public String getTOTAL_RECORDS() {
        return TOTAL_RECORDS;
    }

    public void setTOTAL_RECORDS(String TOTAL_RECORDS) {
        this.TOTAL_RECORDS = TOTAL_RECORDS;
    }

    public String getDOCUMENT_INDEX() {
        return DOCUMENT_INDEX;
    }

    public void setDOCUMENT_INDEX(String DOCUMENT_INDEX) {
        this.DOCUMENT_INDEX = DOCUMENT_INDEX;
    }

    public String getDOCUMENT_NAME() {
        return DOCUMENT_NAME;
    }

    public void setDOCUMENT_NAME(String DOCUMENT_NAME) {
        this.DOCUMENT_NAME = DOCUMENT_NAME;
    }

    public String getDOCUMENT_CREATEDDATETIME() {
        return DOCUMENT_CREATEDDATETIME;
    }

    public void setDOCUMENT_CREATEDDATETIME(String DOCUMENT_CREATEDDATETIME) {
        this.DOCUMENT_CREATEDDATETIME = DOCUMENT_CREATEDDATETIME;
    }

    public String getDOCUMENT_REVISEDDATETIME() {
        return DOCUMENT_REVISEDDATETIME;
    }

    public void setDOCUMENT_REVISEDDATETIME(String DOCUMENT_REVISEDDATETIME) {
        this.DOCUMENT_REVISEDDATETIME = DOCUMENT_REVISEDDATETIME;
    }

    public String getAUTHOR() {
        return AUTHOR;
    }

    public void setAUTHOR(String AUTHOR) {
        this.AUTHOR = AUTHOR;
    }

    public String getAPPLICATION_ID() {
        return APPLICATION_ID;
    }

    public void setAPPLICATION_ID(String APPLICATION_ID) {
        this.APPLICATION_ID = APPLICATION_ID;
    }

    public String getDOCUMENT_ID() {
        return DOCUMENT_ID;
    }

    public void setDOCUMENT_ID(String DOCUMENT_ID) {
        this.DOCUMENT_ID = DOCUMENT_ID;
    }

    public String getDOCUMENT_CODE() {
        return DOCUMENT_CODE;
    }

    public void setDOCUMENT_CODE(String DOCUMENT_CODE) {
        this.DOCUMENT_CODE = DOCUMENT_CODE;
    }

    public String getACCOUNT_NUMBER() {
        return ACCOUNT_NUMBER;
    }

    public void setACCOUNT_NUMBER(String ACCOUNT_NUMBER) {
        this.ACCOUNT_NUMBER = ACCOUNT_NUMBER;
    }

    public String getSOURCE_CHANNEL() {
        return SOURCE_CHANNEL;
    }

    public void setSOURCE_CHANNEL(String SOURCE_CHANNEL) {
        this.SOURCE_CHANNEL = SOURCE_CHANNEL;
    }

    public String getDOCUMENT_TYPE() {
        return DOCUMENT_TYPE;
    }

    public void setDOCUMENT_TYPE(String DOCUMENT_TYPE) {
        this.DOCUMENT_TYPE = DOCUMENT_TYPE;
    }

    public String getNOOFPAGES() {
        return NOOFPAGES;
    }

    public void setNOOFPAGES(String NOOFPAGES) {
        this.NOOFPAGES = NOOFPAGES;
    }

    public String getDOC_STATUS() {
        return DOC_STATUS;
    }

    public void setDOC_STATUS(String DOC_STATUS) {
        this.DOC_STATUS = DOC_STATUS;
    }

    public String getOUTPUT_FILENAME() {
        return OUTPUT_FILENAME;
    }

    public void setOUTPUT_FILENAME(String OUTPUT_FILENAME) {
        this.OUTPUT_FILENAME = OUTPUT_FILENAME;
    }
    
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return super.equals(obj); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public int hashCode() {
        return super.hashCode(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public String toString() {
        return "DocumentDetails{" + "CUSTOMER_CIF=" + CUSTOMER_CIF + ", CUSTOMER_CATEGORY=" + CUSTOMER_CATEGORY + ", TRAY_NAME=" + TRAY_NAME + ", TOTAL_RECORDS=" + TOTAL_RECORDS + ", DOCUMENT_INDEX=" + DOCUMENT_INDEX + ", DOCUMENT_NAME=" + DOCUMENT_NAME + ", DOCUMENT_CREATEDDATETIME=" + DOCUMENT_CREATEDDATETIME + ", DOCUMENT_REVISEDDATETIME=" + DOCUMENT_REVISEDDATETIME + ", AUTHOR=" + AUTHOR + ", APPLICATION_ID=" + APPLICATION_ID + ", DOCUMENT_ID=" + DOCUMENT_ID + ", DOCUMENT_CODE=" + DOCUMENT_CODE + ", ACCOUNT_NUMBER=" + ACCOUNT_NUMBER + ", SOURCE_CHANNEL=" + SOURCE_CHANNEL + ", DOCUMENT_TYPE=" + DOCUMENT_TYPE + ", NOOFPAGES=" + NOOFPAGES + ", DOC_STATUS=" + DOC_STATUS + ", OUTPUT_FILENAME=" + OUTPUT_FILENAME + '}';
    }

}
