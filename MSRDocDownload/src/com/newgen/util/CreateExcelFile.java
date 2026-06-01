/** ******************************************************************
 *    NEWGEN SOFTWARE TECHNOLOGIES LIMITED
 *    Group                                     : Dubai Islamic Bank(DIB)
 *    Product / Project                  		: DMS REVAMP PROJECT
 *    Module                                  	: DOCUMENT EXTRACTION UTILITY
 *    File Name                               	: Main.java
 *    Author                                    : KS.SivaShankar@DIB.AE
 *    Date written                          	: 03/Mar/2025
 *    (DD/MM/YYYY)
 *    Description                            	: THIS JAVA FILE HAS FUNCTIONALITIES OF ACCESSING EXCEL FILE AND READING IT AND DOWNLOADING DOCUMENTS AND KEEPING IN DEST FOLDER WITH TIMESTAMP
 *  CHANGE HISTORY
 ***********************************************************************************************
 * Date              ChangeID                  Change By                    Change Description (Bug No. (If Any))
 * (DD/MM/YYYY)
 *  03/03/2025       CHG00001                   KS.SivaShankar              THIS JAVA FILE HAS FUNCTIONALITIES OF ACCESSING EXCEL FILE AND READING IT AND DOWNLOADING DOCUMENTS AND KEEPING IN DEST FOLDER WITH TIMESTAMP.
 *  24/06/2025       CHG00002                   KS.SivaShankar              ReportExcel - Making Output_filename as the first column and Removing the four extra columns beyond Banking Type
 *  24/06/2025       CHG00005                   KS.SivaShankar              changes on the ReportExcel - Making Output_file format as per IM
 *  22/08/2025       CHG00006                   Gaurav.Berry              	createExcelFileFromDB method create
 *********************************************************************************************** */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.newgen.util;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.newgen.beans.DocumentDetails;
import com.newgen.connection.XMLParser;
import com.newgen.log.LogMe;


public class CreateExcelFile {

