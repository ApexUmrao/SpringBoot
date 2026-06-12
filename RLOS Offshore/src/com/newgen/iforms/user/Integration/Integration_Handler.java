package com.newgen.iforms.user.Integration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.LOS_CommonMethods;
import com.newgen.iforms.user.LOS_EG;
import com.newgen.iforms.user.XMLParser;
import com.newgen.iforms.user.cache.RLOSMappingCache;
import com.newgen.webserviceclient.NGWebServiceClient;
@SuppressWarnings("rawtypes")
public class Integration_Handler {

	static HashMap<String,String> APIRequestURLMasterMap ;

	public static String fetchCustomerDetails(String CustID, String CustomerType, IFormReference ifr) {
		LOS_EG.mLogger.info("inside fetchCustomerDetails");
		try {
				String inputDetails = getInputXML("GET_CUST_DETAILS");
				LOS_EG.mLogger.info("Default input XML for CUST_DETAIL is" + inputDetails.split("~")[0]);
				LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
				LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
				String inputXML = inputDetails.split("~")[0];
				inputXML = inputXML.substring(0, inputXML.indexOf("<CustID>")) + "<CustID>" + CustID
						+ inputXML.substring(inputXML.indexOf("</CustID>"), inputXML.length());
				inputXML = inputXML.substring(0, inputXML.indexOf("<CustNIN>")) + "<CustNIN>" + CustID
						+ inputXML.substring(inputXML.indexOf("</CustNIN>"), inputXML.length());
//				String responseXML = SocketConnector.getSocketResponse(inputXML, inputDetails.split("~")[1],
//						inputDetails.split("~")[2]);
				String responseXML=getRequestURL(inputXML,ifr);
				return IntegrationParser.parseCustomerDetails(responseXML, ifr, CustomerType);
			
			
		} catch (IOException e) {
			LOS_EG.mLogger.info(
					"#####Error in getting input XML for CUST_DETAIL_LOAN_RQ Please check getInputXML Method#####");
			e.printStackTrace();
			return "false~Error in fetching data from middleware";
		}

	}
	
	public static String fetchCIFDetils(String nationalID, String CustomerType, IFormReference ifr) throws IOException
	{
		LOS_EG.mLogger.info("inside fetchCIFDetils");
		String inputDetails = getInputXML("GET_CIF_INFO");
		LOS_EG.mLogger.info("Default input XML for CUST_DETAIL_LOAN is" + inputDetails.split("~")[0]);
		LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
		LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
		String inputXML = inputDetails.split("~")[0];
		inputXML = inputXML.substring(0, inputXML.indexOf("<CustNIN>")) + "<CustNIN>" + nationalID
				+ inputXML.substring(inputXML.indexOf("</CustNIN>"), inputXML.length());
//		String responseXML = SocketConnector.getSocketResponse(inputXML, inputDetails.split("~")[1],
//				inputDetails.split("~")[2]);
		
		String responseXML=getRequestURL(inputXML,ifr);

		
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(responseXML);
		if ("00000".equalsIgnoreCase(xmlParser.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) {
			ifr.setValue("CIF_ID", xmlParser.getValueOf("CIFNumber"));
			LOS_EG.mLogger.info("CIF Number received"+xmlParser.getValueOf("CIFNumber"));
			return xmlParser.getValueOf("CIFNumber");
		}
		else
		{
			return "false~Error in fetching data from middleware -"+xmlParser.getValueOf("Desc"); //Ajay 14Nov
		}
		
	}
	

	//Ajay 15Dec
	public static String collateralEnq(IFormReference ifr) throws IOException 
	{
		try 
		{
			LOS_EG.mLogger.info("inside collateralEnq");
			
			
			String CustID = (String)ifr.getValue("Collateral_CIF");			
			String inputDetailsCustInfo = getInputXML("GET_CUST_DETAILS");
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetailsCustInfo.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetailsCustInfo.split("~")[2]);
			
			String inputXMLCustInfo = inputDetailsCustInfo.split("~")[0];
			inputXMLCustInfo = inputXMLCustInfo.substring(0, inputXMLCustInfo.indexOf("<CustID>")) + "<CustID>" + CustID
						+ inputXMLCustInfo.substring(inputXMLCustInfo.indexOf("</CustID>"), inputXMLCustInfo.length());
			
			inputXMLCustInfo = inputXMLCustInfo.substring(0, inputXMLCustInfo.indexOf("<CustNIN>")) + "<CustNIN>" + CustID
						+ inputXMLCustInfo.substring(inputXMLCustInfo.indexOf("</CustNIN>"), inputXMLCustInfo.length());
				
			LOS_EG.mLogger.info("Input XML for Fetch Customer " + inputXMLCustInfo);
			
//			String responseXMLCustInfo = SocketConnector.getSocketResponse(inputXMLCustInfo, inputDetailsCustInfo.split("~")[1],
//						inputDetailsCustInfo.split("~")[2]);
			
			String responseXMLCustInfo=getRequestURL(inputXMLCustInfo,ifr);

			
			// String responseXMLCustInfo = "<?xml version='1.0' encoding='UTF-8'?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><getResponseResponse xmlns=\"http://middleware.webservice.aub.com\"><getResponseReturn><AUB_MESSAGE><RESPONSE_MESSAGE><ResponseHeader><MsgType>Response</MsgType><ReqID>RLOS</ReqID><ChSysID>RLOS</ChSysID><FuncID>GET_CUST_DETAILS</FuncID><TxnRef>RLOS4999062772852</TxnRef><TxnDate>202112221247414</TxnDate><TxnStatus>Y</TxnStatus><SessLang>E</SessLang><VerNo>0001</VerNo><SessToken>UkxPU0AhMTI=</SessToken><Return><Code>00000</Code><Desc>SUCCESS</Desc></Return></ResponseHeader><ResponseBody><GET_CUST_DETAILS_RS><Name>NA</Name><Type>EC</Type><AnalysisCode>G4</AnalysisCode><ParentCountry>EG</ParentCountry><ResCountry>EG</ResCountry><Branch>0018</Branch><ArabicName>NA</ArabicName><CPR>28407070103174</CPR><Gender>1</Gender><Address1>NA</Address1><Address2>NA</Address2><Address3>NA</Address3><DateofBirth>19840707</DateofBirth><CustomerType>EC</CustomerType><CustomerTypeDecsription>Staff</CustomerTypeDecsription><IsClosed>A</IsClosed><Email>ahmedseaf84@hotmail.com</Email><Tel2>0222582481</Tel2><SmsAlert>01050562318</SmsAlert><Fax>0</Fax><Nationality>EG</Nationality><IncomeFrom>00000000000000000000</IncomeFrom><IncomeTo>00000000000000000000</IncomeTo><Language>AR</Language><IsFatca>N</IsFatca><IsJoint>N</IsJoint><AccountOfficer>HR</AccountOfficer><AccountOfficerName>Human Resources</AccountOfficerName><RegisteredForIVR>N</RegisteredForIVR><CPRExpireDate>0</CPRExpireDate><MaritalStatus>2</MaritalStatus><NoOfDependents>3</NoOfDependents><DateOfIssue>0</DateOfIssue><BasicSalary>000000000000000</BasicSalary><KYCLastUpdatedDate>1201202</KYCLastUpdatedDate><NoOfCreditCards>0</NoOfCreditCards><BirthPlace>Egypt</BirthPlace><Occupation>005</Occupation><RiskCountry>EG</RiskCountry></GET_CUST_DETAILS_RS></ResponseBody></RESPONSE_MESSAGE></AUB_MESSAGE></getResponseReturn></getResponseResponse></soapenv:Body></soapenv:Envelope>"; //Dummy Response
			
			LOS_EG.mLogger.info("Output XML for Fetch Customer " + responseXMLCustInfo);
			
			
			XMLParser xmlParserCustInfo = new XMLParser();
			xmlParserCustInfo.setInputXML(responseXMLCustInfo);
			String name = "", nationality="";
			if ("00000".equalsIgnoreCase(xmlParserCustInfo.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParserCustInfo.getValueOf("Desc"))) 
			{
				name = xmlParserCustInfo.getValueOf("Name");
				nationality = xmlParserCustInfo.getValueOf("Nationality");
			}
			else
			{
				return "false~Error in fetching data from middleware for GET_CUST_DETAILS - "+xmlParserCustInfo.getValueOf("Desc");
			}
			
			
			
			String inputDetails = getInputXML("COLLAT_ENQ_ACC");
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
			String inputXML = inputDetails.split("~")[0];
			inputXML = inputXML.substring(0, inputXML.indexOf("<CustNIN>")) + "<CustNIN>" + ifr.getValue("Collateral_CIF")
					+ inputXML.substring(inputXML.indexOf("</CustNIN>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<CustomerMnemonic>")) + "<CustomerMnemonic>"
					+ ifr.getValue("Collateral_CIF")
					+ inputXML.substring(inputXML.indexOf("</CustomerMnemonic>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<CustomerLocation>")) + "<CustomerLocation>"
					+ inputXML.substring(inputXML.indexOf("</CustomerLocation>"), inputXML.length());
			LOS_EG.mLogger.info("Final input XML for  Collateral Enq " + inputXML);
			
//			String responseXML = SocketConnector.getSocketResponse(inputXML,
//					inputDetails.split("~")[1], inputDetails.split("~")[2]);
			
			String responseXML=getRequestURL(inputXML,ifr);

			
			// String responseXML = "<AUB_MESSAGE><RESPONSE_MESSAGE><ResponseHeader><MsgType>Response</MsgType><ReqID>IB</ReqID><FuncID>COLLAT_ENQ_ACC</FuncID><TxnRef>1638005382017</TxnRef><TxnDate>20211127121500</TxnDate><TxnStatus>Y</TxnStatus><SessLang>E</SessLang><VerNo>0010</VerNo><SessToken>123456789</SessToken><Return><Code>00000</Code><Desc>SUCCESS</Desc></Return></ResponseHeader><ResponseBody><COLLAT_ENQ_ACC_RS><Account><CollateralType>DPS</CollateralType><CollateralTypeDescription>Deposits</CollateralTypeDescription><CollateralDealType>G3M</CollateralDealType><CollateralDealReference>3014000001152</CollateralDealReference><CollateralDealBranch>0001</CollateralDealBranch><TotalAssigned>000000000000000</TotalAssigned><BankValuation>000000002500000</BankValuation><AvailableCollateralValue>000000002500000</AvailableCollateralValue><CollateralHeader>DPS0001G3M3014000001152</CollateralHeader></Account><Account><CollateralType>DPS</CollateralType><CollateralTypeDescription>Deposits</CollateralTypeDescription><CollateralDealType>G3M</CollateralDealType><CollateralDealReference>301400120120F</CollateralDealReference><CollateralDealBranch>0001</CollateralDealBranch><TotalAssigned>000000000000000</TotalAssigned><BankValuation>000000174325000</BankValuation><AvailableCollateralValue>000000174325000</AvailableCollateralValue><CollateralHeader>DPS0001G3M301400120120F</CollateralHeader></Account></COLLAT_ENQ_ACC_RS></ResponseBody></RESPONSE_MESSAGE></AUB_MESSAGE>"; //Dummy Response
			return IntegrationParser.parseCollateralAccEnq(responseXML, ifr,(String)ifr.getValue("Collateral_CIF"),name,nationality);
		} 
		catch (Exception e) 
		{
			LOS_EG.mLogger.info("collateralEnq error is" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
	}

	public static String collateralRes(IFormReference ifr,String CollateralHeader) throws IOException {

		LOS_EG.mLogger.info("inside collateralRes 2");
		String inputDetails = getInputXML("COLLAT_RESERV");
		LOS_EG.mLogger.info("Default input XML for COLLAT_RESERV is" + inputDetails.split("~")[0]);
		LOS_EG.mLogger.info("Socket Server IP is" + inputDetails.split("~")[1]);
		LOS_EG.mLogger.info("Socket Server Port is" + inputDetails.split("~")[2]);
		LOS_EG.mLogger.info("CIF ID is"+  ifr.getValue("CIF_ID"));
		String pType = (String)ifr.getValue("Product_Type");
		LOS_EG.mLogger.info("ProductType : "+pType);
		String loanType = "";
		if(pType.equalsIgnoreCase("PL") || pType.equalsIgnoreCase("CC")) 
		{
			loanType = "IML";
		}
		else if(pType.equalsIgnoreCase("AL"))
		{
			loanType="VIL";
		}
		else if(pType.equalsIgnoreCase("MR"))
		{
			loanType="MUR";
		}
		else if(pType.equalsIgnoreCase("CL"))
		{
			loanType="MCL";
		}
		String inputXML = inputDetails.split("~")[0];
		inputXML = inputXML.substring(0, inputXML.indexOf("<CustNIN>")) + "<CustNIN>" +  ifr.getValue("CIF_ID")
		+ inputXML.substring(inputXML.indexOf("</CustNIN>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<CustomerMnemonic>")) + "<CustomerMnemonic>"
				+ ifr.getValue("CIF_ID")
				+ inputXML.substring(inputXML.indexOf("</CustomerMnemonic>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<CustomerLocation>")) + "<CustomerLocation>"
				+ inputXML.substring(inputXML.indexOf("</CustomerLocation>"), inputXML.length());
		//
		inputXML = inputXML.substring(0, inputXML.indexOf("<DealBranch>")) + "<DealBranch>"
				// + ifr.getValue("Branch_Code")
				+ ifr.getValue("CUSTOMER_BRANC_CODE")
				+ inputXML.substring(inputXML.indexOf("</DealBranch>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<DealType>")) + "<DealType>" +loanType
				+ inputXML.substring(inputXML.indexOf("</DealType>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<Currency>")) + "<Currency>EGP"
				+ inputXML.substring(inputXML.indexOf("</Currency>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<LoanAmount>")) + "<LoanAmount>"
				+ LOS_CommonMethods.handleDoubleZeroInRequest(LOS_CommonMethods.getDoubleAmount((String)ifr.getValue("Req_Loan_Amt"))) //Ajay 29Nov
				+ inputXML.substring(inputXML.indexOf("</LoanAmount>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<CollateralHeader>")) + "<CollateralHeader>"
				+ CollateralHeader
				+ inputXML.substring(inputXML.indexOf("</CollateralHeader>"), inputXML.length());

//		String responseXML = SocketConnector.getSocketResponse(inputXML, inputDetails.split("~")[1],
//				inputDetails.split("~")[2]);
		
		String responseXML=getRequestURL(inputXML,ifr);


		// String responseXML = "<AUB_MESSAGE><RESPONSE_MESSAGE><ResponseHeader><MsgType>Response</MsgType><ReqID>IB</ReqID><FuncID>COLLAT_RESERV</FuncID><TxnRef>1638005594267</TxnRef><TxnDate>20211127122150</TxnDate><TxnStatus>Y</TxnStatus><SessLang>E</SessLang><VerNo>0010</VerNo><SessToken>123456789</SessToken><Return><Code>00000</Code><Desc>SUCCESS</Desc></Return></ResponseHeader><ResponseBody><RESERVATION_OF_COLLATERAL_RS><ReservationReference>RSL30140021100010001</ReservationReference></RESERVATION_OF_COLLATERAL_RS></ResponseBody></RESPONSE_MESSAGE></AUB_MESSAGE>"; //Dummy Response
		
		LOS_EG.mLogger.info("responseXML of collat Reservation is "+responseXML);
		
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(responseXML);
		if ("00000".equalsIgnoreCase(xmlParser.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) 
		{
			if("F".equalsIgnoreCase(xmlParser.getValueOf("Status")))
					{
						return "false~Collateral Reservation Not Successful Status is F";
					}
			else
			{
				ifr.setValue("CollResNo",xmlParser.getValueOf("ReservationReference"));
				LOS_EG.mLogger.info("collResNO is "+xmlParser.getValueOf("ReservationReference")+" "+ifr.getValue("CollResNo"));
				return "true~Collateral Reservation Successful";
			}
			
		} else {
			LOS_EG.mLogger.info("Collat Reservation op is not 0000");
			return "false~Error in fetching data from middleware";
		}
	}

	public static String fetchMaxEligibility(IFormReference ifr, String programType) throws IOException {
		try {
			String inputDetails = getInputXML("Call_Max_Eligibility");
			LOS_EG.mLogger.info("Default input XML for Call_Max_Eligibility is" + inputDetails.split("~")[0]);
			String inputXML = inputDetails.split("~")[0];
			String endPoint = inputDetails.split("~")[1];
			String wsdl = inputDetails.split("~")[2];
			String segment = (String) ifr.getValue("Applicant_Employer_Grade");
			if(((String)ifr.getValue("Product_Type")).equalsIgnoreCase("AL"))
			{
				segment=(String)ifr.getValue("Applicant_Employment_Type");
			}
			else if(programType.equalsIgnoreCase("CLUB MEMBERSHIP") || programType.equalsIgnoreCase("INCOME SURROGATE CLUB MEMBERSHIP") ||
					programType.equalsIgnoreCase("INCOME SURROGATE PROPERTY OWNERSHIP") ||programType.equalsIgnoreCase("ULTIMATE") ||
					programType.equalsIgnoreCase("COMPLEMENTRY CARDS") ||programType.equalsIgnoreCase("INCOME SURROGATE CLUB MEMBERSHIP PROGRAM")|| programType.equalsIgnoreCase("INCOME SURROGATE  COVERED CARD AGAINST DEPOSITS"))
				{
				segment=(String)ifr.getValue("Applicant_Employment_Type");
				}
				
					
			  inputXML = inputXML.substring(0, inputXML.indexOf("<egypt_produc_typet>")) +
			  "<egypt_produc_typet>" + ifr.getValue("Product_Type") +
			  inputXML.substring(inputXML.indexOf("</egypt_produc_typet>"),
			  inputXML.length()); inputXML = inputXML.substring(0,
			  inputXML.indexOf("<program_type>")) + "<program_type>" + programType +
			  inputXML.substring(inputXML.indexOf("</program_type>"), inputXML.length());
			  inputXML = inputXML.substring(0, inputXML.indexOf("<segment>")) + "<segment>"
			  + segment + inputXML.substring(inputXML.indexOf("</segment>"),
			  inputXML.length()); LOS_EG.mLogger.info("Final BRM Details Input XML " +
			  inputXML + " END POINT " + endPoint + " WSDL " + wsdl); NGWebServiceClient
			  ngwsclnt = new NGWebServiceClient(false);
			  
			  String responseXML = ngwsclnt.ExecuteWs(1, inputXML, endPoint,
			  "executeRuleset", wsdl); LOS_EG.mLogger.info("BRMS Response XML is" +
			  responseXML);
			  
			  return IntegrationParser.parseMaxEligibility(responseXML, ifr, segment,
			  programType);
			 
		} catch (Exception e) {
			LOS_EG.mLogger.info("BRMS exception is" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			 return "false~Error in fetching data from middleware";
		}
	}
	@SuppressWarnings("unchecked")
	public static String fetchExceptionFromDB(IFormReference ifr, String data) throws IOException 
	{
		LOS_EG.mLogger.info("Inside fetch Exception From DB");
		String prodType = (String)ifr.getValue("Product_Type");
		LOS_EG.mLogger.info("prodType " +prodType);
		
		if(prodType.equalsIgnoreCase(""))
		{
			return "false~Product Type is Blank";
		}
		String programType ="";
		
		try
		{			
			programType = (String)ifr.getValue("PROGRAM_TYPE");
			LOS_EG.mLogger.info("programType 0 " +programType);
		}
		catch(Exception e)
		{
			
		}
		
		if(programType.equalsIgnoreCase(""))
		{
			return "false~Program Type is Blank";
		}
		
		String segment1 = (String)ifr.getValue("Applicant_Employer_Grade");
		String segment2 = (String)ifr.getValue("Applicant_Employment_Type");
		LOS_EG.mLogger.info("segment 2 " +segment2);
		
		String columnName = "PRODUCT,PROGRAM,SEGMENT,MIN_AGE_SAL,MAX_AGE_SAL,MAX_AGE_SELF,MAX_AGE_PROFES,MIN_INCOME_SAL,MIN_INCOME_SELF,MIN_INCOME_PROF,MIN_LOS_SAL,MIN_LOS_SELF,MIN_LOS_PROF,MIN_TERM_OF_LOAN,MAX_TERM_OF_LOAN,MIN_LOAN_AMOUNT,MAX_LOAN_AMOUNT,MAX_LOAN_AMT_ISCOREHIT,MAX_DBR_PERC_SAL,MAX_DBR_PERC_SELF,MAX_DBR_PERC_PROF,NEGATIVE_PROFESSION,NEGATIVE_AREA,BLUE_COLLARS,EXTERNAL_VERIFICATION,MIN_PAID_IN_CAPITAL,TML,DOWN_PAYMENT_PERCENTAGE,MIN_AGE_SELF,MIN_AGE_PROF";
		String colArr[] = columnName.split(",");
		String query="SELECT "+columnName+" FROM NG_ELOS_MAST_MAX_ELIGIBILITY "
				+ "WHERE PRODUCT = '"+prodType+"' AND PROGRAM = '"+programType+"' AND (SEGMENT = '"+segment1+"' OR SEGMENT = '"+segment2+"' OR SEGMENT = 'NA') ORDER BY PRIORITY";
		String wiName = ifr.getObjGeneralData().getM_strProcessInstanceId();
		String queryForProfitAdminString = "SELECT AdminFees, InterestRate, Admin_Fee_Hidden, Interest_Rate_Hidden, TypeOfProduct, Program_Type FROM NG_ELOS_GR_ProductDetails WHERE p_wi_name = '" + wiName + "'";
		LOS_EG.mLogger.info(queryForProfitAdminString);
		List profAdminResult = ifr.getDataFromDB(queryForProfitAdminString);
		LOS_EG.mLogger.info(profAdminResult);
		int recordCount = 0;
		ifr.clearTable("AutoDev");
		//new policy Aarish
		for(int i=0;i<profAdminResult.size();i++) {
			List<String> arr = (List)profAdminResult.get(i);
			float currAdminFee = Float.parseFloat(arr.get(0));
			LOS_EG.mLogger.info(currAdminFee);
			float currProfRate = Float.parseFloat(arr.get(1));
			float adminFee     = Float.parseFloat(arr.get(2));
			float profRate     = Float.parseFloat(arr.get(3));
			String productType = arr.get(4);

			productType = productType.contains("PL") ? "Personal Finance" :
			              productType.contains("AL") ? "Auto Finance" :
			              productType.contains("CC") ? "Covered Card" :
			              productType; 
			String currProgramType = arr.get(5); 
			if(currAdminFee != adminFee) {
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", "Admin Fee Exception in Product Type " + productType + " (" + currProgramType + ")"  );
				exceptionJsonObj.put("Applicant Type","Applicant");
				LOS_EG.mLogger.info(exceptionJsonArr);
				exceptionJsonArr.add(exceptionJsonObj);
				LOS_EG.mLogger.info("Admin Exception Arr: " + exceptionJsonArr);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				recordCount++;
			}
			if(currProfRate != profRate) {
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", "Profit Rate Exception in Product Type " + productType + " (" + currProgramType +")");
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				LOS_EG.mLogger.info("Profit Rate Exception Arr: " + exceptionJsonArr);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				recordCount++;
			}
		}
		LOS_EG.mLogger.info("Query For Exception " +query);
		
		List lstCheckList = ifr.getDataFromDB(query);
		
		LOS_EG.mLogger.info("Query Output " +lstCheckList);
		
		
		
		String iScore_Rank = (String) ifr.getValue("I_Score_Rating");
		LOS_EG.mLogger.info("iScore Rank: " + iScore_Rank);

		int score = 0;
		try {
		    score = Integer.parseInt(iScore_Rank.trim());
		} catch (Exception e) {
		    // default remains 0 if parsing fails
			LOS_EG.mLogger.info("error in parsing iscore rank" + e);
		}

		if (score < 575) {
		    // your logic
			JSONArray exceptionJsonArr = new JSONArray();
			JSONObject exceptionJsonObj = new JSONObject();
			exceptionJsonObj.put("Type of Exception", "Minimum IScore Rank accepted is 575");
			exceptionJsonObj.put("Applicant Type","Applicant");
			exceptionJsonArr.add(exceptionJsonObj);
			ifr.addDataToGrid("AutoDev", exceptionJsonArr);
			recordCount++;
		}

		
		if(lstCheckList.size()>0)
		{	
			List<String> arr1=(List)lstCheckList.get(0);
			
			Map<String,String> colValMap = new HashMap<>();
			int i=0;
			for(String colName : colArr)
			{
				colValMap.put(colName, arr1.get(i));
				i++;
			}
			
			LOS_EG.mLogger.info("colValMap " +colValMap);
			
			
			
			String ageForm = (String)ifr.getValue("Applicant_Age");		
			LOS_EG.mLogger.info("ageForm " +ageForm);
			
			String empTypeForm = (String)ifr.getValue("Applicant_Employment_Type");		
			LOS_EG.mLogger.info("empTypeForm " +empTypeForm);
			
			String incomeForm = (String)ifr.getValue("NetSalary");	
			LOS_EG.mLogger.info("incomeForm " +incomeForm);
			
			String losForm = (String)ifr.getValue("Applicant_Length_of_Service");	
			LOS_EG.mLogger.info("losForm " +losForm);
			
			String loanAmtForm = (String)ifr.getValue("Req_Loan_Amt");	
			LOS_EG.mLogger.info("loanAmtForm " +loanAmtForm);
			
			
			String loanAmtForm1 = (String)ifr.getValue("Credit_Approved_Amount");	
			LOS_EG.mLogger.info("loanAmtForm " +loanAmtForm1);
			if(ifr.getActivityName().equalsIgnoreCase("RCR_CreditAnalyst")||ifr.getActivityName().equalsIgnoreCase("RCR_PreScanner"))
			{
				String approvedLoanAmtForm = (String)ifr.getValue("Req_Loan_Amt");
				if(!(approvedLoanAmtForm==null || approvedLoanAmtForm.equalsIgnoreCase("")))
				{
					loanAmtForm = approvedLoanAmtForm;
				}
			}
			LOS_EG.mLogger.info("loanAmtForm 2 " +loanAmtForm);
			
			String dbrForm = ((String)ifr.getValue("DBR_POLICY")).replace("%", "").replace(" ", "");	
			LOS_EG.mLogger.info("dbrForm " +dbrForm);
			
			String areaForm = getColumnValueOfGrid(ifr.getDataFromGrid("table108").toString(), "Area Type",0 );	
			LOS_EG.mLogger.info("areaForm " +areaForm);
			
			String professionForm = getColumnValueOfGrid(ifr.getDataFromGrid("table137").toString(), "Profession",0 );	
			LOS_EG.mLogger.info("professionForm " +professionForm);
			
			String blueCollarForm = getColumnValueOfGrid(ifr.getDataFromGrid("table137").toString(), "Blue Collars",0 );	
			LOS_EG.mLogger.info("blueCollarForm " +blueCollarForm);
			
			String capitalForm = getColumnValueOfGrid(ifr.getDataFromGrid("table137").toString(), "Capital",0 );
			LOS_EG.mLogger.info("capitalForm " +capitalForm);
			
			String policyMinAge = colValMap.get("MIN_AGE_SELF");
			String policyMaxAge = colValMap.get("MAX_AGE_SELF");
			String policyMinIncome = colValMap.get("MIN_INCOME_SELF");
			String policyMinLos = colValMap.get("MIN_LOS_SELF");
			String policyMinLoanAmt = colValMap.get("MIN_LOAN_AMOUNT");
			String policyMaxLoanAmt = colValMap.get("MAX_LOAN_AMOUNT");
			String policyDBR = colValMap.get("MAX_DBR_PERC_SELF");
			String policyArea = colValMap.get("NEGATIVE_AREA");
			String policyProfession = colValMap.get("NEGATIVE_PROFESSION");
			String policyBlueCollar = colValMap.get("BLUE_COLLARS");
			String policyMinCapital = colValMap.get("MIN_PAID_IN_CAPITAL");
			
			
			if(empTypeForm.equalsIgnoreCase("Salaried"))
			{
				policyMinAge = colValMap.get("MIN_AGE_SAL");
				policyMaxAge = colValMap.get("MAX_AGE_SAL");
				policyMinIncome = colValMap.get("MIN_INCOME_SAL");
				policyMinLos = colValMap.get("MIN_LOS_SAL");
				policyDBR = colValMap.get("MAX_DBR_PERC_SAL");
			}
			else if(empTypeForm.equalsIgnoreCase("Professionals"))
			{
				policyMinAge = colValMap.get("MIN_AGE_PROF");
				policyMaxAge = colValMap.get("MAX_AGE_PROFES");
				policyMinIncome = colValMap.get("MIN_INCOME_PROF");
				policyMinLos = colValMap.get("MIN_LOS_PROF");
				policyDBR = colValMap.get("MAX_DBR_PERC_PROF");
			}
			else if(empTypeForm.equalsIgnoreCase("Self- Employed") || empTypeForm.equalsIgnoreCase(""))
			{
				policyMinAge = colValMap.get("MIN_AGE_SELF");
				policyMaxAge = colValMap.get("MAX_AGE_SELF");
				policyMinIncome = colValMap.get("MIN_INCOME_SELF");
				policyMinLos = colValMap.get("MIN_LOS_SELF");
				policyDBR = colValMap.get("MAX_DBR_PERC_SELF");
			}
			
			if(policyMinAge=="NA" || policyMinAge=="")
			{
				policyMinAge="-1";
			}
			if(policyMaxAge=="NA" || policyMaxAge=="")
			{
				policyMinAge="9999";
			}
			if(policyMinIncome=="NA" || policyMinIncome=="")
			{
				policyMinIncome="-1";
			}
			if(policyMinLos=="NA" || policyMinLos=="")
			{
				policyMinLos="-1";
			}			
			if(policyMinLoanAmt=="NA" || policyMinLoanAmt=="")
			{
				policyMinLoanAmt="-1";
			}
			if(policyMaxLoanAmt=="NA" || policyMaxLoanAmt=="")
			{
				policyMaxLoanAmt="9999999999";
			}
			if(policyDBR=="NA" || policyDBR=="")
			{
				policyDBR="-1";
			}
			if(policyMinCapital=="NA" || policyMinCapital=="")
			{
				policyMinCapital="-1";
			}
			
			if(isGreater(policyMinAge,ageForm))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", "Minimum Age Exception");
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				recordCount++;
			}
			else if(isSmaller(policyMaxAge,ageForm))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", "Maximum Age Exception");
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				recordCount++;
			}
			
			if(isGreater(policyMinIncome,incomeForm))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", "Minimum Income Exception");
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				recordCount++;
			}
			
			if(isGreater(policyMinLos,losForm))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", "Minimum LOS Exception");
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				recordCount++;
			}
			
			if(isGreater(policyMinLoanAmt,loanAmtForm))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", "Minimum Finance Amount Exception");
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				recordCount++;
			}
			else if(isSmaller(policyMaxLoanAmt,loanAmtForm1))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", "Maximum Finance Amount Exception");
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				recordCount++;
			}
			
