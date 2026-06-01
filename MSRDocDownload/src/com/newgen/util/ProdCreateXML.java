package com.newgen.util;

import com.newgen.log.LogMe;
import com.newgen.omni.wf.util.excp.NGException;

public class ProdCreateXML extends LogMe {
	static private String cabinetName;
//	static private int processDefId;

	public static void init(String cabName)
	{
		cabinetName = cabName;
//		processDefId = processdefId;
	}

	public static String WMConnect(String sUsername, String sPassword) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WMConnect_Input>").append("\n")
		.append("<Option>WMConnect</Option>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<Name>" + sUsername + "</Name>").append("\n")
		.append("<Password>" + sPassword + "</Password>").append("\n")
		.append("<UserExist>Y</UserExist>").append("\n")
		.append("</WMConnect_Input>");			
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		//CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "WMConnect OutputXML ===>" + outputXML);
		return outputXML;    
	}

	public static String WMGetWorkItem(String sessionId, String ProcessInstanceId, int WorkitemId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WMGetWorkItem_Input>").append("\n")
		.append("<Option>WMGetWorkItem</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<ProcessInstanceId>" + ProcessInstanceId + "</ProcessInstanceId>").append("\n")
		.append("<WorkitemId>" + WorkitemId + "</WorkitemId>").append("\n")
		.append("</WMGetWorkItem_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		logger.info( "WMGetWorkItem OutputXML ===>" + outputXML);
		return outputXML;   
	}

	public static String WMCompleteWorkItem(String sessionId, String ProcessInstanceId, int WorkitemId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WMCompleteWorkItem_Input>").append("\n")
		.append("<Option>WMCompleteWorkItem</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<ProcessInstanceId>" + ProcessInstanceId + "</ProcessInstanceId>").append("\n")
		.append("<WorkitemId>" + WorkitemId + "</WorkitemId>").append("\n")
		.append("</WMCompleteWorkItem_Input>");
		//Lock Workitem
		WMGetWorkItem(sessionId, ProcessInstanceId, WorkitemId);		
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		logger.info( "WMCompleteWorkItem OutputXML ===>" + outputXML);
		return outputXML;   
	}

	public static String IsSessionValid(String sessionId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WFIsSessionValid_Input>").append("\n")
		.append("<Option>WFIsSessionValid</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("</WFIsSessionValid_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		//CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "IsSessionValid OutputXML ===>" + outputXML);
		return outputXML;   
	}
}

