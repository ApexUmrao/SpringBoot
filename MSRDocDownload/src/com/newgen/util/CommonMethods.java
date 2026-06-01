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


package com.newgen.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



import com.newgen.beans.DocumentDetails;
import com.newgen.connection.XMLParser;
import com.newgen.log.LogMe;
import com.newgen.dmsapi.DMSXmlList;
import com.newgen.dmsapi.DMSXmlResponse;

import com.newgen.wfdesktop.xmlapi.WFCallBroker;

import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPISException;
import ISPack.ISUtil.JPISIsIndex;
import Jdts.DataObject.JPDBString;


public class CommonMethods {

	File IniFile = null;
	FileInputStream fi = null;
	Properties prop = null;
	public String NG_SUCCESS = "SUCCESS";
	String NG_FAIL = "FAIL";
	String NG_EXCEPTION = "";
	String strPropertyPath = "";
	String Xmlout = "";
	DMSXmlList xmlList = null;
	StringBuffer strBuffer = null;
	String strIP = "";
	String strPort = "";
	int iPort;
	int FileCount;
	String strAppServerType = "";
	public String strCabName = "";
	String strUsername = "";
	String strPwd = "";
	public String strXLSXFilePath = "";
	public String strExcelFilePath = "";
	String strExceptionPath = "";
	public String strToolSpecialDays = "";
	public String strToolSpecialDaysStartAt = "";
	public String strToolStartAt = "";
	public String strToolEndAt = "";
	public String strThreadCount = "";
	public String strDATECRITERIA = "";
	public String strDATECRITERIA_FROM = "";
	String strSiteID = "";
	String strUserCount = "";
	public String strDocDownLoadPath = "";
	public String strSessionId = "";
	private ConnectSocket socket;
	
	public String strJTSIP = "";
	public String strJTSPORT = "";
	public String strEDMSCABINET = "";
	public String strVolumeID = "";
	public String sessionId = "";


