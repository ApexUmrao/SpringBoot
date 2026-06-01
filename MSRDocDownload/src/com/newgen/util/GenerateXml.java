
package com.newgen.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


import com.newgen.omni.wf.util.excp.NGException;



public class GenerateXml
{
	public  static String getWICompleteXML( String processInstanceId, String workItemId, String sessionId, String cabinetName) throws Exception 
	{
		String xml = "<?xml version=\"1.0\" ?>" + "\n" +
				"<WMCompleteWorkItem_Input>" + "\n" +
				"<Option>WMCompleteWorkItem</Option>" + "\n" +
				"<EngineName>"+ cabinetName + "</EngineName>" + "\n" +
				"<SessionId>" + sessionId + "</SessionId>" + "\n" +
				"<ProcessInstanceId>" + processInstanceId + "</ProcessInstanceId>" + "\n" +
				"<WorkItemId>" + workItemId + "</WorkItemId>" + "\n" +
				"<AuditStatus></AuditStatus>" + "\n" +
				"</WMCompleteWorkItem_Input>";
		String outputXml = ExecuteXML.executeXML(xml);
		return outputXml;
	}

	public static String WFUploadWorkItem(String sessionId, String initiateAlso, HashMap<String, String> attribMap,
			String documents, int processDefId, String initiatorActivityName,String cabinetName) throws NGException
	{
		StringBuilder attribXML = new StringBuilder("");
		StringBuilder key = new StringBuilder("");
		StringBuilder val = new StringBuilder("");
		Set<Map.Entry<String, String>> entrySet = attribMap.entrySet();
		Iterator<Entry<String,String>> entrySetIterator = entrySet.iterator();
		while(entrySetIterator.hasNext()){
			Entry<String, String> entry = entrySetIterator.next();
			key = key.append(entry.getKey());	
			val = val.append(entry.getValue());			
			attribXML.append(entry.getKey() + (char)21 + val.toString() + (char)25);//key changed by sanal
			key.setLength(0);
			val.setLength(0);
		}
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WFUploadWorkItem_Input>").append("\n")
		.append("<Option>WFUploadWorkItem</Option>").append("\n")
		.append("<SessionId>" +sessionId+ "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<ProcessDefId>" + processDefId + "</ProcessDefId>").append("\n")
		.append("<InitiateAlso>" + initiateAlso + "</InitiateAlso>").append("\n")
		.append("<ValidationRequired></ValidationRequired>").append("\n")
		.append("<DataDefName></DataDefName>").append("\n")
		.append("<Documents>"+documents+"</Documents>").append("\n")
		.append("<InitiateFromActivityId>1</InitiateFromActivityId>").append("\n")
		.append("<InitiateFromActivityName>"+initiatorActivityName+"</InitiateFromActivityName>").append("\n")
		.append("<Attributes>"+ attribXML + "</Attributes>").append("\n")
		.append("</WFUploadWorkItem_Input>");

		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		return outputXML;  
	}

	
	
	
	public static  String getGetWorkItemXML( String processInstanceId, String workItemId, String sessionId, String cabinetName) throws Exception
	{
		String xml = "<?xml version=\"1.0\" ?>" + "\n" +
				"<WMGetWorkItem_Input>"+ "\n" +
				"<Option>WMGetWorkItem</Option>"+ "\n" +
				"<EngineName>" + cabinetName + "</EngineName>"+ "\n" +
				"<SessionId>" + sessionId + "</SessionId>"+ "\n" +
				"<ProcessInstanceId>" + processInstanceId + "</ProcessInstanceId>" + "\n" +
				"<WorkItemId>" + workItemId + "</WorkItemId>"+ "\n" +
				"</WMGetWorkItem_Input>";
		String outputXml = ExecuteXML.executeXML(xml);
		return outputXml;
	}

	public static  String getConnectInputXML(String cabinetName, String username, String password) 
	{
		String connectInputXML="<?xml version=\"1.0\"?>" +
				"<WMConnect_Input>" +
				"<Option>WMConnect</Option>" +
				"<EngineName>" + cabinetName + "</EngineName>" +
				"<Participant>" +
				"<Name>" + username + "</Name>" +
				"<Password>" + password + "</Password>" +
				"<Scope></Scope>" +
				"<UserExist>N</UserExist>" +
				"<Locale>en-us</Locale>" +
				"<ParticipantType>U</ParticipantType>" +
				"</Particpant>" + 
				"</WMConnect_Input>";
		return connectInputXML;
	}

