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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;

import com.newgen.beans.DocumentDetails;
import com.newgen.connection.XMLParser;
import com.newgen.log.LogMe;
import com.newgen.util.APCallCreateXML;
import com.newgen.util.CommonMethods;
import com.newgen.util.ExecuteXML;
import com.newgen.util.GenerateXml;


import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPISException;
import Jdts.DataObject.JPDBString;

public class Service implements Callable<List<DocumentDetails>> {

	CommonMethods comObj = null;
	boolean moveToHistoryFlag = false;
	String finalStatus = "D";
	
	public Service(CommonMethods obj) {
		comObj=obj;
	}

	@Override
	public List<DocumentDetails> call() throws Exception {

		LogMe.logger.info("Service call method from Thread Name :: " + Thread.currentThread().getName() +
				" ||  Priority: " + Thread.currentThread().getPriority());
		List<DocumentDetails> listDocDetails = new ArrayList<>();
		try {
			boolean running = true;
			while(running) {

				String sQuery = "SELECT WORKITEM_NUMBER,CID FROM BPM_DOC_DOWNLOAD WHERE STATUS ='N' ORDER BY CREATEDDATETIME ";

				String outputqryXML = ExecuteXML.executeXML(GenerateXml.
						APSelectWithColumnNames(comObj.strCabName,sQuery,comObj.sessionId));
				XMLParser xpareser = new XMLParser(outputqryXML);
				String outputString = xpareser.getValueOf("APSelectWithColumnNames_Output");	
				LogMe.logger.info("[executeOnWI]" + "executeOnWI outputString : " + outputString);
				int totalRecords =  Integer.parseInt(xpareser.getValueOf("TotalRetrieved"));

				if (totalRecords > 0) {
					String wiName = xpareser.getValueOf("WORKITEM_NUMBER");
					String CID = xpareser.getValueOf("CID");

					LogMe.logger.info("wiName = " + wiName + " and CID = " + CID);

					try{
						String updateQuery = "";
						int mainCode = -1;
						String status = "P";
						LogMe.logger.info("[updateCalls]" + " comObj.sessionId --> " +comObj.sessionId + " || " + comObj.strCabName);

						updateQuery = GenerateXml.APUpdate("BPM_DOC_DOWNLOAD", "STATUS, THREAD_NAME", 
								"'"+status+"','"+Thread.currentThread().getName()+"'", " WORKITEM_NUMBER='"+wiName+"' AND CID ='"+CID+"' ", 
								comObj.sessionId,comObj.strCabName);
						
						LogMe.logger.info("[updateCalls]" + " Update Query " +updateQuery);
						XMLParser xmlDataParser1 = new XMLParser(updateQuery);
						mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
						
						LogMe.logger.info("[updateCalls]" + " mainCode Update Query " +mainCode);

					}catch (Exception e) {
						LogMe.logger.error("Exception Found"+e);
					}

					String schemaPrefix = comObj.environment.equalsIgnoreCase("PROD")
					        ? comObj.cabinetTable + "."
					        : "";

					String strQuery =
					        "SELECT d.DOCUMENTINDEX, d.IMAGEINDEX, d.VOLUMEID, d.NOOFPAGES, " +
					        "d.DOCUMENTSIZE, d.APPNAME, d.COMMNT, d.NAME, d.AUTHOR " +
					        "FROM " + schemaPrefix + "PDBDOCUMENT d " +
					        "WHERE d.DOCUMENTINDEX IN ( " +
					        "    SELECT pd.DOCUMENTINDEX " +
					        "    FROM " + schemaPrefix + "PDBDOCUMENTCONTENT pd " +
					        "    WHERE pd.PARENTFOLDERINDEX IN ( " +
					        "        SELECT pdb.FOLDERINDEX " +
					        "        FROM " + schemaPrefix + "PDBFOLDER pdb " +
					        "        WHERE pdb.NAME = '" + wiName + "'" +
					        "    ) " +
					        ") " +
					        "ORDER BY d.CREATEDDATETIME DESC";
					
					
						String xmlqryExtractedfields = ExecuteXML.executeXML(GenerateXml.
								APSelectWithColumnNames(comObj.strCabName,strQuery,comObj.sessionId));
						XMLParser parsergetlist = new XMLParser(xmlqryExtractedfields);
						LogMe.logger.info("[executeOnWI]" + "executeOnWI sQuery : " + strQuery);

						try{
							//Added by Shivanshu
							String fetchedDocuments =  parsergetlist.getValueOf("APSelectWithColumnNames_Output");	
							downloadDocuments(fetchedDocuments , wiName, CID);
							if (moveToHistoryFlag) {
							 String	outputXML = APCallCreateXML.APProcedure(comObj.sessionId,
										"BPM_DOC_DOWNLOAD_MOVE_HISTORY",
										"'" + wiName + "','" + finalStatus + "' , '"+ CID +"'", 3);
							}
						}catch (Exception e) {
							LogMe.logger.info("Exception Found"+e);
						}

						String timeStampChk = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
						LogMe.logger.info("timeStamp = " + timeStampChk);

						List<String> specialDays = Arrays.asList(comObj.strToolSpecialDays.toLowerCase().split(","));
						List<String> specialTimesStartAt = Arrays.asList(comObj.strToolSpecialDaysStartAt.split(","));
						DayOfWeek today = LocalDateTime.now().getDayOfWeek();
						String todayName = today.toString().toLowerCase();
						String specialDayStartAt = "";
						boolean isSpecialDay=false;
						if(specialDays.contains(todayName)) {
							int index = specialDays.indexOf(todayName);
							specialDayStartAt = specialTimesStartAt.get(index);
							isSpecialDay=true;
						}
						if ((Integer.parseInt(timeStampChk) > Integer.parseInt(comObj.strToolEndAt) && Integer.parseInt(timeStampChk) < Integer.parseInt(comObj.strToolStartAt)) || 
								(isSpecialDay && Integer.parseInt(timeStampChk) > Integer.parseInt(comObj.strToolEndAt) && Integer.parseInt(timeStampChk) < Integer.parseInt(specialDayStartAt))) {//between 163001 and 050001
							LogMe.logger.info("The Tool/Service is going to abort due to exceeding the execution time defined :: Proceeding for Report File Generation  \t Defined Time : between " + comObj.strToolStartAt + " and " + comObj.strToolEndAt + " means - After 4:30 PM to next day till 5:00 AM Only ");

							LogMe.logger.info("The Tool/Service is going to abort due to exceeding the execution time defined :: Calling System.exit call hence the tool/service Terminates immediately.\t Defined Time : between " + comObj.strToolStartAt + " and " + comObj.strToolEndAt + " means - After 4:30 PM to next day till 5:00 AM Only ");
							running=false;
						}
					}
				
					else {
					LogMe.logger.info("No more customer workitems to process");
					break;
				}
			}
		} catch (Exception e) {
			LogMe.logger.error("Service error :"+e);
			e.printStackTrace();
		}
		return listDocDetails;
	}

