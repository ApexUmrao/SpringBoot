package com.newgen.iforms.user.Integration;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.LOS_CommonMethods;
import com.newgen.iforms.user.LOS_EG;
import com.newgen.iforms.user.XMLParser;

import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPDBRecoverDocData;
import ISPack.ISUtil.JPISException;
import ISPack.ISUtil.JPISIsIndex;


public class IntegrationParser {
	@SuppressWarnings("unchecked")
	public static String parseCustomerDetails(String responseXML, IFormReference ifr, String CustomerType) {
		try {
			LOS_EG.mLogger.info("inside parseCustomerDetails");
			XMLParser xmlParser = new XMLParser();
			xmlParser.setInputXML(responseXML);
			if ("00000".equalsIgnoreCase(xmlParser.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) {
				String gender = "";
				if (xmlParser.getValueOf("Gender").trim().equalsIgnoreCase("1")) {
					gender = "Male";
				} else if (xmlParser.getValueOf("Gender").trim().equalsIgnoreCase("2")) {
					gender = "Female";
				} else if (xmlParser.getValueOf("Gender").trim().equalsIgnoreCase("")) {
					gender = "";
				}
				String dob = xmlParser.getValueOf("DateofBirth").trim();
				if (dob.length() == 8) {
					dob = dob.substring(0, 4) + "/" + dob.substring(4, 6) + "/" + dob.substring(6, 8);
				} else {
					dob = "";
				}
				LOS_EG.mLogger.info(dob + "!!!!!!!!!!!!!inside parseCustomerDetails");
				String nID="";
				if ("Applicant".equalsIgnoreCase(CustomerType)) {
					ifr.setValue("Applicant_Name", xmlParser.getValueOf("Name"));
					ifr.setValue("Applicant_DOB", dob);
					ifr.setValue("Applicant_Age", calculateAge(dob));
					ifr.setValue("Applicant_Gender", gender);
					ifr.setValue("Applicant_Passport_Number", xmlParser.getValueOf("PassportNo"));
					if(((String)ifr.getValue("Applicant_National_ID")).length()==14)
					{
						nID=(String)ifr.getValue("Applicant_National_ID");
					}
				} else if ("Co-Applicant".equalsIgnoreCase(CustomerType)) {
					ifr.setValue("Co_Applicant_Name", xmlParser.getValueOf("Name"));
					ifr.setValue("Co_Applicant_DOB", dob);
					ifr.setValue("Co_Applicant_Age", calculateAge(dob));
					ifr.setValue("Co_Applicant_Gender", gender);
					ifr.setValue("Co_Applicant_Passport_Number", xmlParser.getValueOf("PassportNo"));
					if(((String)ifr.getValue("CO_Applicant_National_ID")).length()==14)
					{
						nID=(String)ifr.getValue("CO_Applicant_National_ID");
					}
				} else if ("Guarantor".equalsIgnoreCase(CustomerType)) {
					ifr.setValue("Guarantor_Name", xmlParser.getValueOf("Name"));
					ifr.setValue("Guarantor_DOB", dob);
					ifr.setValue("Guarantor_Age", calculateAge(dob));
					ifr.setValue("Guarantor_Gender", gender);
					ifr.setValue("Guarantor_Passport_Number", xmlParser.getValueOf("PassportNo"));
					if(((String)ifr.getValue("Guarantor_National_ID")).length()==14)
					{
						nID=(String)ifr.getValue("Guarantor_National_ID");
					}
				}
				JSONArray incomeJsonArr = new JSONArray();
				JSONObject incomeJsonObj = new JSONObject();
				incomeJsonObj.put("Applicant Type", CustomerType);// need to
																	// check
																	// type
				incomeJsonObj.put("Program type", "");
				incomeJsonObj.put("Currency", "");
				incomeJsonObj.put("Income Type", "");
				incomeJsonObj.put("Profit Share", "");
				incomeJsonObj.put("Min Paid In Capital", "");
				incomeJsonObj.put("Company Segment", "");
				incomeJsonObj.put("Profit share in advance", "");
				incomeJsonObj.put("Bonus", "");
				incomeJsonObj.put("Incentive", "");
				incomeJsonObj.put("Rent", "");
				incomeJsonObj.put("Pension", "");// xmlParser.getValueOf("PensionContribution")
				incomeJsonObj.put("AUBE CDs/TDS profit", "");
				incomeJsonObj.put("Allowances", "");// Allowances
				incomeJsonObj.put("Income in Month 1", xmlParser.getValueOf("BasicSalary"));// BasicSalary
				incomeJsonObj.put("Income in Month 2", xmlParser.getValueOf("BasicSalary"));
				incomeJsonObj.put("Income in Month 3", xmlParser.getValueOf("BasicSalary"));
				incomeJsonObj.put("Income in Month 4", xmlParser.getValueOf("BasicSalary"));
				incomeJsonObj.put("Income in Month 5", xmlParser.getValueOf("BasicSalary"));
				incomeJsonObj.put("Income in Month 6", xmlParser.getValueOf("BasicSalary"));
				incomeJsonObj.put("Income in Month 7", xmlParser.getValueOf("BasicSalary"));
				incomeJsonObj.put("Income in Month 8", xmlParser.getValueOf("BasicSalary"));
				incomeJsonObj.put("Income in Month 9", xmlParser.getValueOf("BasicSalary"));
				incomeJsonObj.put("Income in Month 10", xmlParser.getValueOf("BasicSalary"));
				incomeJsonObj.put("Income in Month 11", xmlParser.getValueOf("BasicSalary"));
				incomeJsonObj.put("Income in Month 12", xmlParser.getValueOf("BasicSalary"));
				incomeJsonObj.put("Total Income Amount (Monthly)", xmlParser.getValueOf("BasicSalary"));
				incomeJsonObj.put("Total Income Amount (Yearly)",
						Double.toString(Double.parseDouble(xmlParser.getValueOf("BasicSalary")) * 12));
				incomeJsonObj.put("Additional Income", "");//
				incomeJsonObj.put("Source of additional income", "");
				incomeJsonArr.add(incomeJsonObj);
				ifr.addDataToGrid("table105", incomeJsonArr);
				ifr.setValue("NetSalary", xmlParser.getValueOf("BasicSalary"));//
				ifr.setValue("GrossSalary", "");

				JSONArray addressJsonArr = new JSONArray();
				JSONObject addressJsonObj = new JSONObject();
				addressJsonObj.put("Customer Type ", CustomerType);
				// String typeOfResidence =
				// (xmlParser.getValueOf("TypeOfResidence") == "1") ? "Resident"
				// : "Non Resident";
				addressJsonObj.put("Type of residence", "");//
				addressJsonObj.put("Address Line 1: House number/Flat no.", xmlParser.getValueOf("Address1"));
				addressJsonObj.put("Address Line 2 : Building & Block", xmlParser.getValueOf("Address2"));
				addressJsonObj.put("Address Line 3", xmlParser.getValueOf("Address3"));
				addressJsonObj.put("Address Line 4", xmlParser.getValueOf("Address4"));
				addressJsonObj.put("City", xmlParser.getValueOf("Address5"));
				addressJsonObj.put("Country", xmlParser.getValueOf("ResCountry"));//
				addressJsonObj.put("PO BOX / ZIP Code", xmlParser.getValueOf("ZipCode"));//
				addressJsonObj.put("Residence Status", "");
				addressJsonObj.put("No. of Years at  Address", "");// CurrentAddressFrom
				addressJsonObj.put("Area Type", "");
				addressJsonObj.put("Other Address Detail", "");
				addressJsonArr.add(addressJsonObj);
				ifr.addDataToGrid("table108", addressJsonArr);

				JSONArray empJsonArr = new JSONArray();
				JSONObject empJsonObj = new JSONObject();
				empJsonObj.put("Customer Type ", CustomerType);
				empJsonObj.put("Type of Employment", "");
				empJsonObj.put("if selected Others, please specify", "");
				empJsonObj.put("Length of service", "");
				empJsonObj.put("Employer Name", xmlParser.getValueOf("EmployerName"));
				empJsonObj.put("Joining Date", "");
				empJsonObj.put("Previous Employer", "");
				empJsonObj.put("Previous Employment Period ", "");// YearsWithPrevEmpr
				empJsonObj.put("Previous Position", "");
				empJsonObj.put("Previous Salary", "");
				empJsonObj.put("Employer Category", "");
				empJsonArr.add(empJsonObj);
				ifr.addDataToGrid("table109", empJsonArr);

				JSONArray customerJsonArr = new JSONArray();
				JSONObject customerJsonObj = new JSONObject();
				
				customerJsonObj.put("Customer Type ", CustomerType);
				customerJsonObj.put("Cutomer Name", xmlParser.getValueOf("Name"));
				customerJsonObj.put("Nationality", xmlParser.getValueOf("Nationality"));
				customerJsonObj.put("Marital status", xmlParser.getValueOf("MaritalStatus"));
				customerJsonObj.put("Number of Dependant(s)", xmlParser.getValueOf("NoOfDependents"));//
				customerJsonObj.put("Reference Name", "");
				customerJsonObj.put("Reference Contact Number", "");
				customerJsonObj.put("Customer Mobile number", xmlParser.getValueOf("SmsAlert"));//
				ifr.setValue("MOBILE_NO", xmlParser.getValueOf("SmsAlert"));
				customerJsonObj.put("Contact Number 1", xmlParser.getValueOf("Tel1"));
				customerJsonObj.put("Contact Number 2", xmlParser.getValueOf("Tel2"));
				customerJsonObj.put("Email Address", "");
				customerJsonObj.put("Employment Type", "");
				customerJsonObj.put("Customer National ID", nID);
				customerJsonObj.put("Date of Issue  (NID)", "");
				customerJsonObj.put("Date of Expiry (NID)", "");
				customerJsonObj.put("Passport No", xmlParser.getValueOf("PassportNo"));
				String passportIssueDate = xmlParser.getValueOf("DateOfIssue").trim();
				if (passportIssueDate.length() == 8) {
					passportIssueDate = passportIssueDate.substring(0, 4) + "/" + passportIssueDate.substring(4, 6)
							+ "/" + passportIssueDate.substring(6, 8);
				} else {
					passportIssueDate = "";
				}
				customerJsonObj.put("Date of Issue (Passport)", passportIssueDate);//
				customerJsonObj.put("Date of Expiry (Passport)", "");
				customerJsonObj.put("Place of issue (Passport)", "");
				customerJsonObj.put("Education Qualification", "");
				customerJsonObj.put("Name of University", "");
				customerJsonObj.put("Name of Degree", "");
				customerJsonObj.put("Year", "");
				customerJsonObj.put("Other Qualification", "");
				customerJsonObj.put("No. of years in Egypt", "");
				customerJsonObj.put("Risk Rating", "");
				customerJsonObj.put("Mother's Name", "");
				customerJsonObj.put("Gender", gender);
				customerJsonObj.put("Date of Birth", dob);
				customerJsonObj.put("Age", calculateAge(dob));
				customerJsonObj.put("Reference Relation with Applicant", "");
				customerJsonObj.put("NID Check Status", "");
				customerJsonObj.put("Banking Relationship", "");
				customerJsonObj.put("What is the Name of your bank you deal with", "");
				customerJsonObj.put("Do you have Credit Card ?", "");
				customerJsonObj.put("Please provide the number and type", "");
				customerJsonObj.put("Number of Cards", xmlParser.getValueOf("NoOfCreditCards"));//
				customerJsonObj.put("Do you deal with AUB Egypt SAE", "");
				customerJsonObj.put("Account Number at AUB Egypt SAE", "");
				customerJsonObj.put("Account type", "");

				customerJsonArr.add(customerJsonObj);
				ifr.addDataToGrid("table107", customerJsonArr);
				ifr.setValue("CUSTOMER_BRANC_CODE", xmlParser.getValueOf("Branch"));
				/*
				ifr.setValue("Branch_Code", xmlParser.getValueOf("Branch"));
				String sQuery="select BRANCH_NAME,REGION FROM NG_ELOS_MAST_BRANCH WHERE BRANCH_CODE='"+xmlParser.getValueOf("Branch")+"'";
				LOS_EG.mLogger.info("DB query is: " +sQuery);
				List<List<String>>branchList=ifr.getDataFromDB(sQuery);
				LOS_EG.mLogger.info("DB Result is: " +branchList);
				
				if(branchList.size()>0)
				{
					ifr.setValue("Branch_Name", branchList.get(0).get(0));
					ifr.setValue("Region", branchList.get(0).get(1));
				}*/
				return "true~"+CustomerType + " Fetched Successfuly";
			} else {
				return "false~"+xmlParser.getValueOf("Desc");
			}
		} catch (Exception e) {
			LOS_EG.mLogger.info("Error In parseCustomerDetails" + e.getMessage());
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}

	}

	//Ajay 15Dec
	@SuppressWarnings({ "unchecked", "unused" })
	public static String parseCollateralAccEnq(String responseXML, IFormReference ifr,String CIF, String name, String nationality) {
		try {
			LOS_EG.mLogger.info("inside parseCollateralAccEnq");
			XMLParser xmlParser = new XMLParser();
			xmlParser.setInputXML(responseXML);
			if ("00000".equalsIgnoreCase(xmlParser.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				ByteArrayInputStream bis = new ByteArrayInputStream(responseXML.getBytes(StandardCharsets.UTF_8));
				org.w3c.dom.Document doc = db.parse(bis);
				NodeList nodeList = doc.getElementsByTagName("Account");
				JSONArray collJsonArr = new JSONArray();
				System.out.println(nodeList.getLength());
				for(int x=0,size= nodeList.getLength(); x<size; x++) 
				{	
					JSONObject collObj = new JSONObject();
					Node n=nodeList.item(x);
					NodeList list=n.getChildNodes();
					String bankValuation = "";
					String totalAssigned = "";
					String dealAmount = "";
					String colHeaderWOType="";
					for (int temp = 0; temp < list.getLength(); temp++)
					{
						Node node = list.item(temp);
						if (node.getNodeType() == Node.ELEMENT_NODE) 
						{
							Element eElement = (Element) node;
							collObj.put("Collateral CIF", CIF);
							collObj.put("Applicant Type", "Applicant");
							if ("CollateralTypeDescription".equals(eElement.getNodeName()))
							{
								collObj.put("Collateral Type", eElement.getTextContent());
							}
							else if ("AvailableCollateralValue".equals(eElement.getNodeName()))
							{
								collObj.put("Chosen Collateral Value", LOS_CommonMethods.handleDoubleZeroInResponse(eElement.getTextContent())); //Ajay 29Nov
							}
							else if ("CollateralDealReference".equals(eElement.getNodeName()))
							{
								//collObj.put("Collateral Reference", eElement.getTextContent());
								
							}
							else if("CollateralHeader".equals(eElement.getNodeName()))
							{
								String colHeader=eElement.getTextContent();
								collObj.put("CollHeader", colHeader);
								
								colHeaderWOType = colHeader;
								
								if(colHeaderWOType!=null & colHeaderWOType.length()>3)
								{
									colHeaderWOType = colHeaderWOType.substring(3,colHeaderWOType.length());
								}
								collObj.put("Collateral Reference", colHeaderWOType);
							}
							else if("CollateralType".equals(eElement.getNodeName()))
							{
								collObj.put("CollType", eElement.getTextContent());
							}
							else if("BankValuation".equals(eElement.getNodeName()))
							{
								bankValuation = LOS_CommonMethods.handleDoubleZeroInResponse(eElement.getTextContent());
							}
							else if("TotalAssigned".equals(eElement.getNodeName()))
							{
								totalAssigned = LOS_CommonMethods.handleDoubleZeroInResponse(eElement.getTextContent());
							}
						}
					}
					
					String inputDetailsFDDetails = getInputXML("FD_DETAIL");
					LOS_EG.mLogger.info("Socket Server IP is " + inputDetailsFDDetails.split("~")[1]);
					LOS_EG.mLogger.info("Socket Server Port is " + inputDetailsFDDetails.split("~")[2]);
					String inputXMLFDDetails = inputDetailsFDDetails.split("~")[0];
					inputXMLFDDetails = inputXMLFDDetails.substring(0, inputXMLFDDetails.indexOf("<No>")) + "<No>" + colHeaderWOType
							+ inputXMLFDDetails.substring(inputXMLFDDetails.indexOf("</No>"), inputXMLFDDetails.length());
					
					LOS_EG.mLogger.info("Final input XML for  FD Enq " + inputXMLFDDetails);
//					String responseXMLFDDetails = SocketConnector.getSocketResponse(inputXMLFDDetails,
//							inputDetailsFDDetails.split("~")[1], inputDetailsFDDetails.split("~")[2]);
					
					String responseXMLFDDetails = Integration_Handler.getRequestURL(inputXMLFDDetails,ifr);
							
					LOS_EG.mLogger.info("output XML for  FD Enq " + responseXMLFDDetails);
					
					XMLParser xmlParserFDDetails = new XMLParser();
					xmlParserFDDetails.setInputXML(responseXMLFDDetails);
					
					if ("00000".equalsIgnoreCase(xmlParserFDDetails.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParserFDDetails.getValueOf("Desc"))) 
					{
						collObj.put("InterestRate", LOS_CommonMethods.formatWithTwoDecimalZero(xmlParserFDDetails.getValueOf("IntrRate")));
						dealAmount = LOS_CommonMethods.formatWithTwoDecimalZero(xmlParserFDDetails.getValueOf("DealAmt"));
					}
					else
					{
						return "false~Error in fetching data from middleware for FD_DETAIL - "+xmlParserFDDetails.getValueOf("Desc");
					}
					try
					{
						LOS_EG.mLogger.info("bankValuation "+bankValuation);
						LOS_EG.mLogger.info("totalAssigned "+totalAssigned);
						LOS_EG.mLogger.info("dealAmount "+dealAmount);
						
						// double covRatio = LOS_CommonMethods.getDoubleAmount(bankValuation)/LOS_CommonMethods.getDoubleAmount(totalAssigned);
						double covRatio = LOS_CommonMethods.getDoubleAmount(bankValuation)/LOS_CommonMethods.getDoubleAmount(dealAmount)*100;
						LOS_EG.mLogger.info("covRatio D "+covRatio);
						
						String strCovRatio = String.format("%.02f", covRatio);
						LOS_EG.mLogger.info("covRatio S "+strCovRatio);
						
						if(strCovRatio.equalsIgnoreCase("Infinity"))
						{
							strCovRatio = "0";
						}
						
						collObj.put("Coverage Ratio", strCovRatio);
					}
					catch(Exception covExc)
					{
						LOS_EG.mLogger.info("Exception in calculating Coverage Ratio "+x);
						collObj.put("Coverage Ratio", "0");
					}
					
					collObj.put("Collateral Owner", name);
					collObj.put("Nationality", nationality);
					
					collJsonArr.add(collObj);
					LOS_EG.mLogger.info("Collateral "+collJsonArr);
					//
				}
				//ifr.clearTable("table129");
				//Ajay 22Dec start
				if(collJsonArr.size()>0)
				{
					ifr.addDataToGrid("table129", collJsonArr);
					LOS_EG.mLogger.info("Collateral Grid Data : "+ifr.getDataFromGrid("table129").toString());
					return "true~Collateral Details Fetched Successfully";
				}	
				else
				{
					return "false~No Collateral Found";
				}
				//Ajay 22Dec end
				/*
				 * ifr.setValue("table129_CollateralType",
				 * xmlParser.getValueOf("CollateralType"));
				 * ifr.setValue("table129_AvailableCollateralValue",
				 * xmlParser.getValueOf("AvailableCollateralValue"));
				 */
				 
				 
				
			}
			else {
					return "false~"+xmlParser.getValueOf("Desc");
				}
		} catch (Exception e) {
			LOS_EG.mLogger.info("Error In parseCollateralAccEnq" + e);
			LOS_EG.printException(e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			return "false~Error in fetching data from middleware";
		}
	}
	public static String parseCreditScore(String responseXML, IFormReference ifr)
	{
		try
		{
		LOS_EG.mLogger.info("inside parseCreditScore");
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(responseXML);
		if ("00000".equalsIgnoreCase(xmlParser.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) 
		{
			
			ifr.setValue("Credit_Scoring",xmlParser.getValueOf("score"));
			return "true~Credit Score Details Fetched Successfully";
		}
		else
		{
			return "true~Error in fetching data from middleware";
		}
		}
		catch (Exception e) {
			LOS_EG.mLogger.info("Error In parseCollateralAccEnq" + e);
			LOS_EG.printException(e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			return "false~Error in fetching data from middleware";
		}
		
	}
	public static String parseNIDCheck(String responseXML, IFormReference ifr)
	{
		LOS_EG.mLogger.info("inside parseNIDCheck");
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(responseXML);
		if ("00000".equalsIgnoreCase(xmlParser.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) 
		{
			
			ifr.setValue("table107_NID_Check_Status",xmlParser.getValueOf("Fcn"));
			String Address = xmlParser.getValueOf("Address");
			String BirthDate = xmlParser.getValueOf("BirthDate");
			String CardExpirationDate = xmlParser.getValueOf("CardExpirationDate");
			String FamilyName= xmlParser.getValueOf("FamilyName");
			String Gender= xmlParser.getValueOf("Gender");
			String Governorate= xmlParser.getValueOf("Governorate");
			String MotherFamilyName= xmlParser.getValueOf("MotherFamilyName");
			String MotherFirstName= xmlParser.getValueOf("MotherFirstName");
			String NationalIdNumber=xmlParser.getValueOf("NationalIdNumber");
			String PersonName= xmlParser.getValueOf("PersonName");
			String PoliceStation = xmlParser.getValueOf("PoliceStation");
			String ReferenceNumber=xmlParser.getValueOf("ReferenceNumber");
			String RequestNumber=xmlParser.getValueOf("RequestNumber");
			String RequestTimestamp=xmlParser.getValueOf("RequestTimestamp");
			String fcn = xmlParser.getValueOf("Fcn");
			return "true~NID Check Performed Successfully~"+Address+","+BirthDate+","+CardExpirationDate+","+FamilyName+","+Gender+","+Governorate+","+MotherFamilyName+","+MotherFirstName+","+NationalIdNumber+","+PersonName+","+PoliceStation+","+ReferenceNumber+","+fcn+","+RequestNumber+","+RequestTimestamp;
			
		}
		else 
		{
			return "false~Error in fetching data from middlewarer";
		}
	}
	public static String parseLoanCalculator(String responseXML, IFormReference ifr,String type)
	{
		LOS_EG.mLogger.info("inside parseLoanCalculator");
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(responseXML);
		try
		{
			if ("00000".equalsIgnoreCase(xmlParser.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) 
			{
				String osBalance=xmlParser.getValueOf("Interest");
				osBalance=osBalance.replaceFirst("^0*", "");
				String principleBlance=xmlParser.getValueOf("Principle");
				principleBlance=principleBlance.replaceFirst("^0*", "");
				
				if(principleBlance.equalsIgnoreCase(""))
				{
					principleBlance = "0";
				}
				if(osBalance.equalsIgnoreCase(""))
				{
					osBalance = "0";
				}
				Double emiAmmount=Double.parseDouble(osBalance)+Double.parseDouble(principleBlance);
				if ("Monthly".equalsIgnoreCase(type))
				{
				ifr.setValue("table117_YearlyInstallment", new DecimalFormat(",000").format(emiAmmount));
				}
				else if ("Quarterly".equalsIgnoreCase(type))
				{
				ifr.setValue("table117_InstalmentAmntQtrly", new DecimalFormat(",000").format(emiAmmount));
				}
				else if ("SemiAnnual".equalsIgnoreCase(type))
				{
				ifr.setValue("table117_InstallmentAmtMonthly", new DecimalFormat(",000").format(emiAmmount));
				}
				else if ("Yearly".equalsIgnoreCase(type))
				{
				ifr.setValue("table117_InstallmentAmtBulk", new DecimalFormat(",000").format(emiAmmount));
				}

				return "true~Details Fetched Successfuly";
			}
			else 
			{
				return "false~Error in fetching data from middleware";
			}
		}
		catch(Exception e)
		{
			LOS_EG.mLogger.info("Error In parseMaxEligibility" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
		
	}
	@SuppressWarnings({ "unchecked", "unused" })
	public static String parseLoanSummary(String responseXML, IFormReference ifr) throws ParserConfigurationException, SAXException, IOException
	{
		try
		{
			XMLParser xmlParser = new XMLParser();
			xmlParser.setInputXML(responseXML);
			
			if (!"00000".equalsIgnoreCase(xmlParser.getValueOf("Code")))
			{
				return "false~Error in fetching data from middleware - "+xmlParser.getValueOf("Desc");
			}
			
			if ("0".equalsIgnoreCase(xmlParser.getValueOf("Count")))
			{
				return "false~No Data found";
			}
			
			if (responseXML.indexOf("<Loan></Loan>")>-1)
			{
				return "false~No Data found";
			}
			
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		ByteArrayInputStream bis = new ByteArrayInputStream(responseXML.getBytes());
		org.w3c.dom.Document doc = db.parse(bis);
		NodeList nodeList = doc.getElementsByTagName("Loan");
		for(int x=0,size= nodeList.getLength(); x<size; x++) 
		{	
			JSONArray loanSummaryJsonArray = new JSONArray();
			JSONObject loanSummaryJsonObject = new JSONObject();
			//Node n=nodeList.item(0); //Ajay 15Dec
			Node n=nodeList.item(x); //Ajay 15Dec
			NodeList list=n.getChildNodes();
			for (int temp = 0; temp < list.getLength(); temp++)
			{
			Node node = list.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				if("No".equals(eElement.getNodeName()))
				{
					//
					loanSummaryJsonObject.put("Loan Application Detail", eElement.getTextContent());
				}
				else if("CustName".equals(eElement.getNodeName()))
				{
					loanSummaryJsonObject.put("Owner", eElement.getTextContent());
				}
				if("Type".equals(eElement.getNodeName()))
				{
					//loanSummaryJsonObject.put("Type", eElement.getTextContent());
				
				}
				if("TypeDesc".equals(eElement.getNodeName()))
				{
					loanSummaryJsonObject.put("Type", eElement.getTextContent());
				}
				
				if("Amt".equals(eElement.getNodeName()))
				{
					loanSummaryJsonObject.put("Limit ", eElement.getTextContent());
					// 
				}
				if("StartDate".equals(eElement.getNodeName()))
				{
					String startDate=eElement.getTextContent();
					startDate=startDate.substring(0, 8);
					startDate=startDate.substring(0,4)+"/"+startDate.substring(4,6)+"/"+startDate.substring(6,8);
					loanSummaryJsonObject.put("Start Date",startDate);
					//
				}
				if("Status".equals(eElement.getNodeName()))
				{
					
				}
				if("CcyCode".equals(eElement.getNodeName()))
				{
					
				}
				if("CcyDesc".equals(eElement.getNodeName()))
				{
					
				}
				if("CcyDesc".equals(eElement.getNodeName()))
				{
					
				}
				if("OsBal".equals(eElement.getNodeName()))
				{
					loanSummaryJsonObject.put("Outstanding", eElement.getTextContent());
					//
				}
				if("OsBalBase".equals(eElement.getNodeName()))
				{
					
				}
				if("PasDue".equals(eElement.getNodeName()))
				{
					
					//
				}
				if("MatDate".equals(eElement.getNodeName()))
				{
					
				}
				if("InstAmt".equals(eElement.getNodeName()))
				{
					loanSummaryJsonObject.put("Installment", eElement.getTextContent());//
				}
				if("NRepDate".equals(eElement.getNodeName()))
				{
					//loanSummaryJsonObject.put("Arrears Status", eElement.getTextContent());
				}
				if("NRepAmt".equals(eElement.getNodeName()))
				{
					
				}
				
			}
			}
			loanSummaryJsonArray.add(loanSummaryJsonObject);
			ifr.addDataToGrid("table112", loanSummaryJsonArray);
			}
			
		return "true~Internal Liability Fetched Successfuly";
		}
			catch(Exception e)
			{
				
					LOS_EG.mLogger.info("Error In parseLoanSummary" + e);
					LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
					LOS_EG.printException(e);
					return "false~Error in fetching data from middleware";
				
			}
		
	}
	
	@SuppressWarnings("unchecked")
	public static String parseiScoreOld(String responseXML, IFormReference ifr,String ButtonID)
	{
		LOS_EG.mLogger.info("inside parseiScore Old");
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(responseXML);
		try
		{
			if ("00000".equalsIgnoreCase(xmlParser.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) 
			{
				if(ButtonID.equalsIgnoreCase("button166"))
				{
					ifr.setValue("I_Score_Rating", xmlParser.getValueOf("SCORE"));
					if(xmlParser.getValueOf("SCORE").equalsIgnoreCase("0") || xmlParser.getValueOf("SCORE").equalsIgnoreCase(""))
					{
						ifr.setValue("IScore_Grade", "No Data");
						ifr.setValue("I_Score_Rating", "0");
					}
					else if(Integer.parseInt(xmlParser.getValueOf("SCORE"))<=400)
					{
						ifr.setValue("IScore_Grade", "Defaulting");
					}
					else if(Integer.parseInt(xmlParser.getValueOf("SCORE"))>=401 && Integer.parseInt(xmlParser.getValueOf("SCORE"))<=625)
					{
						ifr.setValue("IScore_Grade", "High Risk");
					}
					else if(Integer.parseInt(xmlParser.getValueOf("SCORE"))>=626 && Integer.parseInt(xmlParser.getValueOf("SCORE"))<=700)
					{
						ifr.setValue("IScore_Grade", "Good");
					}
					else if(Integer.parseInt(xmlParser.getValueOf("SCORE"))>=701 && Integer.parseInt(xmlParser.getValueOf("SCORE"))<=750)
					{
						ifr.setValue("IScore_Grade", "Very Good");
					}
					else if(Integer.parseInt(xmlParser.getValueOf("SCORE"))>=751 && Integer.parseInt(xmlParser.getValueOf("SCORE"))<=850)
					{
						ifr.setValue("IScore_Grade", "Outstanding");
					}
					
					try {
						return attachIscorePDF(xmlParser.getValueOf("EncodedCreditReportPDF"),ifr);
						}
						catch (JPISException e) {
							LOS_EG.mLogger.info("Error In Iscore Attachment" + e);
							e.printStackTrace();
							return "false~Error in fetching data from middleware";
						}
				}
				else
				{
					//for active account
					LOS_EG.mLogger.info("inside for active account");
					ifr.clearTable("table120"); //Ajay 14Nov
						ifr.addDataToGrid("table120", iscoreMultipleTagParseOld(responseXML, "CREDIT_DETAILS"));	
						
					//for closed account
						LOS_EG.mLogger.info("inside for closed account");
						ifr.addDataToGrid("table120", iscoreMultipleTagParseOld(responseXML, "CLOSED_ACCOUNTS"));
						
					LOS_EG.mLogger.info("before attach document");
					
					return "true~Liabilities Fetched  Successfully";
					
				}
			}
			else if ("Subject Not Found".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) 
			{
				if(ButtonID.equalsIgnoreCase("button166"))
				{
					ifr.setValue("IScore_Grade", "No Data");
					ifr.setValue("I_Score_Rating", "0");
				}
				return "false~Error in fetching data from middleware - "+xmlParser.getValueOf("Desc");
			}
			else 
			{
				return "false~Error in fetching data from middleware - "+xmlParser.getValueOf("Desc");
			}
		}
		catch (Exception e) 
		{
			LOS_EG.mLogger.info("Error In parseMaxEligibility" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
		
		//return "false~Error in fetching data from middleware";
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public static String parseiScoreNew(String responseXML, IFormReference ifr,String ButtonID)
	{
		LOS_EG.mLogger.info("inside parseiScore new");
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(responseXML);
		try
		{
			if ("00000".equalsIgnoreCase(xmlParser.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) 
			{
				if(ButtonID.equalsIgnoreCase("button166"))
				{
					ifr.setValue("I_Score_Rating", xmlParser.getValueOf("SCORE"));
					if(xmlParser.getValueOf("SCORE").equalsIgnoreCase("0") || xmlParser.getValueOf("SCORE").equalsIgnoreCase(""))
					{
						ifr.setValue("IScore_Grade", "No Data");
						ifr.setValue("I_Score_Rating", "0");
					}
					else if(Integer.parseInt(xmlParser.getValueOf("SCORE"))<=400)
					{
						ifr.setValue("IScore_Grade", "Defaulting");
					}
					else if(Integer.parseInt(xmlParser.getValueOf("SCORE"))>=401 && Integer.parseInt(xmlParser.getValueOf("SCORE"))<=625)
					{
						ifr.setValue("IScore_Grade", "High Risk");
					}
					else if(Integer.parseInt(xmlParser.getValueOf("SCORE"))>=626 && Integer.parseInt(xmlParser.getValueOf("SCORE"))<=700)
					{
						ifr.setValue("IScore_Grade", "Good");
					}
					else if(Integer.parseInt(xmlParser.getValueOf("SCORE"))>=701 && Integer.parseInt(xmlParser.getValueOf("SCORE"))<=750)
					{
						ifr.setValue("IScore_Grade", "Very Good");
					}
					else if(Integer.parseInt(xmlParser.getValueOf("SCORE"))>=751 && Integer.parseInt(xmlParser.getValueOf("SCORE"))<=850)
					{
						ifr.setValue("IScore_Grade", "Outstanding");
					}
					
					try {
						return attachIscorePDF(xmlParser.getValueOf("PDFStream"),ifr);
						}
						catch (JPISException e) {
							LOS_EG.mLogger.info("Error In Iscore Attachment" + e);
							e.printStackTrace();
							return "false~Error in fetching data from middleware";
						}
				}
				else
				{
					//for active account
					LOS_EG.mLogger.info("inside for active account");
					ifr.clearTable("table120"); //Ajay 14Nov
						ifr.addDataToGrid("table120", iscoreMultipleTagParseNew(responseXML, "OpenAccountDetails"));	
						
					//for closed account
						LOS_EG.mLogger.info("inside for closed account");
						ifr.addDataToGrid("table120", iscoreMultipleTagParseNew(responseXML, "ClosedAccountDetails"));
						
					LOS_EG.mLogger.info("before attach document");
					
					return "true~Liabilities Fetched  Successfully";
					
				}
			}
			else if ("Subject Not Found".equalsIgnoreCase(xmlParser.getValueOf("Desc")) || "true".equalsIgnoreCase(xmlParser.getValueOf("IsNoHit"))) 
			{
				if(ButtonID.equalsIgnoreCase("button166"))
				{
					ifr.setValue("IScore_Grade", "No Data");
					ifr.setValue("I_Score_Rating", "0");
				}
				return "false~Error in fetching data from middleware - "+xmlParser.getValueOf("Desc");
			}
			else 
			{
				return "false~Error in fetching data from middleware - "+xmlParser.getValueOf("Desc");
			}
		}
		catch (Exception e) 
		{
			LOS_EG.mLogger.info("Error In parseMaxEligibility" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
		
		//return "false~Error in fetching data from middleware";
		
		
	}
	public static String attachIscorePDF(String encodedPDF,IFormReference ifr) throws FileNotFoundException, IOException, JPISException
	{
		String status="false~Error in iScore PDF attachment";
		String tempFileLocation=System.getProperty("user.dir")+File.separator+"ISCoreTemplate"+File.separator+ifr.getObjGeneralData().getM_strProcessInstanceId();
		LOS_EG.mLogger.info("@@@@@@Iscore PDF path is "+tempFileLocation);
		File file = new File(tempFileLocation);
		if (!file.exists()) 
		{
			file.mkdir();
		}
		String fname="IScore.pdf";
		File f1=new File(tempFileLocation,fname);
		if(f1.exists())
		{
			f1.delete();
		}

		try ( FileOutputStream fos = new FileOutputStream(f1); ) {
			
		      byte[] decoder = Base64.getDecoder().decode(encodedPDF);

		      fos.write(decoder);
		}
		try
		{
			JPDBRecoverDocData JPISDEC = new JPDBRecoverDocData();
			JPISDEC.m_cDocumentType = 'N';
			JPISDEC.m_sVolumeId = (short) ifr.getObjGeneralData().getM_iVolId();
			LOS_EG.mLogger.info("Before Add document");
			JPISIsIndex IsIndex = new JPISIsIndex();
			CPISDocumentTxn.AddDocument_MT(null, ifr.getObjGeneralData().getM_strJTSIP(),
					(short) (Integer.parseInt(ifr.getObjGeneralData().getM_strJTSPORT())), ifr.getObjGeneralData().getM_strEngineName(), JPISDEC.m_sVolumeId, f1.getPath(), JPISDEC, "",
					IsIndex);
			LOS_EG.mLogger.info("After Add document");
			int intISIndex=(int)IsIndex.m_nDocIndex;
			int intVolumeId=(int)IsIndex.m_sVolumeId;
			
			String sQuery="SELECT FOLDERINDEX FROM PDBFOLDER WHERE NAME ='" +ifr.getObjGeneralData().getM_strProcessInstanceId()+ "'";
			LOS_EG.mLogger.info("1st sQuery is"+sQuery);
			List <List < String >> data=ifr.getDataFromDB(sQuery);
			String folderIndex="0";
			if(data.size()>0)
			{
				folderIndex=data.get(0).get(0);
			}
			
			 sQuery="select DOCSIZE from isdoc WHERE docindex ='" + intISIndex + "' and volumeid = '"+ifr.getObjGeneralData().getM_iVolId()+"' order by docsize desc";
			 LOS_EG.mLogger.info("2st sQuery is"+sQuery);
			 List <List < String >> docSizeData=ifr.getDataFromDB(sQuery);
				String docSize="0";
				
				if(docSizeData.size()>0)
				{
					docSize=docSizeData.get(0).get(0);
				}
				String strISIndex =String.valueOf(JPISDEC.m_nDocIndex)+ "#"+ String.valueOf(JPISDEC.m_sVolumeId)+ "#" ;
				String intISIndexVolId = Integer.toString(intISIndex)  +"#"+intVolumeId+"#";
				LOS_EG.mLogger.info("is are --"+strISIndex+"  "+intISIndexVolId);
			    String addDocinput=LOS_CommonMethods.get_NGOAddDocument_Input(ifr,folderIndex,docSize,"I-Score report - Protest & Bankruptcy",strISIndex,
					ifr.getObjGeneralData().getM_iVolId(),f1.getPath());
			    
			    LOS_EG.mLogger.info("add doc input is --"+addDocinput);

			    String adddocOutput=LOS_CommonMethods.ExecuteQueryOnServer(ifr,addDocinput);
			    LOS_EG.mLogger.info("Doc Index is "+adddocOutput);
			    XMLParser xmlParserAttach= new XMLParser();
			    xmlParserAttach.setInputXML(adddocOutput);
			    if("0".equalsIgnoreCase(xmlParserAttach.getValueOf("Status")) )
			    {
			    	LOS_EG.mLogger.info("Doc Index is "+xmlParserAttach.getValueOf("DocumentIndex"));
			    	return "true~Details feteched and iScore Document Attached successfuly~"+adddocOutput;
			    }
			    else
			    {
			    	return "false~Document Not Attached";
			    }
			    
		}
		catch (Exception e) {
			LOS_EG.mLogger.info("Error In adding Document" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
		
		
	}
	@SuppressWarnings("unchecked")
	public static String parseMaxEligibility(String responseXML, IFormReference ifr,String segment,String programType) {
		try {
			LOS_EG.mLogger.info("inside parseMaxEligibility");
			XMLParser xmlParser = new XMLParser();
			xmlParser.setInputXML(responseXML);
			JSONArray maxEligibilityJsonArr = new JSONArray();
			JSONObject maxEligibilityJsonObj = new JSONObject();
			maxEligibilityJsonObj.put("Program Type", programType);
			maxEligibilityJsonObj.put("Company Category", segment);
			maxEligibilityJsonObj.put("Max Age at Maturity", "");
			maxEligibilityJsonObj.put("Minimum Income",xmlParser.getValueOf("min_income"));
			maxEligibilityJsonObj.put("Minimum Loan Amount",xmlParser.getValueOf("min_loan_amt"));
			maxEligibilityJsonObj.put("Maximum Eligible Loan Amount", xmlParser.getValueOf("max_loan_amt"));
			LOS_EG.mLogger.info("JSON OBJECT IS "+maxEligibilityJsonObj);
			ifr.clearTable("table144");
			maxEligibilityJsonArr.add(maxEligibilityJsonObj);
			ifr.addDataToGrid("table144", maxEligibilityJsonArr);
			// Adding doc Verification grid start
			/*
			 * String[] documents=xmlParser.getValueOf("documentation").split(",");
			 * JSONArray docJsonArr = new JSONArray(); for (String doc : documents) {
			 * JSONObject docJsonObj = new JSONObject();
			 * docJsonObj.put("Document List",doc); docJsonObj.put("Mandatory", "No");
			 * docJsonObj.put("Expected Date", new SimpleDateFormat("yyyy/MM/dd").format(new
			 * Date())); docJsonObj.put("Workstep / Deptt Name", "Introduction"); //// // //
			 * // docJsonArr.add(docJsonObj); }
			 * ifr.addDataToGrid("documentVeificationTable", docJsonArr);
			 */
	    	// Adding doc Verification grid End
			return "true~Maximum Eligibility Fetched Successfully";
		} catch (Exception e) {
			LOS_EG.mLogger.info("Error In parseMaxEligibility" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data from middleware";
		}
		
	}
	@SuppressWarnings("unchecked")
	public static String parseExaception(String responseXML, IFormReference ifr,String programType) {
		try {
			LOS_EG.mLogger.info("inside parseMException");
			XMLParser xmlParser = new XMLParser();
			xmlParser.setInputXML(responseXML);
			LOS_EG.mLogger.info("negativeprofes_exceptions"+xmlParser.getValueOf("negativeprofes_exceptions"));
			ifr.clearTable("AutoDev");
			
			if(!(xmlParser.getValueOf("loan_tenor_eceptionx").equalsIgnoreCase("NA")))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", xmlParser.getValueOf("loan_tenor_eceptionx"));
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
			}
			if(!(xmlParser.getValueOf("min_los_exception").equalsIgnoreCase("NA")))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", xmlParser.getValueOf("min_los_exception"));
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
			}
			if(!(xmlParser.getValueOf("area_exception").equalsIgnoreCase("NA")))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", xmlParser.getValueOf("area_exception"));
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				
			}
			if(!(xmlParser.getValueOf("age_exception").equalsIgnoreCase("NA")))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", xmlParser.getValueOf("age_exception"));
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				
			}
			
			if(!(xmlParser.getValueOf("bluecollar_exception").equalsIgnoreCase("NA")))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", xmlParser.getValueOf("bluecollar_exception"));
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				
			}
			if(!(xmlParser.getValueOf("dbr_exception").equalsIgnoreCase("NA")))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", xmlParser.getValueOf("dbr_exception"));
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				
			}
			if(!(xmlParser.getValueOf("loan_amt_exception").equalsIgnoreCase("NA")))
			{
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", xmlParser.getValueOf("loan_amt_exception"));
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				
			}
			if(!(xmlParser.getValueOf("negativeprofes_exceptions").equalsIgnoreCase("NA")))
			{
				LOS_EG.mLogger.info("inside negativeprofes_exceptions "+xmlParser.getValueOf("negativeprofes_exceptions"));
				JSONArray exceptionJsonArr = new JSONArray();
				JSONObject exceptionJsonObj = new JSONObject();
				exceptionJsonObj.put("Type of Exception", xmlParser.getValueOf("negativeprofes_exceptions"));
				exceptionJsonObj.put("Applicant Type","Applicant");
				exceptionJsonArr.add(exceptionJsonObj);
				ifr.addDataToGrid("AutoDev", exceptionJsonArr);
				
			}
			


			
			
			
			
			return "true~Exception Fetched Successfully";
		} catch (Exception e) {
			LOS_EG.mLogger.info("Error In parseMaxEligibilityException" + e);
			LOS_EG.mLogger.error(LOS_CommonMethods.getCustomStackTrace(e));
			LOS_EG.printException(e);
			return "false~Error in fetching data";
		}
		
	}
	
	public static String parseLoanBooking(String responseXML, IFormReference ifr)
	{
		LOS_EG.mLogger.info("inside parseLoanBooking");
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(responseXML);
		if ("00000".equalsIgnoreCase(xmlParser.getValueOf("Code")) && "SUCCESS".equalsIgnoreCase(xmlParser.getValueOf("Desc"))) 
		{
			ifr.setValue("table135_LOANREFNO", xmlParser.getValueOf("EQLoanRef"));
			//ifr.setValue("Loan_RM_REFNO", xmlParser.getValueOf("RMLoanRef"));
			//ifr.setValue("Loan_Error_Message", xmlParser.getValueOf("Error_message"));
			ifr.setStyle("button194", "disable", "true");
			return "True~Finance Booking Successful";
		}
		else 
		{
			return "false~Error in Booking Finance - "+xmlParser.getValueOf("Desc"); //Ajay 29Dec
		}
	}
    @SuppressWarnings("unchecked")
	public static String addDocGrid(String documentation,IFormReference ifr)
    {
    	
    	return "";
    	
    }
	public static String calculateAge(String dob) throws ParseException {

		if (!dob.equalsIgnoreCase("")) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new SimpleDateFormat("yyyy/MM/dd").parse(dob));
			LocalDate dateofBirth = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
					cal.get(Calendar.DATE));
			LocalDate currentDate = LocalDate.now();
			Period age = Period.between(dateofBirth, currentDate);
			return Integer.toString(age.getYears());
		} else {
			return "";
		}
	}

	@SuppressWarnings("unchecked")//,IFormReference ifr
	public static JSONArray iscoreMultipleTagParseOld(String xml, String tagName ) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		ByteArrayInputStream bis = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
		org.w3c.dom.Document doc = db.parse(bis);
		NodeList nodeList = doc.getElementsByTagName(tagName);
		JSONArray iscoreJsonArr = new JSONArray();
		
		System.out.println(nodeList.getLength());
		for(int x=0,size= nodeList.getLength(); x<size; x++) 
		{
			
			JSONObject iScoreJsonObj = new JSONObject();
			Node n=nodeList.item(x);
			NodeList list=n.getChildNodes();
			
			for (int temp = 0; temp < list.getLength(); temp++)
			{
			Node node = list.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) node;
			//System.out.println(eElement.getNodeName()+" @@Value@@@ "+eElement.getTextContent()); 
			if(tagName.equalsIgnoreCase("CLOSED_ACCOUNTS"))
			{
				iScoreJsonObj.put("Facility Status", "Closed");
			}
			else
			{
				iScoreJsonObj.put("Facility Status", "Open");
			}
			if ("CREDIT_FACILITY_VALUE".equals(eElement.getNodeName()))
			{
				iScoreJsonObj.put("Facility Type", eElement.getTextContent());
				if(eElement.getTextContent().equalsIgnoreCase("Credit Card"))
				{
					iScoreJsonObj.put("Tenor", "0");
				}
			}
			else if ("APPROVAL_AMOUNT".equals(eElement.getNodeName()))
			{
				iScoreJsonObj.put("Facility Amount", eElement.getTextContent());
			}
			else if("LOAN_TERM".equals(eElement.getNodeName()))
			{
				iScoreJsonObj.put("Tenor", eElement.getTextContent());
			}
			else if ("AMT_OF_INSTALMENT".equals(eElement.getNodeName()))
			{
				iScoreJsonObj.put("Installment Amount", eElement.getTextContent());
			}
			else if ("BANK_CODE".equals(eElement.getNodeName()))
			{
				iScoreJsonObj.put("Bank Name & Bank Code", eElement.getTextContent());
			}
			
			else if ("APPROVAL_DATE".equals(eElement.getNodeName()))
			{
				iScoreJsonObj.put("Start Date", eElement.getTextContent());
			}
			else if ("CURRENT_BALANCE".equals(eElement.getNodeName()))
			{
				iScoreJsonObj.put("Outstanding Balance", eElement.getTextContent());
			}
			else if ("CURRENCY".equals(eElement.getNodeName()))
			{
				iScoreJsonObj.put("Currency", eElement.getTextContent());
			}
			else if ("LIABILITY_INDICATOR_VALUE".equals(eElement.getNodeName()))
			{
				iScoreJsonObj.put("Type of Liability Indicator", eElement.getTextContent());
			}
			else if ("MAX_NUM_DAYS_DUE".equals(eElement.getNodeName()))
			{
				iScoreJsonObj.put("Maximum Number of Delay Days", eElement.getTextContent());
			}
			else if ("MAX_DATE_REPORTED".equals(eElement.getNodeName()))
			{
				iScoreJsonObj.put("Last Date of Reporting Maximum No. Of Delay Days", eElement.getTextContent());
			}
			else if ("ASSET_CLASSIFICATION_VALUE".equals(eElement.getNodeName()))
			{
				iScoreJsonObj.put("Liability Classification", eElement.getTextContent());
			}
			else if ("REPAYMENT_TYPE_VALUE".equals(eElement.getNodeName()))
			{
				iScoreJsonObj.put("Payment Frequency type", eElement.getTextContent());
			}
			
			
			/*
			 * Not found in response xml Marginal Interest Value
			 */
			
			}
			
			
			
			
			}
			iscoreJsonArr.add(iScoreJsonObj);
			//return iscoreJsonArr;
			//ifr.addDataToGrid("table120", iscoreJsonArr);
			
			
		}
		LOS_EG.mLogger.info("Iscore Json is "+iscoreJsonArr);
		return iscoreJsonArr;	
	}

	@SuppressWarnings("unchecked")//,IFormReference ifr
	public static JSONArray iscoreMultipleTagParseNew(String xml, String tagName) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		ByteArrayInputStream bis = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
		org.w3c.dom.Document doc = db.parse(bis);
		NodeList nodeList = doc.getElementsByTagName(tagName);
		JSONArray iscoreJsonArr = new JSONArray();
		
		System.out.println(nodeList.getLength());
		for(int x=0,size= nodeList.getLength(); x<size; x++) 
		{
			
			JSONObject iScoreJsonObj = new JSONObject();
			Node n=nodeList.item(x);
			NodeList list=n.getChildNodes();
			
			for (int temp = 0; temp < list.getLength(); temp++)
			{
				Node node = list.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element eElement = (Element) node;
					//System.out.println(eElement.getNodeName()+" @@Value@@@ "+eElement.getTextContent()); 
					if(tagName.equalsIgnoreCase("ClosedAccountDetails"))
					{
						iScoreJsonObj.put("Facility Status", "Closed");
					}
					else
					{
						iScoreJsonObj.put("Facility Status", "Open");
					}
					if ("FACILITY_NATURE_INDICATOR_DESC".equals(eElement.getNodeName()))
					{
						iScoreJsonObj.put("Facility Type", eElement.getTextContent());
						if(eElement.getTextContent().equalsIgnoreCase("Credit Card"))
						{
							iScoreJsonObj.put("Tenor", "0");
						}
					}
					else if ("SANCTION_AMT".equals(eElement.getNodeName()))
					{
						iScoreJsonObj.put("Facility Amount", eElement.getTextContent());
					}
					else if("LOAN_TERM".equals(eElement.getNodeName()))
					{
						iScoreJsonObj.put("Tenor", eElement.getTextContent());
					}
					else if ("AMT_OF_INSTALMENT".equals(eElement.getNodeName()))
					{
						iScoreJsonObj.put("Installment Amount", eElement.getTextContent());
					}
					else if ("DATA_PRDR_ID".equals(eElement.getNodeName()))
					{
						iScoreJsonObj.put("Bank Name & Bank Code", eElement.getTextContent());
					}
					
					else if ("SANCTION_DATE".equals(eElement.getNodeName()))
					{
						iScoreJsonObj.put("Start Date", eElement.getTextContent());
					}
					else if ("CURRENT_BAL_SV".equals(eElement.getNodeName()))
					{
						iScoreJsonObj.put("Outstanding Balance", eElement.getTextContent());
					}
					else if ("CURRENCY".equals(eElement.getNodeName()))
					{
						iScoreJsonObj.put("Currency", eElement.getTextContent());
					}
					else if ("LIABILITY_INDICATOR_DESC".equals(eElement.getNodeName())) //no Such Tag//Needs to be changed
					{
						iScoreJsonObj.put("Type of Liability Indicator", eElement.getTextContent());
					}
					else if ("MAX_NUM_DAYS_DUE".equals(eElement.getNodeName()))
					{
						iScoreJsonObj.put("Maximum Number of Delay Days", eElement.getTextContent());
					}
					else if ("DATE_REPORTED_NDPD".equals(eElement.getNodeName()))
					{
						iScoreJsonObj.put("Last Date of Reporting Maximum No. Of Delay Days", eElement.getTextContent());
					}
					else if ("ASSET_CLASSIFICATION_DESC".equals(eElement.getNodeName())) //no Such Tag//Needs to be changed
					{
						iScoreJsonObj.put("Liability Classification", eElement.getTextContent());
					}
					else if ("REPAYMENT_TYPE_DESC".equals(eElement.getNodeName())) //no Such Tag //Needs to be chnaged
					{
						iScoreJsonObj.put("Payment Frequency type", eElement.getTextContent());
					}					
					/*
					 * Not found in response xml Marginal Interest Value
					 */				
				}			
			}
			iscoreJsonArr.add(iScoreJsonObj);
			//return iscoreJsonArr;
			//ifr.addDataToGrid("table120", iscoreJsonArr);			
		}
		LOS_EG.mLogger.info("Iscore Json is "+iscoreJsonArr);
		return iscoreJsonArr;	
	}
	
	//Ajay 29Dec
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
}