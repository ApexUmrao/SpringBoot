package com.newgen.iforms.user;

import java.util.List;
import java.util.Locale;

/*------------------------------------------------------------------------------------------------------
NEWGEN SOFTWARE TECHNOLOGIES LIMITED
Author                         : 
Group                          : AUB-RLOS
Project/Product                : Application -Projects
Application                    :
Module                         : Branch_Manager,Authorizer_DSU,Legal_Dept
File Name                      : LOS_Br_Authorizer_DSU.java
Date (DD/MM/YYYY)              : June 11, 2021
Description                    : 


Dependent Jars:
	rt.jar,iforms.jar
------------------------------------------------------------------------------------------------------
CHANGE HISTORY

-------------------------------------------------------------------------------------------------------

Problem No/CR No   Change Date   Changed By    Change Description

---------------------------------------------------------------------------------------------------------
*/

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.Integration.Integration_Handler;
import com.newgen.mvcbeans.model.WorkdeskModel;

import java.io.File;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LOS_RCRH_Retail_Credit implements IFormServerEventHandler {

	LOS_LogWriter logs = new LOS_LogWriter();
	LOS_NGGlobals global = new LOS_NGGlobals();
	LOS_CommonMethods comm = new LOS_CommonMethods();
	String propFileAppSever = System.getProperty("user.dir") + File.separator + "BRMS_Properties" + File.separator
			+ "LoaneligibilityBRMS.properties";
	Properties propbasicApp = comm.loadPropertyFile(propFileAppSever);
	String ApplicationServerIp = propbasicApp.getProperty("ApplicationIp");
	String ApplicationServerPort = propbasicApp.getProperty("WrapperPort");
	String ApplicationServerCabinet = propbasicApp.getProperty("cabinetName");


	@Override
	public void beforeFormLoad(FormDef fd, IFormReference ifr) {
		LOS_EG.mLogger.info("beforeFormLoad::::: ");
		throw new UnsupportedOperationException("Not supported yet."); // To

	}

	@Override
	public String setMaskedValue(String string, String string1) {
		LOS_EG.mLogger.info("setMaskedValue::::: ");
		throw new UnsupportedOperationException("Not supported yet."); // To

	}

	@Override
	public JSONArray executeEvent(FormDef fd, IFormReference ifr, String string, String string1) {
		LOS_EG.mLogger.info("executeEvent::::: ");
		throw new UnsupportedOperationException("Not supported yet."); // To

	}

	@SuppressWarnings("unchecked")
	@Override
	public String executeServerEvent(IFormReference ifr, String controlId, String eventType, String param) {
		LOS_EG.mLogger.info("--------------Welcome to executeServerEvent :::::------------- ");

		String activityName = ifr.getActivityName();
		StringBuffer strFilePath = null;
		strFilePath = new StringBuffer(50);
		strFilePath.append(System.getProperty("user.dir"));

		LOS_EG.mLogger.info("Control Type : " + controlId + " Event Type : " + eventType + " Parameters : " + param);
		String WorkitemID = ifr.getObjGeneralData().getM_strProcessInstanceId();
		LOS_EG.mLogger.info("WorkitemID : " + WorkitemID);
		try {
			if ("call".equalsIgnoreCase(eventType)) {
				LOS_EG.mLogger.info("eventType : " + eventType);

			}
			if ("click".equalsIgnoreCase(eventType)) {
				LOS_EG.mLogger.info("eventType : " + eventType);

				// end
			}
			if ("onfocus".equalsIgnoreCase(eventType)) {
				LOS_EG.mLogger.info("eventType : " + eventType);

			}
			if ("onLoad".equals(eventType)) {
				LOS_EG.mLogger.info("eventType vipul: " + eventType);
				ifr.setStyle("button154", "disable", "true");
				ifr.setStyle("button184", "disable", "true");
				ifr.setStyle("button185", "disable", "true");	
			}
			if ("onClick".equals(eventType)) {
				LOS_EG.mLogger.info("Inside Click Control ID is : " + controlId);
				
				//Ajay 15Dec
				if(controlId.equalsIgnoreCase("CreateCollateral"))
				{
					String status="false~Error in fetching data";
					try
					{
						status=Integration_Handler.createCollateral(ifr);
					}
					catch (Exception e) 
					{
					}
					return status;
				}				
				else if(controlId.equalsIgnoreCase("UpdateCollateral"))
				{
					String status="false~Error in fetching data";
					try
					{
						status=Integration_Handler.updateCollateral(ifr);
					}
					catch (Exception e) 
					{
					}
					return status;
				}				
				//Ajay 15Dec
				else if(controlId.equalsIgnoreCase("ReservationEnquiry"))
				{
					String status="false~Error in fetching data";
					try
					{
						status=Integration_Handler.reservationEnquiry(ifr);
					}
					catch (Exception e) 
					{
					}
					return status;
				}			
				else if(controlId.equalsIgnoreCase("Loan_Calculator"))
				{
					LOS_EG.mLogger.info("Inside loan Calculator ");
					String status="false~Error in fetching data from middleware";
					try
					{
					status=Integration_Handler.calculateLoan(ifr,param);
					if (status.split("~")[0].equals("true")) {
						
					} else {
						
					}
					}
					catch (Exception e) {
						
					}
					return status;					
				}
				else if(controlId.equalsIgnoreCase("button218"))
				{
					String status="false~Error in fetching data";
					try
					{
						status=Integration_Handler.fetchMaxEligibilityFromDB(ifr);
						if(status.split("~")[0].equals("true"))
						{
							ifr.setValue("FetchMax_Eligibility", "Y");
							//ifr.setStyle("button218", "disable", "true");
						}
					}
					catch (Exception e) {
						//ifr.setStyle("button218", "disable", "false");
					}
					return status;
				}
				else if(controlId.equalsIgnoreCase("Fetch_Exception"))
				{
					String status="false~Error in fetching data";
					LOS_EG.mLogger.info(" default status is : " + status);
					String tempStatus=Integration_Handler.fetchExceptionFromDB(ifr,param);
					return tempStatus;					
				}
				else if(controlId.equalsIgnoreCase("button205"))
				{
					 //
					 String status="false~Error in fetching data from middleware";
					 try
						{
						status=Integration_Handler.fetchLoanSummary(ifr);
						if (status.split("~")[0].equals("true")) {
							ifr.setStyle("button205", "disable", "true");
							//ifr.setValue("ReserveCollateral_Status", "Y");
						} else {
							ifr.setStyle("button205", "disable", "false");
						}
						}
						catch (Exception e) {
							ifr.setStyle("button205", "disable", "false");
						}
						return status;
				}
				else if (controlId.equalsIgnoreCase("button193") || controlId.equalsIgnoreCase("button166")) 
				{
					String status="false~Error in fetching data from middleware";
					try
					{
					status=Integration_Handler.IScoreFetch(ifr,controlId);
					if (status.split("~")[0].equals("true")) {
						
						if(controlId.equalsIgnoreCase("button193")) //Ajay 14Nov
						{
							ifr.setStyle("button193", "disable", "true");
						}
						//ifr.setValue("ReserveCollateral_Status", "Y");
					} else {
						ifr.setStyle("button193", "disable", "false");
					}
					}
					catch (Exception e) {
						ifr.setStyle("button193", "disable", "false");
					}
					return status;
				}
				else if (controlId.equalsIgnoreCase("button220")) 
				{
					String status="false~Error in fetching data from middleware";
					try
					{
					status=Integration_Handler.fetchCreditScore(ifr);
					if (status.split("~")[0].equals("true")) {
						ifr.setStyle("button220", "disable", "true");
					}
					}
					catch (Exception e) {
						ifr.setStyle("button220", "disable", "false");
					}
					return status;
				}
				else if (controlId.equalsIgnoreCase("collateralChecklist"))
				{					
					LOS_CommonMethods.populateCollateralCheckList(ifr, param);
				}
				/**************National ID Check Start*******************/
				else if (controlId.equalsIgnoreCase("button181")) {
					String status="false~Error in fetching data from middleware";
					try
					{
					status=Integration_Handler.nIDCheck(ifr);
					if (status.split("~")[0].equals("true")) {
						ifr.setStyle("button181", "disable", "true");
						//ifr.setValue("ReserveCollateral_Status", "Y");
					} else {
						ifr.setStyle("button181", "disable", "false");
					}
					}
					catch (Exception e) {
						ifr.setStyle("button181", "disable", "false");
					}
					return status;
				}
				/**************National ID Check END*******************/
				
				
			}
			if ("subformDoneClick".equals(eventType)) {
				LOS_EG.mLogger.info("eventType : " + eventType);

			}

			if ("insertToDB".equals(eventType)) {
				/*
				 * if ("createHistory".equals(controlId)) {
				 * LOS_EG.mLogger.info("controlId : " + controlId); String query
				 * = LOS_CommonMethods.getDataInDecisionHistory(ifr, param);
				 * return query; }
				 */
			}

			if ("onDone".equals(eventType)) {

				if ("Historyset".equals(controlId)) {
					LOS_EG.mLogger.info("[Historyset] : " + param);
					// LOS_CommonMethods.addToDecisionHistoryGrid(ifr, param);
					LOS_CommonMethods.getDataInDecisionHistory(ifr, param);
					LOS_CommonMethods.setApprovalMatrix(ifr);
					String decision=(String) ifr.getValue("Decision");
					if(decision.equalsIgnoreCase("Approve"))
					{
						String status=Integration_Handler.Email(ifr);
						LOS_EG.mLogger.info("EMAIL status: " + status);
						
				      
					
					}

				}

				
			}
			// end
			if ("onTabLoad".equals(eventType)) 
			{
				LOS_EG.mLogger.info("eventType : " + eventType);
				if("INTEREST".equalsIgnoreCase(controlId))
				{
					LOS_CommonMethods.populateInterestRateAndAdminFee(ifr, param);
				}
			}
			if ("ongridsave".equals(eventType)) {
				LOS_EG.mLogger.info("eventType : " + eventType);
			}

			if ("onChange".equals(eventType)) {
				LOS_EG.mLogger.info("eventType : " + eventType);
				

					LOS_EG.mLogger.info("inside eventType : " + eventType);
					if("employerName".equalsIgnoreCase(controlId))
					{
						String sQuery="SELECT EMPLOYER_TIER FROM NG_ELOS_MAST_EMPLOYER WHERE Employer_Name='"+param+"'";
						List < List < String >> employerTier = ifr.getDataFromDB(sQuery);
						ifr.setValue("Applicant_Employer_Grade",employerTier.get(0).get(0));
								
					}

				

			}

			if ("tabclick".equals(eventType)) {
				if ("tab1".equalsIgnoreCase(controlId)) {
					LOS_CommonMethods.loadDecisionCombo(ifr, param);
					LOS_CommonMethods.DocumentList(ifr);
					LOS_EG.mLogger.info("After Setting Doc List  ");
				}
			}
			if ("OnChangeBusiness".equals(eventType)) {
				LOS_EG.mLogger.info("eventType : " + eventType);

			}

			if ("onChange".equals(eventType)) {

			}
			if ("addItemsToComboFromMaster".equals(eventType)) {
				LOS_EG.mLogger.info("[addItemsToComboFromMaster] : " + param);
				LOS_CommonMethods.populateDataInDropdownFromMaster(ifr, param);
				return "ok";
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			return "catch error";
		} finally {
			LOS_EG.mLogger.info("Exit ExecuteServerEvent");
		}

		return "";
	}

	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1, String arg2, String arg3, String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public String generateHTML(EControl arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateDataInWidget(IFormReference arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	public String validateDocumentConfiguration(String arg0, String arg1, File arg2, Locale arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0, javax.servlet.http.HttpServletRequest arg1,
			javax.servlet.http.HttpServletResponse arg2) {
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			WorkdeskModel arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean introduceWorkItemInSpecificProcess(IFormReference arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String onChangeEventServerSide(IFormReference arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void postHookOnDocumentUpload(IFormReference arg0, String arg1, String arg2, File arg3, int arg4) {
		// TODO Auto-generated method stub
		
	}

}