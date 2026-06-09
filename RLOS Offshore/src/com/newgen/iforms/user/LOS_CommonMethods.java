package com.newgen.iforms.user;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*------------------------------------------------------------------------------------------------------
NEWGEN SOFTWARE TECHNOLOGIES LIMITED
Author                         : 
Group                          : AUB-RLOS
Project/Product                : Application -Projects
Application                    :
Module                         : Common methods
File Name                      : LOS_CommonMethods.java
Date (DD/MM/YYYY)              : June 11, 2021
Description                    : This file will contain all common methods 


Dependent Jars:
	rt.jar,iforms.jar
------------------------------------------------------------------------------------------------------
CHANGE HISTORY

-------------------------------------------------------------------------------------------------------

Problem No/CR No   Change Date   Changed By    Change Description

---------------------------------------------------------------------------------------------------------
*/

//import com.newgen.AESEncryption;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.Integration.Integration_Handler;
//import com.newgen.json.JSONArray;
//import com.newgen.json.JSONObject;
import com.newgen.omni.wf.util.app.NGEjbClient;
import com.newgen.webserviceclient.NGWebServiceClient;
public class LOS_CommonMethods {
	
	   static LOS_NGGlobals global = new LOS_NGGlobals();
	   String formDate[]= new String[3];
		static String classPath = System.getProperty("java.class.path") + "\t";
	       
	        String propFileAppSever = System.getProperty("user.dir") + File.separator + "BRMS_Properties" + File.separator + "LoaneligibilityBRMS.properties";
	        Properties propbasicApp = loadPropertyFile(propFileAppSever);
	        String ApplicationServerIp = propbasicApp.getProperty("ApplicationIp");
	        String ApplicationServerPort = propbasicApp.getProperty("WrapperPort");
	        String ApplicationServerCabinet = propbasicApp.getProperty("cabinetName");
	        String SocketPort = propbasicApp.getProperty("SocketPort");
	        String O2MSURLSession = propbasicApp.getProperty("O2MSURLSession");
	        String O2MSURLDocGen = propbasicApp.getProperty("O2MSURLDocGen");
	        String O2MSUsername = propbasicApp.getProperty("userName");
	        String O2MSPassword = propbasicApp.getProperty("password");

	        
	        
	        public static Properties loadPropertyFile(String filePath) {
	    		LOS_EG.mLogger.info("Method called");

	    		LOS_EG.mLogger.info("Property File Path: " + filePath);

	    		Properties propObj = null;
	    		try {
	    			propObj = new Properties();
	    			propObj.load(new FileInputStream(filePath));
	    		} catch (FileNotFoundException e) {
	    			LOS_EG.mLogger.info("Error");
	    			propObj = null;
	    		} catch (IOException e) {
	    			LOS_EG.mLogger.info("Error IO");
	    			propObj = null;
	    		}

	    		LOS_EG.mLogger.info("METHOD_ENDS");
	    		return propObj;
	    	}
	        
	        public static String getCustomStackTrace(Exception e) {
	    		StringWriter sw = new StringWriter();
	    		PrintWriter pw = new PrintWriter(sw);
	    		e.printStackTrace(pw);
	    		return sw.toString();
	    	}
	        
	        public String executeTemplateGenerationCall(String TemplateInputXml,Properties properies)
	        {
	        	
	        	LOS_EG.mLogger.info("Inside executeTemplateGenerationCall");
	        	String result="";
	        	 Socket socket =null;
	        	try
	            {
	        	  String socketIP=properies.getProperty("jtsAddress");	
	        	  int socketPORT=4446;
	              LOS_EG.mLogger.info("Inside Socket Try BLock");
	               socket = new Socket(socketIP, socketPORT);

	              System.out.println("Client Connected.....");
	              LOS_EG.mLogger.info("Client Connected.....");
	              DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
	              if ((TemplateInputXml != null) && (TemplateInputXml.length() > 0))
	              {
	                dout.write(TemplateInputXml.getBytes("UTF-16LE"));
	                dout.flush();
	              }

	              try
	              {
	            	  LOS_EG.mLogger.info("Inside DataInputStream Try BLock");
	            	  DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
	                  byte[] readBuffer = new byte[50000];
	                  int num = in.read(readBuffer);
	                  LOS_EG.mLogger.info("Num:::::"+ num);
	                  if (num > 0) {  
	                    byte[] arrayBytes = new byte[num];
	                    System.arraycopy(readBuffer, 0, arrayBytes, 0, num);
	                    result = new String(arrayBytes, "UTF-16LE");
	                    System.out.println("Result===>>" + result);
	                  }
	                  LOS_EG.mLogger.info("Result===>>" + result);
	                  return result;

	        		}
	        		catch (SocketException localSocketException)
	        		{
	        			return result;
	        		}
	        		catch (IOException i)
	        		{
	        			i.printStackTrace();
	        			return result;
	        		}
	        	} catch (Exception e) {
	        		e.printStackTrace();
	        		return result;
	        	}
	        	//added by shalini 28/01/2020 3:40 PM
	        	finally{

	        		try{
	        			socket.close();

	        		} catch (IOException e) {
	        			System.out.println("Could not close socket");

	        		}
	        	}
	        	//ended
	        }
	        
	        
	        public static String saveFormForDecison(IFormReference ifr, String param) {
	    		// TODO Auto-generated method stub
	    		LOS_EG.mLogger.info("saveFormForDecison function  ");
	    		
	    			try{
	    				String decision ="";
	    				LOS_NGGlobals.activityName = ifr.getActivityName();
	    				LOS_NGGlobals.pId = ifr.getObjGeneralData().getM_strProcessInstanceId();
	    				LOS_NGGlobals.userName = ifr.getUserName();
	    				//String decision = (String)ifr.getControlValue("decision");				
	    				//LOS_EG.mLogger.info("decision: "+  decision );
	    				String query =  "select decision from  los_exttable  WHERE wi_name='" +LOS_NGGlobals.pId+ "'";
	    				//String query = "UPDATE los_exttable SET decision='" + decision+ "' WHERE wi_name='" +LOS_NGGlobals.pId+ "'";
	    				LOS_EG.mLogger.info("check decision value: "+query);				
	    			        List < List < String >> decisionValue = ifr.getDataFromDB(query);             
	    			        if (decisionValue.size() != 0) {
	    			        decision = decisionValue.get(0).get(0);  
	    			        LOS_EG.mLogger.info("check decision valueeeee: "+decision);
	    			        }
	    				//int mainCode = ifr.saveDataInDB(query);
	    				//LOS_EG.mLogger.info("maincode"+mainCode);
	    				return decision;
	    			}
	    	
	    		catch (Exception e) {
	    			e.printStackTrace();
	    			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
	    			return "error";

	    		}
	    		finally {
	    			LOS_EG.mLogger.info("finally block executed");
	    			LOS_NGGlobals.activityName = null;
	    			LOS_NGGlobals.pId = null;
	    			LOS_NGGlobals.userName = null;
	    		}
	    			
	    	}
	    	//end
	    	      