	public String ReadProperty() {
		try {
			strPropertyPath = System.getProperty("user.dir") + File.separator + "Settings.ini";
			IniFile = new File(strPropertyPath);
			FileInputStream File_Ini = new FileInputStream(strPropertyPath);
			prop = new Properties();
			prop.load(File_Ini);
		} catch (FileNotFoundException fe) {
			System.out.println(" \n### configuration file " + fi + " not found ");
			LogMe.logger.error("\nProperties file " + fi + " not found\n" + "Exception in configuration file : " + fe);
			return NG_FAIL;
		} catch (IOException e) {
			System.out.println(" \n###  **[Error]**  Error in configuration file");
			LogMe.logger.error("\nError in configuration file " + fi + "Exception in configuration file : " + e);
			return NG_FAIL;
		}

		try {
			if (IniFile.exists()) {
				LogMe.logger.info("Start Reading INI File ..........");
				if ((prop.getProperty("AppServerIP") != null) && (!prop.getProperty("AppServerIP").equals(""))) {
					strIP = prop.getProperty("AppServerIP").trim();
				} else {
					//System.out.println("\n[ERROR] Login Username not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] AppServerIP not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				if ((prop.getProperty("AppServerPort") != null) && (!prop.getProperty("AppServerPort").equals(""))) {
					strPort = prop.getProperty("AppServerPort").trim();
				} else {
					//System.out.println("\n[ERROR] Login Username not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] AppServerPort not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				iPort = Integer.parseInt(strPort);
				if ((prop.getProperty("AppServerType") != null) && (!prop.getProperty("AppServerType").equals(""))) {
					strAppServerType = prop.getProperty("AppServerType").trim();
				} else {
					//System.out.println("\n[ERROR] Login Username not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] AppServerType not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				
				
				//Added by Shivanshu
				
				if ((prop.getProperty("JTSIP") != null) && (!prop.getProperty("JTSIP").equals(""))) {
					strJTSIP = prop.getProperty("JTSIP").trim();
				} else {
					LogMe.logger.error("\n[ERROR] JTSIP not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				
				if ((prop.getProperty("JTSPort") != null) && (!prop.getProperty("JTSPort").equals(""))) {
					strJTSPORT = prop.getProperty("JTSPort").trim();
				} else {
					LogMe.logger.error("\n[ERROR] JTSPort not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				
				if ((prop.getProperty("EDMSCabinet") != null) && (!prop.getProperty("EDMSCabinet").equals(""))) {
					strEDMSCABINET = prop.getProperty("EDMSCabinet").trim();
				} else {
					LogMe.logger.error("\n[ERROR] EDMSCabinet not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				
				

				if ((prop.getProperty("VolumeID") != null) && (!prop.getProperty("VolumeID").equals(""))) {
					 strVolumeID = prop.getProperty("VolumeID").trim();
				} else {
					//System.out.println("\n[ERROR] Login Username not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] VolumeID not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				if ((prop.getProperty("SiteID") != null) && (!prop.getProperty("SiteID").equals(""))) {
					strSiteID = prop.getProperty("SiteID").trim();
				} else {
					//System.out.println("\n[ERROR] Login Username not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] SiteID not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				if ((prop.getProperty("CabinetName") != null) && (!prop.getProperty("CabinetName").equals(""))) {
					strCabName = prop.getProperty("CabinetName").trim();
				} else {
					//System.out.println("\n[ERROR] Login Username not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] CabinetName not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				if ((prop.getProperty("UserName") != null) && (!prop.getProperty("UserName").equals(""))) {
					strUsername = prop.getProperty("UserName").trim();
				} else {
					//System.out.println("\n[ERROR] Login Username not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] UserName not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				if ((prop.getProperty("Password") != null) && (!prop.getProperty("Password").equals(""))) {
					strPwd = prop.getProperty("Password").trim();
				} else {
					//System.out.println("\n[ERROR] Login Username not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] Password not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				if ((prop.getProperty("XLSXFilePath") != null) && (!prop.getProperty("XLSXFilePath").equals(""))) {
					strXLSXFilePath = prop.getProperty("XLSXFilePath").trim();
				} else {
					System.out.println("\n[ERROR] XLSXFilePath not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] XLSXFilePath not specified in the Settings.ini file.");
					return NG_FAIL;
				}

				if ((prop.getProperty("DocDownLoadPath") != null) && (!prop.getProperty("DocDownLoadPath").equals(""))) {
					//                  strDocDownLoadPath = prop.getProperty("DocDownLoadPath").trim();
					String strDocDownLoadPath1 = prop.getProperty("DocDownLoadPath").trim();
					strDocDownLoadPath = strDocDownLoadPath1 ;//+ System.getProperty("file.separator") + timeStamp;
					File directory = new File(strDocDownLoadPath);
					directory.mkdirs();//It will create list of directoris are available in path
				} else {
					System.out.println("\n[ERROR] DocDownLoadPath not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] DocDownLoadPath not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				if ((prop.getProperty("ExcelFilePath") != null) && (!prop.getProperty("ExcelFilePath").equals(""))) {
					strExcelFilePath = prop.getProperty("ExcelFilePath").trim();
				} else {
					System.out.println("\n[ERROR] ExcelFilePath not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] ExcelFilePath not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				if ((prop.getProperty("Tool_StartAt") != null) && (!prop.getProperty("Tool_StartAt").equals(""))) {
					strToolStartAt = prop.getProperty("Tool_StartAt").trim();
				} else {
					System.out.println("\n[ERROR] Tool_StartAt not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] Tool_StartAt not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				if ((prop.getProperty("Tool_EndAt") != null) && (!prop.getProperty("Tool_EndAt").equals(""))) {
					strToolEndAt = prop.getProperty("Tool_EndAt").trim();
				} else {
					System.out.println("\n[ERROR] Tool_EndAt not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] Tool_EndAt not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				if ((prop.getProperty("Tool_SpecialDays") != null) && (!prop.getProperty("Tool_SpecialDays").equals(""))) {
					strToolSpecialDays = prop.getProperty("Tool_SpecialDays").trim();
				} else {
					System.out.println("\n[ERROR] Tool_SpecialDays not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] Tool_SpecialDays not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				if ((prop.getProperty("Tool_SpecialDaysStartAt") != null) && (!prop.getProperty("Tool_SpecialDaysStartAt").equals(""))) {
					strToolSpecialDaysStartAt = prop.getProperty("Tool_SpecialDaysStartAt").trim();
				} else {
					System.out.println("\n[ERROR] Tool_SpecialDaysStartAt not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] Tool_SpecialDaysStartAt not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				if ((prop.getProperty("Thread_Count") != null) && (!prop.getProperty("Thread_Count").equals(""))) {
					strThreadCount = prop.getProperty("Thread_Count").trim();
				} else {
					System.out.println("\n[ERROR] Thread_Count not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] Thread_Count not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				if ((prop.getProperty("DATE_CRITERIA") != null) && (!prop.getProperty("DATE_CRITERIA").equals(""))) {
					strDATECRITERIA = prop.getProperty("DATE_CRITERIA").trim();
				} else {
					System.out.println("\n[ERROR] DATE_CRITERIA not specified in the configuration file.");
					LogMe.logger.error("\n[ERROR] DATE_CRITERIA not specified in the Settings.ini file.");
					return NG_FAIL;
				}
				LogMe.logger.info("Ends Reading INI File ..........");
			} else {
				LogMe.logger.info("No Property file exist in " + strPropertyPath);
				return NG_FAIL;
			}
		} catch (Exception e) {
			LogMe.logger.error("\n\n### **[Error]** Exception in ReadProperty " + e);
		}
		System.out.println(" strIP = " + strIP + " \n XLSXFilePath = " + strXLSXFilePath);
		
		try {
			ExecuteXML.init("WebSphere", strIP, String.valueOf(strPort));
			APCallCreateXML.init(strCabName);
			ProdCreateXML.init(strCabName);
			initializeSocket();
			connectToWorkflow();
			 LogMe.logger.info("Initialization of Call Broker done successfully.");
		} catch (Exception ex) {
			LogMe.logger.info("Error during reading the configuration file.");
		}
		LogMe.logger.info("Inside ThreadClient() constructor.");
		
		return NG_SUCCESS;
	}

	

	public boolean conCabinet() {
		DMSXmlResponse xmlResponse = null;
		this.Xmlout = "";
		try {
			StringBuilder sConnectCabInputXML = new StringBuilder();
			sConnectCabInputXML.append("<?xml version=1.0?>");
			sConnectCabInputXML.append("<NGOConnectCabinet_Input>");
			sConnectCabInputXML.append("<Option>NGOConnectCabinet</Option>");
			sConnectCabInputXML.append("<CabinetName>").append(this.strCabName).append("</CabinetName>");
			sConnectCabInputXML.append("<Username>").append(this.strUsername).append("</Username>");
			sConnectCabInputXML.append("<UserPassword>").append(this.strPwd).append("</UserPassword>\n");
			sConnectCabInputXML.append("<CurrentDateTime>");
			sConnectCabInputXML.append("</CurrentDateTime>");
			sConnectCabInputXML.append("<UserExist>N</UserExist>");
			sConnectCabInputXML.append("<MainGroupIndex>0</MainGroupIndex>");
			sConnectCabInputXML.append("<UserType>U</UserType>");
			sConnectCabInputXML.append("<Locale>en-us</Locale>");
			sConnectCabInputXML.append("<ListSysFolder>Y</ListSysFolder>");
			sConnectCabInputXML.append("</NGOConnectCabinet_Input>");
			LogMe.logger.info("ConnectCabinet InputXML:\n" + sConnectCabInputXML.toString());
			//            this.Xmlout = WFCallBroker.execute(strBuffer.toString(), this.strIP, this.iPort, 0);
			this.Xmlout = executeCallBroker(sConnectCabInputXML.toString());
			LogMe.logger.info("ConnectCabinet OutputXML:\n" + this.Xmlout);
			xmlResponse = new DMSXmlResponse(this.Xmlout);
			if (xmlResponse.getVal("status").equals("0")) {
				strSessionId = xmlResponse.getVal("UserDBId");
				LogMe.logger.info("Cabinet Connected successfully ....." + strSessionId);
				return true;
			}
			LogMe.logger.info("Problem in Connecting Cabinet : " + xmlResponse.getVal("Error"));
			LogMe.logger.info("Problem in Connecting Cabinet : " + xmlResponse.getVal("Error"));
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			LogMe.logger.info("\n\n### **[Error]** Exception in Connecting Cabinet : " + e.toString());
		}
		return false;
	}

	public String executeCallBroker1 (String inputXML){
		String outputXML=null;
		try {
			outputXML = WFCallBroker.execute(inputXML, this.strIP, this.iPort, 0);
		} catch (Exception e) {
			e.printStackTrace();
			LogMe.logger.info("\n\n### **[Error]** Exception in Connecting Cabinet : " + e.toString());
		}
		return outputXML;
	}

	public String executeCallBroker(String sInputXML) {
		LogMe.logger.info("Execute Call");
		String sOutputXML = "";
		XMLParser parser = null;
		long timeMilli = new Date().getTime();
		try {
			parser = new XMLParser();
			LogMe.logger.info(new StringBuilder().append("input Xml (Execute) = ").append(sInputXML).toString());
			//sOutputXML = WFCallBroker.execute(sInputXML, jtsIP, jtsPort, 1);

			//change id 001
			//            sOutputXML = NGEjbClient.getSharedInstance().makeCall(jtsIP, jndiPort, appserver, sInputXML);
			sOutputXML = WFCallBroker.execute(sInputXML, this.strIP, this.iPort, 0);
			//sOutputXML = ebjClient.makeCall(serverIp,serverport, serverType, sInputXML);
			parser.setInputXML(sOutputXML);
			LogMe.logger.info(new StringBuilder().append("Output Xml (Execute) = ").append(sOutputXML).toString());
			if (parser.getValueOf("Status").equals("11") || parser.getValueOf("Status").equals("-50146") || parser.getValueOf("MainCode").equals("11")) {
				//System.out.println("Reconnecting again");
				LogMe.logger.info("--Reconnecting with DMS -----");
				reconnectToDMS();
				//System.out.println("Reconnecting end");
				LogMe.logger.info("Execute Call Again");
				//System.out.println("Executing call again");
				parser.setInputXML(sInputXML);
				if (parser.getNoOfFields("UserDBId") > 0) {
					sInputXML = sInputXML.replaceAll(parser.getValueOf("UserDBId"), strSessionId);
				} else if (parser.getNoOfFields("SessionId") > 0) {
					sInputXML = sInputXML.replaceAll(parser.getValueOf("SessionId"), strSessionId);
				}
				//System.out.println("Input Xml "+sInputXML);
				sOutputXML = executeCallBroker(sInputXML);
			}
		} catch (Exception ex) {
			LogMe.logger.error("Exception Occured during execute() method =  " + getStackTrace(ex));
		} finally {
			parser = null;
			long endTime = new Date().getTime();
			long diff = endTime - timeMilli;
			LogMe.logger.info(new StringBuilder().append("Difference = ").append(diff).toString());
		}

		return sOutputXML;
	}

	public boolean reconnectToDMS() {
		LogMe.logger.info("Inide reconnectToDMS()");
		try {
			StringBuilder sReConnectToDMSInputXML = new StringBuilder();
			sReConnectToDMSInputXML.append("<?xml version=1.0?>");
			sReConnectToDMSInputXML.append("<NGOConnectCabinet_Input>");
			sReConnectToDMSInputXML.append("<Option>NGOConnectCabinet</Option>");
			sReConnectToDMSInputXML.append("<CabinetName>").append(this.strCabName).append("</CabinetName>");
			sReConnectToDMSInputXML.append("<Username>").append(this.strUsername).append("</Username>");
			sReConnectToDMSInputXML.append("<UserPassword>").append(this.strPwd).append("</UserPassword>\n");
			sReConnectToDMSInputXML.append("<CurrentDateTime>");
			sReConnectToDMSInputXML.append("</CurrentDateTime>");
			sReConnectToDMSInputXML.append("<UserExist>N</UserExist>");
			sReConnectToDMSInputXML.append("<MainGroupIndex>0</MainGroupIndex>");
			sReConnectToDMSInputXML.append("<UserType>U</UserType>");
			sReConnectToDMSInputXML.append("<Locale>en-us</Locale>");
			sReConnectToDMSInputXML.append("<ListSysFolder>Y</ListSysFolder>");
			sReConnectToDMSInputXML.append("</NGOConnectCabinet_Input>");
			LogMe.logger.info("reconnectToDMS Input XML : " + sReConnectToDMSInputXML.toString());
			//System.out.println("Connecting To Server...");
			String str = executeCallBroker(sReConnectToDMSInputXML.toString());

			LogMe.logger.info("reconnectToDMS Output XML : " + str);

			DMSXmlResponse localDMSXmlResponse = new DMSXmlResponse(str);

			if (localDMSXmlResponse.getVal("Status").equals("0")) {
				strSessionId = localDMSXmlResponse.getVal("UserDBID");
				//System.out.println(new StringBuilder().append("Connection to Server reconnectToDMS Successful , Session ID ").append(strSessionId).toString());
				LogMe.logger.info("reconnectToDMS Session ID : " + strSessionId);
				return true;
			}

			//System.out.println(new StringBuilder().append("Could Not reconnectToDMS to Server, Error Code : ").append(localDMSXmlResponse.getVal("Status")).toString());
			LogMe.logger.info(new StringBuilder().append("reconnectToDMS Output XML : ").append(str).toString());

			return false;
		} catch (Exception localException) {
			LogMe.logger.info("Exception in reconnectToDMS Connecting To Server : :   " + getStackTrace(localException));
			//System.out.println(new StringBuilder().append("Exception in reconnectToDMS Connecting To Server ").append(localException).toString());
			LogMe.logger.info("Exception in reconnectToDMS Connecting To Server " + localException);
			LogMe.logger.info(new StringBuilder().append("Exception in reconnectToDMS Connecting To Server ").append(localException).toString());
			System.exit(0);
		}
		return false;
	}

	public String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}


	public void disConCabinet() {
		DMSXmlResponse xmlResponse = null;
		this.Xmlout = "";
		try {
			StringBuilder sDisConnectCabInputXML = new StringBuilder();
			sDisConnectCabInputXML.append("<?xml version='1.0'?>");
			sDisConnectCabInputXML.append("<NGODisConnectCabinet_Input>");
			sDisConnectCabInputXML.append("<Option>NGODisConnectCabinet</Option>");
			sDisConnectCabInputXML.append("<CabinetName>" + this.strCabName + "</CabinetName>");
			sDisConnectCabInputXML.append("<UserDBId>" + this.strSessionId + "</UserDBId>");
			sDisConnectCabInputXML.append("</NGODisConnectCabinet_Input>");
			//            this.Xmlout = WFCallBroker.execute(this.strBuffer.toString(), this.strIP, this.iPort, 0);
			LogMe.logger.info("Disconnect Cabinet InputXML:\n" + sDisConnectCabInputXML.toString());
			this.Xmlout = executeCallBroker(sDisConnectCabInputXML.toString());
			LogMe.logger.info("Disconnect Cabinet OutputXMLt:\n" + this.Xmlout);
			xmlResponse = new DMSXmlResponse(this.Xmlout);
			if (xmlResponse.getVal("Status").equals("0")) {
				LogMe.logger.info("Cabinet Disconnected Successfully ...");
			} else {
				LogMe.logger.info("Problem in Disconnecting cabinet:" + xmlResponse.getVal("Error"));
				LogMe.logger.info("Problem in Disconnecting cabinet:" + xmlResponse.getVal("Error"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogMe.logger.info("\n\n### **[Error]** Exception in Disconnecting cabinet : " + e.toString());
		}
	}


	public String getAPSelect(String strQuery, int noofCols) {
		String strOutputXmlFI = null;
		StringBuilder strBuffFI = new StringBuilder();


		strBuffFI = strBuffFI.append("<?xmlversion=\"1.0\"?>");
		strBuffFI = strBuffFI.append("<APSelectWithColumnNames>");
		strBuffFI = strBuffFI.append("<Option>APSelectWithColumnNames</Option>");
		strBuffFI = strBuffFI.append("<EngineName>").append(strCabName).append("</EngineName>");
		strBuffFI = strBuffFI.append("<SessionId>").append(strSessionId).append("</SessionId>");
		//        strBuffFI = strBuffFI.append("<QueryString>").append(strQuery).append("</QueryString>");
		strBuffFI = strBuffFI.append("<Query>").append(strQuery).append("</Query>");
		strBuffFI = strBuffFI.append("<NoOfCols>").append(noofCols).append("</NoOfCols>");
		strBuffFI = strBuffFI.append("</APSelectWithColumnNames>");
		LogMe.logger.info("strBuffFI==" + strBuffFI);
		//        System.out.print("strBuffFI==" + strBuffFI);
		try {
			//            strOutputXmlFI = WFCallBroker.execute(strBuffFI.toString(), this.strIP, this.iPort, 0);
			strOutputXmlFI = executeCallBroker(strBuffFI.toString());
			LogMe.logger.info("strCompOutputXml==" + strOutputXmlFI); 
			//            System.out.print("strCompOutputXml==" + strOutputXmlFI); 
		} catch (Exception e) {
			LogMe.logger.info("[Error]Error in executing APSelect " + e);

		}
		return strOutputXmlFI;
	}


	public String updateData(String colName, String value, String tableName, String whereClause) {
		LogMe.logger.info("Inside updateData method");
		LogMe.logger.info("colName to be updated======" + colName);
		LogMe.logger.info("value to be updated==" + value);
		LogMe.logger.info("Where clause=" + whereClause);
		StringBuilder inputxml = new StringBuilder();
		inputxml = inputxml.append("<?xmlversion=\"1.0\"?>");
		inputxml = inputxml.append("<APUpdate>");
		inputxml = inputxml.append("<Option>APUpdate</Option>");
		inputxml = inputxml.append("<ProcessDefId>").append(strUsername).append("</ProcessDefId>");
		inputxml = inputxml.append("<Status>true</Status>");
		inputxml = inputxml.append("<SessionId>").append(strSessionId).append("</SessionId>");
		inputxml = inputxml.append("<EngineName>").append(strCabName).append("</EngineName>");
		inputxml = inputxml.append("<TableName>").append(tableName).append("</TableName>");
		inputxml = inputxml.append("<ColName>").append(colName).append("</ColName>");
		inputxml = inputxml.append("<Values>'").append(value).append("'</Values>");
		inputxml = inputxml.append("<WhereClause>").append(whereClause).append("</WhereClause>");
		inputxml = inputxml.append("</APUpdate>");
		LogMe.logger.info("inputxm==" + inputxml);
		XMLParser parsergetlist = null;
		String strCompOutputXml = null;
		try {
			//            strCompOutputXml = WFCallBroker.execute(inputxml.toString(), strIP, Integer.parseInt("3333"), 0);
			strCompOutputXml = executeCallBroker(inputxml.toString());
			parsergetlist = new XMLParser(strCompOutputXml);
			if (parsergetlist.getValueOf("MainCode").equals("0")) {
				return (parsergetlist.getValueOf("MainCode"));
			} else {
				return "";
			}
		} catch (Exception e) {
			LogMe.logger.info("[Error]Error in update the payment date" + e);
			return "";
		} finally {
			if (strCompOutputXml != null) {
				strCompOutputXml = null;
			}
			if (parsergetlist != null) {
				parsergetlist = null;
			}
			if (inputxml != null) {
				inputxml = null;
			}
		}
	}


	public String insertCmplxPODetailsQuery(String sTableName, String winame, String PONum, String POAmount, String POOpenAmount) {
		StringBuilder inputxml = new StringBuilder();
		inputxml = inputxml.append("<?xmlversion=\"1.0\"?>");
		inputxml = inputxml.append("<APInsert>");
		inputxml = inputxml.append("<Option>APInsert</Option>");
		inputxml = inputxml.append("<ProcessDefId>").append(strUsername).append("</ProcessDefId>");
		inputxml = inputxml.append("<Status>true</Status>");
		inputxml = inputxml.append("<SessionId>").append(strSessionId).append("</SessionId>");
		inputxml = inputxml.append("<EngineName>").append(strCabName).append("</EngineName>");
		inputxml = inputxml.append("<TableName>").append(sTableName).append("</TableName>");
		inputxml = inputxml.append("<ColName>WIName,PONumber,POAmount,POOpenAmount</ColName>");
		inputxml = inputxml.append("<Values>'" + winame + "','" + PONum + "'," + POAmount + "," + POOpenAmount + "</Values>");
		inputxml = inputxml.append("</APInsert>");
		LogMe.logger.info("inputxml==" + inputxml);
		XMLParser parsergetlist = null;
		String strCompOutputXml = null;
		try {
			//            strCompOutputXml = WFCallBroker.execute(inputxml.toString(), strIP, Integer.parseInt("3333"), 0);
			strCompOutputXml = executeCallBroker(inputxml.toString());
			LogMe.logger.info("strCompOutputXml==" + strCompOutputXml);
			parsergetlist = new XMLParser(strCompOutputXml);
			if (parsergetlist.getValueOf("MainCode").equals("0")) {
				return "True";
			} else {
				return "False";
			}
		} catch (Exception e) {
			LogMe.logger.info("[Error]Error in Insert  insert CmplxP ODetails Query " + e);
			return "";
		} finally {
			if (strCompOutputXml != null) {
				strCompOutputXml = null;
			}
			if (parsergetlist != null) {
				parsergetlist = null;
			}
			if (inputxml != null) {
				inputxml = null;
			}
		}
	}


	public boolean isRowEmpty(Row row) throws NullPointerException {
		try {
			for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
				Cell cell = row.getCell(c);
				if (cell != null && cell.getCellType() != CellType.BLANK) {
					return false;
				}
			}
		} catch (Exception e) {
			LogMe.logger.error("Exception in isRowEmpty:" + e);
			System.out.println("Exception in isRowEmpty:" + e);
		}
		return true;
	}


	public Boolean isempty(String value) throws Exception {

		if (value != null) {
			if (value.equalsIgnoreCase("") || value.equalsIgnoreCase("null")) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}

	}

	public static boolean chkEmpty(String s) {
		//System.out.println("Inside chkEmpty method for String " + s);
		if (s == null || s.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public static ArrayList<ArrayList<String>> readExcelFile(String filePath, List<Integer> selectedColumns) {
		LogMe.logger.info("~~~~~~~~~~~~~~~~~~ inside readExcelFile ~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~~~~~~~~~~~~~~~~~ inside readExcelFile ~~~~~~~~~~~~~~~~~~~~~~~~~");
		ArrayList<ArrayList<String>> dataList = new ArrayList<>();
		try ( FileInputStream file = new FileInputStream(new File(filePath));  XSSFWorkbook workbook = new XSSFWorkbook(file)) {
			Sheet sheet = workbook.getSheetAt(0); // Read the first sheet
			for (Row row : sheet) {
				ArrayList<String> rowData = new ArrayList<>();
				for (int colIndex : selectedColumns) {
					Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					// Handle empty cells and format values
					String cellValue = getCellValueAsString(cell);
					rowData.add(cellValue.isEmpty() ? "N/A" : cellValue);
				}
				dataList.add(rowData);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//        System.out.println("dataList Before = "+dataList);
		dataList.remove(0);
		//        System.out.println("dataList after = "+dataList);
		return dataList;
	}
	// Method to handle different cell types and avoid errors

	private static String getCellValueAsString(Cell cell) {
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue().toString(); // Convert date to string
			} else {
				return String.valueOf((int) cell.getNumericCellValue()); // Convert numeric to int and string
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		case BLANK:
		default:
			return ""; // Return empty string for blank cells
		}
	}

	public DocumentDetails callDownloadDocumentServiceOfProduct(DocumentDetails objIndividualDocDetails, String docIndex, String strCIF) {
		System.out.println("Inside callDownloadDocumentServiceOfProduct method");
		System.out.println("docIndex = " + docIndex + " \t  || strCIF = " + strCIF  );
		String documentName = "";
		String docExt = "";
		String isIndex = "";
		String sOutputXml = "";
		try {
			//Removed Dormancy Code from here
			sOutputXml = getDocumentImageindex(docIndex);
			LogMe.logger.info("Output xml For NGOGetDocumentProperty_Input Call: "
					+ sOutputXml);
			XMLParser lobjXMLParser = new XMLParser();
			lobjXMLParser.setInputXML(sOutputXml);
			//String lstrMainCode = lobjXMLParser.getValueOf("Status");MainCode
			String lstrMainCode = lobjXMLParser.getValueOf("MainCode");
			int lintMainCode = Integer.parseInt(lstrMainCode);
			LogMe.logger.info("Status of NGOGetDocumentProperty_Input call "
					+ lintMainCode);
			if (lintMainCode == 0) {
				LogMe.logger.info("NGODocument_Input call success");
				//isIndex = lobjXMLParser.getValueOf("ISIndex");
				isIndex = lobjXMLParser.getValueOf("ISINDEX");
				LogMe.logger.info("IsIndex value " + isIndex);
				isIndex = isIndex.substring(0, isIndex.lastIndexOf("#"));
				LogMe.logger.info("IsIndex value after removing last # "
						+ isIndex);
				strSiteID = lobjXMLParser.getValueOf("SITEID");
				//docExt = lobjXMLParser.getValueOf("CreatedByAppName");
				docExt = lobjXMLParser.getValueOf("CREATEDBYAPPNAME");
				LogMe.logger.info("The document Extension " + docExt);
				//documentName = lobjXMLParser.getValueOf("DocumentName");
				documentName = lobjXMLParser.getValueOf("DOCUMENTNAME");
				//CHG00005
				if(documentName != null && !documentName.equals("")) {
					documentName= documentName.replaceAll("[\\[\\]<>/\\\\|?*:,\"]","");
				}
				LogMe.logger.info("The Document Name " + documentName);
				//                    String downloadLocation = strDocDownLoadPath
				//                            + System.getProperty("file.separator") + docIndex + "_" + documentName
				//                            + "." + docExt;
				LogMe.logger.info("strDocDownLoadPath " + strDocDownLoadPath);                    
				System.out.println("strDocDownLoadPath " + strDocDownLoadPath); 
				//CHG00003

				String downloadPath = strDocDownLoadPath
						+ System.getProperty("file.separator") + strCIF;
				LogMe.logger.info("downloadPath " + downloadPath);                    
				System.out.println("downloadPath " + downloadPath); 
				File directory = new File(downloadPath);
				directory.mkdirs();//It will create list of directoris are available in path
				String downloadLocation = downloadPath
						+ System.getProperty("file.separator") + docIndex + "_" + documentName
						+ "." + docExt;
				String DocXMLLocation = downloadPath
						+ System.getProperty("file.separator") + docIndex + "_" + documentName
						+ ".xml";
				objIndividualDocDetails.setOUTPUT_FILENAME(docIndex + "_" + documentName + "." + docExt);
				LogMe.logger.info("Downloaded Location " + downloadLocation);
				System.out.println("Downloaded Location " + downloadLocation);
				LogMe.logger.info("DocXMLLocation Location " + DocXMLLocation);
				System.out.println("DocXMLLocation Location " + DocXMLLocation);

				int m_nDocIndexInt = Integer.parseInt(isIndex);
				//int m_nDocIndexInt = Integer.parseInt(isIndex.substring(0,
				//	isIndex.indexOf("#")));
				LogMe.logger.info("m_nDocIndexInt value is " + m_nDocIndexInt);
				JPISIsIndex JPisIndex = new JPISIsIndex();
				//JPisIndex.m_sVolumeId = Short.parseShort(isIndex
				//		.substring(isIndex.indexOf("#") + 1));
				String tempIsindex = lobjXMLParser.getValueOf("ISINDEX");
				JPisIndex.m_sVolumeId = Short.parseShort(tempIsindex.substring(tempIsindex.indexOf("#") + 1));
				LogMe.logger.info("JPisIndex.m_sVolumeId value is "
						+ JPisIndex.m_sVolumeId);

				LogMe.logger.info("JPisIndex.m_sVolumeId value222 is "
						+ JPisIndex.m_sVolumeId);

				LogMe.logger.info("siteID value222 is " + strSiteID);
				System.out.println("this.strIP = " + this.strIP + " \t  || this.strPort = " + this.strPort + " \t  || this.strCabName = "+ this.strCabName
						+ " \t  || DMS Tray strSiteID = " + strSiteID  + " \t  || DMS Tray JPisIndex.m_sVolumeId = " + JPisIndex.m_sVolumeId  
						+ " \t  || DMS m_nDocIndexInt  = " + m_nDocIndexInt + " \t  || DMS downloadLocation  = " + downloadLocation);
				System.out.println("Before calling GetDocInFile_MT ....");
				try {
					CPISDocumentTxn.GetDocInFile_MT(null, this.strIP, Short.parseShort(this.strPort), this.strCabName, Short.parseShort(strSiteID), (short) JPisIndex.m_sVolumeId, m_nDocIndexInt, "",
							downloadLocation, new JPDBString());

				} catch (JPISException eCPI) {
					eCPI.printStackTrace();
					LogMe.logger.error("Fail : -1  || CALL:NGODownloadDocument : Unknown Error: Download Document Call JPISException " + eCPI);
					System.out.println("Fail : -1  || CALL:NGODownloadDocument : Unknown Error: Download Document Call JPISException " + eCPI);
				} catch (Exception eCPI) {
					LogMe.logger.error("Fail : -1  || CALL:NGODownloadDocument : Unknown Error: Download Document Call Exception " + eCPI);
					System.out.println("Fail : -1  || Unknown Error: Download Document Call Exception " + eCPI);
				} finally {
					DeleteEmptyFile(downloadLocation);
				}
				LogMe.logger.info("After calling GetDocInFile_MT ....");
				//select doc_id from los wala
				//if doc_id is 571 then convert
				//conver function here
				String docid_LOS = "";
				String strInputXML = "<?xml version='1.0'?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><Query>"
						+ "select field_186 from ddt_48 where folddocindex='" + docIndex + "'"
						+ "</Query>"
						+ "<EngineName>"
						+ strCabName
						+ "</EngineName>"
						+ "<SessionId>"
						+ "</SessionId></APSelectWithColumnNames_Input>";

				String strOutputXML = executeCallBroker(strInputXML);
				XMLParser sessionXMLParser = new XMLParser();
				sessionXMLParser.setInputXML(strOutputXML);
				String lstrMainCodeWI = sessionXMLParser.getValueOf("MainCode");
				int sessionMainCode = Integer.parseInt(lstrMainCodeWI);
				if (sessionMainCode == 0) {
					String totalRecords = sessionXMLParser.getValueOf("TotalRetrieved");
					if (!totalRecords.equalsIgnoreCase("0")) {
						docid_LOS = sessionXMLParser.getNextValueOf("FIELD_186");
						// docid_LOS=sessionXMLParser.getNextValueOf("FIELD_216");
					}
				}

				LogMe.logger.info("inside 571 case " + strInputXML);
				LogMe.logger.info("inside 571 case " + strOutputXML);
				LogMe.logger.info("inside 571 case " + docid_LOS);
				LogMe.logger.info("inside 571 case " + docExt);
				
				// Commented by Shivanshu
//				if (docid_LOS.equalsIgnoreCase("571") && (docExt.equalsIgnoreCase("tif") || docExt.equalsIgnoreCase("tiff"))) {
//					try {
//						LogMe.logger.info("inside 571 case ");
//						byte[] inputByteArray = null;
//						RandomInputStream ris = null;
//						Image tempImage = null;
//						RandomInputStreamSource riss = null;
//						riss = new RandomInputStreamSource(downloadLocation);
//						ris = riss.getStream();
//						@SuppressWarnings("deprecation")
//						RandomAccessFileOrArray myTiffFile = new RandomAccessFileOrArray(downloadLocation);
//						//Find number of images in Tiff file
//						int numberOfPages = TiffImage.getNumberOfPages(myTiffFile);
//						LogMe.logger.info("Number of Images in Tiff File" + numberOfPages);
//						Document TifftoPDF = new Document();
//						String downloadLocation_pdf = strDocDownLoadPath
//								+ System.getProperty("file.separator") + docIndex + "_" + documentName
//								+ ".pdf";
//						PdfWriter.getInstance(TifftoPDF, new FileOutputStream(downloadLocation_pdf));
//						TifftoPDF.open();
//						//Run a for loop to extract images from Tiff file
//						//into a Image object and add to PDF recursively
//						for (int i = 1; i <= numberOfPages; i++) {
//							inputByteArray = com.newgen.niplj.fileformat.Tif6.isTifJpegCompressed(ris, i);
//							if (inputByteArray == null) {
//								tempImage = TiffImage.getTiffImage(myTiffFile, i);
//							} else {
//								tempImage = com.itextpdf.text.Image.getInstance(inputByteArray);
//							}
//							TifftoPDF.newPage();
//							//tempImage=TiffImage.getTiffImage(myTiffFile, i);
//							tempImage.setAbsolutePosition(0, 0);
//							tempImage.scaleToFit(612, 792);
//							TifftoPDF.add(tempImage);
//						}
//						TifftoPDF.close();
//						LogMe.logger.info("Tiff to PDF Conversion in Java Completed");
//						System.out.println("Tiff to PDF Conversion in Java Completed");
//						downloadLocation = downloadLocation_pdf;
//						docExt = "pdf";
//						flagLos = 1;
//					} catch (Exception i1) {
//						LogMe.logger.info("error in conversion " + i1.getMessage());
//						i1.printStackTrace();
//					}
//				}
				
				
			} else {
				LogMe.logger.info("Fail : " + lstrMainCode + " || CALL:NGODownloadDocument || " + lobjXMLParser.getValueOf("Error"));
				System.out.println("Fail : " + lstrMainCode + " || CALL:NGODownloadDocument || " + lobjXMLParser.getValueOf("Error"));
			}
			return objIndividualDocDetails;
		} catch (Exception e) {
			LogMe.logger.info("Fail : -1  || CALL:NGODownloadDocument : Unknown Error: Download Document Call Exception " + e.toString());
			System.out.println("Fail : -1  || Unknown Error: Download Document Call Exception " + e.toString());
			return objIndividualDocDetails;
		}
	}

	public void DeleteEmptyFile(String filePath){
		// Create a File object
		File file = new File(filePath);
		// Check if the file exists and is empty (size == 0 bytes)
		if (file.exists() && file.length() == 0) {
			if (file.delete()) {
				System.out.println("Empty file '" + filePath + "' deleted successfully.");
			} else {
				System.out.println("Failed to delete the file.");
			}
		} else {
			System.out.println("File is not empty or does not exist.");
		}
	}

	private String getDocumentImageindex(String docIndex) {
		String imageIndex = "";
		String strOutputXML = "";
//		String strInputXML = "<?xml version='1.0'?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><Query>"
//				+ "select imageindex||'#'||a.volumeid isindex,appname CreatedByAppName,name documentname,siteid from pdbdocument a,isdoc b where documentindex='" + docIndex + "' and a.imageindex=b.docindex and a.volumeid=b.volumeid"
//				+ "</Query>"
//				+ "<EngineName>"
//				+ strCabName
//				+ "</EngineName>"
//				+ "<SessionId>"
//				+ "</SessionId></APSelectWithColumnNames_Input>";
//		LogMe.logger.info("select Input:" + strInputXML);
		
		String strQuery = 
				"select imageindex||'#'||a.volumeid isindex,appname CREATEDBYAPPNAME,name DOCUMENTNAME,siteid SITEID from pdbdocument a,isdoc b where documentindex='" + docIndex + "' and a.imageindex=b.docindex and a.volumeid=b.volumeid";
		
		LogMe.logger.info("select Input:" + strQuery);

		try {
//			strOutputXML = executeCallBroker(strInputXML);
//			LogMe.logger.info("select output:" + strOutputXML);
//			XMLParser sessionXMLParser = new XMLParser();
//			sessionXMLParser.setInputXML(strOutputXML);
//			imageIndex = sessionXMLParser.getValueOf("IMAGEINDEX");
			
			String xmlQuery = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(strCabName,strQuery,strSessionId));
			XMLParser sessionXMLParser= new XMLParser(xmlQuery);
			String outputXML = sessionXMLParser.getValueOf("APSelectWithColumnNames_Output");	

			imageIndex = sessionXMLParser.getValueOf("IMAGEINDEX");
			
			LogMe.logger.info("[executeOnWI]" + "executeOnWI sQuery : " + strQuery);
			LogMe.logger.info("[executeOnWI]" + "executeOnWI outputString : " + outputXML);
			LogMe.logger.info("[executeOnWI]" + "executeOnWI imageIndex : " + imageIndex);


		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strOutputXML;

	}


	public int insertExcelIntoDB(String filePath, String tableName) {
		LogMe.logger.info( "~~~~~~~~~~~~~~~~~~ inside insertExcelIntoDB ~~~~~~~~~~~~~~~~~~~~~~~~~");
		int insertCount =0;
		LogMe.logger.info("insertExcelIntoDB filePath: " + filePath);

		try ( FileInputStream file = new FileInputStream(new File(filePath));  XSSFWorkbook workbook = new XSSFWorkbook(file)) {
			LogMe.logger.info("inside insertExcelIntoDB workbook: " + workbook.getSheetAt(0));

			Sheet sheet = workbook.getSheetAt(0); // Read the first sheet
			
			LogMe.logger.info("inside insertExcelIntoDB sheet " + sheet.getSheetName());

			for (int i=1; i<=sheet.getLastRowNum(); i++) { //skip header row
				Row row = sheet.getRow(i);
				LogMe.logger.info("insertExcelIntoDB row: " + row.getRowNum());

				Cell cell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				String custCID = getCellValueAsString(cell);
				LogMe.logger.info("insertExcelIntoDB custCID: " + custCID);

//				cell = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//				String custCategory = getCellValueAsString(cell);
//				LogMe.logger.info("insertExcelIntoDB custCategory: " +custCategory);

				if(!"".equalsIgnoreCase(custCID)) {
					String outputXML = insertData("DATE_TIME,CUSTOMER_CID,STATUS", "SYSDATE,'"+custCID+"','N'", tableName);
					LogMe.logger.info("insertExcelIntoDB outputXML: " + outputXML);
					insertCount++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogMe.logger.info("Exception insertExcelIntoDB : " + e);
		}
		return insertCount;
	}


	public String insertData(String colName, String values, String tableName) {
		LogMe.logger.info("Inside insertData method");
		LogMe.logger.info("colName to be insert======" + colName);
		LogMe.logger.info("value to be insert==" + values);
		StringBuilder inputxml = new StringBuilder();
		inputxml = inputxml.append("<?xmlversion=\"1.0\"?>");
		inputxml = inputxml.append("<APInsert>");
		inputxml = inputxml.append("<Option>APInsert</Option>");
		inputxml = inputxml.append("<ProcessDefId>").append(strUsername).append("</ProcessDefId>");
		inputxml = inputxml.append("<Status>true</Status>");
		inputxml = inputxml.append("<SessionId>").append(strSessionId).append("</SessionId>");
		inputxml = inputxml.append("<EngineName>").append(strCabName).append("</EngineName>");
		inputxml = inputxml.append("<TableName>").append(tableName).append("</TableName>");
		inputxml = inputxml.append("<ColName>"+colName+"</ColName>");
		inputxml = inputxml.append("<Values>"+values+"</Values>");
		inputxml = inputxml.append("</APInsert>");
		LogMe.logger.info("inputxml==" + inputxml);
		XMLParser parsergetlist = null;
		String strCompOutputXml = null;
		try {
			strCompOutputXml = executeCallBroker(inputxml.toString());
			parsergetlist = new XMLParser(strCompOutputXml);
			if (parsergetlist.getValueOf("MainCode").equals("0")) {
				return (parsergetlist.getValueOf("MainCode"));
			} else {
				return "";
			}
		} catch (Exception e) {
			LogMe.logger.info("[Error]Error in insert the data" + e);
			return "";
		} finally {
			if (strCompOutputXml != null) {
				strCompOutputXml = null;
			}
			if (parsergetlist != null) {
				parsergetlist = null;
			}
			if (inputxml != null) {
				inputxml = null;
			}
		}
	}

	public void moveToHistoryTable() {

	}


	public int executeAPProcedure(String procName, String inValues, int noofCols) {
		String strOutputXmlFI = null;
		StringBuilder strBuffFI = new StringBuilder();

		strBuffFI = strBuffFI.append("<?xmlversion=\"1.0\"?>");
		strBuffFI = strBuffFI.append("<APProcedure_Input>");
		strBuffFI = strBuffFI.append("<Option>APProcedureExtd</Option>");
		strBuffFI = strBuffFI.append("<EngineName>").append(strCabName).append("</EngineName>");
		strBuffFI = strBuffFI.append("<SessionId>").append(strSessionId).append("</SessionId>");
		strBuffFI = strBuffFI.append("<ProcName>").append(procName).append("</ProcName>");
		strBuffFI = strBuffFI.append("<Params>").append(inValues).append("</Params>");
		strBuffFI = strBuffFI.append("<NoOfCols>").append(noofCols).append("</NoOfCols>");
		strBuffFI = strBuffFI.append("</APProcedure_Input>");
		LogMe.logger.info("strBuffFI==" + strBuffFI);
		strOutputXmlFI = executeCallBroker(strBuffFI.toString());
		LogMe.logger.info("executeAPProcedure strOutputXmlFI=" + strOutputXmlFI);
		XMLParser xp = new XMLParser(strOutputXmlFI);
		
		return Integer.parseInt(xp.getValueOf("MainCode"));
	}
	
	private void initializeSocket() {
		try{
			String sQuery1 = "SELECT (SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETIP') IP,"
					+ "(SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETPORT') PORT FROM DUAL";
			String outputXml = ExecuteXML.executeXML(
					GenerateXml.APSelectWithColumnNames(strCabName,sQuery1,strSessionId));
			LogMe.logger.info("outputXml="+outputXml);
			XMLParser xmlDataParser1 = new XMLParser(outputXml);
			int mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
			if (mainCode == 0) {
				String ip = xmlDataParser1.getValueOf("IP");
				String port = xmlDataParser1.getValueOf("PORT");
				socket = ConnectSocket.getReference(strIP, Integer.parseInt(port));
			}
		}catch(Exception e){
			LogMe.logger.error("Exception in initializeSocket",e);
		}
	}
	
	@SuppressWarnings("unused")
	private void connectToWorkflow(){
		try {			
			if (strUsername == null || strPwd == null) {
				throw new Exception("Username or Password not specified.");
			}
			LogMe.logger.info("cabinetName= " + strCabName + " username-" + strUsername);
			String connectInputXML = GenerateXml.getConnectInputXML(strCabName, strUsername, strPwd);
			LogMe.logger.info("Connect Input XML:\n" + modifyInputXML(connectInputXML));
			String connectOutputXML = ExecuteXML.executeXML(connectInputXML);
			LogMe.logger.info(connectOutputXML);
			String mainCode = getTagValue(connectOutputXML, "MainCode");
			mainCode = mainCode.trim();
			LogMe.logger.info("main code: " + mainCode);
			if (!mainCode.equalsIgnoreCase("0")) {
				LogMe.logger.info("Error during connection with workflow.");	
			}
			sessionId = getTagValue(connectOutputXML, "SessionId").trim();
			LogMe.logger.info("session id: " + sessionId);
			if(null == sessionId || sessionId.equalsIgnoreCase("") || sessionId.equalsIgnoreCase("null")) {
//				errorFlag = true;
				LogMe.logger.info("Unable to login, some problem occurred");
			}
			LogMe.logger.info("Successfully logged in into workflow");	
		} catch (Exception e) {
//			errorFlag = true;
			LogMe.logger.info("exception in connectToWorkflow: ",e);;
		}
	}
	
	
	private String getTagValue(String xml, String tag) throws ParserConfigurationException, SAXException, IOException {
		Document doc = getDocument(xml);
		NodeList nodeList = doc.getElementsByTagName(tag);

		int length = nodeList.getLength();

		if (length > 0) {
			Node node = nodeList.item(0);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				NodeList childNodes = node.getChildNodes();
				String value = "";
				int count = childNodes.getLength();
				for (int i = 0; i < count; i++) {
					Node item = childNodes.item(i);
					if (item.getNodeType() == Node.TEXT_NODE) {
						value += item.getNodeValue();
					}
				}
				return value;
			} else if (node.getNodeType() == Node.TEXT_NODE) {
				return node.getNodeValue();
			}

		}
		return "";
	}
	
	private Document getDocument(String xml) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(new StringReader(xml)));
		return doc;
	}

	private String modifyInputXML(String sInputXML)
	{
		return sInputXML.replace(sInputXML.substring(sInputXML.indexOf("<Password>")+10, sInputXML.indexOf("</Password>")),"*********");		
	}
}
