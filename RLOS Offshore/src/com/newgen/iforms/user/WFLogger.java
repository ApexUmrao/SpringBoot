package com.newgen.iforms.user;

import com.newgen.iforms.custom.IFormListenerFactory;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
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

public class WFLogger
  implements IFormListenerFactory
{
  private static String loggerName = "LOS_ELogger";
  private static String processName = "LOS_B";
  public static final Logger mLogger = Logger.getLogger(loggerName);

  public static Map<String, String> loggerDateMap = new HashMap();

  public IFormServerEventHandler getClassInstance(IFormReference iformObj)
  {
    String activityName = iformObj.getActivityName();
    setLogger();
	return null;

   
  }

  public static void setLogger()
  {
    try
    {
      Date date = new Date();
      DateFormat logDateFormat = new SimpleDateFormat("dd-MM-yyyy");

      if ((loggerDateMap.get(logDateFormat.format(date)) != null) && (((String)loggerDateMap.get(logDateFormat.format(date))).equalsIgnoreCase("Y")))
      {
        return;
      }

      Properties p = new Properties();
      p.load(new FileInputStream(System.getProperty("user.dir") + File.separator + "LOS_Log_config" + File.separator + "log4j_aub_los.properties"));
      String dynamicLog = null;
      String orgFileName = null;
      File d = null;
      File fl = null;

      dynamicLog = "LOS_CustomLogs/" + processName + "_Logs/" + logDateFormat.format(date) + "/" + processName + "_Log.xml";
      orgFileName = p.getProperty("log4j.appender." + loggerName + ".File");
      if ((orgFileName != null) && (!(orgFileName.equalsIgnoreCase(""))))
      {
        dynamicLog = orgFileName.substring(0, orgFileName.lastIndexOf("/") + 1) + logDateFormat.format(date) + orgFileName.substring(orgFileName.lastIndexOf("/"));
      }
      d = new File(dynamicLog.substring(0, dynamicLog.lastIndexOf("/")));
      d.mkdirs();
      fl = new File(dynamicLog);
      if (!(fl.exists())) {
        fl.createNewFile();
      }
      p.put("log4j.appender." + loggerName + ".File", dynamicLog);
      PropertyConfigurator.configure(p);

      loggerDateMap.put(logDateFormat.format(date), "Y");
    }
    catch (Exception e)
    {
      printException(e);
    }
  }

  public static void printException(Exception e)
  {
    StringWriter sw = new StringWriter();
    e.printStackTrace(new PrintWriter(sw));
    String exception = sw.toString();
    System.out.println("Exception is " + exception);
  }
}