	        public static String getDataInDecisionHistory(IFormReference ifr, String param) {
	    		// TODO Auto-generated method stub
	    		LOS_EG.mLogger.info("getDataInDecisionHistory function  ");
	    		
	    			try{
	    				
	    				/*String tempQuery = "INSERT INTO atemp2(col1,col2) VALUES ('T1',N'" + ifr.getControlValue("Remarks") + "')";
	    				LOS_EG.mLogger.info("tempQuery 1 "+tempQuery);	
	    				ifr.saveDataInDB(tempQuery);
	    				
	    				tempQuery = "INSERT INTO atemp2(col1,col2) VALUES ('T2',N'" + ifr.getControlValue("Remarks").toString() + "')";
		    			LOS_EG.mLogger.info("tempQuery 2 "+tempQuery);	
		    			ifr.saveDataInDB(tempQuery);
	    				
		    			tempQuery = "INSERT INTO atemp2(col1,col2) VALUES ('T3',N'" + ifr.getControlValue("Remarks").toString().replace("'", "").replace("%", "percentage") + "')";
		    			LOS_EG.mLogger.info("tempQuery 3 "+tempQuery);	
		    			ifr.saveDataInDB(tempQuery);
	    				
		    			tempQuery = "INSERT INTO atemp2(col1,col2) VALUES ('T4',N'" + StandardCharsets.UTF_8.decode(StandardCharsets.UTF_8.encode(ifr.getControlValue("Remarks").toString())) + "')";
		    			LOS_EG.mLogger.info("tempQuery 4 "+tempQuery);	
		    			ifr.saveDataInDB(tempQuery);*/
		    			
		    			
	    				LOS_NGGlobals.activityName = ifr.getActivityName();
	    				LOS_NGGlobals.pId = ifr.getObjGeneralData().getM_strProcessInstanceId();
	    				LOS_NGGlobals.userName = ifr.getUserName();
	    				String decision = (String)ifr.getControlValue("Decision");
	    				
	    				String remarks = (String)ifr.getControlValue("Remarks");
	    				
	    			    if(!(remarks==null)&&(!(remarks.equalsIgnoreCase("")))){						
	    				LOS_EG.mLogger.info("remarks old: "+  remarks);				
	    				remarks=remarks.replaceAll("'", "");				
	    				remarks=remarks.replace("%", "percentage");
	    				}
	    				else{
	    				remarks="";						
	    				}
	    				LOS_EG.mLogger.info("remarks new : "+  remarks );			
	    				String entry_time = (String)ifr.getControlValue("Entry_Date_Time");
	    				LOS_EG.mLogger.info("Activity Name : "+  LOS_NGGlobals.activityName );
	    				LOS_EG.mLogger.info("remarksss : "+ remarks );
	    				LOS_EG.mLogger.info("entry_timeeee: "+  entry_time );
	    				LOS_EG.mLogger.info("\npId :"+LOS_NGGlobals.pId+"\nuserName :"+LOS_NGGlobals.userName+"\ndecision : " + decision); 
	    				//String query = "INSERT INTO ng_los_gr_decision(p_wi_name, user_name, activity_name, decision, decision_reason, insertion_datetime, remarks, submitted_by, entry_datetime) VALUES ('" + LOS_NGGlobals.pId + "','" + LOS_NGGlobals.userName  + "','" + LOS_NGGlobals.activityName  + "','" + decision + "','" + decision_reason + "',GETDATE(),'" + remarks + "','" + LOS_NGGlobals.userName + "','" + entry_time +"')";
	    				String query = "INSERT INTO NG_ELOS_GR_DecisionHistory(p_wi_name, UserName, Workstep, Decision, DateAndTime, Remarks, Entry_Date_Time) VALUES ('" + LOS_NGGlobals.pId + "','" + LOS_NGGlobals.userName  + "','" + LOS_NGGlobals.activityName  + "','" + decision + "',GETDATE(),N'" + remarks + "','" + entry_time +"')";
	    				// String query = "INSERT INTO NG_ELOS_GR_DecisionHistory(p_wi_name, UserName, Workstep, Decision, DateAndTime, Remarks, Entry_Date_Time) VALUES ('" + LOS_NGGlobals.pId + "','" + LOS_NGGlobals.userName  + "','" + LOS_NGGlobals.activityName  + "','" + decision + "',GETDATE(),(select REMARKS FROM NG_ELOS_EXTTABLE WHERE WI_NAME = '"+LOS_NGGlobals.pId+"' ),'" + entry_time +"')";
	    				LOS_EG.mLogger.info("Inserting data into ng_los_gr_decision...query is: "+query);	
	    				int mainCode = ifr.saveDataInDB(query);
	    				LOS_EG.mLogger.info("maincode"+mainCode);
	    				LOS_EG.mLogger.info("######## Decsion "+decision);
	    				/*if(decision.equalsIgnoreCase("Reject"))
	    				{
	    					LOS_EG.mLogger.info("######## Activity Name "+ifr.getActivityName());
	    					if(ifr.getActivityName().contains("RCR"))
	    					{
	    						LOS_EG.mLogger.info("######## Contains RCR Yes");
								LOS_EG.mLogger.info("Before Calling Send SMS for "+ifr.getObjGeneralData().getM_strProcessInstanceId()+" $$$ Event Reject Decsion at RCR");
								
	    						Integration_Handler.Send_SMS(ifr,"Reject");
	    					}
	    				}*/
	    			}
	    	
	    		catch (Exception e) {
	    			e.printStackTrace();
	    			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
	    			return "error";

	    		}
	    		finally {
	    			LOS_EG.mLogger.info("finally block executed");
	    			LOS_NGGlobals.activityName = null;
	    			LOS_NGGlobals.pId = null;
	    			LOS_NGGlobals.userName = null;
	    		}
	    			return "";
	    	}
	        
