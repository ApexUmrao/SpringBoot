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
import com.newgen.util.CommonMethods;
import com.newgen.util.ExecuteXML;
import com.newgen.util.GenerateXml;


import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPISException;
import Jdts.DataObject.JPDBString;

public class Service implements Callable<List<DocumentDetails>> {

	CommonMethods comObj = null;

	public Service(CommonMethods obj) {
		comObj=obj;
	}

	@Override
	public List<DocumentDetails> call() throws Exception {

		LogMe.logger.info("Service call method from Thread Name :: " + Thread.currentThread().getName() +
				" ||  Priority: " + Thread.currentThread().getPriority());
		List<DocumentDetails> listDocDetails = new ArrayList<>();
		try {
			boolean running =true;
			while(running) {
				//				String outputqryXML = comObj.getAPSelect("SELECT CUSTOMER_CID FROM MSR_DOC_EXTRACTION WHERE STATUS ='N' AND ROWNUM <=1",2);


				String sQuery = "SELECT CUSTOMER_CID FROM MSR_DOC_EXTRACTION WHERE STATUS ='N' AND ROWNUM <=1";


				String outputqryXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(comObj.strCabName,sQuery,comObj.sessionId));
				XMLParser xpareser = new XMLParser(outputqryXML);
				String outputString = xpareser.getValueOf("APSelectWithColumnNames_Output");	
				LogMe.logger.info("[executeOnWI]" + "executeOnWI sQuery : " + outputqryXML);
				LogMe.logger.info("[executeOnWI]" + "executeOnWI outputString : " + outputString);
				//				LogMe.logger.info("[executeOnWI]" + "executeOnWI sQuery : " + xp);

				//				XMLParser xp = new XMLParser(outputqryXML);

				if (xpareser.getValueOf("TotalRetrieved").equals("1")) {
					String strCIF = xpareser.getValueOf("CUSTOMER_CID");
					LogMe.logger.info("strCIF = " + strCIF );

					//					if (strCIF != null && !strCIF.isEmpty()) {
					//						comObj.updateData("STATUS,THREAD_NAME", "P','"+Thread.currentThread().getName(), "MSR_DOC_EXTRACTION", "CUSTOMER_CID='"+strCIF+"'");
					//					}

					try{
						String updateQuery = "";
						int mainCode = -1;
						String status = "P";
						LogMe.logger.info("[updateCalls]" + " comObj.sessionId --> " +comObj.sessionId + " || " + comObj.strCabName);

						updateQuery = GenerateXml.APUpdate("MSR_DOC_EXTRACTION", "STATUS, THREAD_NAME", 
								"'"+status+"','"+Thread.currentThread().getName()+"'", "CUSTOMER_CID='"+strCIF+"'", 
								comObj.sessionId,comObj.strCabName);
						LogMe.logger.info("[updateCalls]" + " Update Query " +updateQuery);
						XMLParser xmlDataParser1 = new XMLParser(updateQuery);
						mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
						LogMe.logger.info("[updateCalls]" + " Main Code " +mainCode);
					}catch (Exception e) {
						LogMe.logger.info("Exception Found"+e);
					}

					String query= "SELECT ITEMINDEX, MSRREQID FROM USR_0_EXT_MSR WHERE CID = '" + strCIF + "'";

					String outputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(comObj.strCabName,query,comObj.sessionId));

					XMLParser xp = new XMLParser(outputXML);
					int start = xp.getStartIndex("Records", 0, 0);
					int deadEnd = xp.getEndIndex("Records", start, 0);
					int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
					int end = 0;
					for(int i = 0; i < noOfFields; ++i){
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						String itemIndex = xp.getValueOf("ITEMINDEX", start, end);
						String workitemNo = xp.getValueOf("MSRREQID", start, end);


						String strQuery = "SELECT d.DOCUMENTINDEX ,d.IMAGEINDEX, d.VOLUMEID, d.NOOFPAGES, d.DOCUMENTSIZE, d.APPNAME, d.COMMNT, d.NAME , d.AUTHOR "
								+ "FROM PDBDOCUMENT d  WHERE d.DOCUMENTINDEX IN "
								+ "(SELECT pd.DOCUMENTINDEX FROM PDBDOCUMENTCONTENT pd where pd.PARENTFOLDERINDEX IN ("
								+ itemIndex + ")) AND TRUNC(d.CREATEDDATETIME) <= '"+ comObj.strDATECRITERIA + "' ORDER BY d.CREATEDDATETIME DESC";

						XMLParser parsergetlist = null;


						//					String xmlqryExtractedfields = comObj.getAPSelect(strQuery, 11);
						//					parsergetlist = new XMLParser(xmlqryExtractedfields);

						String xmlqryExtractedfields = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(comObj.strCabName,strQuery,comObj.sessionId));
						parsergetlist= new XMLParser(xmlqryExtractedfields);
						LogMe.logger.info("[executeOnWI]" + "executeOnWI sQuery : " + strQuery);

						try{
							//Added by Shivanshu
							String fetchedDocuments =  parsergetlist.getValueOf("APSelectWithColumnNames_Output");	
							downloadDocuments(fetchedDocuments, workitemNo , strCIF);
						}catch (Exception e) {
							LogMe.logger.info("Exception Found"+e);
						}



						//					DownloadDocumentBean obj = new DownloadDocumentBean();
						//					if (parsergetlist.getValueOf("MainCode").equals("0")) {
						//						String strTotRecords = parsergetlist.getValueOf("TotalRetrieved");
						//						LogMe.logger.info("totalRecords doc Exists: " + strTotRecords);
						//						System.out.println("totalRecords doc Exists1: " + strTotRecords);
						//						int t = Integer.parseInt(strTotRecords);
						//						wfXmlResponseFI = new WFXmlResponse(xmlqryExtractedfields);
						//						wfxmllistFI = wfXmlResponseFI.createList("Records", "Record");
						//						wfxmllistFI.reInitialize(true);
						//						for (; wfxmllistFI.hasMoreElements(true); wfxmllistFI.skip(true)) {
						//							// Creating a Map with String keys and String values
						//							DocumentDetails objDocDetails = new DocumentDetails();
						//							// Adding values to the Map
						//							objDocDetails.setCUSTOMER_CIF(strCIF);
						//							objDocDetails.setTOTAL_RECORDS(strTotRecords);
						//							if (t > 0) {
						//								objDocDetails.setDOC_STATUS("EXIST");
						//								if (!comObj.isempty(wfxmllistFI.getVal("DOCUMENTINDEX").trim())) {
						//									strDOCUMENTINDEX = wfxmllistFI.getVal("DOCUMENTINDEX").trim();
						//									objDocDetails.setDOCUMENT_INDEX(strDOCUMENTINDEX);
						//									System.out.println("strDOCUMENTINDEX ===== " + strDOCUMENTINDEX);
						//								}
						//								if (!comObj.isempty(wfxmllistFI.getVal("NAME").trim())) {
						//									strNAME = wfxmllistFI.getVal("NAME").trim();
						//									objDocDetails.setDOCUMENT_NAME(strNAME);
						//								}
						//								if (!comObj.isempty(wfxmllistFI.getVal("CREATEDDATETIME").trim())) {
						//									strCREATEDDATETIME = wfxmllistFI.getVal("CREATEDDATETIME").trim();
						//									objDocDetails.setDOCUMENT_CREATEDDATETIME(strCREATEDDATETIME);
						//								}
						//								if (!comObj.isempty(wfxmllistFI.getVal("REVISEDDATETIME").trim())) {
						//									strREVISEDDATETIME = wfxmllistFI.getVal("REVISEDDATETIME").trim();
						//									objDocDetails.setDOCUMENT_REVISEDDATETIME(strREVISEDDATETIME);
						//								}
						//								if (!comObj.isempty(wfxmllistFI.getVal("AUTHOR").trim())) {
						//									strAUTHOR = wfxmllistFI.getVal("AUTHOR").trim();
						//									objDocDetails.setAUTHOR(strAUTHOR);
						//								}
						//								if (!comObj.isempty(wfxmllistFI.getVal("NOOFPAGES").trim())) {
						//									strNOOFPAGES = wfxmllistFI.getVal("NOOFPAGES").trim();
						//									objDocDetails.setNOOFPAGES(strNOOFPAGES);
						//								}
						//
						//								LogMe.logger.info("TotalRetrieved doc Exists: " + t);
						//								System.out.println("totalRecords doc Exists2: " + t);
						//								System.out.println("strDOCUMENTINDEX = " + strDOCUMENTINDEX + " || strNAME = " + strNAME + " || strCREATEDDATETIME = " + strCREATEDDATETIME);
						//								DocumentDetails objSts = comObj.callDownloadDocumentServiceOfProduct(objDocDetails, strDOCUMENTINDEX, strCIF );
						//								objDocDetails.setOUTPUT_FILENAME(objSts.getOUTPUT_FILENAME());
						//							} else {
						//								objDocDetails.setDOC_STATUS("NO DOCUMENTS EXIST!");
						//							}
						//							listDocDetails.add(objDocDetails);			
						//						}
						//					}

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
				}
				else {
					LogMe.logger.info("No more customer cif to process");
					break;
				}
			}
		} catch (Exception e) {
			LogMe.logger.error("Service error :"+e);
			e.printStackTrace();
		}
		return listDocDetails;
	}

	public void downloadDocuments(String fetchedDocuments, String workitemNo , String CustomerID) throws Exception{
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

			String folderPath =  folderLocation +   File.separator  + CustomerID + File.separator + workitemNo;
			LogMe.logger.info("[downloadDocuments]" +"folderPath : "+ folderPath);
			File file = new File(folderPath);
			if (!file.exists()) {
				Boolean createFile = file.mkdirs();
				LogMe.logger.info( "[downloadDocuments]" +" Folder created : "+ createFile);
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
					}catch (JPISException e) {
						LogMe.logger.error("[downloadDocuments]" +" Exception "+e);
					}
				}																

			}else{
				LogMe.logger.info("[downloadDocuments]" +"New Workitem Directory Already Exist : ");
			}
		} else{
			LogMe.logger.info("[downloadDocuments]" +"firstmainCode is not Zero(0)");
		}
	}
}