			if(isSmaller(policyDBR,dbrForm))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", "DBR Exception");
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				recordCount++;
			}
			
			if(isGreater(policyMinCapital,capitalForm))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", "Minimum Capital Exception");
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				recordCount++;
			}
			
			
			//String tenorForm = (String)ifr.getValue("Loan_Tenor");
			String tenorForm =(String) ifr.getValue("table117_LoanTenor1");
			LOS_EG.mLogger.info("tenorForm " +tenorForm);
			
			String policyMinTenure = colValMap.get("MIN_TERM_OF_LOAN");
			String policyMaxTenure = colValMap.get("MAX_TERM_OF_LOAN");
			
			if(policyMinTenure=="NA")
			{
				policyMinTenure="-1";
			}
			if(policyMaxTenure=="NA")
			{
				policyMaxTenure="9999";
			}
			
			if(isGreater(policyMinTenure,tenorForm))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", "Minimum Tenor Exception");
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				recordCount++;
			}
			else if(isSmaller(policyMaxTenure,tenorForm))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", "Maximum Tenor Exception");
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				recordCount++;
			}
			
			
			if(areaForm.equalsIgnoreCase("Negative")&&policyArea.equalsIgnoreCase("Not Accepted"))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", "Negative Area Exception");
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				recordCount++;
			}
			
			if(professionForm.equalsIgnoreCase("Negative")&&policyProfession.equalsIgnoreCase("Not Accepted"))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", "Profession Exception");
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				recordCount++;
			}
			
			if(blueCollarForm.equalsIgnoreCase("Negative")&&policyBlueCollar.equalsIgnoreCase("Not Accepted"))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", "Blue Collar Exception");
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				recordCount++;
			}
		}
		
		
		
		String nationality=(String) ifr.getValue("Applicant_Nationality");
		LOS_EG.mLogger.info("Fetch Exception Coverage Ratio");
		
		double dTotCoverageRatio=LOS_CommonMethods.getDoubleAmount(((String) ifr.getValue("Total_CoverageRation")).replace("%",""));
		
		LOS_EG.mLogger.info("nationality "+nationality);
		LOS_EG.mLogger.info("dTotCoverageRatio "+dTotCoverageRatio);
		
		
		if((nationality.equalsIgnoreCase("EG") && dTotCoverageRatio >95) || ((!nationality.equalsIgnoreCase("EG")) && dTotCoverageRatio >90))
		{
			LOS_EG.mLogger.info("CVR IF");
			JSONArray exceptionJsonArr = new JSONArray();
			JSONObject exceptionJsonObj = new JSONObject();
			exceptionJsonObj.put("Type of Exception", "Coverage Ratio Exception");
			exceptionJsonObj.put("Applicant Type","Applicant");
			exceptionJsonArr.add(exceptionJsonObj);
			ifr.addDataToGrid("AutoDev", exceptionJsonArr);
			recordCount++;
		}
		else
		{
			LOS_EG.mLogger.info("CVR ELSe");
		}
			
		
		if(recordCount==0)
		{
			return "true~No Exception is there";
		}
		else
		{
			return "true~Exception is fetched";
		}
	}
	
	public static String fetchMaxEligibilityFromDB(IFormReference ifr) 
	{
		LOS_EG.mLogger.info("Inside fetch Eligibility From DB 11");
		
		String prodType = (String)ifr.getValue("Product_Type");
		LOS_EG.mLogger.info("prodType " +prodType);
		
		if(prodType.equalsIgnoreCase(""))
		{
			return "false~Product Type is Blank";
		}
		String programType ="";
		
		try
		{
			programType = (String)ifr.getValue("PROGRAM_TYPE");
			LOS_EG.mLogger.info("programType 0 " +programType);
		}
		catch(Exception e)
		{
			
		}
		
		if(programType.equalsIgnoreCase(""))
		{
			return "false~Program Type is Blank";
		}
		
		String ageForm = (String)ifr.getValue("Applicant_Age");		
		LOS_EG.mLogger.info("ageForm " +ageForm);
		if(ageForm.equalsIgnoreCase(""))
		{
			return "false~Age is not captured. Please fill DOB/Age";
		}
		
		
		String retAgeForm = (String)ifr.getValue("RETIREMENT_AGE");		
		LOS_EG.mLogger.info("retAgeForm " +retAgeForm);
		
		
		String segment1 = (String)ifr.getValue("Applicant_Employer_Grade");
		LOS_EG.mLogger.info("segment 1 " +segment1);
		
		String segment2 = (String)ifr.getValue("Applicant_Employment_Type");
		LOS_EG.mLogger.info("segment 2 " +segment2);
		
		String columnName = "PRODUCT,PROGRAM,SEGMENT,MIN_AGE_SAL,MAX_AGE_SAL,MAX_AGE_SELF,MAX_AGE_PROFES,MIN_INCOME_SAL,MIN_INCOME_SELF,MIN_INCOME_PROF,MIN_LOS_SAL,MIN_LOS_SELF,MIN_LOS_PROF,MIN_TERM_OF_LOAN,MAX_TERM_OF_LOAN,MIN_LOAN_AMOUNT,MAX_LOAN_AMOUNT,MAX_LOAN_AMT_ISCOREHIT,MAX_DBR_PERC_SAL,MAX_DBR_PERC_SELF,MAX_DBR_PERC_PROF,NEGATIVE_PROFESSION,NEGATIVE_AREA,BLUE_COLLARS,EXTERNAL_VERIFICATION,MIN_PAID_IN_CAPITAL,TML,DOWN_PAYMENT_PERCENTAGE,MIN_AGE_SELF,MIN_AGE_PROF";
		String colArr[] = columnName.split(",");
		String query="SELECT "+columnName+" FROM NG_ELOS_MAST_MAX_ELIGIBILITY "
				+ "WHERE PRODUCT = '"+prodType+"' AND PROGRAM = '"+programType+"' AND (SEGMENT = '"+segment1+"' OR SEGMENT = '"+segment2+"' OR SEGMENT = 'NA') ORDER BY PRIORITY";
		
		
		
		LOS_EG.mLogger.info("Query For Exception " +query);
		
		List lstCheckList = ifr.getDataFromDB(query);
		
		LOS_EG.mLogger.info("Query Output" +lstCheckList);
		
		ifr.clearTable("table144");
		
		int recordCount = 0;
		
		
		if(lstCheckList.size()>0)
		{
			List<String> arr1=(List)lstCheckList.get(0);
			
			Map<String,String> colValMap = new HashMap<>();
			int i=0;
			for(String colName : colArr)
			{
				colValMap.put(colName, arr1.get(i));
				i++;
			}
			
			LOS_EG.mLogger.info("colValMap : " +colValMap);
			
			String empTypeForm = (String)ifr.getValue("Applicant_Employment_Type");		
			LOS_EG.mLogger.info("empTypeForm " +empTypeForm);
			
			String tenorForm = (String)ifr.getValue("Loan_Tenor");
			LOS_EG.mLogger.info("tenorForm " +tenorForm);
			
			String incomeForm = (String)ifr.getValue("NetSalary");	
			LOS_EG.mLogger.info("incomeForm " +incomeForm);
			
			String liabilitiesForm = (String)ifr.getValue("DisposableIncomeWithoutOverTime");	
			LOS_EG.mLogger.info("liabilitiesForm " +liabilitiesForm);
			
			double dIncomeForm = LOS_CommonMethods.getDoubleAmount(incomeForm);
			double dLiabilitiesForm = LOS_CommonMethods.getDoubleAmount(liabilitiesForm);
			
			String policyMaxAge = colValMap.get("MAX_AGE_SELF");
			String policyMinIncome = colValMap.get("MIN_INCOME_SELF");			
			String policyMinLoanAmt = colValMap.get("MIN_LOAN_AMOUNT");
			String policyMaxLoanAmt = colValMap.get("MAX_LOAN_AMOUNT");
			String policyMinTenure = colValMap.get("MIN_TERM_OF_LOAN");
			String policyMaxTenure = colValMap.get("MAX_TERM_OF_LOAN");
			String policyDBR = colValMap.get("MAX_DBR_PERC_SELF");
			
			if(empTypeForm.equalsIgnoreCase("Salaried"))
			{
				policyMaxAge = colValMap.get("MAX_AGE_SAL");
				policyMinIncome = colValMap.get("MIN_INCOME_SAL");
				policyDBR = colValMap.get("MAX_DBR_PERC_SAL");
			}
			else if(empTypeForm.equalsIgnoreCase("Professionals"))
			{
				policyMaxAge = colValMap.get("MAX_AGE_PROFES");
				policyMinIncome = colValMap.get("MIN_INCOME_PROF");
				policyDBR = colValMap.get("MAX_DBR_PERC_PROF");
			}
			else if(empTypeForm.equalsIgnoreCase("Self- Employed") || empTypeForm.equalsIgnoreCase(""))
			{
				policyMaxAge = colValMap.get("MAX_AGE_SELF");
				policyMinIncome = colValMap.get("MIN_INCOME_SELF");
				policyDBR = colValMap.get("MAX_DBR_PERC_SELF");
			}
			
			
			double maxEligibleTenor=0;
			double iAgeForm = LOS_CommonMethods.getDoubleAmount(ageForm);
			double iPolicyMaxAge = LOS_CommonMethods.getDoubleAmount(policyMaxAge);
			double iRetAge = LOS_CommonMethods.getDoubleAmount(retAgeForm);
			double iPolicyMaxTenure = LOS_CommonMethods.getDoubleAmount(policyMaxTenure);
			
			int tenorScenario = 0;	
			if(iAgeForm<=iPolicyMaxAge)
			{
				LOS_EG.mLogger.info("ageForm is smaller than Policy Age");
				double tempTenor = (iPolicyMaxAge-iAgeForm)*12;
				
				LOS_EG.mLogger.info("tempTenor "+tempTenor + "  ; iPolicyMaxAge : "+iPolicyMaxAge+ "  ; iPolicyMaxTenure : "+iPolicyMaxTenure);
				
				if(tempTenor>iPolicyMaxTenure)
				{
					tenorScenario = 1;
					maxEligibleTenor = iPolicyMaxTenure;
				}
				else
				{
					tenorScenario = 2;
					maxEligibleTenor=tempTenor;
				}
			}
			else
			{
				LOS_EG.mLogger.info("ageForm is greater than Policy Age");
				if(iRetAge==0)
				{
					return "false~Retirement Age is not captured. Please fill Retirement age";
				}
				
				double tempTenor = (iRetAge-iAgeForm)*12;
				
				LOS_EG.mLogger.info("tempTenor "+tempTenor + "  ; iPolicyMaxAge : "+iPolicyMaxAge+ "  ; iPolicyMaxTenure : "+iPolicyMaxTenure);
				
				if(tempTenor>iPolicyMaxTenure)
				{
					tenorScenario = 3;
					maxEligibleTenor = iPolicyMaxTenure;
				}
				else
				{
					tenorScenario = 4;
					maxEligibleTenor=tempTenor;
				}
			}
			
			LOS_EG.mLogger.info("maxEligibleTenor "+maxEligibleTenor);
			
			if(maxEligibleTenor<=0 && (!prodType.equalsIgnoreCase("CC")))
			{
				String messageTenor = "Could not Calculate Maximum Eligiblity - Tenor "+maxEligibleTenor;
				switch(tenorScenario)
				{
					case 1:
						messageTenor = "Could not Calculate Maximum Eligiblity (Policy Tenor is zero or less)";
						break;
					
					case 2:
						messageTenor = "Could not Calculate Maximum Eligiblity (Due to Age) "+iAgeForm;
						break;
					
					case 3:
						messageTenor = "Could not Calculate Maximum Eligiblity (Policy Tenor is zero or less)";
						break;
					
					case 4:
						messageTenor = "Could not Calculate Maximum Eligiblity (Due to Retirement Age) "+iRetAge;
						break;
					
					default:
						break;
						
				}
				return "false~"+messageTenor;
			}
			
			String interestRate="";
			
			if(prodType.equalsIgnoreCase("CC"))
			{
				interestRate = "27";
			}
			// Removed by Aarish: due to New Policy change
//			else if(programType.equalsIgnoreCase("BANKER FINANCE"))
//			{
//				int iTenorForm = 0;
//				
//				try
//				{
//					iTenorForm = Integer.parseInt(tenorForm);
//				}
//				catch(Exception intP)
//				{
//					iTenorForm = 0;
//				}
//				
//				if(iTenorForm<=84)
//				{
//					interestRate = "13.5";
//				}
//				else if(iTenorForm<=120)
//				{
//					interestRate = "14.5";
//				}
//				else
//				{
//					interestRate = "15.5";
//				}				
//			}
			else
			{
				//change by Aarish: New policy Change
				String sQuery="SELECT INTEREST_RATE,ADMIN_FEE FROM NG_ELOS_MAST_INTEREST_RATE WHERE PROGRAM_TYPE='"+programType+"' AND (SEGMENT='"+segment1+"' OR SEGMENT = '"+segment2+"'  OR SEGMENT='ALL')";
				LOS_EG.mLogger.info("Query For Interest Rate " +sQuery);
				List IntrstList = ifr.getDataFromDB(sQuery);
				if(IntrstList.size()>0)
				{
					List<String>tempLIST=(List)IntrstList.get(0);
					interestRate = tempLIST.get(0);
				}
			}	

			double dInterestRate = LOS_CommonMethods.getDoubleAmount(interestRate);
			
			if(dInterestRate<=0)
			{
				return "false~Could not find interest rate "+dInterestRate;
			}			
			
			double dDBRPolicy = LOS_CommonMethods.getDoubleAmount(policyDBR);
			
			
			LOS_EG.mLogger.info("dDBRPolicy "+dDBRPolicy);
			LOS_EG.mLogger.info("dIncomeForm "+dIncomeForm);
			LOS_EG.mLogger.info("dLiabilitiesForm "+dLiabilitiesForm);
			
			double disposableIncome = ((dDBRPolicy*dIncomeForm)/100)-dLiabilitiesForm;
			
			LOS_EG.mLogger.info("disposableIncome "+disposableIncome);
			
			double maxEligibleLoanAmt =0d;
			
			if(prodType.equalsIgnoreCase("CC"))
			{
				maxEligibleLoanAmt = disposableIncome*20;
				LOS_EG.mLogger.info("maxEligibleLoanAmt CC "+maxEligibleLoanAmt);
			}
			else
			{
				maxEligibleLoanAmt = ((Math.pow((1 + (dInterestRate/1200)), maxEligibleTenor) - 1) * disposableIncome) / (Math.pow((1 + (dInterestRate/1200)), maxEligibleTenor) * (dInterestRate/1200));	
				LOS_EG.mLogger.info("maxEligibleLoanAmt Non CC "+maxEligibleLoanAmt);
			}
			
			if(maxEligibleLoanAmt<0)
			{
				maxEligibleLoanAmt = 0;
			}
			
			double dPolicyMaxLoanAmt = LOS_CommonMethods.getDoubleAmount(policyMaxLoanAmt);
			
			if(maxEligibleLoanAmt>dPolicyMaxLoanAmt)
			{
				maxEligibleLoanAmt = dPolicyMaxLoanAmt;
			}
			
			JSONArray maxEligibilityJsonArr = new JSONArray();
			JSONObject maxEligibilityJsonObj = new JSONObject();
			
			maxEligibilityJsonObj.put("Maximum Eligible Loan Tenor", String.format("%.0f",maxEligibleTenor));
			maxEligibilityJsonObj.put("Maximum Eligible Loan Amount", String.format("%.2f",maxEligibleLoanAmt));
			
			maxEligibilityJsonObj.put("Maximum Age", policyMaxAge);
			maxEligibilityJsonObj.put("Maximum Tenor", policyMaxTenure);
			maxEligibilityJsonObj.put("Maximum Loan Amount", policyMaxLoanAmt);
			maxEligibilityJsonObj.put("Maximum DBR", policyDBR);
			maxEligibilityJsonObj.put("Rate Of Interest", interestRate);
			
			LOS_EG.mLogger.info("JSON OBJECT IS "+maxEligibilityJsonObj);
			
			ifr.clearTable("table144");
			maxEligibilityJsonArr.add(maxEligibilityJsonObj);
			ifr.addDataToGrid("table144", maxEligibilityJsonArr);
			return "true~Eligibilty Fetched Successfully";
		}
		else
		{
			return "false~No Record Found in Master for the mentioned Criteria";
		}
	}
	
	
	public static String fetchMaxEligibilityFromDB_Old(IFormReference ifr) 
	{
		LOS_EG.mLogger.info("Inside fetch Eligibility From DB 11");
		
		String prodType = (String)ifr.getValue("Product_Type");
		LOS_EG.mLogger.info("prodType " +prodType);
		
		if(prodType.equalsIgnoreCase(""))
		{
			return "false~Product Type is Blank";
		}
		String programType ="";
		
		try
		{
			programType = (String)ifr.getValue("PROGRAM_TYPE");
			LOS_EG.mLogger.info("programType 0 " +programType);
		}
		catch(Exception e)
		{
			
		}
		
		if(programType.equalsIgnoreCase(""))
		{
			return "false~Program Type is Blank";
		}
		
		String segment1 = (String)ifr.getValue("Applicant_Employer_Grade");
		LOS_EG.mLogger.info("segment 1 " +segment1);
		
		String segment2 = (String)ifr.getValue("Applicant_Employment_Type");
		LOS_EG.mLogger.info("segment 2 " +segment2);
		
		String columnName = "PRODUCT,PROGRAM,SEGMENT,MIN_AGE_SAL,MAX_AGE_SAL,MAX_AGE_SELF,MAX_AGE_PROFES,MIN_INCOME_SAL,MIN_INCOME_SELF,MIN_INCOME_PROF,MIN_LOS_SAL,MIN_LOS_SELF,MIN_LOS_PROF,MIN_TERM_OF_LOAN,MAX_TERM_OF_LOAN,MIN_LOAN_AMOUNT,MAX_LOAN_AMOUNT,MAX_LOAN_AMT_ISCOREHIT,MAX_DBR_PERC_SAL,MAX_DBR_PERC_SELF,MAX_DBR_PERC_PROF,NEGATIVE_PROFESSION,NEGATIVE_AREA,BLUE_COLLARS,EXTERNAL_VERIFICATION,MIN_PAID_IN_CAPITAL,TML,DOWN_PAYMENT_PERCENTAGE,MIN_AGE_SELF,MIN_AGE_PROF";
		String colArr[] = columnName.split(",");
		String query="SELECT "+columnName+" FROM NG_ELOS_MAST_MAX_ELIGIBILITY "
				+ "WHERE PRODUCT = '"+prodType+"' AND PROGRAM = '"+programType+"' AND (SEGMENT = '"+segment1+"' OR SEGMENT = '"+segment2+"' OR SEGMENT = 'NA') ORDER BY PRIORITY";
		
		
		
		LOS_EG.mLogger.info("Query For Exception " +query);
		
		List lstCheckList = ifr.getDataFromDB(query);
		
		LOS_EG.mLogger.info("Query Output" +lstCheckList);
		
		ifr.clearTable("table144");
		
		int recordCount = 0;
		ifr.clearTable("AutoDev");
		
		if(lstCheckList.size()>0)
		{
			List<String> arr1=(List)lstCheckList.get(0);
			
			Map<String,String> colValMap = new HashMap<>();
			int i=0;
			for(String colName : colArr)
			{
				colValMap.put(colName, arr1.get(i));
				i++;
			}
			
			String empTypeForm = (String)ifr.getValue("Applicant_Employment_Type");		
			LOS_EG.mLogger.info("empTypeForm " +empTypeForm);
			
			String policyMaxAge = colValMap.get("MAX_AGE_SELF");
			String policyMinIncome = colValMap.get("MIN_INCOME_SELF");			
			String policyMinLoanAmt = colValMap.get("MIN_LOAN_AMOUNT");
			String policyMaxLoanAmt = colValMap.get("MAX_LOAN_AMOUNT");
			
			if(empTypeForm.equalsIgnoreCase("Salaried"))
			{
				policyMaxAge = colValMap.get("MAX_AGE_SAL");
				policyMinIncome = colValMap.get("MIN_INCOME_SAL");
			}
			else if(empTypeForm.equalsIgnoreCase("Professionals"))
			{
				policyMaxAge = colValMap.get("MAX_AGE_PROFES");
				policyMinIncome = colValMap.get("MIN_INCOME_PROF");
			}
			else if(empTypeForm.equalsIgnoreCase("Self- Employed") || empTypeForm.equalsIgnoreCase(""))
			{
				policyMaxAge = colValMap.get("MAX_AGE_SELF");
				policyMinIncome = colValMap.get("MIN_INCOME_SELF");
			}
			
			JSONArray maxEligibilityJsonArr = new JSONArray();
			JSONObject maxEligibilityJsonObj = new JSONObject();
			maxEligibilityJsonObj.put("Program Type", programType);
			maxEligibilityJsonObj.put("Company Category", segment1);
			maxEligibilityJsonObj.put("Max Age at Maturity", policyMaxAge);
			maxEligibilityJsonObj.put("Minimum Income",policyMinIncome);
			maxEligibilityJsonObj.put("Minimum Loan Amount",policyMinLoanAmt);
			maxEligibilityJsonObj.put("Maximum Eligible Loan Amount", policyMaxLoanAmt);
			LOS_EG.mLogger.info("JSON OBJECT IS "+maxEligibilityJsonObj);
			ifr.clearTable("table144");
			maxEligibilityJsonArr.add(maxEligibilityJsonObj);
			ifr.addDataToGrid("table144", maxEligibilityJsonArr);
			return "true~Eligibilty Fetched Successfully";
		}
		else
		{
			return "false~No Record Found in Master for the mentioned Criteria";
		}
	}
	
	
	//Ajay 14Nov	
	public static String getColumnValueOfGrid(String gridJsonData,String colName, int rowIndex)
	{
		try
		{
			Object obj = JSONValue.parse(gridJsonData);
			JSONArray array = (JSONArray)obj;
		    JSONObject obj2 = (JSONObject)array.get(rowIndex);
		    
		    String ret  = (String)obj2.get(colName);
		    
		    if(ret==null)
		    {
		    	ret="";
		    }
		    return ret;
		}
		catch(Exception e)
		{
			return "";
		}
	}
	//Ajay 14Nov
	private static boolean isGreater(String value1, String value2) 
	{
		try
		{
			double d1 = Double.parseDouble(value1);
			double d2 = Double.parseDouble(value2);
			
			if(d1>d2)
			{
				return true;
			}
			else
			{
				return false;
			}	
		}
		catch(Exception e)
		{
			LOS_EG.mLogger.error("Error in is Greater Method "+e);
			return false;
		}
	}
	
	//Ajay 14Nov
	private static boolean isSmaller(String value1, String value2) 
	{
		try
		{
			double d1 = Double.parseDouble(value1);
			double d2 = Double.parseDouble(value2);
			
			if(d1<d2)
			{
				return true;
			}
			else
			{
				return false;
			}	
		}
		catch(Exception e)
		{
			LOS_EG.mLogger.error("Error in is Greater Method "+e);
			return false;
		}
	}
	//need to comment or remove
	public static String fetchException(IFormReference ifr, String data) throws IOException {
		try {
			String inputDetails = getInputXML("CALL_EXCEPTION");
			LOS_EG.mLogger.info("Default input XML for CALL_EXCEPTION is" + inputDetails.split("~")[0]);
			String inputXML = inputDetails.split("~")[0];
			String endPoint = inputDetails.split("~")[1];
			String wsdl = inputDetails.split("~")[2];
			String programType=data.split("~")[3];
			String segment=(String)ifr.getValue("Applicant_Employer_Grade");
			String Tenor=(String) ifr.getValue("Loan_Tenor");
			if(((String)ifr.getValue("Product_Type")).equalsIgnoreCase("AL"))
			{
				segment=(String)ifr.getValue("Applicant_Employment_Type");
			}
			else if(programType.equalsIgnoreCase("CLUB MEMBERSHIP") || programType.equalsIgnoreCase("INCOME SURROGATE CLUB MEMBERSHIP") ||
					programType.equalsIgnoreCase("INCOME SURROGATE PROPERTY OWNERSHIP") ||programType.equalsIgnoreCase("ULTIMATE") ||
					programType.equalsIgnoreCase("COMPLEMENTRY CARDS") ||programType.equalsIgnoreCase("INCOME SURROGATE CLUB MEMBERSHIP PROGRAM")|| programType.equalsIgnoreCase("INCOME SURROGATE  COVERED CARD AGAINST DEPOSITS"))
				{
				segment=(String)ifr.getValue("Applicant_Employment_Type");
				}
			
			if(((String)ifr.getValue("Product_Type")).equalsIgnoreCase("CC") || Tenor.equalsIgnoreCase(""))
			{
				Tenor="0";
			}
			String age="0";
			String loanAmt="0";
			String minLOS="0";
			if( !((String)ifr.getValue("Applicant_Age")).equalsIgnoreCase(""))
			{
				age=(String)ifr.getValue("Applicant_Age");
			}
			if( !((String)ifr.getValue("Req_Loan_Amt")).equalsIgnoreCase(""))
			{
				loanAmt=(String)ifr.getValue("Req_Loan_Amt");
			}
			if( !((String)ifr.getValue("Applicant_Length_of_Service")).equalsIgnoreCase(""))
			{
				minLOS=(String)ifr.getValue("Applicant_Length_of_Service");
			}
			  inputXML = inputXML.substring(0, inputXML.indexOf("<applicant_age>"))+"<applicant_age>" + age +inputXML.substring(inputXML.indexOf("</applicant_age>"),inputXML.length()); 
			  inputXML = inputXML.substring(0,inputXML.indexOf("<area_type>")) + "<area_type>" + data.split("~")[0] +inputXML.substring(inputXML.indexOf("</area_type>"), inputXML.length());
			  inputXML = inputXML.substring(0, inputXML.indexOf("<bluecollar>")) + "<bluecollar>"+ data.split("~")[2] + inputXML.substring(inputXML.indexOf("</bluecollar>"),inputXML.length()); 
			  inputXML = inputXML.substring(0, inputXML.indexOf("<dbr>")) + "<dbr>"+ "0" + inputXML.substring(inputXML.indexOf("</dbr>"),inputXML.length());
			  inputXML = inputXML.substring(0, inputXML.indexOf("<egypt_produc_typet>")) + "<egypt_produc_typet>"+ ifr.getValue("Product_Type") + inputXML.substring(inputXML.indexOf("</egypt_produc_typet>"),inputXML.length());
			  inputXML = inputXML.substring(0, inputXML.indexOf("<profession>")) + "<profession>"+ data.split("~")[1] + inputXML.substring(inputXML.indexOf("</profession>"),inputXML.length());
			  inputXML = inputXML.substring(0, inputXML.indexOf("<program_type>")) + "<program_type>"+ programType + inputXML.substring(inputXML.indexOf("</program_type>"),inputXML.length());
			  inputXML = inputXML.substring(0, inputXML.indexOf("<requested_loan_amt>")) + "<requested_loan_amt>"+ loanAmt + inputXML.substring(inputXML.indexOf("</requested_loan_amt>"),inputXML.length());
			  inputXML = inputXML.substring(0, inputXML.indexOf("<segment>")) + "<segment>"+ segment + inputXML.substring(inputXML.indexOf("</segment>"),inputXML.length());
			  inputXML = inputXML.substring(0, inputXML.indexOf("<Min_LOS>")) + "<Min_LOS>"+ ifr.getValue("Applicant_Length_of_Service") + inputXML.substring(inputXML.indexOf("</Min_LOS>"),inputXML.length());
			  inputXML = inputXML.substring(0, inputXML.indexOf("<Tenor>")) + "<Tenor>"+ Tenor + inputXML.substring(inputXML.indexOf("</Tenor>"),inputXML.length());
			  
			   
			  LOS_EG.mLogger.info("Final BRMS Input XML For Exception  " +
			  inputXML + " END POINT " + endPoint + " WSDL " + wsdl); NGWebServiceClient
			  ngwsclnt = new NGWebServiceClient(true);
			  
			  String responseXML = ngwsclnt.ExecuteWs(1, inputXML, endPoint,
			  "executeRuleset", wsdl); LOS_EG.mLogger.info("BRMS Response XML is" +
			  responseXML);
			  
			  
			  return IntegrationParser.parseExaception(responseXML, ifr,data.split("~")[3]);
			 
		} catch (Exception e) {
			LOS_EG.mLogger.info("BRMS exception is" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data";
		}
	}
	public static String nIDCheck(IFormReference ifr) throws IOException {
		try {
			LOS_EG.mLogger.info("inside nIDCheck");
			String inputDetails = getInputXML("ISCORE_GET_CIF_INFO");
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
			String inputXML = inputDetails.split("~")[0];
			inputXML = inputXML.substring(0, inputXML.indexOf("<CustNIN>")) + "<CustNIN>" +  ifr.getValue("table107_CustomerNationalID")
			+ inputXML.substring(inputXML.indexOf("</CustNIN>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<NationalIdNumber>")) + "<NationalIdNumber>"
					+ ifr.getValue("table107_CustomerNationalID")
					+ inputXML.substring(inputXML.indexOf("</NationalIdNumber>"), inputXML.length());
//			String responseXML = SocketConnector.getSocketResponse(inputXML,
//					inputDetails.split("~")[1], inputDetails.split("~")[2]);
			
			String responseXML=getRequestURL(inputXML,ifr);

			return IntegrationParser.parseNIDCheck(responseXML, ifr);
		} catch (Exception e) {

			LOS_EG.mLogger.info("collateralEnq error is" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
	}
	
	public static String calculateLoan(IFormReference ifr,String data) throws IOException 
	{
		try
		{
		LOS_EG.mLogger.info("inside Calculate Loan");
		String inputDetails = getInputXML("LOAN_CALCULATOR");
		LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
		LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
		String inputXML = inputDetails.split("~")[0];
		String interestRate=(String)ifr.getValue("table117_InterestRate");
		String marginRate=(String)ifr.getValue("table117_MarginRate"); //Ajay 29Dec
		String cifID=(String)ifr.getValue("CIF_ID");
		if(cifID.equalsIgnoreCase(""))
		{
			cifID="100100";
		}
		String pType = (String)ifr.getValue("Product_Type");
		LOS_EG.mLogger.info("ProductType : "+pType);
		String loanType = "";
		if(pType.equalsIgnoreCase("PL")) 
		{
			loanType = "IML";
		}
		else if(pType.equalsIgnoreCase("AL"))
		{
			loanType="VIL";
		}
		else if(pType.equalsIgnoreCase("MR"))
		{
			loanType="MUR";
		}
		else if(pType.equalsIgnoreCase("CL"))
		{
			loanType="MCL";
		}
		/*
		if(interestRate.contains("."))
		{
			interestRate=interestRate.replace(".", "")+"000000";
		}
		else
		{
			interestRate=interestRate+"0000000";
		}*/
		
		String Sub_Product_Type = (String) ifr.getValue("Sub_Product_Type");
		if(Sub_Product_Type.equalsIgnoreCase("Secured"))
		{
			interestRate = LOS_CommonMethods.handleSevenZeroInRequest(LOS_CommonMethods.getDoubleAmount(interestRate) + LOS_CommonMethods.getDoubleAmount(marginRate));	
		}
		else
		{
			interestRate = LOS_CommonMethods.handleSevenZeroInRequest(LOS_CommonMethods.getDoubleAmount(interestRate));
			marginRate = "";
		}
		
			
		
		LOS_EG.mLogger.info("SInterest Rate Field is - " + interestRate);
		
		inputXML = inputXML.substring(0, inputXML.indexOf("<BasicNumber>")) + "<BasicNumber>"+cifID+ inputXML.substring(inputXML.indexOf("</BasicNumber>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<DealReference>")) + "<DealReference>"+""+ inputXML.substring(inputXML.indexOf("</DealReference>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<DealBranch>")) + "<DealBranch>"+ifr.getValue("Branch_Code")+ inputXML.substring(inputXML.indexOf("</DealBranch>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<DealType>")) + "<DealType>"+loanType+ inputXML.substring(inputXML.indexOf("</DealType>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<Currency>")) + "<Currency>"+(String)ifr.getValue("table117_Currency")+ inputXML.substring(inputXML.indexOf("</Currency>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<DealAmount>")) + "<DealAmount>"+data.split("~")[0]+ inputXML.substring(inputXML.indexOf("</DealAmount>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<StartDate>")) + "<StartDate>"+((String)ifr.getValue("table117_ValueDate")).replace("/", "")+ inputXML.substring(inputXML.indexOf("</StartDate>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<InterestBasisDays>")) + "<InterestBasisDays>"+"1"+ inputXML.substring(inputXML.indexOf("</InterestBasisDays>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<ActualRate>")) + "<ActualRate>"+interestRate+ inputXML.substring(inputXML.indexOf("</ActualRate>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<RepaymentFrequency>")) + "<RepaymentFrequency>"+getFrequency(ifr,data.split("~")[3]).split("~")[0]+ inputXML.substring(inputXML.indexOf("</RepaymentFrequency>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<NoOfRepayments>")) + "<NoOfRepayments>"+data.split("~")[1]+ inputXML.substring(inputXML.indexOf("</NoOfRepayments>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<FirstRepaymentDate>")) + "<FirstRepaymentDate>"+(getFrequency(ifr,data.split("~")[3]).split("~")[1]).replace("/", "")+ inputXML.substring(inputXML.indexOf("</FirstRepaymentDate>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<MaturityDate>")) + "<MaturityDate>"+data.split("~")[2].replace("/", "")+ inputXML.substring(inputXML.indexOf("</MaturityDate>"), inputXML.length());
		LOS_EG.mLogger.info("Final Loan calculator Request is "+inputXML);
//		String responseXML = SocketConnector.getSocketResponse(inputXML,inputDetails.split("~")[1], inputDetails.split("~")[2]);

		String responseXML=getRequestURL(inputXML,ifr);
		
		responseXML=responseXML.replace("&lt;", "<");
		responseXML=responseXML.replace("&gt;", ">");
		LOS_EG.mLogger.info("Response for Loan Calculator  is "+responseXML);
		return IntegrationParser.parseLoanCalculator(responseXML, ifr,data.split("~")[3]);
		}
		catch (Exception e) {
			LOS_EG.mLogger.info("Loan Calculator" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
		
		
	}
//	public static String bookLoan(IFormReference ifr,String data) throws IOException {
//		try 
//		{
//		LOS_EG.mLogger.info("inside bookLoan");
//		
//		//Ajay 15Dec Start
//		String Sub_Product_Type = (String) ifr.getValue("Sub_Product_Type");		
//		String collResNo = (String)ifr.getValue("CollResNo");
//		String pType = (String)ifr.getValue("Product_Type");
//		LOS_EG.mLogger.info("ProductType : "+pType);
//		String loanType = "";
//		if(pType.equalsIgnoreCase("PL")) 
//		{
//			loanType = "IML";
//		}
//		else if(pType.equalsIgnoreCase("AL"))
//		{
//			loanType="VIL";
//		}
//		else if(pType.equalsIgnoreCase("MR"))
//		{
//			loanType="MUR";
//		}
//		else if(pType.equalsIgnoreCase("CL"))
//		{
//			loanType="MCL";
//		}
//		try
//		{
//			if(Sub_Product_Type.equalsIgnoreCase("Secured") && (!collResNo.equalsIgnoreCase("")))
//			{
//				String reservationEnquiryOP = checkReservationBeforeLoanBook(ifr);
//				
//				String resArr[] = reservationEnquiryOP.split("~");
//				if(resArr[0].equalsIgnoreCase("true"))
//				{
//					double reserveAmount = LOS_CommonMethods.getDoubleAmount(resArr[1]);
//					double loanAmount = LOS_CommonMethods.getDoubleAmount("Credit_Approved_Amount");
//					
//					if(reserveAmount<loanAmount)
//					{
//						return "false~Reserve Amount is less than Finance Amount";
//					}
//				}
//				else
//				{
//					return "false~"+resArr[1];
//				}
//			}
//		}
//		catch(Exception rE)	
//		{
//			
//		}
//		//Ajay 15Dec End
//		
//		String interestRate=ifr.getTableCellValue("table117", 0, 2);
//		String marginRate=ifr.getTableCellValue("table117", 0, 28);
//		if(Sub_Product_Type.equalsIgnoreCase("Secured"))
//		{
//			interestRate = String.format("%.03f", (LOS_CommonMethods.getDoubleAmount(interestRate) + LOS_CommonMethods.getDoubleAmount(marginRate)));
//		}
//		else
//		{
//			marginRate = "";
//		}
//		
//		String inputDetails = getInputXML("BOOK_LOAN");
//		LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
//		LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
//		String inputXML = inputDetails.split("~")[0];
//		inputXML = inputXML.substring(0, inputXML.indexOf("<CustNIN>")) + "<CustNIN>" +  ifr.getValue("Applicant_National_ID")
//		+ inputXML.substring(inputXML.indexOf("</CustNIN>"), inputXML.length());
//		String accNumber=data.split("~")[0];
//		String branch=accNumber.substring(0, 4);
//		String suffix=accNumber.substring(10, 13);
//		String cifID= accNumber.substring(4,10);//5-10
//		double AdminFeeAmount=0;
//		try //Ajay 29Nov
//		{
//			if(Double.parseDouble((String)ifr.getTableCellValue("table117", 0, 45))>0 && Double.parseDouble((String)ifr.getTableCellValue("table117", 0, 45))<100)
//			{
//				AdminFeeAmount=(Double.parseDouble(data.split("~")[5])*Double.parseDouble((String)ifr.getTableCellValue("table117", 0, 45)))/100;
//			}
//		}
//		catch(Exception e)
//		{
//			
//		}	
//		
//
//		//Currecy from product details
//		inputXML = inputXML.substring(0, inputXML.indexOf("<Branch>")) + "<Branch>"+ branch+ inputXML.substring(inputXML.indexOf("</Branch>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<EQNUserID>")) + "<EQNUserID>"+""+ inputXML.substring(inputXML.indexOf("</EQNUserID>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<Currency>")) + "<Currency>"+"EGP"+ inputXML.substring(inputXML.indexOf("</Currency>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<RMcode>")) + "<RMcode>"+"EGY"+ inputXML.substring(inputXML.indexOf("</RMcode>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<LoanType>")) + "<LoanType>"+loanType+ inputXML.substring(inputXML.indexOf("</LoanType>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<CIF>")) + "<CIF>"+cifID+ inputXML.substring(inputXML.indexOf("</CIF>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<CLC>")) + "<CLC>"+"000"+ inputXML.substring(inputXML.indexOf("</CLC>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<Amount>")) + "<Amount>"+LOS_CommonMethods.handleDoubleZeroInRequest(LOS_CommonMethods.getDoubleAmount(data.split("~")[5]))+ inputXML.substring(inputXML.indexOf("</Amount>"), inputXML.length());//Ajay 29Nov
//		// inputXML = inputXML.substring(0, inputXML.indexOf("<startDate>")) + "<startDate>"+new SimpleDateFormat("yyyyMMdd").format(new Date())+ inputXML.substring(inputXML.indexOf("</startDate>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<startDate>")) + "<startDate>"+getLoanStartDate()+ inputXML.substring(inputXML.indexOf("</startDate>"), inputXML.length());
//		//startDate cussrent dat yyyy/mm/dd
//		inputXML = inputXML.substring(0, inputXML.indexOf("<Pegged>")) + "<Pegged>"+"N"+ inputXML.substring(inputXML.indexOf("</Pegged>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<InterestFrequency>")) + "<InterestFrequency>"+getFrequency(ifr,data.split("~")[1],data.split("~")[3]).split("~")[0]+ inputXML.substring(inputXML.indexOf("</InterestFrequency>"), inputXML.length());
//		//revolving code
//		inputXML = inputXML.substring(0, inputXML.indexOf("<Capitalizedflag>")) + "<Capitalizedflag>"+"N"+ inputXML.substring(inputXML.indexOf("</Capitalizedflag>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<MaturityDate>")) + "<MaturityDate>"+data.split("~")[2].replace("/", "")+ inputXML.substring(inputXML.indexOf("</MaturityDate>"), inputXML.length());
//		//MaturityDate--last installment date
//		inputXML = inputXML.substring(0, inputXML.indexOf("<ReceivingAccountBranchNumber>")) + "<ReceivingAccountBranchNumber>"+branch+ inputXML.substring(inputXML.indexOf("</ReceivingAccountBranchNumber>"), inputXML.length());
//		
//		inputXML = inputXML.substring(0, inputXML.indexOf("<ReceivingAccountBasicNumber>")) + "<ReceivingAccountBasicNumber>"+cifID+ inputXML.substring(inputXML.indexOf("</ReceivingAccountBasicNumber>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<ReceivingAccountSuffix>")) + "<ReceivingAccountSuffix>"+suffix+ inputXML.substring(inputXML.indexOf("</ReceivingAccountSuffix>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<PrinciplePaymentAccountBranchNumber>")) + "<PrinciplePaymentAccountBranchNumber>"+branch+ inputXML.substring(inputXML.indexOf("</PrinciplePaymentAccountBranchNumber>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<PrinciplePaymentAccountBasicNumber>")) + "<PrinciplePaymentAccountBasicNumber>"+cifID+ inputXML.substring(inputXML.indexOf("</PrinciplePaymentAccountBasicNumber>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<PrinciplePaymentAccountSuffixNumber>")) + "<PrinciplePaymentAccountSuffixNumber>"+suffix+ inputXML.substring(inputXML.indexOf("</PrinciplePaymentAccountSuffixNumber>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<InterestPaymentAccountBranchNumber>")) + "<InterestPaymentAccountBranchNumber>"+branch+ inputXML.substring(inputXML.indexOf("</InterestPaymentAccountBranchNumber>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<InterestPaymentAccountBasicNumber>")) + "<InterestPaymentAccountBasicNumber>"+cifID+ inputXML.substring(inputXML.indexOf("</InterestPaymentAccountBasicNumber>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<InterestPaymentAccountSuffixNumber>")) + "<InterestPaymentAccountSuffixNumber>"+suffix+ inputXML.substring(inputXML.indexOf("</InterestPaymentAccountSuffixNumber>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<InterestDaysBasis>")) + "<InterestDaysBasis>"+"1"+ inputXML.substring(inputXML.indexOf("</InterestDaysBasis>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<BaseCode>")) + "<BaseCode>"+""+ inputXML.substring(inputXML.indexOf("</BaseCode>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<DiffCode>")) + "<DiffCode>"+""+ inputXML.substring(inputXML.indexOf("</DiffCode>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<Rate>")) + "<Rate>"+interestRate+ inputXML.substring(inputXML.indexOf("</Rate>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<Margin>")) + "<Margin>"+marginRate+ inputXML.substring(inputXML.indexOf("</Margin>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<PenaltyRateCodePrinciple>")) + "<PenaltyRateCodePrinciple>"+""+ inputXML.substring(inputXML.indexOf("</PenaltyRateCodePrinciple>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<PenaltyRateCodeInterest>")) + "<PenaltyRateCodeInterest>"+""+ inputXML.substring(inputXML.indexOf("</PenaltyRateCodeInterest>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<ReviewFrequency>")) + "<ReviewFrequency>"+""+ inputXML.substring(inputXML.indexOf("</ReviewFrequency>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<NextReviewDate>")) + "<NextReviewDate>"+""+ inputXML.substring(inputXML.indexOf("</NextReviewDate>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<Rollover>")) + "<Rollover>"+"Y"+ inputXML.substring(inputXML.indexOf("</Rollover>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<CommitmentRef>")) + "<CommitmentRef>"+""+ inputXML.substring(inputXML.indexOf("</CommitmentRef>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<OriginalStartDate>")) + "<OriginalStartDate>"+""+ inputXML.substring(inputXML.indexOf("</OriginalStartDate>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<LoanSuffix>")) + "<LoanSuffix>"+"300"+ inputXML.substring(inputXML.indexOf("</LoanSuffix>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<FirstRepaymentDate>")) + "<FirstRepaymentDate>"+data.split("~")[3].replaceAll("/", "")+ inputXML.substring(inputXML.indexOf("</FirstRepaymentDate>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<InterestAdjustment>")) + "<InterestAdjustment>"+""+ inputXML.substring(inputXML.indexOf("</InterestAdjustment>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<NarrativeLine1>")) + "<NarrativeLine1>"+""+ inputXML.substring(inputXML.indexOf("</NarrativeLine1>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<NarrativeLine2>")) + "<NarrativeLine2>"+""+ inputXML.substring(inputXML.indexOf("</NarrativeLine2>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<ScheduleRecalculation>")) + "<ScheduleRecalculation>"+"2"+ inputXML.substring(inputXML.indexOf("</ScheduleRecalculation>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<RegularAmount>")) + "<RegularAmount>"+LOS_CommonMethods.handleDoubleZeroInRequest(AdminFeeAmount)+ inputXML.substring(inputXML.indexOf("</RegularAmount>"), inputXML.length());//Ajay 29Nov
//		inputXML = inputXML.substring(0, inputXML.indexOf("<RMLoanRef>")) + "<RMLoanRef>"+"CCLB-2021-06-2"+ inputXML.substring(inputXML.indexOf("</RMLoanRef>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<NumberOfPrincipleRepayments>")) + "<NumberOfPrincipleRepayments>"+"0"+ inputXML.substring(inputXML.indexOf("</NumberOfPrincipleRepayments>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<PrincipleRepaymentAmount>")) + "<PrincipleRepaymentAmount>"+data.split("~")[4]+ inputXML.substring(inputXML.indexOf("</PrincipleRepaymentAmount>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<PrincipleRepaymentFrequency>")) + "<PrincipleRepaymentFrequency>"+getFrequency(ifr,data.split("~")[1],data.split("~")[3]).split("~")[0]+ inputXML.substring(inputXML.indexOf("</PrincipleRepaymentFrequency>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<NextInterestDate>")) + "<NextInterestDate>"+data.split("~")[3].replaceAll("/", "")+ inputXML.substring(inputXML.indexOf("</NextInterestDate>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<SecurityCode>")) + "<SecurityCode>"+"SE"+ inputXML.substring(inputXML.indexOf("</SecurityCode>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<FYCRateType>")) + "<FYCRateType>"+""+ inputXML.substring(inputXML.indexOf("</FYCRateType>"), inputXML.length());
//		inputXML = inputXML.substring(0, inputXML.indexOf("<OriginalLoanReference>")) + "<OriginalLoanReference>"+ifr.getValue("CollResNo")+ inputXML.substring(inputXML.indexOf("</OriginalLoanReference>"), inputXML.length());
//		
//		LOS_EG.mLogger.info("Final Book LOAN Request IS "+inputXML);
//		String responseXML = SocketConnector.getSocketResponse(inputXML,inputDetails.split("~")[1], inputDetails.split("~")[2]);
//		//responseXML=responseXML.replace("&lt;", "<");
//		LOS_EG.mLogger.info("Response for Book LOAN  IS "+responseXML);
//		return IntegrationParser.parseLoanBooking(responseXML, ifr);
//		} 
//		catch (Exception e) {
//
//			LOS_EG.mLogger.info("Iscore error is" + e);
//			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
//			LOS_EG.printException(e);
//			return "false~Error in fetching data from middleware";
//		}
//	}
	
	public static String bookLoanManual(IFormReference ifr,String data) throws IOException {
		try
		{
			LOS_EG.mLogger.info("inside bookLoan");

			//Ajay 15Dec Start
			String Sub_Product_Type = (String) ifr.getValue("Sub_Product_Type");
			String collResNo = (String)ifr.getValue("CollResNo");
			String pType = (String)ifr.getValue("Product_Type");
			LOS_EG.mLogger.info("ProductType : "+pType);

			try
			{
				if(Sub_Product_Type.equalsIgnoreCase("Secured") && (!collResNo.equalsIgnoreCase("")))
				{
					String reservationEnquiryOP = checkReservationBeforeLoanBook(ifr);

					String resArr[] = reservationEnquiryOP.split("~");
					if(resArr[0].equalsIgnoreCase("true"))
					{
						double reserveAmount = LOS_CommonMethods.getDoubleAmount(resArr[1]);
						double loanAmount = LOS_CommonMethods.getDoubleAmount("Credit_Approved_Amount");

						if(reserveAmount<loanAmount)
						{
							return "false~Reserve Amount is less than Finance Amount";
						}
					}
					else
					{
						return "false~"+resArr[1];
					}
				}
			}
			catch(Exception rE)
			{

			}
			//Ajay 15Dec End

			String interestRate=ifr.getTableCellValue("table117", 0, 2);
			String marginRate=ifr.getTableCellValue("table117", 0, 28);
			if(Sub_Product_Type.equalsIgnoreCase("Secured"))
			{
				interestRate = String.format("%.03f", (LOS_CommonMethods.getDoubleAmount(interestRate) + LOS_CommonMethods.getDoubleAmount(marginRate)));
			}
			else
			{
				marginRate = "";
			}

			String inputDetails = getInputXML("BOOK_LOAN");
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
			String inputXML = inputDetails.split("~")[0];
			inputXML = inputXML.substring(0, inputXML.indexOf("<CustNIN>")) + "<CustNIN>" +  ifr.getValue("Applicant_National_ID")
			+ inputXML.substring(inputXML.indexOf("</CustNIN>"), inputXML.length());
			String accNumber=data.split("~")[0];
			String branch=accNumber.substring(0, 4);
			String suffix=accNumber.substring(10, 13);
			String cifID= accNumber.substring(4,10);//5-10
			String loanAccountNumber = ifr.getValue("CustomerLoanAccountNumber").toString();
			double AdminFeeAmount=0;
			try //Ajay 29Nov
			{
				if(Double.parseDouble((String)ifr.getTableCellValue("table117", 0, 45))>0 && Double.parseDouble((String)ifr.getTableCellValue("table117", 0, 45))<100)
				{
					AdminFeeAmount=(Double.parseDouble(data.split("~")[5])*Double.parseDouble((String)ifr.getTableCellValue("table117", 0, 45)))/100;
				}
			}
			catch(Exception e)
			{

			}


			//Currecy from product details
			inputXML = inputXML.substring(0, inputXML.indexOf("<CustomerNo>")) + "<CustomerNo>"+cifID+ inputXML.substring(inputXML.indexOf("</CIF>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<CustomerLocation>")) + "<CustomerLocation>"+""+ inputXML.substring(inputXML.indexOf("</CustomerLocation>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<AccountOfficer>")) + "<AccountOfficer>"+""+ inputXML.substring(inputXML.indexOf("</AccountOfficer>"), inputXML.length());

			inputXML = inputXML.substring(0, inputXML.indexOf("<Branch>")) + "<Branch>"+ branch+ inputXML.substring(inputXML.indexOf("</Branch>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<DealType>")) + "<DealType>"+"VRM"+ inputXML.substring(inputXML.indexOf("</DealType>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<Currency>")) + "<Currency>"+"EGP"+ inputXML.substring(inputXML.indexOf("</Currency>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<Amount>")) + "<Amount>"+LOS_CommonMethods.handleDoubleZeroInRequest(LOS_CommonMethods.getDoubleAmount(data.split("~")[5]))+ inputXML.substring(inputXML.indexOf("</Amount>"), inputXML.length());//Ajay 29Nov
			inputXML = inputXML.substring(0, inputXML.indexOf("<startDate>")) + "<startDate>"+getLoanStartDate()+ inputXML.substring(inputXML.indexOf("</startDate>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<MaturityDate>")) + "<MaturityDate>"+data.split("~")[2].replace("/", "")+ inputXML.substring(inputXML.indexOf("</MaturityDate>"), inputXML.length());

			inputXML = inputXML.substring(0, inputXML.indexOf("<InterestFrequency>")) + "<InterestFrequency>"+getFrequency(ifr,data.split("~")[1],data.split("~")[3]).split("~")[0]+ inputXML.substring(inputXML.indexOf("</InterestFrequency>"), inputXML.length());

			String[] accountTags = {
					"ReceivingAccount",
					"PrincipleAccount",
					"InterestAccount"
			};

			for (String tag : accountTags) {

				String accountXml =
						"<" + tag + ">" +
								"<Branch>" + branch + "</Branch>" +
								"<Basic>" + cifID + "</Basic>" +
								"<Suffix>" + suffix + "</Suffix>" +
								"</" + tag + ">";

				inputXML = inputXML.replaceAll(
						"(?s)<" + tag + ">.*?</" + tag + ">",
						accountXml);
			}
			
			inputXML = inputXML.substring(0, inputXML.indexOf("<LoanAccountSuffix>")) + "<LoanAccountSuffix>"+loanAccountNumber.substring(0, 3)+ inputXML.substring(inputXML.indexOf("</LoanAccountSuffix>"), inputXML.length());
			
			inputXML = inputXML.substring(0, inputXML.indexOf("<FirstRepaymentDate>")) + "<FirstRepaymentDate>"+data.split("~")[3].replaceAll("/", "")+ inputXML.substring(inputXML.indexOf("</FirstRepaymentDate>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<FirstPrincipleRepaymentDate>")) + "<FirstPrincipleRepaymentDate>"+data.split("~")[3].replaceAll("/", "")+ inputXML.substring(inputXML.indexOf("</FirstPrincipleRepaymentDate>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<FirstInterestRepaymentDate>")) + "<FirstInterestRepaymentDate>"+data.split("~")[3].replaceAll("/", "")+ inputXML.substring(inputXML.indexOf("</FirstInterestRepaymentDate>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<ChargeAmount1>")) + "<ChargeAmount1>"+""+ inputXML.substring(inputXML.indexOf("</ChargeAmount1>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<LastRepaymentAmount>")) + "<LastRepaymentAmount>"+""+ inputXML.substring(inputXML.indexOf("</LastRepaymentAmount>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<NumberOfRepayments>")) + "<NumberOfRepayments>"+"1"+ inputXML.substring(inputXML.indexOf("</NumberOfRepayments>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<RepaymentFrequency>")) + "<RepaymentFrequency>"+getFrequency(ifr,data.split("~")[1],data.split("~")[3]).split("~")[0]+ inputXML.substring(inputXML.indexOf("</RepaymentFrequency>"), inputXML.length());
            inputXML = inputXML.substring(0, inputXML.indexOf("<NextInterestDate>")) + "<NextInterestDate>"+""+ inputXML.substring(inputXML.indexOf("</NextInterestDate>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<ReservationReference>")) + "<ReservationReference>"+""+ inputXML.substring(inputXML.indexOf("</ReservationReference>"), inputXML.length());
			
			inputXML = inputXML.substring(0, inputXML.indexOf("<NarrativeOnDeal>")) + "<NarrativeOnDeal>"+""+ inputXML.substring(inputXML.indexOf("</NarrativeOnDeal>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<SettlmentNarrative1>")) + "<SettlmentNarrative1>"+""+ inputXML.substring(inputXML.indexOf("</SettlmentNarrative1>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<SettlmentNarrative2>")) + "<SettlmentNarrative2>"+""+ inputXML.substring(inputXML.indexOf("</SettlmentNarrative2>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<SettlmentNarrative3>")) + "<SettlmentNarrative3>"+""+ inputXML.substring(inputXML.indexOf("</SettlmentNarrative3>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<SettlmentNarrative4>")) + "<SettlmentNarrative4>"+""+ inputXML.substring(inputXML.indexOf("</SettlmentNarrative4>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<CommitmentReference>")) + "<CommitmentReference>"+""+ inputXML.substring(inputXML.indexOf("</CommitmentReference>"), inputXML.length());
			
			
			inputXML = inputXML.substring(0, inputXML.indexOf("<InterestAmount>")) + "<InterestAmount>"+""+ inputXML.substring(inputXML.indexOf("</InterestAmount>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<IsDisbursement>")) + "<IsDisbursement>"+""+ inputXML.substring(inputXML.indexOf("</IsDisbursement>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<BankFinancedDisbursement>")) + "<BankFinancedDisbursement>"+""+ inputXML.substring(inputXML.indexOf("</BankFinancedDisbursement>"), inputXML.length());

//			inputXML = inputXML.substring(0, inputXML.indexOf("<RegularAmount>")) + "<RegularAmount>"+LOS_CommonMethods.handleDoubleZeroInRequest(AdminFeeAmount)+ inputXML.substring(inputXML.indexOf("</RegularAmount>"), inputXML.length());//Ajay 29Nov
//			inputXML = inputXML.substring(0, inputXML.indexOf("<NextInterestDate>")) + "<NextInterestDate>"+data.split("~")[3].replaceAll("/", "")+ inputXML.substring(inputXML.indexOf("</NextInterestDate>"), inputXML.length());
			
			inputXML = inputXML.substring(0, inputXML.indexOf("<InstallmentAmount>")) + "<InstallmentAmount>"+""+ inputXML.substring(inputXML.indexOf("</InstallmentAmount>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<InstallmentDate>")) + "<InstallmentDate>"+""+ inputXML.substring(inputXML.indexOf("</InstallmentDate>"), inputXML.length());

			LOS_EG.mLogger.info("Final Book LOAN Request IS "+inputXML);
//			String responseXML = SocketConnector.getSocketResponse(inputXML,inputDetails.split("~")[1], inputDetails.split("~")[2]);

			String responseXML=getRequestURL(inputXML,ifr);
			
			//responseXML=responseXML.replace("&lt;", "<");
			LOS_EG.mLogger.info("Response for Book LOAN  IS "+responseXML);
			return IntegrationParser.parseLoanBooking(responseXML, ifr);
		}
		catch (Exception e) {

			LOS_EG.mLogger.info("Iscore error is" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
	}
	
	public static String fetchLoanSummary(IFormReference ifr)
	{
		try 
		{
			LOS_EG.mLogger.info("inside fetchLoanSummary");
			String inputDetails = getInputXML("LOAN_SUMMARY");
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
			String inputXML = inputDetails.split("~")[0];
			//inputXML = inputXML.substring(0, inputXML.indexOf("<CustNIN>")) + "<CustNIN>" +  ifr.getValue("Applicant_National_ID")
			//+ inputXML.substring(inputXML.indexOf("</CustNIN>"), inputXML.length());
			
			inputXML= inputXML.substring(0, inputXML.indexOf("<CustID>")) + "<CustID>" +  ifr.getValue("CIF_ID")
				+ inputXML.substring(inputXML.indexOf("</CustID>"), inputXML.length());
			LOS_EG.mLogger.info("Final Loan Summary Request IS "+inputXML);
//			String responseXML = SocketConnector.getSocketResponse(inputXML,inputDetails.split("~")[1], inputDetails.split("~")[2]);

			String responseXML=getRequestURL(inputXML,ifr);
			
			LOS_EG.mLogger.info("Response for LoanSummary  IS "+responseXML);
			return IntegrationParser.parseLoanSummary(responseXML, ifr);
		}
		catch(Exception e)
		{
			LOS_EG.mLogger.info("LoanSummary error is" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
	}
	public static String fetchCreditScore(IFormReference ifr)
	{
		try 
		{
			LOS_EG.mLogger.info("inside fetchCreditScore");
			String inputDetails = getInputXML("ISCORE_LOS");
			String productCode=(String)ifr.getValue("Product_Type");
			String tempDOB=(String)ifr.getValue("Applicant_DOB");
			LOS_EG.mLogger.info("DOB" +tempDOB);
			String blank="";
			String DOB=tempDOB.split("/")[2]+"/"+tempDOB.split("/")[1]+"/"+tempDOB.split("/")[0];
			//String DOB=tempDOB.split("/")[2]+"/"+tempDOB.split("/")[1]+"/"+tempDOB.split("/")[0];
			Date d1= new SimpleDateFormat("dd/MM/yyyy").parse(DOB);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String dateOfBirth= df.format(d1);
			String amt=(String)ifr.getValue("table117_TotalApprovedAmount");
			LOS_EG.mLogger.info("DateOfBirth" +dateOfBirth);
			LOS_EG.mLogger.info("Amt" +amt);
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
			LOS_EG.mLogger.info("WI NAME is "+ifr.getObjGeneralData().getM_strProcessInstanceId().split("-")[2]);
			String wi_name=ifr.getObjGeneralData().getM_strProcessInstanceId().split("-")[2];
			String inputXML = inputDetails.split("~")[0];
			LOS_EG.mLogger.info("Input xml" +inputXML);
			String nationalityCode=(String)ifr.getValue("Applicant_Nationality");
			LOS_EG.mLogger.info("Nationality Code "+nationalityCode);
			String sQuery="SELECT ISSUANCE_AUTH_CODE FROM NG_ELOS_MAST_ISSUANCE_AUTH_CODE WHERE COUNTRY_CODE='"+nationalityCode+"'";
			LOS_EG.mLogger.info("Query For Iscore " +sQuery);
			List IntrstList = ifr.getDataFromDB(sQuery);
			String idSource="";
			if(IntrstList.size()>0)
			{
				List<String>tempLIST=(List)IntrstList.get(0);
				idSource=tempLIST.get(0);
				LOS_EG.mLogger.info("auth code" +idSource);
				
			}
			if(productCode.equalsIgnoreCase("PL") || productCode.equalsIgnoreCase("Personal Finance"))
			{
				productCode="PERSONAL_LOAN";
				inputXML = inputXML.substring(0, inputXML.indexOf("<vehicleLoan>")) + "<vehicleLoan>" +  blank
						+ inputXML.substring(inputXML.indexOf("</vehicleLoan>"), inputXML.length());
			}
			else if(productCode.equalsIgnoreCase("CC") || productCode.equalsIgnoreCase("Covered Card"))
			{
				productCode="CREDIT_CARD";
				inputXML = inputXML.substring(0, inputXML.indexOf("<vehicleLoan>")) + "<vehicleLoan>" +  blank
						+ inputXML.substring(inputXML.indexOf("</vehicleLoan>"), inputXML.length());
			}
			else
			{
				productCode="VEHICLE_LOAN";
				
			}
			String nid=(String)ifr.getValue("Applicant_National_ID");
			String dateFormat="yyyy-MM-dd";
			String currdate= new SimpleDateFormat(dateFormat).format(new Date());
			String issueDate=(String)ifr.getTableCellValue("table107", 0, 11);//(String)ifr.getValue("table107_DateOfIssueNID");
			LOS_EG.mLogger.info("issueDate" +issueDate);
			String formatIss=issueDate.replace('/', '-');
			LOS_EG.mLogger.info("issueDate" +formatIss);
			String expiryDate=(String)ifr.getTableCellValue("table107", 0, 12);//(String)ifr.getValue("table107_DateOfExpiryNID");
			String formatExp=expiryDate.replace('/', '-');
			LOS_EG.mLogger.info("formatExp" +formatExp);
			if(nid.length()!=14)//chnges by Uroosa
			{
				String idType="Passport";
				String blnk="";
				String issueDate1=(String)ifr.getTableCellValue("table107", 0, 14);//(String)ifr.getValue("table107_DateOfIssueNID");
				LOS_EG.mLogger.info("issueDate1" +issueDate1);
				String formatIss1=issueDate1.replace('/', '-');
				String expiryDate1=(String)ifr.getTableCellValue("table107", 0, 15);//(String)ifr.getValue("table107_DateOfIssueNID");
				LOS_EG.mLogger.info("expiryDate1" +expiryDate1);
				String formatExp1=expiryDate1.replace('/', '-');
				LOS_EG.mLogger.info("issueDate" +formatIss);
				LOS_EG.mLogger.info("issueDate" +formatIss);
				inputXML = inputXML.substring(0, inputXML.indexOf("<idType>")) + "<idType>" +  idType
						+ inputXML.substring(inputXML.indexOf("</idType>"), inputXML.length());
				inputXML = inputXML.substring(0, inputXML.indexOf("<idNumber>")) + "<idNumber>" +  ifr.getValue("Applicant_Passport_Number")
						+ inputXML.substring(inputXML.indexOf("</idNumber>"), inputXML.length());
				inputXML = inputXML.substring(0, inputXML.indexOf("<nationalId>")) + "<nationalId>" +  blnk
				+ inputXML.substring(inputXML.indexOf("</nationalId>"), inputXML.length());
				inputXML = inputXML.substring(0, inputXML.indexOf("<nationalOrOtherIdType>")) + "<nationalOrOtherIdType>" +  idSource
				+ inputXML.substring(inputXML.indexOf("</nationalOrOtherIdType>"), inputXML.length());
				inputXML = inputXML.substring(0, inputXML.indexOf("<issueDate>")) + "<issueDate>" +  formatIss1
						+ inputXML.substring(inputXML.indexOf("</issueDate>"), inputXML.length());
				inputXML = inputXML.substring(0, inputXML.indexOf("<expiryDate>")) + "<expiryDate>" +  formatExp1
						+ inputXML.substring(inputXML.indexOf("</expiryDate>"), inputXML.length());
			}
			else
			{
				inputXML = inputXML.substring(0, inputXML.indexOf("<idNumber>")) + "<idNumber>" +  ifr.getValue("Applicant_National_ID")
			+ inputXML.substring(inputXML.indexOf("</idNumber>"), inputXML.length());
				inputXML = inputXML.substring(0, inputXML.indexOf("<nationalId>")) + "<nationalId>" +  ifr.getValue("Applicant_National_ID")
				+ inputXML.substring(inputXML.indexOf("</nationalId>"), inputXML.length());
				inputXML = inputXML.substring(0, inputXML.indexOf("<issueDate>")) + "<issueDate>" +  formatIss
				+ inputXML.substring(inputXML.indexOf("</issueDate>"), inputXML.length());
		inputXML = inputXML.substring(0, inputXML.indexOf("<expiryDate>")) + "<expiryDate>" +  formatExp
				+ inputXML.substring(inputXML.indexOf("</expiryDate>"), inputXML.length());
			}
		
			
			/*String isDate=issueDate.split("/")[2]+"/"+issueDate.split("/")[1]+"/"+issueDate.split("/")[0];
			Date d2= new SimpleDateFormat("dd/MM/yyyy").parse(isDate);
			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			String formattedIssueDate= df1.format(d2);*/
			//LOS_EG.mLogger.info("formattedIssueDate" +formattedIssueDate);
		
			/*String expDate=expiryDate.split("/")[2]+"/"+expiryDate.split("/")[1]+"/"+expiryDate.split("/")[0];
			Date d3= new SimpleDateFormat("dd/MM/yyyy").parse(expDate);
			DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
			String formattedExpDate= df2.format(d3);
			LOS_EG.mLogger.info("formattedExpDate" +formattedExpDate);*/
			String genderCode=(String)ifr.getValue("Applicant_Gender");
			inputXML = inputXML.substring(0, inputXML.indexOf("<CustNIN>")) + "<CustNIN>" +  ifr.getValue("Applicant_National_ID")
			+ inputXML.substring(inputXML.indexOf("</CustNIN>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<applicantId>")) + "<applicantId>" +  wi_name
			+ inputXML.substring(inputXML.indexOf("</applicantId>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<productType>")) + "<productType>" +  productCode
			+ inputXML.substring(inputXML.indexOf("</productType>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<applicationDate>")) + "<applicationDate>" +  currdate
					+ inputXML.substring(inputXML.indexOf("</applicationDate>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<requestedAmount>")) + "<requestedAmount>" + (String)ifr.getTableCellValue("table117", 0, 60)
					+ inputXML.substring(inputXML.indexOf("</requestedAmount>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<requestedRate>")) + "<requestedRate>" + (String)ifr.getTableCellValue("table117", 0, 2)
					+ inputXML.substring(inputXML.indexOf("</requestedRate>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<requestedTerm>")) + "<requestedTerm>" +  (String)ifr.getValue("Loan_Tenor")
					+ inputXML.substring(inputXML.indexOf("</requestedTerm>"), inputXML.length());
			
					
			inputXML = inputXML.substring(0, inputXML.indexOf("<firstName>")) + "<firstName>" +  ifr.getValue("Applicant_Name")
					+ inputXML.substring(inputXML.indexOf("</firstName>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<gender>")) + "<gender>" +  genderCode.toUpperCase()
					+ inputXML.substring(inputXML.indexOf("</gender>"), inputXML.length());
			
			
			inputXML = inputXML.substring(0, inputXML.indexOf("<netAmount>")) + "<netAmount>" +  (String)ifr.getTableCellValue("table105", 0, 27)
					+ inputXML.substring(inputXML.indexOf("</netAmount>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<currency>")) + "<currency>" +  (String)ifr.getTableCellValue("table105", 0, 2)
					+ inputXML.substring(inputXML.indexOf("</currency>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<city>")) + "<city>" +  (String)ifr.getTableCellValue("table108", 0, 6)
					+ inputXML.substring(inputXML.indexOf("</city>"), inputXML.length());
			inputXML= inputXML.substring(0, inputXML.indexOf("<dateOfBirth>")) + "<dateOfBirth>" +  dateOfBirth
			+ inputXML.substring(inputXML.indexOf("</dateOfBirth>"), inputXML.length());
			LOS_EG.mLogger.info("Final Loan fetchCreditScore Request IS "+inputXML);
//			String responseXML = SocketConnector.getSocketResponse(inputXML,inputDetails.split("~")[1], inputDetails.split("~")[2]);

			String responseXML=getRequestURL(inputXML,ifr);
			
			//LOS_EG.mLogger.info("Response for fetchCreditScore  IS "+responseXML);
			LOS_EG.mLogger.info("Response for iScore  IS before Replacing "+responseXML);
			responseXML=responseXML.replace("&lt;", "<");
			responseXML=responseXML.replace("&gt;", ">");
			LOS_EG.mLogger.info("Response for iScore  IS "+responseXML);
			XMLParser xmlParser = new XMLParser();
			xmlParser.setInputXML(responseXML);
			if (!"00000".equalsIgnoreCase(xmlParser.getValueOf("Code")))
			{
				return "false~Error in fetching data from middleware - "+xmlParser.getValueOf("Desc");
			}
			else
			{
				LOS_EG.mLogger.info("score is  "+xmlParser.getValueOf("score"));
				//ifr.setValue("Credit_Score_Rating", xmlParser.getValueOf("SCORE"));
			}
			//return IntegrationParser.parseLoanSummary(responseXML, ifr); 8june
			return IntegrationParser.parseCreditScore(responseXML, ifr);
		}
		catch(Exception e)
		{
			LOS_EG.mLogger.info("LoanSummary error is" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
	}
	public static String IScoreFetch(IFormReference ifr,String buttonID) throws IOException 
	{
		if(getIScoreIntegrationType().equalsIgnoreCase("OLD"))
		{
			return IScoreFetchOld(ifr,buttonID);
		}
		else
		{
			return IScoreFetchNew(ifr,buttonID);
		}
	}
	
	
	public static String IScoreFetchNew(IFormReference ifr,String buttonID) throws IOException {
		try {
			LOS_EG.mLogger.info("inside IScoreFetch");
			String inputDetails = getInputXML("ISCORE_LIVE_INVOKER_NEW");
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
			String inputXML = inputDetails.split("~")[0];
			//"RLOS"+ Long.toString(System.nanoTime())
			String productCode=(String)ifr.getValue("Product_Type");
			String nid=(String)ifr.getValue("Applicant_National_ID");
			String nationalityCode=(String)ifr.getValue("Applicant_Nationality");
			LOS_EG.mLogger.info("Nationality Code "+nationalityCode);
			String sQuery="SELECT ISSUANCE_AUTH_CODE FROM NG_ELOS_MAST_ISSUANCE_AUTH_CODE WHERE COUNTRY_CODE='"+nationalityCode+"'";
			LOS_EG.mLogger.info("Query For Iscore " +sQuery);
			String sQuery1="SELECT IscoreNationalityCode FROM NG_ELOS_MAST_NATIONALITY_CODES WHERE COUNTRY_CODE='"+nationalityCode+"'";
			LOS_EG.mLogger.info("Query For Iscore new " +sQuery1);
			List IntrstList1 = ifr.getDataFromDB(sQuery1);
			String nationality1="";
			if(IntrstList1.size()>0)
			{
				List<String>tempLIST=(List)IntrstList1.get(0);
				nationality1=tempLIST.get(0);
				LOS_EG.mLogger.info("nationality code" +nationality1);
				
			}
			List IntrstList = ifr.getDataFromDB(sQuery);
			String idSource="";
			if(IntrstList.size()>0)
			{
				List<String>tempLIST=(List)IntrstList.get(0);
				idSource=tempLIST.get(0);
				LOS_EG.mLogger.info("auth code" +idSource);
				
			}
			if(productCode.equalsIgnoreCase("PL") || productCode.equalsIgnoreCase("Personal Finance"))
			{
				productCode="014";
			}
			else if(productCode.equalsIgnoreCase("CC") || productCode.equalsIgnoreCase("Covered Card"))
			{
				productCode="003";
			}
			else 
			{
				productCode="001";
			}
			String dateFormat="dd-MMM-yyyy";
			String currdate= new SimpleDateFormat(dateFormat).format(new Date());
			
			String genderCode=(String)ifr.getValue("Applicant_Gender");
			if(genderCode.equalsIgnoreCase("Female") || genderCode.equalsIgnoreCase("Female"))
			{
				genderCode="002";
			}
			
			else 
			{
				genderCode="001";
			}
			String tempDOB=(String)ifr.getValue("Applicant_DOB");
			String applicationType=(String)ifr.getValue("Application_Type");
		/*	if(applicationType.equalsIgnoreCase("Single") || applicationType.equalsIgnoreCase("Single"))
			{
				applicationType="PERSON";
			}
			else
			{
				applicationType="COMPANY";
			}*/
			LOS_EG.mLogger.info("DOB" +tempDOB);
			String DOB=tempDOB.split("/")[2]+"/"+tempDOB.split("/")[1]+"/"+tempDOB.split("/")[0];
			Date d1= new SimpleDateFormat("dd/MM/yyyy").parse(DOB);
			DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			String dateOfBirth= df.format(d1);
			LOS_EG.mLogger.info("DateOfBirth" +dateOfBirth);
			if(nid.length()==14)//chnges by Uroosa
			{
			inputXML = inputXML.substring(0, inputXML.indexOf("<CustNIN>")) + "<CustNIN>"+ifr.getValue("Applicant_National_ID")+ inputXML.substring(inputXML.indexOf("</CustNIN>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<IdentifierType>")) + "<IdentifierType>"+"003"+ inputXML.substring(inputXML.indexOf("</IdentifierType>"), inputXML.length());			
			inputXML = inputXML.substring(0, inputXML.indexOf("<IdentifierValue>")) + "<IdentifierValue>"+ifr.getValue("Applicant_National_ID")+ inputXML.substring(inputXML.indexOf("</IdentifierValue>"), inputXML.length());				
			
			}
			else
			{
				inputXML = inputXML.substring(0, inputXML.indexOf("<CustNIN>")) + "<CustNIN>"+ifr.getValue("Applicant_Passport_Number")+ inputXML.substring(inputXML.indexOf("</CustNIN>"), inputXML.length());
				inputXML = inputXML.substring(0, inputXML.indexOf("<IdentifierType>003")) + "<IdentifierType>"+idSource+ inputXML.substring(inputXML.indexOf("</IdentifierType>"), inputXML.length());
				inputXML = inputXML.substring(0, inputXML.indexOf("<IdentifierValue>")) + "<IdentifierValue>"+ifr.getValue("Applicant_Passport_Number")+ inputXML.substring(inputXML.indexOf("</IdentifierValue>"), inputXML.length());				
				
			}//changes end
			
			
			inputXML = inputXML.substring(0, inputXML.indexOf("<ReferenceNumber>")) + "<ReferenceNumber>"+"RLOS"+ Long.toString(System.nanoTime())+ inputXML.substring(inputXML.indexOf("</ReferenceNumber>"), inputXML.length());			
			inputXML = inputXML.substring(0, inputXML.indexOf("<InquiryLoanType>")) + "<InquiryLoanType>"+productCode+ inputXML.substring(inputXML.indexOf("</InquiryLoanType>"), inputXML.length());			
			inputXML = inputXML.substring(0, inputXML.indexOf("<InquiryType>")) + "<InquiryType>"+"NEW_INQUIRY"+ inputXML.substring(inputXML.indexOf("</InquiryType>"), inputXML.length());			
			/*inputXML = inputXML.substring(0, inputXML.indexOf("<SubjectType>")) + "<SubjectType>"+applicationType+ inputXML.substring(inputXML.indexOf("</SubjectType>"), inputXML.length());			
			*/LOS_EG.mLogger.info("DateOfBirth" +dateOfBirth);
			inputXML = inputXML.substring(0, inputXML.indexOf("<DateOfBirth>")) + "<DateOfBirth>"+dateOfBirth+ inputXML.substring(inputXML.indexOf("</DateOfBirth>"), inputXML.length());			
			LOS_EG.mLogger.info("DateOfBirth" +dateOfBirth);
			//inputXML = inputXML.substring(0, inputXML.indexOf("<Gender>")) + "<Gender>"+genderCode+ inputXML.substring(inputXML.indexOf("</Gender>"), inputXML.length());			
			//inputXML = inputXML.substring(0, inputXML.indexOf("<IdentifierType>")) + "<IdentifierType>"+"003"+ inputXML.substring(inputXML.indexOf("</IdentifierType>"), inputXML.length());			
			inputXML = inputXML.substring(0, inputXML.indexOf("<Name>")) + "<Name>"+ifr.getValue("Applicant_Name")+ inputXML.substring(inputXML.indexOf("</Name>"), inputXML.length());			
			inputXML = inputXML.substring(0, inputXML.indexOf("<Nationality>")) + "<Nationality>"+nationality1+ inputXML.substring(inputXML.indexOf("</Nationality>"), inputXML.length());			
			//inputXML = inputXML.substring(0, inputXML.indexOf("<IdentifierValue>")) + "<IdentifierValue>"+ifr.getValue("Applicant_National_ID")+ inputXML.substring(inputXML.indexOf("</IdentifierValue>"), inputXML.length());				
			LOS_EG.mLogger.info("Final New iScore Request IS "+inputXML);
//			String responseXML = SocketConnector.getSocketResponse(inputXML,inputDetails.split("~")[1], inputDetails.split("~")[2]);

			String responseXML=getRequestURL(inputXML,ifr);
			
			LOS_EG.mLogger.info("Response for new iScore  IS before Replacing "+responseXML);
			responseXML=responseXML.replace("&lt;", "<");
			responseXML=responseXML.replace("&gt;", ">");
			responseXML=responseXML.replace("&#xd;", "");
			LOS_EG.mLogger.info("Response for New iScore  IS "+responseXML);
			return IntegrationParser.parseiScoreNew(responseXML, ifr,buttonID);
			
		} catch (Exception e) {

			LOS_EG.mLogger.info("New Iscore error is" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
	}
	
	public static String IScoreFetchOld(IFormReference ifr,String buttonID) throws IOException {
		try {
			LOS_EG.mLogger.info("inside IScoreFetch");
			String inputDetails = getInputXML("ISCORE_LIVE_INVOKER");
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
			String inputXML = inputDetails.split("~")[0];
			//"RLOS"+ Long.toString(System.nanoTime())
			String productCode=(String)ifr.getValue("Product_Type");
			if(productCode.equalsIgnoreCase("PL") || productCode.equalsIgnoreCase("Personal Finance"))
			{
				productCode="014";
			}
			else if(productCode.equalsIgnoreCase("CC") || productCode.equalsIgnoreCase("Covered Card"))
			{
				productCode="003";
			}
			else 
			{
				productCode="001";
			}
			String dateFormat="dd/MM/yyyy";
			String currdate= new SimpleDateFormat(dateFormat).format(new Date());
			
			String genderCode=(String)ifr.getValue("Applicant_Gender");
			if(genderCode.equalsIgnoreCase("Female") || genderCode.equalsIgnoreCase("Female"))
			{
				genderCode="002";
			}
			
			else 
			{
				genderCode="001";
			}
			String tempDOB=(String)ifr.getValue("Applicant_DOB");
			String DOB=tempDOB.split("/")[2]+"/"+tempDOB.split("/")[1]+"/"+tempDOB.split("/")[0];
			String nid=(String)ifr.getValue("Applicant_National_ID");
			String nationalityCode=(String)ifr.getValue("Applicant_Nationality");
			LOS_EG.mLogger.info("Nationality Code "+nationalityCode);
			String sQuery="SELECT ISSUANCE_AUTH_CODE FROM NG_ELOS_MAST_ISSUANCE_AUTH_CODE WHERE COUNTRY_CODE='"+nationalityCode+"'";
			LOS_EG.mLogger.info("Query For Iscore " +sQuery);
			List IntrstList = ifr.getDataFromDB(sQuery);
			String idSource="";
			if(IntrstList.size()>0)
			{
				List<String>tempLIST=(List)IntrstList.get(0);
				idSource=tempLIST.get(0);
				LOS_EG.mLogger.info("auth code" +idSource);
				
			}
			inputXML = inputXML.substring(0, inputXML.indexOf("<CustNIN>")) + "<CustNIN>" +  ifr.getValue("Applicant_National_ID")
			+ inputXML.substring(inputXML.indexOf("</CustNIN>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<RequestId>")) + "<RequestId>"+"RLOS"+ Long.toString(System.nanoTime())+ inputXML.substring(inputXML.indexOf("</RequestId>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<ProductCode>")) + "<ProductCode>"+productCode+ inputXML.substring(inputXML.indexOf("</ProductCode>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<ApplicationNumber>")) + "<ApplicationNumber>"+ifr.getObjGeneralData().getM_strProcessInstanceId().split("-")[2]+ inputXML.substring(inputXML.indexOf("</ApplicationNumber>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<Date>")) + "<Date>"+currdate+ inputXML.substring(inputXML.indexOf("</Date>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<LastName>")) + "<LastName>"+ifr.getValue("Applicant_Name")+ inputXML.substring(inputXML.indexOf("</LastName>"), inputXML.length());
			
			if(nid.length()==14)//chnges by Uroosa
			{
			inputXML = inputXML.substring(0, inputXML.indexOf("<IdValue>")) + "<IdValue>"+ifr.getValue("Applicant_National_ID")+ inputXML.substring(inputXML.indexOf("</IdValue>"), inputXML.length());
			
			}
			else
			{
				inputXML = inputXML.substring(0, inputXML.indexOf("<IdValue>")) + "<IdValue>"+ifr.getValue("Applicant_Passport_Number")+ inputXML.substring(inputXML.indexOf("</IdValue>"), inputXML.length());
				inputXML = inputXML.substring(0, inputXML.indexOf("<IdSource>003")) + "<IdSource>"+idSource+ inputXML.substring(inputXML.indexOf("</IdSource>"), inputXML.length());
				
			}//changes end
			inputXML = inputXML.substring(0, inputXML.indexOf("<GenderCode>")) + "<GenderCode>"+genderCode+ inputXML.substring(inputXML.indexOf("</GenderCode>"), inputXML.length());
			inputXML = inputXML.substring(0, inputXML.indexOf("<DOB>")) + "<DOB>"+DOB+ inputXML.substring(inputXML.indexOf("</DOB>"), inputXML.length());
			
			LOS_EG.mLogger.info("Final iScore Request IS "+inputXML);
//			String responseXML = SocketConnector.getSocketResponse(inputXML,inputDetails.split("~")[1], inputDetails.split("~")[2]);

			String responseXML=getRequestURL(inputXML,ifr);
			
			LOS_EG.mLogger.info("Response for iScore  IS before Replacing "+responseXML);
			responseXML=responseXML.replace("&lt;", "<");
			responseXML=responseXML.replace("&gt;", ">");
			LOS_EG.mLogger.info("Response for iScore  IS "+responseXML);
			return IntegrationParser.parseiScoreOld(responseXML, ifr,buttonID);
		} catch (Exception e) {

			LOS_EG.mLogger.info("Iscore error is" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
	}
//---------------------------------------Anum Code Start Here---------------------------------------------	


public static String Send_SMS(IFormReference ifr, String eventName) {
		LOS_EG.mLogger.info("inside Send SMS for "+ifr.getObjGeneralData().getM_strProcessInstanceId()+" $$$ Event - "+eventName);
		
		try {
			LOS_EG.mLogger.info("new logger");
			String inputDetails = getInputXML("Send_SMS_RQ");
			LOS_EG.mLogger.info("Default input XML for Send_SMS_RQ is" + inputDetails.split("~")[0]);
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
			String inputXML = inputDetails.split("~")[0];

			String mobile = (String) ifr.getTableCellValue("Q_NG_ELOS_CustomerDeatils", 0, 8);

			LOS_EG.mLogger.info("mobile number" + mobile);
			inputXML = inputXML.substring(0, inputXML.indexOf("<ToAddr>")) + "<ToAddr>" + mobile
					+ inputXML.substring(inputXML.indexOf("</ToAddr>"), inputXML.length());

			LOS_EG.mLogger.info("inside send sms value of input xml" + inputXML);

			String activityName = ifr.getActivityName();
			LOS_EG.mLogger.info("inside send sms activity name" + activityName);
			
			String pType = (String)ifr.getValue("Product_Type");
			
			
			if(eventName.equalsIgnoreCase("FinalApprove"))
			{
				if (pType.equalsIgnoreCase("CC")) 
				{
					inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>" + "Dear customer, "
							+ "Congratulations; your Covered Card request is approved with amount of EGP  "+LOS_CommonMethods.getAmountComma((String)ifr.getValue("Credit_Approved_Amount"))+" , your card had been dispatched to you"
							+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				}
//				else 
//				{
//					inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>" + "Dear customer, "
//							+ "Congratulations, your Finance had been booked with amount of EGP "+LOS_CommonMethods.getAmountComma((String)ifr.getValue("Credit_Approved_Amount"))
//							+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
//				}
			}
			else if(eventName.equalsIgnoreCase("InitialApprove"))
			{
				if (pType.equalsIgnoreCase("CC")) 
				{
					inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>" + "Dear customer, "
							+ "Congratulation your Credit Card request had been initially approved. We will keep you informed once card is printed"
							+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				}
				else 
				{
					inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>" + "Dear customer, "
							+ "Congratulations, your Finance Request had been initially approved, one of our bank staff will contact you shortly to fulfill remaining documents"
							+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				}
			}
			else if(eventName.equalsIgnoreCase("Reject"))
			{
				if (pType.equalsIgnoreCase("CC")) 
				{
					inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>" + "Dear customer, "
							+ "We are sorry to inform you that your Covered Card request was not approved."
							+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				}
				else
				{
					inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>" + "Dear customer, "
							+ "We are sorry to inform you that your finance request was not approved."
							+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				}
			}
			
			LOS_EG.mLogger.info("inputXML for Send SMS : " + inputXML);
			
//			String responseXML=SocketConnector.getSocketResponse(inputXML,
//			inputDetails.split("~")[1], inputDetails.split("~")[2]);

			String responseXML=getRequestURL(inputXML,ifr);
			
			/*String responseXML = "<?xml version=\"1.0\"><AUB_MESSAGE xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"SEND_SMS_RS.xsd\"><RESPONSE_MESSAGE><ResponseHeader><MsgType>Response</MsgType><ReqID></ReqID><ChSysID></ChSysID><FuncID>SEND_SMS</FuncID><UserID></UserID><TxnRef></TxnRef><TxnDate></TxnDate><Cust></Cust><TxnStatus></TxnStatus><SessLang></SessLang><VerNo></VerNo><SessToken></SessToken><Return><Code>0000</Code><Desc>Success</Desc></Return></ResponseHeader><ResponseBody><SEND_SMS_RS><MessageRefNo></MessageRefNo><MobileNo></MobileNo></SEND_SMS_RS></ResponseBody></RESPONSE_MESSAGE></AUB_MESSAGE>";
			LOS_EG.mLogger.info("inside send sms response xml" + responseXML);
			XMLParser xmlParser = new XMLParser();

			xmlParser.setInputXML(responseXML);
			if ("0000".equalsIgnoreCase(xmlParser.getValueOf("Code"))
					&& "SUCCESS".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) {
				return "true~SMS send Successful";
			} else {
				return "false~SMS not send ";
			}*/

		}

		catch (IOException e) {
			LOS_EG.mLogger.info("#####Error in getting input XML for send_sms_RQ Please check getInputXML Method#");
			e.printStackTrace();
		}
		return "";
		
	}
	
	public static String Old_Send_SMS_Old(IFormReference ifr) {
		LOS_EG.mLogger.info("inside Send_SMS");
		
		try {
			LOS_EG.mLogger.info("new logger");
			String inputDetails = getInputXML("Send_SMS_RQ");
			LOS_EG.mLogger.info("Default input XML for Send_SMS_RQ is" + inputDetails.split("~")[0]);
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
			String inputXML = inputDetails.split("~")[0];

			String mobile = (String) ifr.getTableCellValue("Q_NG_ELOS_CustomerDeatils", 0, 8);

			LOS_EG.mLogger.info("mobile number" + mobile);
			inputXML = inputXML.substring(0, inputXML.indexOf("<ToAddr>")) + "<ToAddr>" + mobile
					+ inputXML.substring(inputXML.indexOf("</ToAddr>"), inputXML.length());

			LOS_EG.mLogger.info("inside send sms value of input xml" + inputXML);

			String activityName = ifr.getActivityName();
			LOS_EG.mLogger.info("inside send sms activity name" + activityName);

			if (activityName.equalsIgnoreCase("RCR_Credit_TL")) {

				// String workitem_name = (String) ifr.getValue("WorkItemName");
				if (!(ifr.getValue("Product_Type") == "CC")) {
					inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>" + "Dear customer, "
							+ "Congratulations, your Finance Request had been initially approved, one of our bank staff will contact you shortly to fulfill remaining documents"
							+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				}
				else 
				{
					inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>" + "Dear customer, "
							+ "Congratulation your Credit Card request had been initially approved. We will keep you informed once card is printed"
							+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				}

				LOS_EG.mLogger.info("inside send sms,if condition" + inputXML);
			}

			if (activityName.equalsIgnoreCase("LPU_Checker")) {

				if (!(ifr.getValue("Product_Type") == "CC")) {
					inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>" + "Dear customer, "
							+ "Congratulations, your Finance had been booked with amount of EGP "+ifr.getValue("Credit_Approved_Amount")
							+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				}
				else 
				{
					inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>" + "Dear customer, "
							+ "Congratulations; your Covered Card request is approved with amount of EGP  "+ifr.getValue("Credit_Approved_Amount")+" , your card had been dispatched to you"
							+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				}

			}
			if(activityName.contains("RCR"))
			{
				LOS_EG.mLogger.info("######## Contains RCR Yes");
				LOS_EG.mLogger.info("######## Contains RCR Yes Product Type "+ifr.getValue("Product_Type"));
				if (!(ifr.getValue("Product_Type") == "CC")) {
					inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>" + "Dear customer, "
							+ "We are sorry to inform you that your finance request was not approved after RCR"
							+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				}
				else 
				{
					inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>" + "Dear customer, "
							+ "We are sorry to inform you that your Covered Card request was not approved after RCR"
							+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				}	
			}

//			 String responseXML=SocketConnector.getSocketResponse(inputXML,
//			 inputDetails.split("~")[1], inputDetails.split("~")[2]);

			String responseXML=getRequestURL(inputXML,ifr);
			
			/*String responseXML = "<?xml version=\"1.0\"><AUB_MESSAGE xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"SEND_SMS_RS.xsd\"><RESPONSE_MESSAGE><ResponseHeader><MsgType>Response</MsgType><ReqID></ReqID><ChSysID></ChSysID><FuncID>SEND_SMS</FuncID><UserID></UserID><TxnRef></TxnRef><TxnDate></TxnDate><Cust></Cust><TxnStatus></TxnStatus><SessLang></SessLang><VerNo></VerNo><SessToken></SessToken><Return><Code>0000</Code><Desc>Success</Desc></Return></ResponseHeader><ResponseBody><SEND_SMS_RS><MessageRefNo></MessageRefNo><MobileNo></MobileNo></SEND_SMS_RS></ResponseBody></RESPONSE_MESSAGE></AUB_MESSAGE>";
			LOS_EG.mLogger.info("inside send sms response xml" + responseXML);
			XMLParser xmlParser = new XMLParser();

			xmlParser.setInputXML(responseXML);
			if ("0000".equalsIgnoreCase(xmlParser.getValueOf("Code"))
					&& "SUCCESS".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) {
				return "true~SMS send Successful";
			} else {
				return "false~SMS not send ";
			}*/

		}

		catch (IOException e) {
			LOS_EG.mLogger.info("#####Error in getting input XML for send_sms_RQ Please check getInputXML Method#");
			e.printStackTrace();
		}
		return "";
		
	}

	public static String Email(IFormReference ifr) {
		LOS_EG.mLogger.info("inside email");
		try {
			
			if(true) //Ajay 15Dec
			{
				return "true"; 
			}

			String inputDetails = getInputXML("Email_RQ");
			LOS_EG.mLogger.info("Default input XML for Send_Email_RQ is" + inputDetails.split("~")[0]);
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
			String inputXML = inputDetails.split("~")[0];
			String activityName = ifr.getActivityName();
			String Sub_Product_Type = (String) ifr.getValue("Sub_Product_Type");
			LOS_EG.mLogger.info("Sub_Product_Type" + Sub_Product_Type);
			String Product_Type = (String) ifr.getValue("Product_Type");
			LOS_EG.mLogger.info("Product_Type" + Product_Type);
			String workitem_name = (String) ifr.getValue("WorkItemName");
			LOS_EG.mLogger.info("workitem_name" + workitem_name);
			String CustomerLoanAccountNumber = (String) ifr.getValue("CustomerLoanAccountNumber");
			LOS_EG.mLogger.info("CustomerLoanAccountNumber" + CustomerLoanAccountNumber);
			String Collateral_Value = (String) ifr.getTableCellValue("table129", 0, 6);
			String customer_email= (String) ifr.getTableCellValue("Q_NG_ELOS_CustomerDeatils", 0, 9);
			LOS_EG.mLogger.info("Collateral_Value" + Collateral_Value);
			LOS_EG.mLogger.info("customer_email " + customer_email);

			String Credit_Card_Limit = (String) ifr.getValue("Credit_Card_Limit");
			LOS_EG.mLogger.info("Credit_Card_Limit" + Credit_Card_Limit);

			String CC_expiry_date = (String) ifr.getValue("Credit card expiry date");
			LOS_EG.mLogger.info("Credit card expiry date" + CC_expiry_date);

			String loan_amt = (String) ifr.getValue("Req_Loan_Amt");
			LOS_EG.mLogger.info("loan_amt" + loan_amt);

			String interest_rate = (String) ifr.getTableCellValue("Q_NG_ELOS_ProductDetailsInfo",0,2);
			LOS_EG.mLogger.info("interest_rate" + interest_rate);
			
			String inst_amt = (String) ifr.getTableCellValue("Q_NG_ELOS_ProductDetailsInfo",0,24);
			LOS_EG.mLogger.info("inst_amt" + inst_amt);
			String suffix = (String) ifr.getTableCellValue("table135",0,3);
			LOS_EG.mLogger.info("inst_amt" + suffix);

			String Nominated_CR = (String) ifr.getTableCellValue("table135",0,4);
			LOS_EG.mLogger.info("Nominated_CR" + Nominated_CR);

 			String Nominated_DR = (String) ifr.getTableCellValue("Q_NG_ELOS_LPU",0,5);
			LOS_EG.mLogger.info("Nominated_DR" + Nominated_DR);

			String Nominated_Charge = (String) ifr.getTableCellValue("Q_NG_ELOS_LPU",0,6);
			LOS_EG.mLogger.info("Nominated_Charge" + Nominated_Charge);

			String branch_code=(String) ifr.getValue("Branch_Code");
			LOS_EG.mLogger.info("branch_code" + branch_code);

			if (activityName.equalsIgnoreCase("LOS_RCR_Credit_TL")) {
				inputXML = inputXML.substring(0, inputXML.indexOf("<ToAddr>")) + "<ToAddr>" + "Nihal.mohamed@ahliunited.com"
						+ inputXML.substring(inputXML.indexOf("</ToAddr>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Subject>")) + "<Subject>" + "Approved case : "+ workitem_name + ""
						+ inputXML.substring(inputXML.indexOf("</Subject>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>"
						+ "Dear Risk control department,\n" + "Please be notified that application number "
						+ workitem_name + " has been approved and routed to the frontline for fulfilment.\n" + "Regards"
						+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());

				LOS_EG.mLogger.info("inside send email value of input xml" + inputXML);
			}

			 if (activityName.equalsIgnoreCase("LPU_Checker") && Sub_Product_Type.equalsIgnoreCase("Secured")
					&& Product_Type.equalsIgnoreCase("PL") && Product_Type.equalsIgnoreCase("AL"))// 2
			{
				inputXML = inputXML.substring(0, inputXML.indexOf("<ToAddr>")) + "<ToAddr>" + "tamer.tantawy@ahliunited.com"
						+ inputXML.substring(inputXML.indexOf("</ToAddr>"), inputXML.length());// Subject

				inputXML = inputXML.substring(0, inputXML.indexOf("<Subject>")) + "<Subject>" + "Approved case : "
						+ workitem_name + "" + inputXML.substring(inputXML.indexOf("</Subject>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>"
						+ "Kindly amend the below collateral principle & profit on account "+branch_code+" "
						+ workitem_name + " " + suffix + " "
						+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				LOS_EG.mLogger.info("inside send email value of input xml" + inputXML);
			}

			

			 if (activityName.equalsIgnoreCase("LPU_Checker") && Sub_Product_Type.equalsIgnoreCase("Secured")
					&& Product_Type.equalsIgnoreCase("PL") && Product_Type.equalsIgnoreCase("AL"))// 2
			{
				inputXML = inputXML.substring(0, inputXML.indexOf("<ToAddr>")) + "<ToAddr>" + "tamer.tantawy@ahliunited.com"
						+ inputXML.substring(inputXML.indexOf("</ToAddr>"), inputXML.length());// Subject

				inputXML = inputXML.substring(0, inputXML.indexOf("<Subject>")) + "<Subject>" + "Approved case : "
						+ workitem_name + "" + inputXML.substring(inputXML.indexOf("</Subject>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>"
						+ "Please amend the below data on suffix no. (finance suffix no.)\n"
						+ "1.	Sundry A. Code (BC for unsecured AI for secured)\n" + "2.	St. Frequency: V31\n"
						+ "3.	Nominated CR / Nominated DR and Nominated Charge to be" + Nominated_CR + "/"
						+ Nominated_DR + "/" + Nominated_Charge + "\n" + "4.	Special Condition : 105 / 162\n"
						+ "5.	Sector Code : 8312\n"
						+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				LOS_EG.mLogger.info("inside send email value of input xml" + inputXML);
			}

			 if (activityName.equalsIgnoreCase("LPU_Checker"))
			{
				inputXML = inputXML.substring(0, inputXML.indexOf("<ToAddr>")) + "<ToAddr>" + "tamer.tantawy@ahliunited.com"
						+ inputXML.substring(inputXML.indexOf("</ToAddr>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>"
						+ "Dear Branch ,  Please find below finance details \n"
						+ "Personal Finance Acc. # (Branch no) (account no.) (current account suffix)/ customer name \n"
						+ "	Finance amount                             EGP" + loan_amt + " \n" + "	Profit rate"
						+ interest_rate + "%\n" + "	Number of installment        XX Month\n"
						+ "	Installment  amount            EGP" + inst_amt + "(Monthly)\n"
						+ "	First repayment date           XX/XX/XXXX\n"
						+ "	Finance maturity date               XX/XX/XXXX ( last  installment )\n"
						+ "	Admin fees                                XXX\n"
						+ "	Hold amount                        \n" + "	Repayment Schedule as shown below:\n"
						+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Subject>")) + "<Subject>" + "Approved case : "
						+ workitem_name + "" + inputXML.substring(inputXML.indexOf("</Subject>"), inputXML.length());
			}

			 if (activityName.equalsIgnoreCase("RCCU_RiskLimit_Authorizer")
					&& Sub_Product_Type.equalsIgnoreCase("Secured") && Product_Type.equalsIgnoreCase("CC")) {
				inputXML = inputXML.substring(0, inputXML.indexOf("<ToAddr>")) + "<ToAddr>" + "Manal.magdy@ahliunited.com"
						+ inputXML.substring(inputXML.indexOf("</ToAddr>"), inputXML.length());// Subject

				inputXML = inputXML.substring(0, inputXML.indexOf("<Subject>")) + "<Subject>" + "Approved case : "
						+ workitem_name + "" + inputXML.substring(inputXML.indexOf("</Subject>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>"
						+ "Granting (MASTER/VISA) limit for EGP" + Credit_Card_Limit + "& Expiry date: "
						+ CC_expiry_date + " \n" + "Dear Card center, \n"
						+ "Reference to attached approval kindly proceed with issuance CC \n" + "Dear CPU Teams, \n"
						+ "Kindly set SAC to be AK and open Suffix \n" + "Dear LPU Teams, \n"
						+ "As Per approval, Kindly set the Collateral value for  EGP " + Collateral_Value
						+ "is placed via covered card issue \n"
						+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				LOS_EG.mLogger.info("inside send email value of input xml" + inputXML);
			}

			 if (activityName.equalsIgnoreCase("RCCU_RiskLimit_Authorizer")
					&& Sub_Product_Type.equalsIgnoreCase("Unsecured") && Product_Type.equalsIgnoreCase("CC")) {
				inputXML = inputXML.substring(0, inputXML.indexOf("<ToAddr>")) + "<ToAddr>" + "Manal.magdy@ahliunited.com"
						+ inputXML.substring(inputXML.indexOf("</ToAddr>"), inputXML.length());// Subject

				inputXML = inputXML.substring(0, inputXML.indexOf("<Subject>")) + "<Subject>" + "Approved case : "
						+ workitem_name + "" + inputXML.substring(inputXML.indexOf("</Subject>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>"
						+ "Granting (MASTER/VISA) limit for EGP" + Credit_Card_Limit + "& Expiry date:" + CC_expiry_date
						+ "\n" + "Dear Card center,\n"
						+ "Reference to attached approval kindly proceed with issuance CC\n" + "Dear CPU Teams,\n"
						+ "Kindly set SAC to be AJ and open Suffix\n"
						+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				LOS_EG.mLogger.info("inside send email value of input xml" + inputXML);
			}

			 if (activityName.equalsIgnoreCase("PB_Supervisor") || activityName.equalsIgnoreCase("Authorizer_DSU")
					|| activityName.equalsIgnoreCase("Legal_Dept") ||activityName.equalsIgnoreCase("Fraud_Dept")
					|| activityName.equalsIgnoreCase("Admin") || activityName.equalsIgnoreCase("Collection_Dept")
					|| activityName.equalsIgnoreCase("Compliance_Dept")
					|| activityName.equalsIgnoreCase("CRO_CRMH") || activityName.equalsIgnoreCase("Fulfillment_Docs"))
			{
				inputXML = inputXML.substring(0, inputXML.indexOf("<ToAddr>")) + "<ToAddr>" + "bassem.gerguis@ahliunited.com"
						+ inputXML.substring(inputXML.indexOf("</ToAddr>"), inputXML.length());// Subject

				inputXML = inputXML.substring(0, inputXML.indexOf("<Subject>")) + "<Subject>"
						+ "Update on application number : " + workitem_name + ""
						+ inputXML.substring(inputXML.indexOf("</Subject>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>"
						+ "Please be notified that application number " + workitem_name
						+ " has been approved from "+ifr.getActivityName()+" and routed to the next queue.\n" + "Regards"

						+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				LOS_EG.mLogger.info("inside send email value of input xml" + inputXML);
			}

			 if (activityName.equalsIgnoreCase("LPU_Maker") || activityName.equalsIgnoreCase("LPU_Checker")
					|| activityName.equalsIgnoreCase("CPU"))
			{
				inputXML = inputXML.substring(0, inputXML.indexOf("<ToAddr>")) + "<ToAddr>" + "tamer.tantawy@ahliunited.com"
						+ inputXML.substring(inputXML.indexOf("</ToAddr>"), inputXML.length());// Subject

				inputXML = inputXML.substring(0, inputXML.indexOf("<Subject>")) + "<Subject>"
						+ "Update on application number : " + workitem_name + ""
						+ inputXML.substring(inputXML.indexOf("</Subject>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>"
						+ "Please be notified that application number " + workitem_name
						+ " has been approved  from "+ifr.getActivityName()+" and routed to the next queue.\n" + "Regards"

						+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				LOS_EG.mLogger.info("inside send email value of input xml" + inputXML);
			}


			 if (activityName.equalsIgnoreCase("RCCU_RiskControl_Maker")
					|| activityName.equalsIgnoreCase("RCCU_RiskControl_Checker")
					|| activityName.equalsIgnoreCase("RCCU_RiskControl_Authorizer")
					|| activityName.equalsIgnoreCase("RCCU_RiskLimit_Maker")
					|| activityName.equalsIgnoreCase("RCCU_RiskLimit_Checker")
					|| activityName.equalsIgnoreCase("RCCU_RiskLimit_Authorizer"))

			{
				inputXML = inputXML.substring(0, inputXML.indexOf("<ToAddr>")) + "<ToAddr>" + "Manal.magdy@ahliunited.com"
						+ inputXML.substring(inputXML.indexOf("</ToAddr>"), inputXML.length());// Subject

				inputXML = inputXML.substring(0, inputXML.indexOf("<Subject>")) + "<Subject>"
						+ "Update on application number : " + workitem_name + ""
						+ inputXML.substring(inputXML.indexOf("</Subject>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>"
						+ "Please be notified that application number " + workitem_name
						+ " has been approved from "+ifr.getActivityName()+" and routed to the next queue.\n" + "Regards"

						+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				LOS_EG.mLogger.info("inside send email value of input xml" + inputXML);
			}

			if ( activityName.equalsIgnoreCase("RCR_PreScanner")
					|| activityName.equalsIgnoreCase("RCR_CreditAnalyst") || activityName.equalsIgnoreCase("RCR_Credit_TL")
					|| activityName.equalsIgnoreCase("Head_Of_Initiation") || activityName.equalsIgnoreCase("RCRH"))

			{
				inputXML = inputXML.substring(0, inputXML.indexOf("<ToAddr>")) + "<ToAddr>" + "Nihal.mohamed@ahliunited.com"
						+ inputXML.substring(inputXML.indexOf("</ToAddr>"), inputXML.length());// Subject

				inputXML = inputXML.substring(0, inputXML.indexOf("<Subject>")) + "<Subject>"
						+ "Update on application number : " + workitem_name + ""
						+ inputXML.substring(inputXML.indexOf("</Subject>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>"
						+ "Please be notified that application number " + workitem_name
						+ " has been approved from "+ifr.getActivityName()+" and routed to the next queue.\n" + "Regards"

						+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				LOS_EG.mLogger.info("inside send email value of input xml" + inputXML);
			}


			if (activityName.equalsIgnoreCase("Branch_Manager") )

			{
				inputXML = inputXML.substring(0, inputXML.indexOf("<ToAddr>")) + "<ToAddr>" + "nourhan.basyouni@ahliunited.com"
						+ inputXML.substring(inputXML.indexOf("</ToAddr>"), inputXML.length());// Subject

				inputXML = inputXML.substring(0, inputXML.indexOf("<Subject>")) + "<Subject>"
						+ "Update on application number : " + workitem_name + ""
						+ inputXML.substring(inputXML.indexOf("</Subject>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>"
						+ "Please be notified that application number " + workitem_name
						+ " has been approved from "+ifr.getActivityName()+" and routed to the next queue.\n" + "Regards"

						+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				LOS_EG.mLogger.info("inside send email value of input xml" + inputXML);
			}


			 if (activityName.equalsIgnoreCase("RCRH")) {
				inputXML = inputXML.substring(0, inputXML.indexOf("<ToAddr>")) + "<ToAddr>" + "Nihal.mohamed@ahliunited.com"
						+ inputXML.substring(inputXML.indexOf("</ToAddr>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Subject>")) + "<Subject>"
						+ "Update on application number : " + workitem_name + ""
						+ inputXML.substring(inputXML.indexOf("</Subject>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>" + "Dear customer,\n"
						+ "Please be notified that application number " + workitem_name
						+ "Finance has been approved. We will inform you once the finance amount is booked.\n" + "Regards"

						+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());
				LOS_EG.mLogger.info("inside send email value of input xml" + inputXML);
			}

			 if (activityName.equalsIgnoreCase("Introduction")) {
				inputXML = inputXML.substring(0, inputXML.indexOf("<ToAddr>")) + "<ToAddr>" + customer_email
						+ inputXML.substring(inputXML.indexOf("</ToAddr>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Subject>")) + "<Subject>"
						+ "Update on application number : " + workitem_name + ""
						+ inputXML.substring(inputXML.indexOf("</Subject>"), inputXML.length());

				inputXML = inputXML.substring(0, inputXML.indexOf("<Body>")) + "<Body>" + "Dear customer,\n+ "
						+ "Please be notified that application number " + workitem_name
						+ "finance amount has been booked.\n" + "Regards "
						+ inputXML.substring(inputXML.indexOf("</Body>"), inputXML.length());

				LOS_EG.mLogger.info("inside email value of input xml" + inputXML);
			}

//			 String responseXML=SocketConnector.getSocketResponse(inputXML,
//			 inputDetails.split("~")[1], inputDetails.split("~")[2]);

			 String responseXML=getRequestURL(inputXML,ifr);
			 
			 /*String responseXML = "<?xml version=\"1.0\"><AUB_MESSAGE xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"AUB_Schema.xsd\"><RESPONSE_MESSAGE><ResponseHeader><MsgType>Response</MsgType><ReqID></ReqID><ChSysID></ChSysID><FuncID>SEND_EMAIL</FuncID><UserID></UserID><TxnRef></TxnRef><TxnDate></TxnDate><Cust></Cust><TxnStatus></TxnStatus><SessLang></SessLang><VerNo></VerNo><SessToken></SessToken><Return><Code>0000</Code><Desc>Success</Desc></Return></ResponseHeader><ResponseBody><SEND_EMAIL_RS/></ResponseBody></RESPONSE_MESSAGE></AUB_MESSAGE>";
			LOS_EG.mLogger.info("inside send email response xml" + responseXML);
			XMLParser xmlParser = new XMLParser();

			xmlParser.setInputXML(responseXML);
			if ("00000".equalsIgnoreCase(xmlParser.getValueOf("Code"))
					&& "SUCCESS".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) {
				return "true~Email send Successful";
			} else {
				return "false~Email not send ";
			}*/
		} catch (IOException e) {
			LOS_EG.mLogger.info("#####Error in getting input XML for Email_RQ Please check getInputXML Method#");
			e.printStackTrace();
		}
		return "";
	}
	public static String getInputXML(String callName) throws IOException {
		Properties p = new Properties();
		p.load(new FileInputStream(System.getProperty("user.dir") + File.separator + "CustomConfig" + File.separator
				+ "IntegrationInputs.properties"));
		if (callName.equalsIgnoreCase("Call_Max_Eligibility") || callName.equalsIgnoreCase("CALL_EXCEPTION")) {

			return p.getProperty(callName) + "~" + p.getProperty("ENDPOINTURL");
		} else {
			String inputXML = p.getProperty(callName);
			String ServerIP = p.getProperty("SocketServerIP");
			String ServerPort = p.getProperty("SocketServerPort");
			
			return inputXML + "~" + ServerIP + "~" + ServerPort;
		}

	}
	
	public static String getLoanStartDate()
	{
		try
		{
			Properties p = new Properties();
			p.load(new FileInputStream(System.getProperty("user.dir") + File.separator + "CustomConfig" + File.separator
					+ "IntegrationInputs.properties"));
			
			String datePropFile = p.getProperty("LOAN_BOOK_STARTDATE");
			
			LOS_EG.mLogger.info("datePropFile : " + datePropFile);
			
			if(datePropFile==null || datePropFile.trim().equalsIgnoreCase("") || datePropFile.trim().equalsIgnoreCase("CURRENT_DATE"))
			{
				return LOS_CommonMethods.getCurrentDate("yyyyMMdd");
			}
			else
			{			
				return datePropFile;
			}	
		}
		catch (Exception e)
		{
			return LOS_CommonMethods.getCurrentDate("yyyyMMdd");
		}

	}
	public static String getFrequency(IFormReference ifr,String type)
	{
		if ("Monthly".equalsIgnoreCase(type)) {
			String Date = (String) ifr.getValue("table117_FirstInstallmentDate");
			if (Date.equalsIgnoreCase("") || Date.length() < 8) {
				Date = ifr.getTableCellValue("table117", 0, 3);
			}

			String day = Date.split("/")[2];
			return "V" + day + "~" + Date;
		} else if ("Quarterly".equalsIgnoreCase(type)) {
			String Date = (String) ifr.getValue("table117_FirstInstDateQuatrly");
			String month = Date.split("/")[1];
			String day = Date.split("/")[2];
			if ("01".equalsIgnoreCase(month) || "04".equalsIgnoreCase(month) || "07".equalsIgnoreCase(month)
					|| "10".equalsIgnoreCase(month)) {
				return "S" + day + "~" + Date;
			}
			if ("02".equalsIgnoreCase(month) || "05".equalsIgnoreCase(month) || "08".equalsIgnoreCase(month)
					|| "11".equalsIgnoreCase(month)) {
				return "T" + day + "~" + Date;
			}
			if ("03".equalsIgnoreCase(month) || "06".equalsIgnoreCase(month) || "09".equalsIgnoreCase(month)
					|| "12".equalsIgnoreCase(month)) {
				return "U" + day + "~" + Date;
			}

		} else if ("SemiAnnual".equalsIgnoreCase(type) || "Semi Annually".equalsIgnoreCase(type)) {
			String Date = (String) ifr.getValue("table117_FirstInstDateSemiAnnly");
			String month = Date.split("/")[1];
			String day = Date.split("/")[2];
			if ("01".equalsIgnoreCase(month) || "07".equalsIgnoreCase(month)) {
				return "M" + day + "~" + Date;
			} else if ("02".equalsIgnoreCase(month) || "08".equalsIgnoreCase(month)) {
				return "N" + day + "~" + Date;
			} else if ("03".equalsIgnoreCase(month) || "09".equalsIgnoreCase(month)) {
				return "O" + day + "~" + Date;
			} else if ("04".equalsIgnoreCase(month) || "10".equalsIgnoreCase(month)) {
				return "P" + day + "~" + Date;
			} else if ("05".equalsIgnoreCase(month) || "11".equalsIgnoreCase(month)) {
				return "Q" + day + "~" + Date;
			} else if ("06".equalsIgnoreCase(month) || "12".equalsIgnoreCase(month)) {
				return "R" + day + "~" + Date;
			}
		} else if ("Yearly".equalsIgnoreCase(type)) {
			String Date = (String) ifr.getValue("table117_FirstInstallmentDate2");
			String month = Date.split("/")[1];
			String day = Date.split("/")[2];
			if ("01".equalsIgnoreCase(month)) {
				return "A" + day + "~" + Date;
			} else if ("02".equalsIgnoreCase(month)) {
				return "B" + day + "~" + Date;
			} else if ("03".equalsIgnoreCase(month)) {
				return "C" + day + "~" + Date;
			} else if ("04".equalsIgnoreCase(month)) {
				return "D" + day + "~" + Date;
			} else if ("05".equalsIgnoreCase(month)) {
				return "E" + day + "~" + Date;
			} else if ("06".equalsIgnoreCase(month)) {
				return "F" + day + "~" + Date;
			} else if ("07".equalsIgnoreCase(month)) {
				return "G" + day + "~" + Date;
			} else if ("08".equalsIgnoreCase(month)) {
				return "H" + day + "~" + Date;
			} else if ("09".equalsIgnoreCase(month)) {
				return "I" + day + "~" + Date;
			} else if ("10".equalsIgnoreCase(month)) {
				return "J" + day + "~" + Date;
			} else if ("11".equalsIgnoreCase(month)) {
				return "K" + day + "~" + Date;
			} else if ("12".equalsIgnoreCase(month)) {
				return "L" + day + "~" + Date;
			}
		}

		return "";
	}	
	public static String getFrequency(IFormReference ifr,String type,String Date)
	{
		if ("Monthly".equalsIgnoreCase(type)) {
			//String Date = (String) ifr.getValue("table117_FirstInstallmentDate");
			if (Date.equalsIgnoreCase("") || Date.length() < 8) {
				Date = ifr.getTableCellValue("table117", 0, 3);
			}

			String day = Date.split("/")[2];
			return "V" + day + "~" + Date;
		} else if ("Quarterly".equalsIgnoreCase(type)) {
			//String Date = (String) ifr.getValue("table117_FirstInstDateQuatrly");
			String month = Date.split("/")[1];
			String day = Date.split("/")[2];
			if ("01".equalsIgnoreCase(month) || "04".equalsIgnoreCase(month) || "07".equalsIgnoreCase(month)
					|| "10".equalsIgnoreCase(month)) {
				return "S" + day + "~" + Date;
			}
			if ("02".equalsIgnoreCase(month) || "05".equalsIgnoreCase(month) || "08".equalsIgnoreCase(month)
					|| "11".equalsIgnoreCase(month)) {
				return "T" + day + "~" + Date;
			}
			if ("03".equalsIgnoreCase(month) || "06".equalsIgnoreCase(month) || "09".equalsIgnoreCase(month)
					|| "12".equalsIgnoreCase(month)) {
				return "U" + day + "~" + Date;
			}

		} else if ("SemiAnnual".equalsIgnoreCase(type) || "Semi Annually".equalsIgnoreCase(type)) {
			//String Date = (String) ifr.getValue("table117_FirstInstDateSemiAnnly");
			String month = Date.split("/")[1];
			String day = Date.split("/")[2];
			if ("01".equalsIgnoreCase(month) || "07".equalsIgnoreCase(month)) {
				return "M" + day + "~" + Date;
			} else if ("02".equalsIgnoreCase(month) || "08".equalsIgnoreCase(month)) {
				return "N" + day + "~" + Date;
			} else if ("03".equalsIgnoreCase(month) || "09".equalsIgnoreCase(month)) {
				return "O" + day + "~" + Date;
			} else if ("04".equalsIgnoreCase(month) || "10".equalsIgnoreCase(month)) {
				return "P" + day + "~" + Date;
			} else if ("05".equalsIgnoreCase(month) || "11".equalsIgnoreCase(month)) {
				return "Q" + day + "~" + Date;
			} else if ("06".equalsIgnoreCase(month) || "12".equalsIgnoreCase(month)) {
				return "R" + day + "~" + Date;
			}
		} else if ("Yearly".equalsIgnoreCase(type)) {
			//String Date = (String) ifr.getValue("table117_FirstInstallmentDate2");
			String month = Date.split("/")[1];
			String day = Date.split("/")[2];
			if ("01".equalsIgnoreCase(month)) {
				return "A" + day + "~" + Date;
			} else if ("02".equalsIgnoreCase(month)) {
				return "B" + day + "~" + Date;
			} else if ("03".equalsIgnoreCase(month)) {
				return "C" + day + "~" + Date;
			} else if ("04".equalsIgnoreCase(month)) {
				return "D" + day + "~" + Date;
			} else if ("05".equalsIgnoreCase(month)) {
				return "E" + day + "~" + Date;
			} else if ("06".equalsIgnoreCase(month)) {
				return "F" + day + "~" + Date;
			} else if ("07".equalsIgnoreCase(month)) {
				return "G" + day + "~" + Date;
			} else if ("08".equalsIgnoreCase(month)) {
				return "H" + day + "~" + Date;
			} else if ("09".equalsIgnoreCase(month)) {
				return "I" + day + "~" + Date;
			} else if ("10".equalsIgnoreCase(month)) {
				return "J" + day + "~" + Date;
			} else if ("11".equalsIgnoreCase(month)) {
				return "K" + day + "~" + Date;
			} else if ("12".equalsIgnoreCase(month)) {
				return "L" + day + "~" + Date;
			}
		}

		return "";
	}
	//Ajay 15Dec
	public static String fetchCDTDDetails(IFormReference ifr) throws IOException 
	{
		try 
		{
			LOS_EG.mLogger.info("inside Fetch CD TD Details");
			
			String CustID = (String)ifr.getValue("cd");			
			String inputDetailsCustInfo = getInputXML("GET_CUST_DETAILS");
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetailsCustInfo.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetailsCustInfo.split("~")[2]);
			
			String inputXMLCustInfo = inputDetailsCustInfo.split("~")[0];
			inputXMLCustInfo = inputXMLCustInfo.substring(0, inputXMLCustInfo.indexOf("<CustID>")) + "<CustID>" + CustID
						+ inputXMLCustInfo.substring(inputXMLCustInfo.indexOf("</CustID>"), inputXMLCustInfo.length());
			
			inputXMLCustInfo = inputXMLCustInfo.substring(0, inputXMLCustInfo.indexOf("<CustNIN>")) + "<CustNIN>" + CustID
						+ inputXMLCustInfo.substring(inputXMLCustInfo.indexOf("</CustNIN>"), inputXMLCustInfo.length());
				
			LOS_EG.mLogger.info("Input XML for Fetch Customer " + inputXMLCustInfo);
			
//			String responseXMLCustInfo = SocketConnector.getSocketResponse(inputXMLCustInfo, inputDetailsCustInfo.split("~")[1],
//						inputDetailsCustInfo.split("~")[2]);

			String responseXMLCustInfo=getRequestURL(inputXMLCustInfo,ifr);
			
			LOS_EG.mLogger.info("Output XML for Fetch Customer " + responseXMLCustInfo);
			
			XMLParser xmlParserCustInfo = new XMLParser();
			xmlParserCustInfo.setInputXML(responseXMLCustInfo);
			String name = "", nationality="";
			if ("00000".equalsIgnoreCase(xmlParserCustInfo.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParserCustInfo.getValueOf("Desc"))) 
			{
				name = xmlParserCustInfo.getValueOf("Name");
				nationality = xmlParserCustInfo.getValueOf("Nationality");
			}
			else
			{
				return "false~Error in fetching data from middleware for GET_CUST_DETAILS - "+xmlParserCustInfo.getValueOf("Desc");
			}
			
			String inputDetails = getInputXML("GET_NON_COLLAT_DEP");
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
			String inputXML = inputDetails.split("~")[0];
			inputXML = inputXML.substring(0, inputXML.indexOf("<CustNIN>")) + "<CustNIN>" + CustID
					+ inputXML.substring(inputXML.indexOf("</CustNIN>"), inputXML.length());
			
			inputXML = inputXML.substring(0, inputXML.indexOf("<CustomerNo>")) + "<CustomerNo>"
					+ CustID
					+ inputXML.substring(inputXML.indexOf("</CustomerNo>"), inputXML.length());
			
			LOS_EG.mLogger.info("Final input XML for  CD Enq " + inputXML);
//			String responseXML = SocketConnector.getSocketResponse(inputXML,
//					inputDetails.split("~")[1], inputDetails.split("~")[2]);

			String responseXML=getRequestURL(inputXML,ifr);
			
			XMLParser xmlParser = new XMLParser();
			xmlParser.setInputXML(responseXML);
			JSONArray cdDetailsJsonArray = new JSONArray();
			
			if ("00000".equalsIgnoreCase(xmlParser.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) 
			{
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				ByteArrayInputStream bis = new ByteArrayInputStream(responseXML.getBytes());
				org.w3c.dom.Document doc = db.parse(bis);
				NodeList nodeList = doc.getElementsByTagName("DepositsData");
				
				for(int x=0,size= nodeList.getLength(); x<size; x++) 
				{
					
					Node n=nodeList.item(x);
					NodeList list=n.getChildNodes();
					String refNo="",expDate="";
					for (int temp = 0; temp < list.getLength(); temp++)
					{
						Node node = list.item(temp);
						if (node.getNodeType() == Node.ELEMENT_NODE) 
						{
							Element eElement = (Element) node;
							if("ReferenceNo".equals(eElement.getNodeName()))
							{
								refNo = eElement.getTextContent();
								LOS_EG.mLogger.info("refNo " + refNo);
							}
							else if("ExpiryDate".equals(eElement.getNodeName()))
							{
								expDate = eElement.getTextContent();
								LOS_EG.mLogger.info("expDate " + expDate);
								expDate = LOS_CommonMethods.convertDateFormat(expDate,"yyyyMMdd","yyyy/MM/dd");
								LOS_EG.mLogger.info("expDate after conversion " + expDate);
							}
						}
					}
					
					String inputDetailsFDDetails = getInputXML("FD_DETAIL");
					LOS_EG.mLogger.info("Socket Server IP is " + inputDetailsFDDetails.split("~")[1]);
					LOS_EG.mLogger.info("Socket Server Port is " + inputDetailsFDDetails.split("~")[2]);
					String inputXMLFDDetails = inputDetailsFDDetails.split("~")[0];
					inputXMLFDDetails = inputXMLFDDetails.substring(0, inputXMLFDDetails.indexOf("<No>")) + "<No>" + refNo
							+ inputXMLFDDetails.substring(inputXMLFDDetails.indexOf("</No>"), inputXMLFDDetails.length());
					
					LOS_EG.mLogger.info("Final input XML for  FD Enq " + inputXMLFDDetails);
//					String responseXMLFDDetails = SocketConnector.getSocketResponse(inputXMLFDDetails,
//							inputDetailsFDDetails.split("~")[1], inputDetailsFDDetails.split("~")[2]);

					String responseXMLFDDetails=getRequestURL(inputXMLFDDetails,ifr);
					
					LOS_EG.mLogger.info("output XML for  FD Enq " + responseXMLFDDetails);
					
					XMLParser xmlParserFDDetails = new XMLParser();
					xmlParserFDDetails.setInputXML(responseXMLFDDetails);
					
					if ("00000".equalsIgnoreCase(xmlParserFDDetails.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParserFDDetails.getValueOf("Desc"))) 
					{
						
						LOS_EG.mLogger.info("Type From FD Details " + xmlParserFDDetails.getValueOf("Type"));
						
						JSONObject cdDetailsJsonObject = new JSONObject();
						cdDetailsJsonObject.put("CIF", CustID);
						cdDetailsJsonObject.put("Name", name);
						cdDetailsJsonObject.put("Nationality", nationality);
						cdDetailsJsonObject.put("CDs/TDs Reference Number", refNo);
						cdDetailsJsonObject.put("Type", xmlParserFDDetails.getValueOf("Type"));
						cdDetailsJsonObject.put("Balance", LOS_CommonMethods.formatWithTwoDecimalZero(xmlParserFDDetails.getValueOf("CurrBal")));
						cdDetailsJsonObject.put("Expire Date", expDate); //Ajay 22Dec
						cdDetailsJsonObject.put("Interest Rate", LOS_CommonMethods.formatWithTwoDecimalZero(xmlParserFDDetails.getValueOf("IntrRate")));
						cdDetailsJsonObject.put("TYPE_DESC", xmlParserFDDetails.getValueOf("TypeDesc"));
						cdDetailsJsonArray.add(cdDetailsJsonObject);
					}
					else
					{
						return "false~Error in fetching data from middleware for FD_DETAIL - "+xmlParserFDDetails.getValueOf("Desc");
					}
				}
				
				if(cdDetailsJsonArray.size()>0)
				{
					ifr.addDataToGrid("table142", cdDetailsJsonArray);
					
					LOS_EG.mLogger.info("CD Detail Grid Data : "+ifr.getDataFromGrid("table142")); //Ajay 22Dec
					
					return "true~Successfully Fetched CD Details";
				}
				else
				{
					return "true~No Record Found";
				}
			}
			else
			{
				return "false~Error in fetching data from middleware for GET_NON_COLLAT_DEP - "+xmlParser.getValueOf("Desc");
			}
		} 
		catch (Exception e) 
		{
			LOS_EG.mLogger.info("collateralEnq error is" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
	}
	
	//Ajay 15Dec
	public static String createCollateral(IFormReference ifr) throws IOException 
	{
		try 
		{
			LOS_EG.mLogger.info("inside Create Collateral");
			
			String collRefNo = (String)ifr.getValue("table142_CollRefNo");
			
			String inputDetailsCollatAddMaint = getInputXML("COLLAT_ADD_MAINT");
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetailsCollatAddMaint.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetailsCollatAddMaint.split("~")[2]);
			
			String inputXMLCollatAddMaint = inputDetailsCollatAddMaint.split("~")[0];
			
			String actionType = "A";
			String dealFullRefNo = (String)ifr.getValue("table142_REFNO");
			
			if(!(collRefNo==null || collRefNo.equalsIgnoreCase("")))
			{
				actionType = "M";
				dealFullRefNo = (String)ifr.getValue("table142_CollRefNo");
			}
			
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<ActionType>")) + "<ActionType>" + actionType
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</ActionType>"), inputXMLCollatAddMaint.length());
						
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<CustNIN>")) + "<CustNIN>" + ifr.getValue("table142_CIF")
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</CustNIN>"), inputXMLCollatAddMaint.length());
						
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<CustID>")) + "<CustID>" + ifr.getValue("table142_CIF")
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</CustID>"), inputXMLCollatAddMaint.length());
						
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<CustomerNo>")) + "<CustomerNo>" + ifr.getValue("table142_CIF")
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</CustomerNo>"), inputXMLCollatAddMaint.length());
						
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<DealFullRef>")) + "<DealFullRef>" + dealFullRefNo
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</DealFullRef>"), inputXMLCollatAddMaint.length());
						
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<CollateralType>")) + "<CollateralType>" + "DPS"
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</CollateralType>"), inputXMLCollatAddMaint.length());
						
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<CollateralCountry>")) + "<CollateralCountry>" + ifr.getValue("table142_Nationality")
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</CollateralCountry>"), inputXMLCollatAddMaint.length());
						
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<DepartmentCode>")) + "<DepartmentCode>" + "HRD"
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</DepartmentCode>"), inputXMLCollatAddMaint.length());
						 
			/*inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<ExpiryDate>")) + "<ExpiryDate>" + LOS_CommonMethods.convertDateFormat((String)ifr.getValue("table142_ExpireDate"),"yyyy/MM/dd","yyyyMMdd")
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</ExpiryDate>"), inputXMLCollatAddMaint.length());*/
						 
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<ValuationMargin>")) + "<ValuationMargin>" + LOS_CommonMethods.handleThreeZeroInRequest(LOS_CommonMethods.getDoubleAmount((String)ifr.getValue("table142_CoverageRation")))
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</ValuationMargin>"), inputXMLCollatAddMaint.length());
				
			LOS_EG.mLogger.info("Input XML for Create/Maintain Collateral 1 " + inputXMLCollatAddMaint);
			
//			String responseXMLCollatAddMaint = SocketConnector.getSocketResponse(inputXMLCollatAddMaint, inputDetailsCollatAddMaint.split("~")[1],
//						inputDetailsCollatAddMaint.split("~")[2]);

			
			String responseXMLCollatAddMaint=getRequestURL(inputXMLCollatAddMaint,ifr);
			
			// String responseXMLCollatAddMaint = "<AUB_MESSAGE xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"AUB_Schema.xsd\"><RESPONSE_MESSAGE><ResponseHeader><MsgType>Response</MsgType><ReqID>RLOS</ReqID><ChSysID>RLOS</ChSysID><FuncID>COLLAT_ADD_MAINT</FuncID><TxnRef>COB2023177379049</TxnRef><TxnDate>20211215152150</TxnDate><Cust><CustEnt><EntID>AUBEG</EntID><CustNIN>27704240101101</CustNIN><CustSys><CustID>351122</CustID><SysID>EQN</SysID></CustSys></CustEnt></Cust><TxnStatus>Y</TxnStatus><SessLang>EN</SessLang><SessToken>UkxPU0AhMTI=</SessToken><Return><Code>00000</Code><Desc>SUCCESS</Desc></Return></ResponseHeader><ResponseBody><COLLAT_ADD_MAINT_RS><CreatedReference>0085QSC467329040319F</CreatedReference></COLLAT_ADD_MAINT_RS></ResponseBody></RESPONSE_MESSAGE></AUB_MESSAGE>"; //Dummy Response
			LOS_EG.mLogger.info("Output XML for Create/Maintain Collateral " + responseXMLCollatAddMaint);
			
			XMLParser xmlParserCollatAddMaint = new XMLParser();
			xmlParserCollatAddMaint.setInputXML(responseXMLCollatAddMaint);
			
			if ("00000".equalsIgnoreCase(xmlParserCollatAddMaint.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParserCollatAddMaint.getValueOf("Desc"))) 
			{
				ifr.setValue("table142_CollRefNo", xmlParserCollatAddMaint.getValueOf("CreatedReference"));
				return "true~Collateral Created/Maintained Successfully";
			}
			else
			{
				return "false~Error in Creating Collateral - "+xmlParserCollatAddMaint.getValueOf("Desc");
			}
		} 
		catch (Exception e) 
		{
			LOS_EG.mLogger.info("Create Collateral Error is" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
	}
	
	//Ajay 15Dec
	public static String reservationEnquiry(IFormReference ifr) throws IOException 
	{
		try 
		{
			LOS_EG.mLogger.info("inside Reservation Enquiry");
			
			String collResNo = (String)ifr.getValue("CollResNo");
			
			String inputDetailsReservationEnquiry = getInputXML("RESERVATION_ENQUIRY");
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetailsReservationEnquiry.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetailsReservationEnquiry.split("~")[2]);
			
			String inputXMLReservationEnquiry = inputDetailsReservationEnquiry.split("~")[0];
			
			inputXMLReservationEnquiry = inputXMLReservationEnquiry.substring(0, inputXMLReservationEnquiry.indexOf("<ReservationReference>")) + "<ReservationReference>" + collResNo
						+ inputXMLReservationEnquiry.substring(inputXMLReservationEnquiry.indexOf("</ReservationReference>"), inputXMLReservationEnquiry.length());
						
			LOS_EG.mLogger.info("Input XML for Reservation Enquiry " + inputXMLReservationEnquiry);
			
//			String responseXMLReservationEnquiry = SocketConnector.getSocketResponse(inputXMLReservationEnquiry, inputDetailsReservationEnquiry.split("~")[1],
//						inputDetailsReservationEnquiry.split("~")[2]);

			String responseXMLReservationEnquiry=getRequestURL(inputXMLReservationEnquiry,ifr);
			
			// String responseXMLReservationEnquiry = "<AUB_MESSAGE xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"AUB_Schema.xsd\"><RESPONSE_MESSAGE><ResponseHeader><MsgType>Response</MsgType><ReqID>RLOS</ReqID><ChSysID>RLOS</ChSysID><FuncID>RESERVATION_ENQUIRY</FuncID><TxnRef>COB2022374475449</TxnRef><TxnDate>20211215152150</TxnDate><TxnStatus>Y</TxnStatus><SessLang>EN</SessLang><SessToken>UkxPU0AhMTI=</SessToken><Return><Code>00000</Code><Desc>SUCCESS</Desc></Return></ResponseHeader><ResponseBody><RESERVATION_ENQUIRY_RS><TotalAssigned>000000000900000</TotalAssigned><CollateralDetails><CollateralReference>3014000001152</CollateralReference><CollateralCurrency>EGP</CollateralCurrency><AssignmentPercentage>10</AssignmentPercentage><AssignmentAmount>50000</AssignmentAmount><AvailableAmount>000000007550000</AvailableAmount></CollateralDetails></RESERVATION_ENQUIRY_RS></ResponseBody></RESPONSE_MESSAGE></AUB_MESSAGE>"; //Dummy Response
			LOS_EG.mLogger.info("Output XML for Reservation Enquiry " + responseXMLReservationEnquiry);
			
			XMLParser xmlParserReservationEnquiry = new XMLParser();
			xmlParserReservationEnquiry.setInputXML(responseXMLReservationEnquiry);
			
			if ("00000".equalsIgnoreCase(xmlParserReservationEnquiry.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParserReservationEnquiry.getValueOf("Desc"))) 
			{	
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				ByteArrayInputStream bis = new ByteArrayInputStream(responseXMLReservationEnquiry.getBytes(StandardCharsets.UTF_8));
				org.w3c.dom.Document doc = db.parse(bis);
				NodeList nodeList = doc.getElementsByTagName("CollateralDetails"); 
				
				System.out.println(nodeList.getLength());
				for(int x=0,size= nodeList.getLength(); x<size; x++) 
				{	
					Node n=nodeList.item(x);
					NodeList list=n.getChildNodes();
					
					int colGridRowIndex = -1;
					String coverageRatio = "";
					String assignmentAmount = "";
					
					for (int temp = 0; temp < list.getLength(); temp++)
					{
						
						Node node = list.item(temp);
						if (node.getNodeType() == Node.ELEMENT_NODE) 
						{
							Element eElement = (Element) node;
							if ("CollateralReference".equals(eElement.getNodeName()))
							{
								colGridRowIndex = getRowIndexOfCollateralGrid (ifr.getDataFromGrid("table129")+"", eElement.getTextContent()+"") ;
								
								LOS_EG.mLogger.info("colGridRowIndex Returned " + colGridRowIndex);
								//collObj.put("Collateral Reference", eElement.getTextContent());
								
							}
							else if ("AssignmentPercentage".equals(eElement.getNodeName()))
							{
								// coverageRatio = eElement.getTextContent();
								coverageRatio = String.format("%.02f", LOS_CommonMethods.getDoubleAmount(eElement.getTextContent())/1000);
								//collObj.put("Coverage Ratio", eElement.getTextContent());
								
							}
							else if ("AssignmentAmount".equals(eElement.getNodeName()))
							{
								assignmentAmount = LOS_CommonMethods.handleDoubleZeroInResponse(eElement.getTextContent());
								//collObj.put("Available Collateral Value", eElement.getTextContent());
								
							}
							else if ("AvailableAmount".equals(eElement.getNodeName()))
							{
								//collObj.put("Collateral Reference", eElement.getTextContent());								
							}
						}
					}
					
					if(colGridRowIndex>=0)
					{
						LOS_EG.mLogger.info("colGridRowIndex is valid "+assignmentAmount);
						
						ifr.setTableCellValue("table129", colGridRowIndex, 9, coverageRatio);
						ifr.setTableCellValue("table129", colGridRowIndex, 7, assignmentAmount);
					}
					else
					{
						LOS_EG.mLogger.info("colGridRowIndex is not valid");
					}
					//collJsonArr.add(collObj);
					//LOS_EG.mLogger.info("Collateral "+collJsonArr);
					//
				}
				//ifr.clearTable("table129");
				//ifr.addDataToGrid("table129", collJsonArr);
				
				return "true~Reservation Enquiry Fetched Successfully";
			}
			else
			{
				return "false~Error in Fetching Reservation Enquiry - "+xmlParserReservationEnquiry.getValueOf("Desc");
			}
		} 
		catch (Exception e) 
		{
			LOS_EG.mLogger.info("Create Collateral Error is" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
	}
	
	//Ajay 15Dec
	public static String checkReservationBeforeLoanBook(IFormReference ifr) throws IOException 
	{
		try 
		{
			LOS_EG.mLogger.info("inside Reservation Enquiry");
			
			String collResNo = (String)ifr.getValue("CollResNo");
			
			String inputDetailsReservationEnquiry = getInputXML("RESERVATION_ENQUIRY");
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetailsReservationEnquiry.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetailsReservationEnquiry.split("~")[2]);
			
			String inputXMLReservationEnquiry = inputDetailsReservationEnquiry.split("~")[0];
			
			inputXMLReservationEnquiry = inputXMLReservationEnquiry.substring(0, inputXMLReservationEnquiry.indexOf("<ReservationReference>")) + "<ReservationReference>" + collResNo
						+ inputXMLReservationEnquiry.substring(inputXMLReservationEnquiry.indexOf("</ReservationReference>"), inputXMLReservationEnquiry.length());
						
			LOS_EG.mLogger.info("Input XML for Reservation Enquiry " + inputXMLReservationEnquiry);
			
//			String responseXMLReservationEnquiry = SocketConnector.getSocketResponse(inputXMLReservationEnquiry, inputDetailsReservationEnquiry.split("~")[1],
//						inputDetailsReservationEnquiry.split("~")[2]);

			String responseXMLReservationEnquiry=getRequestURL(inputXMLReservationEnquiry,ifr);
			
			// String responseXMLReservationEnquiry = "<AUB_MESSAGE xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"AUB_Schema.xsd\"><RESPONSE_MESSAGE><ResponseHeader><MsgType>Response</MsgType><ReqID>RLOS</ReqID><ChSysID>RLOS</ChSysID><FuncID>RESERVATION_ENQUIRY</FuncID><TxnRef>COB2022374475449</TxnRef><TxnDate>20211215152150</TxnDate><TxnStatus>Y</TxnStatus><SessLang>EN</SessLang><SessToken>UkxPU0AhMTI=</SessToken><Return><Code>00000</Code><Desc>SUCCESS</Desc></Return></ResponseHeader><ResponseBody><RESERVATION_ENQUIRY_RS><TotalAssigned>000000000900000</TotalAssigned><CollateralDetails><CollateralReference>0085G3S464064151020F</CollateralReference><CollateralCurrency>EGP</CollateralCurrency><AssignmentPercentage>65000</AssignmentPercentage><AssignmentAmount>000000008450000</AssignmentAmount><AvailableAmount>000000007550000</AvailableAmount></CollateralDetails></RESERVATION_ENQUIRY_RS></ResponseBody></RESPONSE_MESSAGE></AUB_MESSAGE>"; //Dummy Response
			LOS_EG.mLogger.info("Output XML for Reservation Enquiry " + responseXMLReservationEnquiry);
			
			XMLParser xmlParserReservationEnquiry = new XMLParser();
			xmlParserReservationEnquiry.setInputXML(responseXMLReservationEnquiry);
			
			if ("00000".equalsIgnoreCase(xmlParserReservationEnquiry.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParserReservationEnquiry.getValueOf("Desc"))) 
			{
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				ByteArrayInputStream bis = new ByteArrayInputStream(responseXMLReservationEnquiry.getBytes(StandardCharsets.UTF_8));
				org.w3c.dom.Document doc = db.parse(bis);
				NodeList nodeList = doc.getElementsByTagName("CollateralDetails"); 
				
				System.out.println(nodeList.getLength());
				double totalOutstanding = 0d;
				for(int x=0,size= nodeList.getLength(); x<size; x++) 
				{	
					Node n=nodeList.item(x);
					NodeList list=n.getChildNodes();
					
					for (int temp = 0; temp < list.getLength(); temp++)
					{
						
						Node node = list.item(temp);
						if (node.getNodeType() == Node.ELEMENT_NODE) 
						{
							Element eElement = (Element) node;
							
							if ("AssignmentAmount".equals(eElement.getNodeName()))
							{
								totalOutstanding = totalOutstanding+LOS_CommonMethods.getDoubleAmount(LOS_CommonMethods.handleDoubleZeroInResponse(eElement.getTextContent()));
							}
						}
					}
				}				
				return "true~"+String.format("%.02f", totalOutstanding);
			}
			else
			{
				return "false~Error in Fetching Reservation Enquiry - "+xmlParserReservationEnquiry.getValueOf("Desc");
			}
		} 
		catch (Exception e) 
		{
			LOS_EG.mLogger.info("Create Collateral Error is" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
	}
	
	//Ajay 15Dec
	private static int getRowIndexOfCollateralGrid (String gridJsonData, String intgCollateralRefValue) 
	{
		try
		{
			
			LOS_EG.mLogger.info("gridJsonData " + gridJsonData);
			
			LOS_EG.mLogger.info("intgCollateralRefValue "+intgCollateralRefValue);
			
			Object obj = JSONValue.parse(gridJsonData);
			JSONArray array = (JSONArray)obj;
			
			System.out.println(array.size());
			
			for(int i = 0 ;i<array.size() ;i++)
			{
				LOS_EG.mLogger.info("i " + i);
				JSONObject obj2 = (JSONObject)array.get(i);
				String colRefVal = (String)obj2.get("Collateral Reference");
				LOS_EG.mLogger.info("colRefVal "+colRefVal);
				if(intgCollateralRefValue.equalsIgnoreCase(colRefVal))
				{
					LOS_EG.mLogger.info("Match Found "+i+"  colRefVal "+colRefVal);
					return i;
				}
			}		
		}
		catch(Exception e)
		{
			LOS_EG.mLogger.info("Exception e " + e);
		}
		LOS_EG.mLogger.info("Returning -1" );
		return -1;
	}
	
	public static String updateCollateral(IFormReference ifr) throws IOException 
	{
		try 
		{
			LOS_EG.mLogger.info("inside update Collateral");
			
			String collResNo = (String)ifr.getValue("CollResNo");
			
			LOS_EG.mLogger.info("collResNo "+collResNo);
			
			if(!(collResNo == null || collResNo.trim().equalsIgnoreCase("")))
			{
				return "false~Reservation has been done. Cannot Update Collateral";
			}
			
			String inputDetailsCollatAddMaint = getInputXML("COLLAT_ADD_MAINT");
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetailsCollatAddMaint.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetailsCollatAddMaint.split("~")[2]);
			
			String inputXMLCollatAddMaint = inputDetailsCollatAddMaint.split("~")[0];
			
			String actionType = "M";
			String dealFullRefNo = (String)ifr.getValue("table129_CollateralReference");
			
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<ActionType>")) + "<ActionType>" + actionType
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</ActionType>"), inputXMLCollatAddMaint.length());
						
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<CustNIN>")) + "<CustNIN>" + ifr.getValue("table129_CollateralCIF")
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</CustNIN>"), inputXMLCollatAddMaint.length());
						
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<CustID>")) + "<CustID>" + ifr.getValue("table129_CollateralCIF")
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</CustID>"), inputXMLCollatAddMaint.length());
						
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<CustomerNo>")) + "<CustomerNo>" + ifr.getValue("table129_CollateralCIF")
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</CustomerNo>"), inputXMLCollatAddMaint.length());
						
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<DealFullRef>")) + "<DealFullRef>" + dealFullRefNo
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</DealFullRef>"), inputXMLCollatAddMaint.length());
						
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<CollateralType>")) + "<CollateralType>" + "DPS"
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</CollateralType>"), inputXMLCollatAddMaint.length());
						
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<CollateralCountry>")) + "<CollateralCountry>" + ifr.getValue("table129_Nationality")
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</CollateralCountry>"), inputXMLCollatAddMaint.length());
						
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<DepartmentCode>")) + "<DepartmentCode>" + "HRD"
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</DepartmentCode>"), inputXMLCollatAddMaint.length());
						 
			/*inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<ExpiryDate>")) + "<ExpiryDate>" + LOS_CommonMethods.convertDateFormat((String)ifr.getValue("table142_ExpireDate"),"yyyy/MM/dd","yyyyMMdd")
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</ExpiryDate>"), inputXMLCollatAddMaint.length());*/
						 
			inputXMLCollatAddMaint = inputXMLCollatAddMaint.substring(0, inputXMLCollatAddMaint.indexOf("<ValuationMargin>")) + "<ValuationMargin>" + LOS_CommonMethods.handleThreeZeroInRequest(LOS_CommonMethods.getDoubleAmount((String)ifr.getValue("table129_Coverage_Ratio")))
						+ inputXMLCollatAddMaint.substring(inputXMLCollatAddMaint.indexOf("</ValuationMargin>"), inputXMLCollatAddMaint.length());
				
			LOS_EG.mLogger.info("Input XML for Create/Maintain Collateral 1 " + inputXMLCollatAddMaint);
			
//			String responseXMLCollatAddMaint = SocketConnector.getSocketResponse(inputXMLCollatAddMaint, inputDetailsCollatAddMaint.split("~")[1],
//						inputDetailsCollatAddMaint.split("~")[2]);

			String responseXMLCollatAddMaint=getRequestURL(inputXMLCollatAddMaint,ifr);
			
			// String responseXMLCollatAddMaint = "<AUB_MESSAGE xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"AUB_Schema.xsd\"><RESPONSE_MESSAGE><ResponseHeader><MsgType>Response</MsgType><ReqID>RLOS</ReqID><ChSysID>RLOS</ChSysID><FuncID>COLLAT_ADD_MAINT</FuncID><TxnRef>COB2023177379049</TxnRef><TxnDate>20211215152150</TxnDate><Cust><CustEnt><EntID>AUBEG</EntID><CustNIN>27704240101101</CustNIN><CustSys><CustID>351122</CustID><SysID>EQN</SysID></CustSys></CustEnt></Cust><TxnStatus>Y</TxnStatus><SessLang>EN</SessLang><SessToken>UkxPU0AhMTI=</SessToken><Return><Code>00000</Code><Desc>SUCCESS</Desc></Return></ResponseHeader><ResponseBody><COLLAT_ADD_MAINT_RS><CreatedReference>0085QSC467329040319F</CreatedReference></COLLAT_ADD_MAINT_RS></ResponseBody></RESPONSE_MESSAGE></AUB_MESSAGE>"; //Dummy Response
			LOS_EG.mLogger.info("Output XML for Update Collateral " + responseXMLCollatAddMaint);
			
			XMLParser xmlParserCollatAddMaint = new XMLParser();
			xmlParserCollatAddMaint.setInputXML(responseXMLCollatAddMaint);
			
			if ("00000".equalsIgnoreCase(xmlParserCollatAddMaint.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParserCollatAddMaint.getValueOf("Desc"))) 
			{
				ifr.setValue("table142_CollRefNo", xmlParserCollatAddMaint.getValueOf("CreatedReference"));//TBCC
				return "true~Collateral updated Successfully";
			}
			else
			{
				return "false~Error in Updating Collateral - "+xmlParserCollatAddMaint.getValueOf("Desc");
			}
		} 
		catch (Exception e) 
		{
			LOS_EG.mLogger.info("Updating Collateral Error is" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
	}
	
	public static String getIScoreIntegrationType()
	{
		try
		{
			Properties p = new Properties();
			p.load(new FileInputStream(System.getProperty("user.dir") + File.separator + "CustomConfig" + File.separator
					+ "IntegrationInputs.properties"));
			
			String iscoreIntgType = p.getProperty("ISCORE_INTEGRATION_TYPE");
			
			LOS_EG.mLogger.info("iscoreIntgType : " + iscoreIntgType);
			
			if(iscoreIntgType==null || iscoreIntgType.trim().equalsIgnoreCase("") || iscoreIntgType.trim().equalsIgnoreCase("OLD"))
			{
				return "OLD";
			}
			else
			{			
				return "NEW";
			}	
		}
		catch (Exception e)
		{
			return "OLD";
		}

	}
	
	
	//Added by Shivanshu for Customer CID Creation
	public static String createCustomerCID(String nationalID, IFormReference ifr) throws IOException{
		LOS_EG.mLogger.info("inside createCustomerCID");
		
		String fullName = null;
		String shortName = null;
		String fullNameArabic = null;
		String shortNameArabic = null;
		String idExpiryDate = null;
		String mobileNo = null;
		String customerType = null;
		String accountOfficer = null;
		String sundryCode = null;
		String nationality = null;
		String branch = null;
		String idType = null;
		String segment = null;
		
		String inputDetails = getInputXML("CREATE_CUST_CID"); //Need to Config in this CONFIGProperties file.
		LOS_EG.mLogger.info("Default input XML for CREATE_CUST_CID is" + inputDetails.split("~")[0]);
		LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
		LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
		String inputXML = inputDetails.split("~")[0];
		
		inputXML = inputXML.substring(0, inputXML.indexOf("<CustNIN>")) + "<CustNIN>" + nationalID
				+ inputXML.substring(inputXML.indexOf("</CustNIN>"), inputXML.length());
		
		// Replace CustomerType
		inputXML = inputXML.substring(0, inputXML.indexOf("<CustomerType>")) + "<CustomerType>" + customerType
		        + inputXML.substring(inputXML.indexOf("</CustomerType>"), inputXML.length());

		// Replace AccountOfficer
		inputXML = inputXML.substring(0, inputXML.indexOf("<AccountOfficer>")) + "<AccountOfficer>" + accountOfficer
		        + inputXML.substring(inputXML.indexOf("</AccountOfficer>"), inputXML.length());

		// Replace SundryCode
		inputXML = inputXML.substring(0, inputXML.indexOf("<SundryCode>")) + "<SundryCode>" + sundryCode
		        + inputXML.substring(inputXML.indexOf("</SundryCode>"), inputXML.length());

		// Replace Nationality
		inputXML = inputXML.substring(0, inputXML.indexOf("<Nationality>")) + "<Nationality>" + nationality
		        + inputXML.substring(inputXML.indexOf("</Nationality>"), inputXML.length());

		// Replace Branch
		inputXML = inputXML.substring(0, inputXML.indexOf("<Branch>")) + "<Branch>" + branch
		        + inputXML.substring(inputXML.indexOf("</Branch>"), inputXML.length());

		// Replace FullName
		inputXML = inputXML.substring(0, inputXML.indexOf("<FullName>")) + "<FullName>" + fullName
		        + inputXML.substring(inputXML.indexOf("</FullName>"), inputXML.length());

		// Replace ShortName
		inputXML = inputXML.substring(0, inputXML.indexOf("<ShortName>")) + "<ShortName>" + shortName
		        + inputXML.substring(inputXML.indexOf("</ShortName>"), inputXML.length());

		// Replace FullNameArabic
		inputXML = inputXML.substring(0, inputXML.indexOf("<FullNameArabic>")) + "<FullNameArabic>" + fullNameArabic
		        + inputXML.substring(inputXML.indexOf("</FullNameArabic>"), inputXML.length());

		// Replace ShortNameArabic
		inputXML = inputXML.substring(0, inputXML.indexOf("<ShortNameArabic>")) + "<ShortNameArabic>" + shortNameArabic
		        + inputXML.substring(inputXML.indexOf("</ShortNameArabic>"), inputXML.length());

		// Replace IDType
		inputXML = inputXML.substring(0, inputXML.indexOf("<IDType>")) + "<IDType>" + idType
		        + inputXML.substring(inputXML.indexOf("</IDType>"), inputXML.length());

		// Replace IDNumber
		inputXML = inputXML.substring(0, inputXML.indexOf("<IDNumber>")) + "<IDNumber>" + nationalID
		        + inputXML.substring(inputXML.indexOf("</IDNumber>"), inputXML.length());

		// Replace IDExpiryDate
		inputXML = inputXML.substring(0, inputXML.indexOf("<IDExpiryDate>")) + "<IDExpiryDate>" + idExpiryDate
		        + inputXML.substring(inputXML.indexOf("</IDExpiryDate>"), inputXML.length());

		// Replace SegMent
		inputXML = inputXML.substring(0, inputXML.indexOf("<SegMent>")) + "<SegMent>" + segment
		        + inputXML.substring(inputXML.indexOf("</SegMent>"), inputXML.length());

		// Replace MobileNo
		inputXML = inputXML.substring(0, inputXML.indexOf("<MobileNo>")) + "<MobileNo>" + mobileNo
		        + inputXML.substring(inputXML.indexOf("</MobileNo>"), inputXML.length());
		
//		String responseXML = SocketConnector.getSocketResponse(inputXML, inputDetails.split("~")[1],
//				inputDetails.split("~")[2]);

		String responseXML=getRequestURL(inputXML,ifr);
		
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(responseXML);
		LOS_EG.mLogger.info("xmlParser " + responseXML + " parser "+xmlParser);

		if ("00000".equalsIgnoreCase(xmlParser.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) {
			ifr.setValue("Customer CID", xmlParser.getValueOf("CustNo"));
			LOS_EG.mLogger.info("Customer CID received"+xmlParser.getValueOf("CustNo"));
			return "true~Customer CID " +xmlParser.getValueOf("CustNo")+ " created successfully";
		}else {	
			return "false~Error in creating customer CID";
		}
	}
	
	//Added by Shivanshu Customer Account Creation
		public static String createCustomerAccount(String nationalID, String CustID, IFormReference ifr) throws IOException{
			LOS_EG.mLogger.info("inside createCustomerAccount");
			
			String referDebitTRX = null;
			String branch = null;
			String currency = null;
			String basicNumber = null;
			String suffix = null;
			String accountType = null;

			String inputDetails = getInputXML("CREATE_CUST_ACCOUNT"); //Need to Config in this CONFIGProperties file.
			LOS_EG.mLogger.info("Default input XML for CREATE_CUST_ACCOUNT is" + inputDetails.split("~")[0]);
			LOS_EG.mLogger.info("Socket Server IP is " + inputDetails.split("~")[1]);
			LOS_EG.mLogger.info("Socket Server Port is " + inputDetails.split("~")[2]);
			String inputXML = inputDetails.split("~")[0];
			
			inputXML = inputXML.substring(0, inputXML.indexOf("<CustID>")) + "<CustID>" + CustID
					+ inputXML.substring(inputXML.indexOf("</CustID>"), inputXML.length());
			
			inputXML = inputXML.substring(0, inputXML.indexOf("<CustNIN>")) + "<CustNIN>" + nationalID
					+ inputXML.substring(inputXML.indexOf("</CustNIN>"), inputXML.length());
			
		
			// Replace ReferDebitTRX
			inputXML = inputXML.substring(0, inputXML.indexOf("<ReferDebitTRX>")) + "<ReferDebitTRX>" + referDebitTRX
			        + inputXML.substring(inputXML.indexOf("</ReferDebitTRX>"), inputXML.length());

			
			// Replace Branch
			inputXML = inputXML.substring(0, inputXML.indexOf("<Branch>")) + "<Branch>" + branch
			        + inputXML.substring(inputXML.indexOf("</Branch>"), inputXML.length());

			// Replace BasicNumber
			inputXML = inputXML.substring(0, inputXML.indexOf("<BasicNumber>")) + "<BasicNumber>" + basicNumber
			        + inputXML.substring(inputXML.indexOf("</BasicNumber>"), inputXML.length());

			// Replace Suffix
			inputXML = inputXML.substring(0, inputXML.indexOf("<Suffix>")) + "<Suffix>" + suffix
			        + inputXML.substring(inputXML.indexOf("</Suffix>"), inputXML.length());

			// Replace AccountType
			inputXML = inputXML.substring(0, inputXML.indexOf("<AccountType>")) + "<AccountType>" + accountType
			        + inputXML.substring(inputXML.indexOf("</AccountType>"), inputXML.length());

		
			// Replace Currency
			inputXML = inputXML.substring(0, inputXML.indexOf("<Currency>")) + "<Currency>" + currency
			        + inputXML.substring(inputXML.indexOf("</Currency>"), inputXML.length());
			
//			String responseXML = SocketConnector.getSocketResponse(inputXML, inputDetails.split("~")[1],
//					inputDetails.split("~")[2]);

			String responseXML=getRequestURL(inputXML,ifr);
			
			XMLParser xmlParser = new XMLParser();
			xmlParser.setInputXML(responseXML);
			LOS_EG.mLogger.info("xmlParser " + responseXML + " parser "+xmlParser);

			if ("00000".equalsIgnoreCase(xmlParser.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) {
				ifr.setValue("Account Number", xmlParser.getValueOf("AccountNumber"));
				ifr.setValue("IBAN", xmlParser.getValueOf("IBAN"));
				LOS_EG.mLogger.info("Account Number received"+xmlParser.getValueOf("AccountNumber") + " IBAN received "+xmlParser.getValueOf("IBAN"));
				return "true~Customer Account Number " +xmlParser.getValueOf("AccountNumber")+ " created successfully";
			}else {	
				return "false~Error in creating customer account";
			}
		}
		
		
		public static String getRequestURL(String requestXML, IFormReference ifr) throws IOException {
			
			Properties prop = new Properties();
			prop.load(new FileInputStream(System.getProperty("user.dir") + File.separator + "CustomConfig" + File.separator
					+ "IntegrationInputs.properties"));
			
			String isAPINew = prop.get("API_URL_TYPE").toString();

		    String callName = requestXML.substring(requestXML.indexOf("<FuncID>") + 8, requestXML.indexOf("</FuncID>"));
		    
		    APIRequestURLMasterMap = RLOSMappingCache.getInstance().getAPIUrlMasterMap(ifr);
		    String urlString = APIRequestURLMasterMap.get(callName+"#"+isAPINew);
		    
		    
		    /**************************************************Creating Final Request XML ******************************************************************/
		   
		    //Changing TxnDate 
		    if("BILL_PAYMENT".equalsIgnoreCase(callName))
		    {
		    	requestXML = requestXML.substring(0, requestXML.indexOf("<TxnDate>")) + "<TxnDate>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(new Date().getTime()+1*3600*1000))
						+ requestXML.substring(requestXML.indexOf("</TxnDate>"), requestXML.length());
		    }
		    else
		    {
		    requestXML = requestXML.substring(0, requestXML.indexOf("<TxnDate>")) + "<TxnDate>" + new SimpleDateFormat("yyyyMMddHHmmSS").format(new Date(new Date().getTime()+1*3600*1000))
					+ requestXML.substring(requestXML.indexOf("</TxnDate>"), requestXML.length());
		    }
		    //Changing TxnRef
		    requestXML = requestXML.substring(0, requestXML.indexOf("<TxnRef>")) + "<TxnRef>" +"RLOS"+ Long.toString(System.nanoTime()).substring(0, 13)
					+ requestXML.substring(requestXML.indexOf("</TxnRef>"), requestXML.length());
		    //Changing Session Token need to change for Hashing
		    requestXML = requestXML.substring(0, requestXML.indexOf("<SessToken>")) + "<SessToken>" + prop.get("SESSIONTOKEN")
					+ requestXML.substring(requestXML.indexOf("</SessToken>"), requestXML.length());
		    LOS_EG.mLogger.info("Final Request XML" + requestXML);
		    System.out.println("Final Request XML" + requestXML);
		    
		    /**************************************************Creating Final Request XML ******************************************************************/
		    return HttpConnection.connectURLXML(requestXML, callName, urlString);
		  }
}