	        public static String getGroupIndex(IFormReference ifr)
	        {
	        	LOS_EG.mLogger.info("Inside getGroupIndex function");
	        	try
	        	{
	        	String groupIndex = "";
	        	LOS_NGGlobals.userName = ifr.getUserName();
	        	String query =  "SELECT GroupIndex FROM PDBGroupMember WHERE PDBGroupMember.UserIndex = (SELECT UserIndex FROM PDBUser WHERE UserName = '" +LOS_NGGlobals.userName+ "')";
	        	LOS_EG.mLogger.info("check decision value: "+query);				
			       List < List < String >> groupValue = ifr.getDataFromDB(query);             
			        if (groupValue.size() != 0) {
			        groupIndex = groupValue.get(0).get(0);  
			        LOS_EG.mLogger.info("check groupIndex valueeeee: "+groupValue);
			        }
			        groupIndex = "13";
			        return groupIndex;
	        	}
	        	catch (Exception e) {
	    			e.printStackTrace();
	    			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
	    			return "error";

	    		}
	    		finally {
	    			LOS_EG.mLogger.info("finally block executed");
	    			LOS_NGGlobals.activityName = null;
	    			LOS_NGGlobals.pId = null;
	    			LOS_NGGlobals.userName = null;
	    		}
	        }
	        
	        public String getDate(String date)
	    	{
	    		parseDate(date);
	    		return (formDate[0] +"/" +formDate[1] +"/"+formDate[2]);
	    		
	    	}
	    	
	        public String ageCalculator(String dob)
	        {
	        	parseDate(dob);
	        	LocalDate birthdate = LocalDate.of(Integer.valueOf(formDate[2]), Integer.valueOf(formDate[1]), Integer.valueOf(formDate[0]));      //Birth date
	        	LocalDate now =  LocalDate.now();                        //Today's date
	        	Period diff = Period.between(birthdate, now); //difference between the dates is calculated
	        	int age =diff.getYears();
	        	return (String.valueOf(age));
	        }
	      
	        public  void parseDate(String date2)
	        {    
	        	
	        	formDate[2] =  date2.substring(0,4);
	        	formDate[1] =date2.substring(5,7);
	        	formDate[0] =date2.substring(8,10);

	        }
	        
	        public String callWebService(String SOAP_inputXml,String EndPointurl1,String methodName )
	        {
	        	
	           System.out.println("Inside callWebService");
	           LOS_EG.mLogger.info("Inside callWebService");
	           LOS_EG.mLogger.info("SOAP_inputXml1: " +SOAP_inputXml );

	            //String SOAPResponse_xml1 = NGWebServiceClient.ExecuteWs(SOAP_inputXml, EndPointurl1, methodName);
	            NGWebServiceClient ngwsclnt = new NGWebServiceClient(false);
	            String SOAPResponse_xml1 = ngwsclnt.ExecuteWs(SOAP_inputXml, EndPointurl1, methodName);
	            LOS_EG.mLogger.info("SOAPResponse_xml" +SOAPResponse_xml1);
	                   
	            System.out.println("SOAPResponse_xml: " +SOAPResponse_xml1 );
	            LOS_EG.mLogger.info("SOAPResponse_xml: " +SOAPResponse_xml1 );
	            XMLParser xmlParser =new XMLParser(SOAPResponse_xml1);
	            String result="";
	            if(EndPointurl1.contains("OMSSessionManagement"))
	            { result =xmlParser.getValueOf("ax29:sessionId");
	            LOS_EG.mLogger.info("sessionId: " +result );
	            }
	            if(EndPointurl1.contains("OMSDocumentGeneration"))
	            {
	            	result=xmlParser.getValueOf("ax21:requestId");
	            	 LOS_EG.mLogger.info("request_id: " +result );
	            }
	            
	            return result; 
	               
	            
	        }
	        public static void populateDataInDropdownFromMaster(IFormReference iformReference, String str) {
	        	LOS_EG.mLogger.info("[populateDataInDropdownFromMaster] str fro JS: " + str);
				String arr[] = str.split(",");
				String dropDownId = arr[0];
				String tableName= arr[1];
				String columnName= arr[2];
				String whereColumn= arr[3];
				String whereValue = arr[4];
				String sWorkitemID=iformReference.getObjGeneralData().getM_strProcessInstanceId();
				List<String> dataFromMaster = getDataFromMaster(iformReference, tableName, columnName, whereColumn, whereValue);
				iformReference.clearCombo(dropDownId);
				for (String data : dataFromMaster) {
					iformReference.addItemInCombo(dropDownId, data, data);
				}
			}

