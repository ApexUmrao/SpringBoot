package com.newgen.iforms.user;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.Integration.Integration_Handler;
import com.newgen.mvcbeans.model.WorkdeskModel;

//Added by Shivanshu
public class LOS_Fulfillment_Docs implements IFormServerEventHandler {
	
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
				LOS_EG.mLogger.info("eventType : " + eventType);
			}
			
			if ("onClick".equals(eventType)) {
				LOS_EG.mLogger.info("Inside Click Control ID is : " + controlId);
				
				
				//Added by Shivanshu
				String nationalID=(String) ifr.getValue("Applicant_National_ID");
				LOS_EG.mLogger.info("national id is : " + nationalID);
				
				if(controlId.equalsIgnoreCase("AccountCreationBTN") )
				{
					String status="false~Error in Creating Account";
					try{
						status=Integration_Handler.createCustomerAccount(nationalID, "Applicant",ifr);
						 LOS_EG.mLogger.info("success status is : " + status);
							if (status.split("~")[0].equals("true")) {
								ifr.setStyle("AccountCreationBTN", "disable", "true");
								ifr.setValue("Account_Creation_Status", "Y");
								return status;								
							} else {
								ifr.setStyle("AccountCreationBTN", "disable", "false");
								return status;
							}
					}catch (Exception e) {
						LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
					}
					return status;
				}
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
	public String generateHTML(EControl arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean introduceWorkItemInSpecificProcess(IFormReference arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			WorkdeskModel arg3) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public void updateDataInWidget(IFormReference arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1, File arg2, Locale arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1, String arg2, String arg3, String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

}
