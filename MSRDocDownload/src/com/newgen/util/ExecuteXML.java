package com.newgen.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.newgen.log.LogMe;
import com.newgen.omni.wf.util.app.NGEjbClient;
import com.newgen.omni.wf.util.excp.NGException;

public class ExecuteXML extends LogMe
{
	private static NGEjbClient objNGEjbClient = null;

	public static void init(String appName, String IP, String port) throws NGException
	{		
		logger.info("objNGEjbClient start:" );		

		if(objNGEjbClient == null)
		{
			logger.info("objNGEjbClient start inside" );		
			objNGEjbClient = NGEjbClient.getSharedInstance();
			logger.info("objNGEjbClient middle inside" );		
			objNGEjbClient.initialize(IP, port, appName);
			logger.info("objNGEjbClient end inside" );		

		}
	}

	public static String executeXML(String inputXML) throws NGException
	{
		logger.info("InputXML ===>" + inputXML);
		String outputXML="";
		try {
			outputXML = objNGEjbClient.makeCall(inputXML);
			logger.info("OutputXML ===>" + outputXML);
			return outputXML;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputXML;

		
	}

	public static String executeRestAPI(String url, String inputXML) throws Exception{
		String outputXML="";
		HttpURLConnection conn=null;
		try {
			logger.info("URL ..."+url);
			URL urlName = new URL(url);
			conn =  (HttpURLConnection) urlName.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");

			OutputStream os = conn.getOutputStream();
			os.write(inputXML.getBytes());
			os.flush();
			logger.info("conn.getResponseCode()===> "+conn.getResponseCode());
			logger.info(" HttpURLConnection.HTTP_CREATED===> "+ HttpURLConnection.HTTP_OK);
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
				throw new RuntimeException("Failed : HTTP error code : "+conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			logger.info("Output from server ...\n");
			String out ;
			while ((out = br.readLine()) != null) {
				outputXML=out;
				logger.info("RestAPI output===> "+outputXML);
			}
		} catch (MalformedURLException e) {
			logger.info( "RestAPI exception1===> "+e.getMessage());
			e.printStackTrace();
		}catch(IOException e) {
			logger.info( "RestAPI exception2===> "+e.getMessage());
			e.printStackTrace();
		}
		finally{
			if(conn!=null){
				conn.disconnect();
			}
		}
		return outputXML;
	}
}