		public static List<String> getDataFromMaster(IFormReference iformReference, String tableName, String columnName, String whereColumn, String whereValue) {
			String query = "";
			 query = "SELECT DISTINCT "+columnName+" FROM "+tableName+" where "+whereColumn+" ='"+whereValue+"'";
			 LOS_EG.mLogger.info("[getDataFromMaster] query: " + query);
			return getMasterDataList(iformReference, query);
		}
		
		public static List<String> getMasterDataList(IFormReference iformReference, String query) {
			List<String> data = new ArrayList<>();
			List<List<String>> dbData = iformReference.getDataFromDB(query);
			if (!dbData.isEmpty()) {
				for (int r = 0; r < dbData.size(); ++r) {
					List<String> rowList = dbData.get(r);
					for (int c = 0; c < rowList.size(); ++c) {
						data.add(rowList.get(c));
					}
				}
			}
			return data;
		}
		
		public static String addToDecisionHistoryGrid(IFormReference iformObj, String stringData) {
			
			try
			{
				LOS_EG.mLogger.info("inside addToDecisionHistoryGrid");
			String strData[] = stringData.split("#");
			String currDate=new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime());
			String Workstep = "";
			String username = "";
			String decision = "";
			String remarks = "";
			try
			{
			 Workstep = strData[1];
			 username = strData[0];
			 decision = strData[2];
			 remarks = strData[3];
			}
			catch (Exception e)
			{LOS_EG.mLogger.info("inside addToDecisionHistoryGrid Exception"+e.getMessage());
				
			}
			JSONArray jsonArr = new JSONArray();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("Date & Time", currDate.toString());
			jsonObj.put("User Name", username);
			jsonObj.put("Workstep Name", Workstep);
			jsonObj.put("Action", decision);
			jsonObj.put("Remarks", remarks);
			jsonArr.add(jsonObj);
			iformObj.addDataToGrid(LOS_NGGlobals.decisionHistoryTable, jsonArr); 
			
			}
			catch(Exception ex)
			{
				LOS_EG.mLogger.info("inside addToDecisionHistoryGrid Exception"+ex.getMessage());
			}
			return "SUCCESS~DataAddedToGrid";
		}
		
        public static void loadRequestType(IFormReference ifr, String param) {
    		// TODO Auto-generated method stub
    		LOS_EG.mLogger.info("loadRequestType function  ");
    		
    			try{
    				String request_type ="";
    				LOS_NGGlobals.activityName = ifr.getActivityName();
    				LOS_NGGlobals.pId = ifr.getObjGeneralData().getM_strProcessInstanceId();
    				LOS_NGGlobals.userName = ifr.getUserName();
    				String product_type = (String) ifr.getValue("Product_Type");
    				
    				String query =  "select REQUEST_TYPE from  NG_ELOS_MAST_REQUEST_TYPE  WHERE PRODUCT_TYPE='" +product_type+ "'";
    			
    				LOS_EG.mLogger.info("Query Result :: " +query);
    				List lstRequest_type = ifr.getDataFromDB(query);

    				String label="";

    				for(int i=0;i<lstRequest_type.size();i++)
    				{
    					List<String> arr1=(List)lstRequest_type.get(i);
    					label=arr1.get(0);

    					ifr.addItemInCombo("Request_Type",label,label);
    				}
    				
    			}
    	
    		catch (Exception e) {
    			e.printStackTrace();
    			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
    			

    		}
    		finally {
    			LOS_EG.mLogger.info("finally block executed");
    			LOS_NGGlobals.activityName = null;
    			LOS_NGGlobals.pId = null;
    			LOS_NGGlobals.userName = null;
    		}
    			
    	}
        
        
        public static void loadDecisionCombo(IFormReference ifr, String param) {
    		// TODO Auto-generated method stub
    		LOS_EG.mLogger.info("loadDecisionCombo function  ");
    		
    			try{
    				String request_type ="";
    				LOS_NGGlobals.activityName = ifr.getActivityName();
    				LOS_NGGlobals.pId = ifr.getObjGeneralData().getM_strProcessInstanceId();
    				LOS_NGGlobals.userName = ifr.getUserName();
    				String product_type = (String) ifr.getValue("Product_Type");
    				String initiation_type = (String) ifr.getValue("Initiation_Type");
    				if(product_type.equalsIgnoreCase("Personal Finance"))
    				{
    					product_type="PL";	
    				}
    				else if(product_type.equalsIgnoreCase("Auto Finance"))
    				{
    					product_type="AL";
    				}
    				else if(product_type.equalsIgnoreCase("Covered Card"))
    				{
    					product_type="CC";
    				}
    				else if(product_type.equalsIgnoreCase("Cash Line Facility"))
    				{
    					product_type="CL";
    				}
    				else if(product_type.equalsIgnoreCase("Murabaha Finance"))
    				{
    					product_type="MR";
    				}
    				
    				String query =  "select Decision from  NG_ELOS_MAST_Decision_Main  WHERE (Product_Type='" +product_type+ "' OR Product_Type='ALL') and Workstep_Name='" +LOS_NGGlobals.activityName+"'";
    								
					LOS_EG.mLogger.info("Query Result loadDecisionCombo :: " +query);
    				List lstDecision = ifr.getDataFromDB(query);
    				LOS_EG.mLogger.info("Query Result :: " +lstDecision);

    				String label="";
    				ifr.clearCombo("Decision");
    				for(int i=0;i<lstDecision.size();i++)
    				{
    					List<String> arr1=(List)lstDecision.get(i);
    					label=arr1.get(0);

    					ifr.addItemInCombo("Decision",label,label);
    				}
    			}
    	
    		catch (Exception e) {
    			e.printStackTrace();
    			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
    			

    		}
    		finally {
    			LOS_EG.mLogger.info("finally block executed");
    			LOS_NGGlobals.activityName = null;
    			LOS_NGGlobals.pId = null;
    			LOS_NGGlobals.userName = null;
    		}
    			
    	}
        public static void loadTierCondtion(String param, IFormReference ifr)
        {
        	String emplName=param.split("~")[0];
			String sQuery="SELECT EMPLOYER_TIER,Conditions FROM NG_ELOS_MAST_EMPLOYER WHERE Employer_Name='"+emplName+"'";
			LOS_EG.mLogger.info("Tier Query is"+sQuery);
			List < List < String >> employerTier = ifr.getDataFromDB(sQuery);
			LOS_EG.mLogger.info("employerTier "+employerTier);
			if(employerTier.size()>0) {
			ifr.setValue(param.split("~")[1],employerTier.get(0).get(0));//table109_EmployerCategory
			if("Applicant_Employer_Grade".equalsIgnoreCase(param.split("~")[1]))
			{
			String[] condition=employerTier.get(0).get(1).split(",");
			LOS_EG.mLogger.info("Condition is"+condition[0]);
			//ifr.clear("Condition");
			ifr.setValue("Condition", employerTier.get(0).get(1));
			
			
			}
			}		
        }
        public static String dedupecheck(IFormReference ifr)
        {
        	String DuplicateResponse="";
        	try{
	        	 //String DuplicateResponse="";
	        	 String NID=(String) ifr.getValue("Applicant_National_ID");
	        	 LOS_EG.mLogger.info("NID: "+NID);
	        	 String PassportNo=(String) ifr.getValue("Applicant_Passport_Number");
	        	 LOS_EG.mLogger.info("PassportNo: "+PassportNo);
	        	 String productType=(String) ifr.getValue("Product_Type");
	        	 LOS_EG.mLogger.info("productType: "+productType);
	        	 String subproduct=(String) ifr.getValue("Sub_Product_Type");
	        	 LOS_EG.mLogger.info("subproduct: "+subproduct);
	        	 String Nationality=(String) ifr.getValue("Applicant_Nationality");
	        	 LOS_EG.mLogger.info("Nationality: "+Nationality);
	        	 String CurrentWiName=ifr.getObjGeneralData().getM_strProcessInstanceId();
	        	 String query;
	        	 if(Nationality.equalsIgnoreCase("EG") || Nationality.equalsIgnoreCase("EGYPT"))
	        	 {
	        		 query="SELECT WI_NAME FROM NG_ELOS_EXTTABLE WHERE APPLICANT_NATIONAL_ID='"+NID+"' AND  PRODUCT_TYPE = '"+productType+"' AND SUB_PRODUCT_TYPE = '"+subproduct+"' AND CURRENT_WS NOT IN ('INTRODUCTION','Error_Handling','Branch_Ops','Remittance','Card_Center_To_Courier','Branch_Manager','Archival','Archival','Exit','Discard') AND WI_NAME != '"+CurrentWiName+"' ORDER BY WI_NAME DESC";
	        		 LOS_EG.mLogger.info("query: "+query);
	        	 }
	        	 else
	        	 {
	        		 query="SELECT WI_NAME FROM NG_ELOS_EXTTABLE WHERE APPLICANT_PASSPORT_NUMBER='"+PassportNo+"' AND  PRODUCT_TYPE = '"+productType+"' AND SUB_PRODUCT_TYPE = '"+subproduct+"' AND CURRENT_WS NOT IN ('INTRODUCTION','Error_Handling','Branch_Ops','Remittance','Card_Center_To_Courier','Branch_Manager','Archival','Archival','Exit','Discard') AND WI_NAME != '"+CurrentWiName+"' ORDER BY WI_NAME DESC";
	        		 LOS_EG.mLogger.info("query: "+query);
	        	 }
	        	 LOS_EG.mLogger.info("query: "+query);
	        	 List < List < String >> lstDuplicateWi = ifr.getDataFromDB(query);
	        	 LOS_EG.mLogger.info("lstDuplicateWi - Duplicate: "+lstDuplicateWi);
	        	 String DuplicateWi;
	        	 String DuplicateFlag;
	        	 if (lstDuplicateWi.size() != 0) 
	        	 {
	        		 LOS_EG.mLogger.info("Duplicates Found : "+lstDuplicateWi);
	        		 DuplicateWi=(String) lstDuplicateWi.get(0).get(0);
	        		 DuplicateFlag="Y";
	        		 LOS_EG.mLogger.info("DuplicateWi  : "+DuplicateWi);
	        		 LOS_EG.mLogger.info("DuplicateFlag  : "+DuplicateFlag);
	        		 ifr.setValue("DUPLICATE_WI_NAME", DuplicateWi);
	        		 ifr.setValue("IS_DUPLICATE",DuplicateFlag);
	        		 DuplicateResponse="Duplicate Case Found -" +DuplicateWi; 
	        		 LOS_EG.mLogger.info("DuplicateResponse  : "+DuplicateResponse);
	        		 return DuplicateResponse;
	        	 }
	        	 else
	        	 {
	        		 DuplicateWi="";
	        		 DuplicateFlag="N";
	        		 LOS_EG.mLogger.info("DuplicateWi  : "+DuplicateWi);
	        		 LOS_EG.mLogger.info("DuplicateFlag  : "+DuplicateFlag);
	        		 ifr.setValue("DUPLICATE_WI_NAME", DuplicateWi);
	        		 ifr.setValue("IS_DUPLICATE",DuplicateFlag);
	        		 DuplicateResponse="Duplicate Case Not Found "; 
	        		 LOS_EG.mLogger.info("DuplicateResponse  : "+DuplicateResponse);
	        		 return DuplicateResponse;
	        	 }
	        	 
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        		LOS_EG.mLogger.info("Exception in Duplicate : "+e);
        		DuplicateResponse="Error in Fetching Duplicate "; 
       		 	LOS_EG.mLogger.info("DuplicateResponse  : "+DuplicateResponse);
       		 	return DuplicateResponse;
        		 
        	}
        	 
        }
        public static void setApprovalMatrix(IFormReference ifr)
        {
        	LOS_EG.mLogger.info("Inside Approval Matrix");
        	String productType="";
        	if(((String)ifr.getValue("Product_Type")).equalsIgnoreCase("CC"))
        	{
        		productType="CC";
        	}
        	String sQuery="SELECT RCR_TL,HOI,RCRH,CRO FROM NG_ELOS_MAST_APPROVAL_MATRIX WHERE PRODUCT_TYPE='"+productType+"' AND SUB_PRODUCT_TYPE='"+ifr.getValue("Sub_Product_Type")+"' AND "+ifr.getValue("Credit_Approved_Amount")+" BETWEEN MIN_LOAN_AMOUNT AND MAX_LOAN_AMOUNT";
        	LOS_EG.mLogger.info("Inside Approval Matrix Query is"+sQuery);
        	List < List < String >> approvalMatrix = ifr.getDataFromDB(sQuery);
        	LOS_EG.mLogger.info("Inside Approval Matrix Result is"+approvalMatrix);
			if(approvalMatrix.size()>0) {
				
			ifr.setValue("RCR_TL",approvalMatrix.get(0).get(0));
			ifr.setValue("HOI",approvalMatrix.get(0).get(1));
			ifr.setValue("RCRH",approvalMatrix.get(0).get(2));
			ifr.setValue("CRO",approvalMatrix.get(0).get(3));
			
			
			}		
        }
        
        public static void DocumentList(IFormReference iformObj)
        {
        	LOS_EG.mLogger.info("Inside DocumentList()");
        	LOS_EG.mLogger.info("Inside Document Set ");
        	String program_type = (String) iformObj.getValue("Program_Type");

        	try
        	{
        		LOS_EG.mLogger.info("Inside DocumentSet(). control :");

        		LOS_EG.mLogger.info("Inside Documents program_type : " + program_type);
        		
        		
        		String query = "SELECT DOCUMENT_NAME FROM NG_ELOS_MAST_DOCUMENTS with (nolock) WHERE WORKSTEP_NAME = '"
        						+ iformObj.getActivityName()+"' AND (PRODUCT_TYPE='"+iformObj.getValue("Product_Type")
        						+"' OR PRODUCT_TYPE='ALL') AND (Sub_Product_Type='"+iformObj.getValue("Sub_Product_Type")
        						+"' OR Sub_Product_Type='ALL') ";

        		if ("SECURED PERSONAL FINANCES / Goods Murabaha".equalsIgnoreCase((String) iformObj.getValue("Program_Type"))) {

        			query += " AND (DOCUMENT_NAME  LIKE '%Murabaha Goods%' " +
        					"OR DOCUMENT_NAME  LIKE '%Goods Murabaha%')";
        		} else {
        			query += " AND (DOCUMENT_NAME NOT LIKE '%Murabaha Goods%' " +
       		             "AND DOCUMENT_NAME NOT LIKE '%Goods Murabaha%')";
        		}
        		
        		query += " ORDER BY DOCUMENT_NAME DESC";

        		LOS_EG.mLogger.info("Query  :: " +query);
        		List lstDocuments = iformObj.getDataFromDB(query);

        		LOS_EG.mLogger.info("Query Result :: " +lstDocuments);
        		String value="";
        		String label="";
        		iformObj.clearCombo("Document_Name");
        		for(int i=0;i<lstDocuments.size();i++)
        		{
        			@SuppressWarnings({ "unchecked", "rawtypes" })
        			List<String> arr1=(List)lstDocuments.get(i);
        			label=arr1.get(0);
        			value=arr1.get(0);
        			iformObj.addItemInCombo("Document_Name",label,value);
        		}
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        		LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
        	}
        }
        public static void DocumentGrid(IFormReference iformObj)
    	{
        	LOS_EG.mLogger.info("Inside DocumentGrid()");
        	LOS_EG.mLogger.info("Inside Document Grid "+iformObj.getObjGeneralData().getM_strProcessInstanceId());
        	String product_type = (String) iformObj.getValue("Product_Type");
        	String program_type = (String) iformObj.getValue("Program_Type");
        	try
    		{
        		String query="SELECT Document_TYPE FROM NG_ELOS_MAST_DOCUMENT_CHECKLIST WHERE PRODUCT_TYPE='"+product_type+"' AND Program_type='"+program_type+"'";
				 LOS_EG.mLogger.info("Query For documentChecklist " +query);
   				List lstCheckList = iformObj.getDataFromDB(query);
   				iformObj.clearTable("documentVeificationTable");
   				for(int i=0;i<lstCheckList.size();i++)
   				{
   					
   					
   					List<String> arr1=(List)lstCheckList.get(i);
   					LOS_EG.mLogger.info("Query For data is " +arr1.get(0));
   					JSONArray ja= new JSONArray();
   					JSONObject jo= new JSONObject();
   					jo.put("Document List", arr1.get(0));
   					jo.put("Mandatory", "No");
   					jo.put("Expected Date", new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
   					jo.put("Workstep / Deptt Name", "Introduction");
   					ja.add(jo);
   					iformObj.addDataToGrid("documentVeificationTable", ja);
   				}
    		}
        	catch(Exception e)
    		{
    			e.printStackTrace();
    			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
    		}
    	}
        	
        public static String ExecuteQueryOnServer(IFormReference iformObj,String sInputXML)
    	{
    		try
    		{
    			LOS_EG.mLogger.info("Server Ip :"+iformObj.getServerIp());
    			LOS_EG.mLogger.info("Server Port :"+iformObj.getServerPort());
    			LOS_EG.mLogger.info("App Server type :"+iformObj.getObjGeneralData().getM_strAppServerType());
    			LOS_EG.mLogger.info("Input XML :"+sInputXML);
    			
    			String outputXML=NGEjbClient.getSharedInstance().makeCall(iformObj.getServerIp(), iformObj.getServerPort() + "",iformObj.getObjGeneralData().getM_strAppServerType(), sInputXML);
    			LOS_EG.mLogger.info("[OutputXml]:\n "+outputXML);
    			return outputXML;
    			 
    		}
    		catch(Exception excp)
    		{
    			LOS_EG.mLogger.info("Exception occured in executing API on server :\n"+excp);
    			LOS_EG.printException(excp);
    			return null;
    		}
    	}
        
        public static  String executeQueryAPSelect(IFormReference iformObj, String sQuery) 
    	{
    		try
    		{
    	     	String inputXML = "<?xml version=\"1.0\"?>" +
                "<APSelect_Input>" +
                "<Option>APSelect</Option>" +
                "<Query>" + sQuery + "</Query>" +
                "<EngineName>" + iformObj.getCabinetName() + "</EngineName>" +
                "<SessionId>" + getSessionID(iformObj) + "</SessionId>" +
                "</APSelect_Input>";
    			
    	     	LOS_EG.mLogger.info("Inside ExecuteQuery_APSelect [InputXml]:\n "+inputXML);
    	     	

    		     return ExecuteQueryOnServer(iformObj,inputXML);
    		} 
    		catch (Exception e) 
    		{
    			LOS_EG.printException(e);
    			return "";
    		}
    	}

        public static String getSessionID(IFormReference iFormRef) {
    		return iFormRef.getObjGeneralData().getM_strDMSSessionId();
    	}
        public static String get_NGOAddDocument_Input(IFormReference ifr, String folderIndex,String docSize,String DocumentName,String strISIndex, int volumeID, String filePath)
    	{
    		
    		String docExt = "pdf";
    		
    		String xml = "<?xml version=\"1.0\"?>" + "\n" +
    		"<NGOAddDocument_Input>" + "\n" +
    		"<Option>NGOAddDocument</Option>" + "\n" +
    		"<CabinetName>" + ifr.getObjGeneralData().getM_strEngineName() + "</CabinetName>" + "\n" +
    		"<UserDBId>" + ifr.getObjGeneralData().getM_strDMSSessionId() + "</UserDBId>" + "\n" +
    		"<GroupIndex>0</GroupIndex>" + "\n" +
    		"<ParentFolderIndex>" + folderIndex + "</ParentFolderIndex>" + "\n" +
    		"<DocumentName>" + DocumentName + "</DocumentName>" + "\n" +
    		"<CreatedByAppName>"+docExt+"</CreatedByAppName>" + "\n" +
    		"<Comment></Comment>" + "\n" +
    		"<VersionComment></VersionComment>" + "\n" +
    		"<VolumeIndex>" + volumeID + "</VolumeIndex>" + "\n" +
    		"<FilePath>" + filePath + "</FilePath>" + "\n" +
    		"<DataDefinition></DataDefinition>" + "\n" +
    		"<ISIndex>" + strISIndex + "</ISIndex>" + "\n" +
    		"<NoOfPages>1</NoOfPages>" + "\n" +
    		"<DocumentType>N</DocumentType>" + "\n" +
    		"<DocumentSize>" + docSize + "</DocumentSize>" + "\n" +
    		"</NGOAddDocument_Input>";
    		   
    		return xml;   
    	}
        
        //Ajay 29Nov
        public static double getDoubleAmount(String value) 
    	{
    		try
    		{
    			return Double.parseDouble(value);
    		}
    		catch(Exception e)
    		{
    			return 0d;
    		}
    	}
    	
        //Ajay 29Nov
        public static String handleDoubleZeroInRequest(double d) 
    	{
        	String formattedData = String.format("%.02f", d).replace(".", "");			
    		return formattedData;
    	}
        
        //Ajay 06Dec
        public static String handleSevenZeroInRequest(double amount)
    	{
    		String formattedData = String.format("%.07f", amount).replace(".", "");    			
    		return formattedData;
    	}

        //Ajay 29Nov
		public static String handleDoubleZeroInResponse(String amt) 
		{
			try
			{
				Double d = Double.parseDouble(amt);
				d = d/100;
				return new BigDecimal(d.toString()).stripTrailingZeros().toPlainString();
			}
			catch(Exception e)
			{
				return "0";
			}
		}
		
		//Ajay 13Dec
		public static String getCurrentDateTimeiForm() 
		{
			Calendar cal = Calendar.getInstance();	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String strDate = sdf.format(cal.getTime());
			LOS_EG.mLogger.info("Current strDate "+strDate);
			return strDate;
		}	  
		
		//Ajay 15Dec
		public static String convertDateFormat(String strIpDate, String ipDateFormat, String opDateFormat)
		{
			String opDate="";
			try
			{
				DateFormat originalFormat = new SimpleDateFormat(ipDateFormat);
				DateFormat targetFormat = new SimpleDateFormat(opDateFormat);
				Date date = originalFormat.parse(strIpDate);
				opDate = targetFormat.format(date);
			}
			catch(Exception e)
			{
				LOS_EG.mLogger.error("Exception in convert Date Format "+e);
			}
			return opDate;
		}	
		
		//Ajay 15Dec
		public static String formatWithTwoDecimalZero(String ipAmt) 
    	{
			try
			{
				String formattedData = String.format("%.02f", getDoubleAmount(ipAmt));
				return formattedData;
			}
			catch(Exception e)	
			{
				return "";
			}
    	}	

		//Ajay 15Dec
        public static String handleThreeZeroInRequest(double amount)
    	{
    		String formattedData = String.format("%.03f", amount).replace(".", "");    			
    		return formattedData;
    	}
		
		//Ajay 22Dec
		public static void setInitiatorEmailID(IFormReference ifr)
        {
        	LOS_EG.mLogger.info("Inside Set Initiator Email ID Matrix");
			
			String sQuery="select MailId FROM PDBUSER WHERE upper(UserName) = upper('"+ifr.getUserName()+"')";
			LOS_EG.mLogger.info("Email query is: " +sQuery);
			List<List<String>>emailList=ifr.getDataFromDB(sQuery);
			LOS_EG.mLogger.info("DB Result is: " +emailList);
			
			if(emailList.size()>0)
			{
				ifr.setValue("INITIATOR_EMAILID", emailList.get(0).get(0));
			}		
        }
		
		//Ajay 13Dec
		public static String getCurrentDate(String strDateFormat) 
		{
			Calendar cal = Calendar.getInstance();	
			SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
			String strDate = sdf.format(cal.getTime());
			LOS_EG.mLogger.info("Current strDate "+strDate);
			return strDate;
		}

		public static String getAmountComma(String strIpAmt) 
		{
			 DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
		     
		     return decimalFormat.format(getDoubleAmount(strIpAmt));
		}
		
		public static void populateCollateralCheckList(IFormReference ifr, String param)
		{
			String query="SELECT type_OF_exception FROM NG_ELOS_MAST_COLLATERAL_CHECKLIST WHERE PRODUCT_TYPE='"+param.split("~")[0]+"' AND (program_Type='ALL' OR Program_type='"+param.split("~")[1]+"')";
			LOS_EG.mLogger.info("Query For CollCheckList " +query);
			List lstCheckList = ifr.getDataFromDB(query);
			ifr.clearTable("table130");
			for(int i=0;i<lstCheckList.size();i++)
			{	
				List<String> arr1=(List)lstCheckList.get(i);
				LOS_EG.mLogger.info("Query For data is " +arr1.get(0));
				JSONArray ja= new JSONArray();
				JSONObject jo= new JSONObject();
				jo.put("Collateral Checklist", arr1.get(0));
				jo.put("Program Type", param.split("~")[1]);
				ja.add(jo);
				ifr.addDataToGrid("table130", ja);
			}
		}
		//Modified by Aarish: New Policy Change
		public static void populateInterestRateAndAdminFee(IFormReference ifr, String param)
		{
			
			String segment1 = (String)ifr.getValue("Applicant_Employer_Grade");
			LOS_EG.mLogger.info("segment 1 " +segment1);
			
			String segment2 = (String)ifr.getValue("Applicant_Employment_Type");
			LOS_EG.mLogger.info("segment 2 " +segment2);
			String sQuery="SELECT INTEREST_RATE,ADMIN_FEE FROM NG_ELOS_MAST_INTEREST_RATE WHERE PROGRAM_TYPE='"+param+"' AND (SEGMENT='"+segment1+"' OR SEGMENT='"+segment2+"'    OR SEGMENT='ALL')";
			LOS_EG.mLogger.info("Query For Interest Rate " +sQuery);
			List IntrstList = ifr.getDataFromDB(sQuery);
			
			if(IntrstList.size()>0)
			{
				List<String> tempLIST = (List<String>) IntrstList.get(0);
				LOS_EG.mLogger.info("Interest Query Output: " + IntrstList);

				try {
				    // Parse string to double
					double interestRate = tempLIST.size() > 0 && tempLIST.get(0) != null && !tempLIST.get(0).trim().isEmpty()
					        ? Double.parseDouble(tempLIST.get(0))
					        : 0.0;

					double adminFee = tempLIST.size() > 1 && tempLIST.get(1) != null && !tempLIST.get(1).trim().isEmpty()
					        ? Double.parseDouble(tempLIST.get(1))
					        : 0.0;


				    // Format to 5 decimal places
				    String formattedInterestRate = String.format("%.5f", interestRate);
				    String formattedAdminFee = String.format("%.5f", adminFee);

				    // Set formatted values
				    ifr.setValue("table117_InterestRate", formattedInterestRate);
				    ifr.setValue("table117_Interest_Rate_Hidden", formattedInterestRate);
				    LOS_EG.mLogger.info("Interest Rate: " + formattedInterestRate);

				    ifr.setValue("table117_AdminFee", formattedAdminFee);
				    ifr.setValue("table117_Admin_Fee_Hidden", formattedAdminFee);
				    LOS_EG.mLogger.info("Admin Fees: " + formattedAdminFee);

				} catch (NumberFormatException e) {
				    LOS_EG.mLogger.error("Error parsing Interest/Admin Fee values: " + e.getMessage(), e);
				}

				
			
			}
		}
		
		public static void populateSellerCodeInfo(IFormReference ifr, String param)
		{
			LOS_EG.mLogger.info("inside populateSellerCodeInfo ");
			String wsName = ifr.getActivityName();
			LOS_EG.mLogger.info("wsName " +wsName);
			if(wsName.equalsIgnoreCase("Introduction"))
			{
				String sQuery="SELECT STAFF_NUMBER,EMAIL FROM NG_LOS_MAST_SELLER_DESCRIPTION WHERE UPPER(NAME) =  UPPER('"+ifr.getUserName()+"')";
				LOS_EG.mLogger.info("Query For Seller Code " +sQuery);
				List sellerList = ifr.getDataFromDB(sQuery);
				if(sellerList.size()>0)
				{
					List<String>tempLIST=(List)sellerList.get(0);
					ifr.setValue("Seller_Code", tempLIST.get(0));
					ifr.setValue("Q_LOS_BRANCH_INFO_seller_email_id", tempLIST.get(1));
				}
			}
		}
}
