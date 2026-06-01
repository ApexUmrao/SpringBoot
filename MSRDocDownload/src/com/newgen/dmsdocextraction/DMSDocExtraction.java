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

package com.newgen.dmsdocextraction;

import com.newgen.beans.DocumentDetails;
import com.newgen.util.CommonMethods;

import com.newgen.log.LogMe;
import com.newgen.util.CreateExcelFile;

import java.util.ArrayList;

import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class DMSDocExtraction {


	public static void main(String[] args) {
		System.out.println("~~~~~~~~~~~~~~~~~~###################*************#########################~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~main Method Starts of DMSDocExtraction ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~###################*************#########################~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		LogMe.intitalizeLog();
		
		LogMe.logger.info("~~~~~~~~~~~~~~~~~~###################*************#########################~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		LogMe.logger.info("~~~~~~~~~~~~~~~~~~ReadProperty Method Starts of DMSDocExtraction ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		LogMe.logger.info("~~~~~~~~~~~~~~~~~~###################*************#########################~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		CommonMethods comObj = new CommonMethods();
		String strStatus = comObj.ReadProperty();
		
	

		LogMe.logger.info("~~~~~~~~~~~~~~~~~~ReadProperty Method Ends of DMSDocExtraction ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		if (strStatus.equalsIgnoreCase(comObj.NG_SUCCESS)) {
			LogMe.logger.info("~~~~~~~~~~~~~~~~~~ReadProperty Method Success of DMSDocExtraction ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//			if (comObj.conCabinet()) {
				try {
					String tableName="MSR_DOC_EXTRACTION"; //New tablename -- col -- CUSTOMER_CID,STATUS,THREAD_NAME
//					int insertCount = comObj.insertExcelIntoDB(comObj.strXLSXFilePath,tableName);
					LogMe.logger.info("Total row inserted : " + tableName);


					int threadCount = Integer.parseInt(comObj.strThreadCount);
					ExecutorService executor = Executors.newFixedThreadPool(threadCount);
					List<Future<List<DocumentDetails>>> futures = new ArrayList<Future<List<DocumentDetails>>>();
					try {
						for(int i=0; i<threadCount; i++) {
							Future<List<DocumentDetails>> f = executor.submit(new Service(comObj));
							Thread.sleep(5000);
							futures.add(f);
						}
					} catch (Exception e) {
						LogMe.logger.error(e);
					}finally {
						List<DocumentDetails> listDocDetails = new ArrayList<>();

						if(executor != null) {
							for (Future<List<DocumentDetails>> future : futures) {
								List<DocumentDetails> result =future.get();//blocks until task completes
								listDocDetails.addAll(result);
							}
							executor.shutdown();
							LogMe.logger.info("All task completed, now main thread continues");
						}
						CreateExcelFile.createExcelFile(listDocDetails, comObj.strExcelFilePath);
//						CreateExcelFile.createExcelFileFromDB(comObj,comObj.strExcelFilePath);
//						comObj.executeAPProcedure("DMS_REVAMP_HIST_MOVEMENT", "'DMS_REVAMP'", 1);
						comObj.disConCabinet();
					}
				}catch (Exception e) {
					LogMe.logger.error(e);
				}
			}
//		}
		LogMe.logger.info("~~~~~~~~~~~~~~~~~~###################*************#########################~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		LogMe.logger.info("~~~~~~~~~~~~~~~~~~main Method Ends of DMSDocExtraction ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		LogMe.logger.info("~~~~~~~~~~~~~~~~~~###################*************#########################~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
}
