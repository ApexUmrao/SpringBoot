package com.newgen.iforms.user;
/*------------------------------------------------------------------------------------------------------
NEWGEN SOFTWARE TECHNOLOGIES LIMITED
Author                         : 
Group                          : AUB-RLOS
Project/Product                : Application -Projects
Application                    :
Module                         : Log Writer 
File Name                      : LOS_LogWriter.java
Date (DD/MM/YYYY)              : June 11, 2021
Description                    : 


Dependent Jars:
	rt.jar
------------------------------------------------------------------------------------------------------
CHANGE HISTORY

-------------------------------------------------------------------------------------------------------

Problem No/CR No   Change Date   Changed By    Change Description

------------------------------------------------------------------------------------------------------
*/


import java.text.SimpleDateFormat;
import java.util.*;
import java.text.*;
import java.io.*;
import java.text.DateFormat;
import java.math.BigInteger;

public class LOS_LogWriter {	

	public void WriteToLog(String strOutput) {
		String fileName = "";
		StringBuffer str = new StringBuffer();
		Calendar calendar = new GregorianCalendar();
		String DtString = String.valueOf(""
				+ calendar.get(Calendar.DAY_OF_MONTH) + "-"
				+ (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.YEAR));

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		
		str.append("\n \t["+DateFormat.getDateTimeInstance(0, 2).format(
				new java.util.Date())+"]\n");
		str.append(strOutput);

		StringBuffer strFilePath = null;
		StringBuffer strFilePath1 = null;
		String tmpFilePath = "";

		
		// return dateFormat.format(calendar.getTime());
		try {
			strFilePath = new StringBuffer(50);
			strFilePath.append(System.getProperty("user.dir")).append(
					File.separatorChar).append("IformLogs");
			File file = new File(strFilePath.toString());
			int flag = 0;
			if (!file.exists())
				file.mkdirs();
			//fileName = "Logs_" + DtString;

			//strFilePath.append(File.separatorChar);
			//strFilePath.append(fileName);
			//tmpFilePath = strFilePath.toString();
			//tmpFilePath = getFileName_New(tmpFilePath);
			tmpFilePath = strFilePath.toString() + File.separatorChar + "Logs_"+DtString+".xml";		
			
			//tmpFilePath = strFilePath.toString()+".xml";
			BufferedWriter out = new BufferedWriter(new FileWriter(tmpFilePath,
					true));
			out.write(str.toString());
			out.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			strFilePath = null;
		}
	}

	/*public String getFileName_New(String tmpFilePath) {
		try {
			double MaxfileSize=10;
			String tmpFilePathNext="";
			tmpFilePath = tmpFilePath + ".xml";
			System.out.println("tmpFilePath=="+tmpFilePath);
			File dem = new File(tmpFilePath);
			File sFile = null;
			File sFileNext=null;

			if (!dem.exists()) {
				return tmpFilePath;
			}

			String sParentDir = tmpFilePath.substring(0, tmpFilePath
					.lastIndexOf("\\"));
			File Logdir = new File(sParentDir);
			String[] ChildDir = Logdir.list();

			if (!dem.exists())
				return tmpFilePath;
			long fileSize = dem.length();
			if ((double) fileSize / (1024 * 1024) < MaxfileSize) {
				return tmpFilePath;
			} else {
				String sFileName = dem.getName();
				String sCheckFileName = sFileName.substring(0, sFileName
						.lastIndexOf("_"));

				for (int cnt = 2; cnt < 10; cnt++) {
					tmpFilePath = sParentDir + "\\" + sCheckFileName + "_"
							+ cnt + ".xml";
					sFile = new File(tmpFilePath);
					if (!sFile.exists())
						return tmpFilePath;
					fileSize = sFile.length();
					if ((double) fileSize / (1024 * 1024) < MaxfileSize) {
						tmpFilePathNext=sParentDir + "\\" + sCheckFileName + "_"
						+ (cnt+1) + ".xml";
						sFileNext = new File(tmpFilePathNext);
						if (sFileNext.exists())
						{
							sFileNext.delete();
						}
						return tmpFilePath;
					}
					
				}
			tmpFilePath = sParentDir + "\\" + sCheckFileName + "_1.xml";
			sFile = new File(tmpFilePath);
			if (sFile.exists())
			{
				sFile.delete();
			}
			tmpFilePathNext=sParentDir + "\\" + sCheckFileName + "_2.xml";
			sFileNext = new File(tmpFilePathNext);
			if (sFileNext.exists())
			{
				sFileNext.delete();
			}
			return tmpFilePath;
			}
		} catch (Exception ex) {
			System.out.println("Error in getFileName_New()" + ex);
		} finally {

		}
		return tmpFilePath;
	}*/
}