	public static  String getFetchWorkItemsInputXML(String sessionId, String cabinetName, String queueId) 
	{
		String xml = "<?xml version=\"1.0\"?>" + "\n" +
				"<WMFetchWorkItems_Input>" + "\n" +
				"<Option>WMFetchWorkItem</Option>" + "\n" +
				"<EngineName>" + cabinetName + "</EngineName>" + "\n" +
				"<SessionID>" + sessionId + "</SessionID>" + "\n" +
				"<QueueId>" + queueId + "</QueueId>" + "\n" +
				"<BatchInfo>" + "\n" +
				"<NoOfRecordsToFetch>100</NoOfRecordsToFetch>" + "\n" +
				"<LastWorkItem></LastWorkItem>" + "\n" +
				"<LastValue></LastValue>" + "\n" +
				"<LastProcessInstance></LastProcessInstance>" + "\n" +
				"</BatchInfo>" + "\n" +
				"</WMFetchWorkItems_Input>";

		return xml;
	}

	public static  String getFetchWorkItemAttributesXML(String processInstanceId, String workItemId, String sessionId, String cabinetName) {
		String xml = "<?xml version=\"1.0\" ?>" + "\n" +
				"<WMFetchWorkItemAttributes_Input>" + "\n" +
				"<Option>WMFetchWorkItemAttributes</Option>" + "\n" +
				"<EngineName>" + cabinetName + "</EngineName>" + "\n" +
				"<SessionId>" + sessionId + "</SessionId>" + "\n" +
				"<ProcessInstanceId>" + processInstanceId + "</ProcessInstanceId>" + "\n" +
				"<WorkItemId>" + workItemId + "</WorkItemId>" + "\n" +		
				"</WMFetchWorkItemAttributes_Input>" ;

		return xml;
	}

	public static String APProcedureWithColumnNames(String cabinetName, String sessionId, String strProcName, String strParams) {

		return "<?xml version=\"1.0\" ?>" + "\n" +
				"<APProcedure_Input>" + "\n" +
				"<Option>APProcedureWithColumnNames</Option>" + "\n" +
				"<SessionId>" + sessionId + "</SessionId>" + "\n" +
				"<ProcName>" + strProcName + "</ProcName>" + "\n" +
				"<Params>" + strParams + "</Params>" + "\n" +
				"<EngineName>" + cabinetName + "</EngineName>" + "\n" +
				"<APProcedure_Input>";

	}

	public static String APProcedure_old(String strEngineName, String strSessionId, String strProcName, String strParams) {
		return "<?xml version=\"1.0\"?>" +    		
				"<WMTestProcedure_Input>" +
				"<Option>APProcedure</Option>" +
				"<SessionId>"+strSessionId+"</SessionId>" +
				"<ProcessDefId>61</ProcessDefId>" +
				"<ProcName>"+strProcName+"</ProcName>" +
				"<Params>"+strParams+"</Params>" +
				"<Status></Status>" +
				"<EngineName>"+strEngineName+"</EngineName>" +
				"<WMTestProcedure_Input>";

	}

	public static String APDelete(String cabinetName, String tableName, String sWhere, String sessionId) throws NGException 
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
	
	public static String APSelectWithColumnNames(String strEngineName,String strQuery, String sessionId) {
		return "<?xml version=\"1.0\"?>"
				+ "<APSelect_Input>"
				+ "<Option>APSelectWithColumnNames</Option>"
				+ "<QueryString>" + strQuery + "</QueryString>"
				+ "<EngineName>" + strEngineName + "</EngineName>"
				+ "<SessionId>" + sessionId + "</SessionId>"
				+ "</APSelect_Input>";         
	}
	//	public static String getAPWebserviceForFCDBUpdate(String SessionId,String sCabname, String wi_name,String refNo, String correlationId , String channelName) {
	//		
	//		String sInputXML = "<?xml version=\"1.0\"?><APWebService_Input>" +
	//				"<Option>WebService</Option>" +
	//				"<Calltype>WS-2.0</Calltype>" +
	//				"<SessionId>"+SessionId+"</SessionId>" +
	//				"<SENDERID>WMS</SENDERID>" +
	//				"<correlationId>"+correlationId+"</correlationId>" +
	//				"<EngineName>"+sCabname+"</EngineName>" +
	//				"<InnerCalltype>TFO_CreateAmendContract</InnerCalltype>" +
	//				"<FCDBDetails>"
	//				+ "<REF_NO>" + refNo + "</REF_NO>"
	//				+ "<channelName>LINKED_WI</channelName>"
	//				+ "<sysrefno>"+refNo+"</sysrefno>"
	//				+ "<WiName>" +wi_name + "</WiName>"
	//				+ "<USER>FCDBUser</USER>"
	//				+ "<senderID>WMS</senderID>"
	//				+ "<workItemNumber>" + wi_name + "</workItemNumber>"
	//				+ "<operationType>FCDBUpdate</operationType>"
	//				+ "</FCDBDetails>" +
	//				"</APWebService_Input>";
	//		return sInputXML;
	//	}
	public static String APProcedure_new(String sessionId,String cabinetName, String ProcName,
			String InValues, int NoOfCols) throws NGException
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