	public static void createExcelFile(List<DocumentDetails> listDocDetails, String strExcelFilePath) {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String excelFilePath = strExcelFilePath +  System.getProperty("file.separator")  + "DocumentDetails_"+timeStamp+".xlsx";
		// Create an Excel workbook and sheet
		try ( Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("DocumentDetails");
			// Create header row
			Row headerRow = sheet.createRow(0);
			//            String[] columns = {"CUSTOMER_CIF","CUSTOMER_CATEGORY","TRAY_NAME","TOTAL_RECORDS","DOCUMENT_INDEX","DOCUMENT_NAME","OUTPUT_FILENAME","DOCUMENT_CREATEDDATETIME","DOCUMENT_REVISEDDATETIME","AUTHOR","APPLICATION_ID","DOCUMENT_ID","DOCUMENT_CODE","ACCOUNT_NUMBER","SOURCE_CHANNEL","DOCUMENT_TYPE","NOOFPAGES","DOC_STATUS"};
			//            String[] columns = {"OUTPUT_FILENAME","DocumentName_DMS","DocumentType_DMS","DocumnentID_DMS","IndexOne_CIF","IndexTwo_ProdID","IndexFour_AcquisitionDate","Created By","TrayName","SourcePlatform","ApplicationReferenceID","BankingType","TOTAL_RECORDS","DOCUMENT_INDEX" ,"DOCUMENT_CODE","NOOFPAGES"};
			String[] columns = {"DocumentName_DMS","DocumentType_DMS","DocumnentID_DMS","IndexOne_CIF","IndexTwo_ProdID","IndexFour_AcquisitionDate","Created By","TrayName","SourcePlatform","ApplicationReferenceID","BankingType","NOOFPAGES"};
			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
				// Apply bold style
				CellStyle headerStyle = workbook.createCellStyle();
				Font font = workbook.createFont();
				font.setBold(true);
				headerStyle.setFont(font);
				cell.setCellStyle(headerStyle);
			}
			// Populate data rows
			int rowNum = 1;
			for (DocumentDetails doclist : listDocDetails) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(doclist.getOUTPUT_FILENAME());
				//                row.createCell(1).setCellValue(doclist.getDOCUMENT_NAME());//CHG00005 index is modified accordingly
				row.createCell(1).setCellValue(doclist.getDOCUMENT_TYPE());
				row.createCell(2).setCellValue(doclist.getDOCUMENT_ID());
				row.createCell(3).setCellValue(doclist.getCUSTOMER_CIF());
				row.createCell(4).setCellValue(doclist.getACCOUNT_NUMBER());
				row.createCell(5).setCellValue(doclist.getDOCUMENT_CREATEDDATETIME());
				row.createCell(6).setCellValue(doclist.getAUTHOR());
				row.createCell(7).setCellValue(doclist.getTRAY_NAME());
				row.createCell(8).setCellValue(doclist.getSOURCE_CHANNEL());
				row.createCell(9).setCellValue(doclist.getAPPLICATION_ID());
				row.createCell(10).setCellValue(doclist.getCUSTOMER_CATEGORY());
				row.createCell(11).setCellValue(doclist.getNOOFPAGES());
			}
			// Auto-size columns
			for (int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}
			// Write to file
			try ( FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
				workbook.write(fileOut);
			}
			System.out.println("Excel file '" + excelFilePath + "' created successfully.");
		} catch (IOException e) {
			System.err.print(e.getMessage());
		}
	}


	public static void createExcelFileFromDB(CommonMethods comObj, String strExcelFilePath) {
		LogMe.logger.info("~~~~~~~~~~~~~~~~~~inside createExcelFileFromDB ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String excelFilePath = strExcelFilePath +  System.getProperty("file.separator")  + "DocumentDetails_"+timeStamp+".xlsx";
		// Create an Excel workbook and sheet
		try ( Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("DocumentDetails");
			// Create header row
			Row headerRow = sheet.createRow(0);
			String[] columns = {"DocumentName_DMS","DocumentType_DMS","DocumnentID_DMS","IndexOne_CIF","IndexTwo_ProdID","IndexFour_AcquisitionDate","Created By","TrayName","SourcePlatform","ApplicationReferenceID","BankingType","NOOFPAGES"};
			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
				// Apply bold style
				CellStyle headerStyle = workbook.createCellStyle();
				Font font = workbook.createFont();
				font.setBold(true);
				headerStyle.setFont(font);
				cell.setCellStyle(headerStyle);
			}
			// Populate data rows
			int rowNum = 1;

			String strOutputXML = comObj.getAPSelect("select DOCUMENT_NAME,DOCUMENT_TYPE,DOCUMNENT_ID,CUSTOMER_CIF,INDEXTWO_PRODID,INDEXFOUR_ACQUISITIONDATE,CREATED_BY,TRAYNAME,SOURCEPLATFORM,APPLICATION_REF_ID,BANKING_TYPE,NO_OF_PAGES from DMS_REVAMP_DOC_DETAILS",12);

			XMLParser sessionXMLParser = new XMLParser();
			sessionXMLParser.setInputXML(strOutputXML);
			String lstrMainCodeWI = sessionXMLParser.getValueOf("MainCode");
			int sessionMainCode = Integer.parseInt(lstrMainCodeWI);
			if (sessionMainCode == 0) {
				int totalRecords =  Integer.parseInt(sessionXMLParser.getValueOf("TotalRetrieved"));
				if (totalRecords>0) {
					int start=sessionXMLParser.getStartIndex("Records", 0, 0);
					int deadEnd=sessionXMLParser.getEndIndex("Records", start, 0);
					int end =0;
					int noOfFields=sessionXMLParser.getNoOfFields("Record",start,deadEnd);
					LogMe.logger.info("createExcelFileFromDB noOfFields: "+noOfFields);

					for(int i=0; i<noOfFields;++i) {
						Row row = sheet.createRow(rowNum++);
						start = sessionXMLParser.getStartIndex("Record", end, 0);
						end = sessionXMLParser.getEndIndex("Record", start, 0);

						
						row.createCell(0).setCellValue(sessionXMLParser.getValueOf("DOCUMENT_NAME",start,end));
						row.createCell(1).setCellValue(sessionXMLParser.getValueOf("DOCUMENT_TYPE",start,end));
						row.createCell(2).setCellValue(sessionXMLParser.getValueOf("DOCUMNENT_ID",start,end));
						row.createCell(3).setCellValue(sessionXMLParser.getValueOf("CUSTOMER_CIF",start,end));
						row.createCell(4).setCellValue(sessionXMLParser.getValueOf("INDEXTWO_PRODID",start,end));
						row.createCell(5).setCellValue(sessionXMLParser.getValueOf("INDEXFOUR_ACQUISITIONDATE",start,end));
						row.createCell(6).setCellValue(sessionXMLParser.getValueOf("CREATED_BY",start,end));
						row.createCell(7).setCellValue(sessionXMLParser.getValueOf("TRAYNAME",start,end));
						row.createCell(8).setCellValue(sessionXMLParser.getValueOf("SOURCEPLATFORM",start,end));
						row.createCell(9).setCellValue(sessionXMLParser.getValueOf("APPLICATION_REF_ID",start,end));
						row.createCell(10).setCellValue(sessionXMLParser.getValueOf("BANKING_TYPE",start,end));
						row.createCell(11).setCellValue(sessionXMLParser.getValueOf("NO_OF_PAGES",start,end));
					}
				}
			}

			// Auto-size columns
			for (int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}
			// Write to file
			try ( FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
				workbook.write(fileOut);
			}
			LogMe.logger.info("Excel file '" + excelFilePath + "' created successfully.");
		} catch (IOException e) {
			LogMe.logger.error("Error createExcelFileFromDB :"+e);
			System.err.print(e.getMessage());
		}
	}

	public static void createExcelFile1(List<DocumentDetails> listDocDetails, String strExcelFilePath) {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String excelFilePath = strExcelFilePath +  System.getProperty("file.separator")  + "DocumentDetails_"+timeStamp+".xlsx";
		// Create an Excel workbook and sheet
		try ( Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("DocumentDetails");
			// Create header row
			Row headerRow = sheet.createRow(0);
			//            String[] columns = {"CUSTOMER_CIF","CUSTOMER_CATEGORY","TRAY_NAME","TOTAL_RECORDS","DOCUMENT_INDEX","DOCUMENT_NAME","OUTPUT_FILENAME","DOCUMENT_CREATEDDATETIME","DOCUMENT_REVISEDDATETIME","AUTHOR","APPLICATION_ID","DOCUMENT_ID","DOCUMENT_CODE","ACCOUNT_NUMBER","SOURCE_CHANNEL","DOCUMENT_TYPE","NOOFPAGES","DOC_STATUS"};
			String[] columns = {"CUSTOMER_CIF","CUSTOMER_CATEGORY","TRAY_NAME","TOTAL_RECORDS","DOCUMENT_INDEX","DOCUMENT_NAME","OUTPUT_FILENAME","DOCUMENT_CREATEDDATETIME","DOCUMENT_REVISEDDATETIME","AUTHOR","APPLICATION_ID","DOCUMENT_ID","DOCUMENT_CODE","ACCOUNT_NUMBER","SOURCE_CHANNEL","DOCUMENT_TYPE","NOOFPAGES"};
			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
				// Apply bold style
				CellStyle headerStyle = workbook.createCellStyle();
				Font font = workbook.createFont();
				font.setBold(true);
				headerStyle.setFont(font);
				cell.setCellStyle(headerStyle);
			}
			// Populate data rows
			int rowNum = 1;
			for (DocumentDetails doclist : listDocDetails) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(doclist.getCUSTOMER_CIF());
				row.createCell(1).setCellValue(doclist.getCUSTOMER_CATEGORY());
				row.createCell(2).setCellValue(doclist.getTRAY_NAME());
				row.createCell(3).setCellValue(doclist.getTOTAL_RECORDS());
				row.createCell(4).setCellValue(doclist.getDOCUMENT_INDEX());
				row.createCell(5).setCellValue(doclist.getDOCUMENT_NAME());
				row.createCell(6).setCellValue(doclist.getOUTPUT_FILENAME());
				row.createCell(7).setCellValue(doclist.getDOCUMENT_CREATEDDATETIME());
				row.createCell(8).setCellValue(doclist.getDOCUMENT_REVISEDDATETIME());
				row.createCell(9).setCellValue(doclist.getAUTHOR());
				row.createCell(10).setCellValue(doclist.getAPPLICATION_ID());
				row.createCell(11).setCellValue(doclist.getDOCUMENT_ID());
				row.createCell(12).setCellValue(doclist.getDOCUMENT_CODE());
				row.createCell(13).setCellValue(doclist.getACCOUNT_NUMBER());
				row.createCell(14).setCellValue(doclist.getSOURCE_CHANNEL());
				row.createCell(15).setCellValue(doclist.getDOCUMENT_TYPE());
				row.createCell(16).setCellValue(doclist.getNOOFPAGES());
				//                row.createCell(17).setCellValue(doclist.getDOC_STATUS());
			}
			// Auto-size columns
			for (int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}
			// Write to file
			try ( FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
				workbook.write(fileOut);
			}
			System.out.println("Excel file '" + excelFilePath + "' created successfully.");
		} catch (IOException e) {
			System.err.print(e.getMessage());
		}
	}
}
