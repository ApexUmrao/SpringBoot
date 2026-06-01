package com.newgen.util;

import java.util.ArrayList;
import java.util.List;
import com.newgen.log.LogMe;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class APCallCreateXML extends LogMe {
	static private String cabinetName;
//	static private int processDefId;

	public static void init(String cabName)
	{
		cabinetName = cabName;
//		processDefId = processdefId;
	}

	public static String APDelete(String tableName, String sWhere, String sessionId) throws NGException 
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APDelete_Input>").append("\n")
		.append("<Option>APDelete</Option>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<TableName>"+ tableName + "</TableName>").append("\n")
		.append( "<WhereClause>" + sWhere + "</WhereClause>").append("\n")
		.append("</APDelete_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());

		return outputXML;
	}

	public static String APInsert(String tableName,String columnName,String strValues, String sessionId) throws NGException
	{

		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APInsert_Input>").append("\n")
		.append("<Option>APInsert</Option>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<TableName>"+ tableName + "</TableName>").append("\n")
		.append("<ColName>" + columnName + "</ColName>").append("\n")
		.append( "<Values>" + strValues + "</Values>").append("\n")
		.append("</APInsert_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		return outputXML;   
	}

	public static String APSelect(String Query) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APSelectWithColumnNames_Input>").append("\n")
		.append("<Option>APSelectWithColumnNames</Option>").append("\n")
		.append("<EngineName>"+ cabinetName +"</EngineName>").append("\n")
		.append("<QueryString>"+Query+"</QueryString>").append("\n")
		.append("</APSelectWithColumnNames_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		return outputXML; 
	}

	public static String APUpdate(String tableName, String columnName, String strValues, String whereClause, String sessionId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APUpdate_Input>").append("\n")
		.append("<Option>APUpdate</Option>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<TableName>"+ tableName + "</TableName>").append("\n")
		.append("<ColName>" + columnName + "</ColName>").append("\n")
		.append( "<Values>" + strValues + "</Values>").append("\n")
		.append( "<WhereClause>" + whereClause + "</WhereClause>").append("\n")
		.append("</APUpdate_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		return outputXML;   
	}

	public static String APTemplate(String wiName, String sessionId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APTemplate_Input>").append("\n")
		.append("<Option>APTemplate</Option>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<Query>"+ wiName + "</Query>").append("\n")
		.append("</APTemplate_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		return outputXML;   
	}
	
	public static String APProcedure(String sessionId, String ProcName, String InValues, int NoOfCols) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APProcedure_Input>").append("\n")
		.append("<Option>APProcedure</Option>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<ProcName>"+ ProcName + "</ProcName>").append("\n")
		.append("<Params>" + InValues + "</Params>").append("\n")
		.append("<NoOfCols>" + NoOfCols + "</NoOfCols>").append("\n")
		.append("</APProcedure_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		return outputXML;   
	}

	public static String Webservice(String sessionId) throws NGException, InterruptedException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APWebService_Input>").append("\n")
		.append("<Option>WebService</Option>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<Calltype>CustEIDAInfo</Calltype>").append("\n")
		.append("<EIDANum>784199173063076</EIDANum>").append("\n")
		.append("<REF_No>9999</REF_No>").append("\n")
		.append("</APWebService_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		System.out.println(outputXML);
		return outputXML;   
	}
	public static List<ArrayList<String>> getDataFromDB(String columns,String tableName, String whereClause, String tagName){
		List<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		String query = "select "+columns+" from "+tableName;
		if(!"".equals(whereClause) && null != whereClause){
			query += " where "+whereClause+"";
		}  
		logger.info( "query :" +query);
		String outputXML;
		try{
			outputXML = APCallCreateXML.APSelect(query);
			logger.info( "outputXML :" +outputXML);
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			logger.info( "mainCode :" +mainCode);
			String tagNames[] = tagName.split(",");
			logger.info( "after split :" +tagNames.length);
			if(mainCode == 0){
				int start = xp.getStartIndex("Records", 0, 0);
				int deadEnd = xp.getEndIndex("Records", start, 0);
				int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
				int end = 0;
				for (int i = 0; i < noOfFields; ++i) {
					start = xp.getStartIndex("Record", end, 0);
					end = xp.getEndIndex("Record", start, 0);
					ArrayList<String> outputInner = new ArrayList<String>();
					for (int j =0; j < tagNames.length; ++j){
						String tagValue = xp.getValueOf(tagNames[j].trim(), start, end);
						logger.info( "tagValue :" +tagValue);
						outputInner.add(tagValue);
						logger.info( "outputInner :" +outputInner);
					}
					output.add(i,outputInner);
					logger.info( "output :" +output);
				}
			}
			logger.info( "output GETDATAFROMDB :" +output);
		} catch(Exception e) {
			logger.error( "error in getDataFromDB: ");
			logger.error( e);
		}
		return output;
	}
}
