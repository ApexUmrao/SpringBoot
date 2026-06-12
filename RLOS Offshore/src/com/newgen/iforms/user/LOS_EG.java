package com.newgen.iforms.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.newgen.iforms.custom.IFormListenerFactory;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.cache.RLOSMappingCache;


public class LOS_EG implements IFormListenerFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static String loggerName = "LOS_ELogger";
	private static String processName = "LOS_EG";
	public static final Logger mLogger = Logger.getLogger(loggerName);

	public static Map<String, String> loggerDateMap = new HashMap();

	@Override
	public IFormServerEventHandler getClassInstance(IFormReference arg0) {
		setLogger(arg0);
		String activityName = arg0.getActivityName();
		RLOSMappingCache.getInstance();
		if ("LOS_EG".equalsIgnoreCase(processName)) {
			try {
				mLogger.info("Activity Name : " + activityName);

				if ("Introduction".equals(activityName) || "PB_Supervisor".equals(activityName)
						|| "Checker_TL_DSU".equals(activityName) || "Re_Initiator".equals(activityName)|| "Rejected_Queue".equals(activityName)) {
					return new LOS_Introduction();
				}
				else if ("Branch_Manager".equals(activityName) || "Authorizer_DSU".equals(activityName)|| "Legal_Dept".equals(activityName))
				{
					return new LOS_Br_Authorizer_DSU();
				}
				else if("RCR_PreScanner".equals(activityName))
				{
					return new LOS_RCR_PreScanner();
				}
				else if("RCR_CreditAnalyst".equals(activityName) ||"Fraud_Dept".equals(activityName) || "Admin".equals(activityName) || "Collection_Dept".equals(activityName) || "Compliance_Dept".equals(activityName))
				{
					return new LOS_RCR_CreditAnalyst();
				}
				else if("RCR_Credit_TL".equals(activityName))
				{
					return new LOS_RCR_Credit_TL();
				}
				else if("Head_Of_Initiation".equals(activityName))
				{
					return new LOS_Head_Of_Initiation();
				}
				else if("CRO_CRMH".equals(activityName) || "RCRH".equals(activityName) || "Fulfillment_Docs".equals(activityName))
				{
					return new LOS_RCRH_Retail_Credit();
				}
				else if("RCCU_RiskControl_Maker".equals(activityName) || "RCCU_RiskControl_Checker".equals(activityName) || "RCCU_RiskControl_Authorizer".equals(activityName))
				{
					return new RCCU_Risk_Control();
				}
				else if("RCCU_RiskLimit_Maker".equals(activityName) || "RCCU_RiskLimit_Checker".equals(activityName) || "RCCU_RiskLimit_Authorizer".equals(activityName))
				{
					return new RCCU_Risk_Limit();
				}
				else if("LPU_Maker".equals(activityName) || "LPU_Checker".equals(activityName) || "CPU".equals(activityName) || "Disbursal_Integration".equals(activityName) ||
						"Error_Handling".equals(activityName) || "Branch_Ops".equals(activityName) ||"Remittance".equals(activityName) ||"Card_Center_To_Courier".equals(activityName))
				{
					return new Operations();
				}
				//Added by Shivanshu
				else if("LOS_EG_Fulfillment_Docs".equals(activityName))
				{
					return new LOS_Fulfillment_Docs();
				}

			}
			
			catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	
	public static void setLogger(IFormReference iformObj)
	{
		try
		{
			String wiName=iformObj.getObjGeneralData().getM_strProcessInstanceId();
			Date date = new Date();
			DateFormat logDateFormat = new SimpleDateFormat("dd-MM-yyyy");
			if(loggerDateMap.get(logDateFormat.format(date))!=null && loggerDateMap.get(logDateFormat.format(date)).equalsIgnoreCase("Y"))
			{
				mLogger.debug("Logger is already set for "+logDateFormat.format(date));
				return;
			}
			String dynamicLog = null;
			String rplogFilePath = System.getProperty("user.dir")	+ File.separatorChar +"LOS_CustomLogs" + File.separatorChar + "LOS_EG_Logs";
			String logFilePath=rplogFilePath+ File.separatorChar+ logDateFormat.format(date);
			String logFileName = wiName + ".log";
			dynamicLog = logFilePath + File.separatorChar + logFileName;
			Properties p = new Properties();
			p.load(new FileInputStream(System.getProperty("user.dir")+ File.separator + "LOS_Log_config"+ File.separator+ "log4j_aub_los.properties"));
			File d = new File(logFilePath);
			d.mkdirs();
			File fl = new File(dynamicLog);
			if (fl.createNewFile())
			{
				mLogger.info("*****Log file created successfully");
			} 
			else
			{
				mLogger.info("*****Updating Log File");
			}
			p.put("log4j.appender."+loggerName+".File", dynamicLog );
			PropertyConfigurator.configure(p);
			
		}
		catch (Exception e)
		{
			printException(e);
		}
	}

	public static void printException(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exception = sw.toString();
		System.out.println("" + exception);
		mLogger.info(exception);
	}

}
