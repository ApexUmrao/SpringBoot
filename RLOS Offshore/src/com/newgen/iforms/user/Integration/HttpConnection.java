package com.newgen.iforms.user.Integration;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.newgen.iforms.user.LOS_EG;
import com.newgen.iforms.user.cache.RLOSMappingCache;

import jcifs.https.Handler;
import javax.net.ssl.X509TrustManager;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@SuppressWarnings("unused")
public class HttpConnection  {
	public static String RunMode, s, err;
	
	public static Properties prop = new Properties();
	static {

		try {
			prop.load(new FileReader(System.getProperty("user.dir") + File.separator + "CustomConfig"
					+ File.separator + "IntegrationInputs.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LOS_EG.mLogger.info("Integration.properties not found");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			LOS_EG.mLogger.info("IO Exception Occured");
		}
	}

	
	
	
//	public static String connectURLXML(String requestXML, String callName,String urlString ) {
//	    int ResponseCode = 0;
//	    String xmlResponse = "", inputLine = "";
//	    try {
//	      String dummyResponseFlag = prop.get("DUMMYCALL").toString();
//	      LOS_EG.mLogger.info("Dummy Response FLAG " + dummyResponseFlag);
//	      if (dummyResponseFlag == null) {
//	        dummyResponseFlag = "false";
//	      } 
//	      if ("true".equalsIgnoreCase(dummyResponseFlag))
//	        return dummyResponseXML(callName); 
//	      if (requestXML == null || "".equalsIgnoreCase(requestXML))
//	        return ""; 
////	      String urlString = (String)NG_Socket_Service.prop.get("EndPointURL");
//	      if (urlString == null || "".equalsIgnoreCase(urlString))
//	    	  LOS_EG.mLogger.error("EndpointURL NOT FOUND : " + callName); 
//	      LOS_EG.mLogger.info("urlString is: " + urlString);
//	      URL url = null;
//	      HttpsURLConnection s_conn = null;
//	      url = new URL(urlString);
//	      LOS_EG.mLogger.info("url: " + url);
//	      ignoreCertificates();
//	      s_conn = (HttpsURLConnection)url.openConnection();
//	      LOS_EG.mLogger.info("conn: " + s_conn);
//	      s_conn.setRequestProperty("Content-Type", "text/xml");
//	      //s_conn.setRequestProperty("Accept-Encoding", "")
//	      int connectionTimeOut = 120000;
//	      int readTimeOut = 120000;
//	      try {
//	        connectionTimeOut = Integer.parseInt((String)prop.get("ConnectionTimeOut"));
//	        readTimeOut = Integer.parseInt((String)prop.get("ReadTimeOut"));
//	      } catch (Exception e) {
//	        LOS_EG.mLogger.info("Exception at setting connection Timeout");
//	        e.printStackTrace();
//	      } 
//	      s_conn.setConnectTimeout(connectionTimeOut);
//	      s_conn.setReadTimeout(readTimeOut);
//	      String methodName = (String)prop.get("METHODNAME");
//	      LOS_EG.mLogger.info("methodName = " + methodName);
//	      s_conn.setRequestMethod(methodName);
//	      byte[] postData = requestXML.getBytes();
//	      s_conn.setDoInput(true);
//	      s_conn.setDoOutput(true);
//	      OutputStream out = s_conn.getOutputStream();
//	      out.write(postData);
//	      out.close();
//	      ResponseCode = s_conn.getResponseCode();
//	      LOS_EG.mLogger.info("ResponseCode" + ResponseCode);
//	      InputStream inputStream = null;
//	      try {
//	        inputStream = s_conn.getInputStream();
//	      } catch (Exception e) {
//	        LOS_EG.mLogger.info("getting ErrorStream now");
//	        inputStream = s_conn.getErrorStream();
//	        LOS_EG.mLogger.info("Got Error Stream");
//	        e.printStackTrace();
//	      } 
//	      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,StandardCharsets.UTF_8));
//	      StringBuffer content = new StringBuffer();
//	      while ((inputLine = reader.readLine()) != null)
//	        content.append(inputLine); 
//	      reader.close();
//	      LOS_EG.mLogger.info("Parsing respnse:");
//	      xmlResponse = content.toString();
//	      if (ResponseCode == 200)
//	        xmlResponse = content.toString();
//	      LOS_EG.mLogger.info("Decoded Response is"+URLDecoder.decode("xmlResponse", "utf-8"));
//	      xmlResponse=StringEscapeUtils.unescapeXml(xmlResponse);
//	      LOS_EG.mLogger.info("completed");
//	    } catch (Exception e) {
//	      LOS_EG.mLogger.info("Exception Occured in CONNECTURLXML..Response Code is: " + ResponseCode);
//	      e.printStackTrace();
//	    } 
//	    return xmlResponse;
//	  }

	
	public static String connectURLXML(String requestXML,
            String callName,
            String urlString) {

		int responseCode = 0;

		try {

			String dummyCall =
					prop.getProperty("DUMMYCALL", "false");

			LOS_EG.mLogger.info("Dummy Response FLAG : {} " + dummyCall);

			if ("true".equalsIgnoreCase(dummyCall)) {
				return dummyResponseXML(callName);
			}

			if (requestXML == null || requestXML.trim().isEmpty()) {
				LOS_EG.mLogger.info("Empty request received");
				return "";
			}

			if (urlString == null || urlString.trim().isEmpty()) {
				throw new IllegalArgumentException(
						"Endpoint URL not configured for " + callName);
			}

			LOS_EG.mLogger.info("Calling URL : {} " + urlString);

			if ("true".equalsIgnoreCase(prop.getProperty("IGNORE_SSL"))) {
			    ignoreCertificates();
			}
			
			URL url = new URL(urlString);

			HttpsURLConnection conn =
					(HttpsURLConnection) url.openConnection();

			conn.setRequestProperty("Content-Type", "text/xml");

			conn.setConnectTimeout(
					Integer.parseInt(
							prop.getProperty("ConnectionTimeOut", "120000")));

			conn.setReadTimeout(
					Integer.parseInt(
							prop.getProperty("ReadTimeOut", "120000")));

			conn.setRequestMethod(
					prop.getProperty("METHODNAME", "POST"));

			conn.setDoInput(true);
			conn.setDoOutput(true);

			try (OutputStream out = conn.getOutputStream()) {
				out.write(requestXML.getBytes(StandardCharsets.UTF_8));
			}

			responseCode = conn.getResponseCode();

			LOS_EG.mLogger.info(
					"Response Code received : {} "+ responseCode);

			InputStream inputStream;

			try {
				inputStream = conn.getInputStream();
			} catch (Exception ex) {
				LOS_EG.mLogger.error(
						"Using Error Stream due to exception", ex);
				inputStream = conn.getErrorStream();
			}

			StringBuilder responseBuilder = new StringBuilder();

			try (BufferedReader reader =
					new BufferedReader(
							new InputStreamReader(
									inputStream,
									StandardCharsets.UTF_8))) {

				String line;

				while ((line = reader.readLine()) != null) {
					responseBuilder.append(line);
				}
			}

			String xmlResponse = responseBuilder.toString();

			LOS_EG.mLogger.info(
					"Response received successfully for {} "+callName);

			return StringEscapeUtils.unescapeXml(xmlResponse);

		} catch (Exception e) {

			LOS_EG.mLogger.error(
					"Error while invoking API [{}]. Response Code [{}]"+ callName +" and "+responseCode,
					e);
			e.printStackTrace();
			return "Exception Occurred";
		}
}
	
	protected static String dummyResponseXML(String callName) {

		String filePath =
				System.getProperty("user.dir")
				+ File.separator
				+ "CustomConfig"
				+ File.separator
				+ "DummyResponse.properties";

		Properties dummyResponseProperties = new Properties();

		try (FileReader reader = new FileReader(filePath)) {

			dummyResponseProperties.load(reader);

			String response =
					dummyResponseProperties.getProperty(callName);

			LOS_EG.mLogger.info(
					"Dummy response returned for {} "+ callName);

			return response;

		} catch (Exception e) {

			LOS_EG.mLogger.error(
					"Unable to load DummyResponse.properties", e);

			return "<ERROR>DUMMY RESPONSE FILE NOT FOUND</ERROR>";
		}
	}
	
	public static void ignoreCertificates() throws Exception {
	    TrustManager tm = new TrustManager();
	    TrustManager[] trustAllCerts = { tm };
	    HostnameVerifier AllowAllHostnameVerifier = new HostnameVerifier() {
	        public boolean verify(String urlHostName, SSLSession session) {
	          return true;
	        }
	      };
	    SSLContext sc = SSLContext.getInstance("SSL");
	    sc.init(null, (javax.net.ssl.TrustManager[])trustAllCerts, new SecureRandom());
	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	    HttpsURLConnection.setDefaultHostnameVerifier(AllowAllHostnameVerifier);
	  }
	
	
	static class TrustManager implements X509TrustManager {
	    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
	    
	    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
	    
	    public X509Certificate[] getAcceptedIssuers() {
	      return null;
	    }
	  }

}

