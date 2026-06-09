package com.newgen.iforms.user;
/*------------------------------------------------------------------------------------------------------
NEWGEN SOFTWARE TECHNOLOGIES LIMITED
Author                         : 
Group                          : AUB-RLOS
Project/Product                : Application -Projects
Application                    :
Module                         : Global Variable 
File Name                      : LOS_NGGlobals.java
Date (DD/MM/YYYY)              : June 11, 2021
Description                    : 


Dependent Jars:
	rt.jar,iforms.ja
------------------------------------------------------------------------------------------------------
CHANGE HISTORY

-------------------------------------------------------------------------------------------------------

Problem No/CR No   Change Date   Changed By    Change Description

------------------------------------------------------------------------------------------------------
*/

public class LOS_NGGlobals {

	public static String Branch_INIT_SMS_MESSAGE="Dear $$CUSTOMER_NAME$$, kindly note that your application for a finance of UGX. $$LOAN_AMT$$ has been submitted.";
	  public static String JTS_IP;
	  public static int JTS_PORT;
	  public static String CABINET;
	  public static String SESSION;
	  public static String USERNAME;
	  public static String PASSWORD;
	  public static String PROCESSDEFID;
	  public static String INITIATE_ACTIVITY;
	  public static String PID;
      public String CSQRY = "SELECT CALL_NAME,SOCKET_IP,SOCKET_PORT,REQUEST_XML FROM NG_LOS_MAST_INTEGRATION WHERE call_name=";
	  public String CSWHRCOND="";
	  public static String activityName;
	  public static String pId;
	  public static String userName;
	  public static String initiationDate;
      public static int FetchCustTyp_flag =0;  
      public static int FetchAccNo_flag =0;
      public static int FetchCollDetails_flag =0;
      public static int FetchAddress_flag =0;
      public static int FetchBasicDetails_flag =0;
      public static int FetchFdDetails_flag =0;
      public static int FetchCrbDetails_flag =0;
      public static int FetchExtCustInq_flag=0;
      public static int FetchEmploymentDetails_flag=0;
      public static int day;
      public static int month;
      public static int year;
      
      public static String decisionHistoryTable = "table74";
	
      public LOS_NGGlobals() {}
	
}