	public void downloadDocuments(String fetchedDocuments, String workitemNo , String CID ) throws Exception{
		LogMe.logger.info("[downloadDocuments]" +"  fetchedDocuments "+fetchedDocuments);
		String folderLocation = comObj.strDocDownLoadPath;
		String JTSIP = comObj.strJTSIP;
		String JTSPort = comObj.strJTSPORT;
		String EDBMSCabinet = comObj.strEDMSCABINET;

		XMLParser parser = new XMLParser(fetchedDocuments);
		int fisrtmainCode = Integer.parseInt(parser.getValueOf("MainCode"));
		if (fisrtmainCode == 0) {

			int start = parser.getStartIndex("Records", 0, 0);
			int deadEnd = parser.getEndIndex("Records", start, 0);
			int noOfFields = parser.getNoOfFields("Record", start, deadEnd);

			//			String folderPath =  folderLocation + File.separator + CID + File.separator + workitemNo;
			//			LogMe.logger.info("[downloadDocuments]" +"folderPath : "+ folderPath);
			//			File file = new File(folderPath);
			//			if (!file.exists()) {
			//				Boolean createFile = file.mkdirs();
			//				LogMe.logger.info( "[downloadDocuments]" +" Folder created : "+ createFile);

			String folderPath = folderLocation + File.separator + CID
					+ File.separator + workitemNo;

			LogMe.logger.info("[downloadDocuments] Folder Path : " + folderPath);

			File directory = new File(folderPath);

			if (!directory.exists()) {

				boolean created = directory.mkdirs();

				if (created) {
					LogMe.logger.info("[downloadDocuments] Directory created successfully : " + folderPath);

					for (int i = 0; i < noOfFields; ++i) { 
						String Record = parser.getNextValueOf("Record");
						XMLParser xp2 = new XMLParser(Record);
						String IMAGEINDEX = xp2.getValueOf("IMAGEINDEX");
						String appname = xp2.getValueOf("APPNAME");
						String volumeID = xp2.getValueOf("VOLUMEID");
						String name = xp2.getValueOf("NAME");

						String docName  = name; // Adding key as name # commnt and values will be imageindex # volumid # appname
						JPDBString oSiteName = new JPDBString("adcbedmsuatsite");

						try {
							CPISDocumentTxn.GetDocInFile_MT(null,JTSIP,Short.parseShort(JTSPort),
									EDBMSCabinet,Short.parseShort("1"),Short.parseShort(volumeID),
									Integer.parseInt(IMAGEINDEX),
									null,folderPath+"/"+docName
									+"."+appname,oSiteName);

							moveToHistoryFlag = true;

						}catch (JPISException e) {
							LogMe.logger.error("[downloadDocuments]" +" Exception "+e);
						}
					}
				} else {
					LogMe.logger.error("[downloadDocuments] Failed to create directory :  "+ folderPath);
					throw new IOException("Unable to create directory : " + folderPath);
				}

			}else{
				LogMe.logger.info("[downloadDocuments]" +"New Workitem Directory Already Exist : ");
			}
		} else{
			LogMe.logger.info("[downloadDocuments]" +"firstmainCode is not Zero(0)");
		}
	}
}