	public static String getAPWebserviceForEventGen(String SessionId,String sCabname, String wiName,String refNo,
			String processName, String sourcingChannel, String mode) {
		StringBuilder sInputXML = new StringBuilder();
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APWebService_Input>").append("\n")
		.append("<Option>WebService</Option>").append("\n")
		.append("<EngineName>" + sCabname + "</EngineName>").append("\n")
		.append("<SessionId>" + SessionId + "</SessionId>").append("\n")
		.append("<Calltype>WS-2.0</Calltype>").append("\n")
		.append("<InnerCallType>BPMModify</InnerCallType>").append("\n")			
		.append("<wiNumber>" +wiName + "</wiNumber>").append("\n")
		.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
		.append("<SENDERID>" + "WMS" + "</SENDERID>").append("\n")
		.append("<mode>"+mode+"</mode>").append("\n")
		.append("<channelName>"+sourcingChannel+"</channelName>").append("\n")           
		.append("<correlationId>"+refNo+"</correlationId>").append("\n")
		.append("<channelRefNumber>"+refNo+"</channelRefNumber>").append("\n")  
		.append("<sysrefno>"+refNo+"</sysrefno>").append("\n")
		.append("<processName>"+processName+"</processName>").append("\n")
		.append("</APWebService_Input>");		
		return sInputXML.toString();
	}


	//	public static String APUpdate(String tableName, String columnName, String strValues, String whereClause, String sessionId, String username, String password) throws NGException
	//    {
	//        
	//        StringBuilder sInputXML = new StringBuilder(); 
	//        sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
	//        .append("<APUpdate_Input>").append("\n")
	//        .append("<Option>APUpdate</Option>").append("\n")
	//        .append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
	//        .append("<SessionId>" + sessionId + "</SessionId>").append("\n")
	//        .append("<TableName>"+ tableName + "</TableName>").append("\n")
	//        .append("<ColName>" + columnName + "</ColName>").append("\n")
	//        .append( "<Values>" + strValues + "</Values>").append("\n")
	//        .append( "<WhereClause>" + whereClause + "</WhereClause>").append("\n")
	//        .append("</APUpdate_Input>");
	//        String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
	//       // TFOLogMe.logMe(TFOLogMe.LOG_LEVEL_INFO, "outputXML: " + outputXML);        
	//
	// 
	//
	//        return outputXML;   
	//    }

	public static String APUpdate(String tableName, String columnName, String strValues, String whereClause,
			String sessionId, String cabinetName) {
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
		String outputXML = null;
		try {
			outputXML = ExecuteXML.executeXML(sInputXML.toString());
		} catch (NGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TFOLogMe.logMe(TFOLogMe.LOG_LEVEL_INFO, "outputXML: " + outputXML);        



		return outputXML; 
	}

	public static String IsSessionValid(String sessionId,String cabinetName) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WFIsSessionValid_Input>").append("\n")
		.append("<Option>WFIsSessionValid</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("</WFIsSessionValid_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		return outputXML;   
	}

	public static String getIdForName(String sessionId,String cabinetName) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WFGetIdforName_Input><Option>WFGetIdforName</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<ObjectType>Q</ObjectType><ObjectName>SystemPFEQueue</ObjectName>").append("\n")
		.append("</WFGetIdforName_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		return outputXML;   
	}
	
	public static String apInsertInputXml(String sEngineName,
			String sSessionId, String TableName, String ColName, String Values) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<APInsert_Input>");
		sb.append("<Option>APInsert</Option>");
		sb.append("<TableName>" + TableName + "</TableName>");
		sb.append("<ColName>" + ColName + "</ColName>");
		sb.append("<Values>" + Values + "</Values>");
		sb.append("<EngineName>" + sEngineName + "</EngineName>");
		sb.append("<SessionId>" + sSessionId + "</SessionId>");
		sb.append("</APInsert_Input>");
		return sb.toString();
	}
	public static String WMUnlockWorkItem(String sEngineName, String sessionId, String ProcessInstanceId, int WorkitemId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WMUnlockWorkItem_Input>").append("\n")
		.append("<Option>WMUnlockWorkItem</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + sEngineName + "</EngineName>").append("\n")
		.append("<UnlockOption>W</UnlockOption>").append("\n")
		.append("<ProcessInstanceId>" + ProcessInstanceId + "</ProcessInstanceId>").append("\n")
		.append("<WorkitemId>" + WorkitemId + "</WorkitemId>").append("\n")
		.append("</WMUnlockWorkItem_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		return outputXML;
	}
}

