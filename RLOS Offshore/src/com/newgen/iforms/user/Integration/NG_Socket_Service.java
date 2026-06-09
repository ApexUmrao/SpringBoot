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
//import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


import jcifs.https.Handler;
import javax.net.ssl.X509TrustManager;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@SuppressWarnings("unused")
public class NG_Socket_Service extends Thread {
	private static int port, maxConnections = 10;
	public static String RunMode, s, err;
	private static Logger logger = Logger.getLogger("consoleLogger");

	public static Properties prop = new Properties();
	static {
		PropertyConfigurator.configure(System.getProperty("user.dir") + File.separator + "CustomConfig" 
	              + File.separator + "log4j_WebServiceWrapper.properties");
		try {
			prop.load(new FileReader(System.getProperty("user.dir") + File.separator + "CustomConfig"
					+ File.separator + "Integration.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.info("Integration.properties not found");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			logger.info("IO Exception Occured");
		}
	}

	public static void main(String[] args) {

		port = 4445;
		int k = 0;
		ServerSocket listener = null;
		Socket server = null;
		// ******* Establish connection *************
		try {
			listener = new ServerSocket(port);

			while (true) {
				k = k + 1;
				// doComms connection;
				logger.info("Waiting for Request Count--" + k);
				System.out.println("Waiting for Request Count--" + k);
				server = listener.accept();
				doComms conn_c = new doComms(server);
				Thread t = new Thread(conn_c);
				t.start();
			}
		} catch (IOException ioe) {
			logger.info("IOException on socket listen:" + ioe);
			System.out.println("Catch  3 IOException on socket listen: " + ioe);
			ioe.printStackTrace();
		} finally {
			try {
				if (listener != null) {
					listener.close();
					listener = null;
					System.out.println("Closing Listener");
					logger.info("Closing Listener");
				}
				if (server != null) {
					server.close();
					server = null;
					System.out.println("Closing Server Socket");
					logger.info("Closing Server Socket");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Exception " + e.toString());
				System.out.println("Exception " + e.toString());
			}
		}
	}

}

// --------------------------------------------------------------------------------------------------
@SuppressWarnings({ "unchecked", "unused" })
// ********** Communicating Class I/O *****************
class doComms implements Runnable {
	private Socket server;
	private static Logger loggern = Logger.getLogger("connectivityLogger");
	private static Logger elogger = Logger.getLogger("errorLogger");
	Properties property = new Properties();
	static int flag = 0;
	NG_Socket_Service N = new NG_Socket_Service();

	doComms(Socket server) {
		this.server = server;
	}

	@SuppressWarnings("static-access")
	public void run() {

		System.out.println("==========Start Listening for local Port==========:" + server.getRemoteSocketAddress());
		loggern.info("==========Start Listening for local Port==========:" + server.getRemoteSocketAddress());
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		/*JSONObject requestJson = null;
		JSONObject responseJson = null;*/
		Object is = null;
		try {
			 ois = new ObjectInputStream(this.server.getInputStream());
		      is = ois.readObject();
			loggern.info("==========READING XML REQUEST==========");
			String requestXML = (String) is;
			loggern.info("==========Request XML==========");
			loggern.info(requestXML);
			System.out.println("======="+requestXML);
			String responseXML = getResponseXML(requestXML);
			loggern.info("==========Response XML==========");
			loggern.info(responseXML);
			oos = new ObjectOutputStream(this.server.getOutputStream());
			oos.writeObject(responseXML);
		} catch (Exception ioe) {
			ioe.printStackTrace();
			DataOutputStream out1 = null;
			try {
				out1 = new DataOutputStream(new DataOutputStream(server.getOutputStream()));
			} catch (Exception e) {
				System.out.println("Data Output Stream initialization error");
				N.err = "Data Output Stream initialization error" + e.toString();
				e.printStackTrace();
			}
			System.out.println("Catch 19 IOException on socket listen: " + ioe);
			N.err = "IOException on socket listen: " + ioe;
			try {
			} catch (Exception e) {
				N.err = "Write Data Exception " + e.toString();
				e.printStackTrace();
			}
			loggern.info("IOException on socket listen: " + ioe);
			
		} 
		finally {
			try {
				if (ois != null) {
					ois.close();
					ois = null;
				}
				if (oos != null) {
					oos.close();
					oos = null;
				}
				if (server != null) {
					System.out.println("====Closing local Cient Socket=======" + server.getRemoteSocketAddress());
					loggern.info("====Closing local Cient Socket=======" + server.getRemoteSocketAddress());
					server.close();
					server = null;
					System.out.println("====Successfuly Closed=======");
					loggern.info("====Successfuly Closed=======");
				}
			} catch (Exception e) {
				System.out.println("Exception " + e.toString());
				loggern.info("Exception " + e.toString());
				N.err = e.toString();
				e.printStackTrace();
			}
		}
	}
	public String getResponseXML(String requestXML) {
	    String callName = requestXML.substring(requestXML.indexOf("<FuncID>") + 8, requestXML.indexOf("</FuncID>"));
	    /**************************************************Creating Final Request XML ******************************************************************/
	    
	    //Changing TxnDate 
	    if("BILL_PAYMENT".equalsIgnoreCase(callName))
	    {
	    	requestXML = requestXML.substring(0, requestXML.indexOf("<TxnDate>")) + "<TxnDate>" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(new Date().getTime()+1*3600*1000))
					+ requestXML.substring(requestXML.indexOf("</TxnDate>"), requestXML.length());
	    }
	    else
	    {
	    requestXML = requestXML.substring(0, requestXML.indexOf("<TxnDate>")) + "<TxnDate>" + new SimpleDateFormat("yyyyMMddHHmmSS").format(new Date(new Date().getTime()+1*3600*1000))
				+ requestXML.substring(requestXML.indexOf("</TxnDate>"), requestXML.length());
	    }
	    //Changing TxnRef
	    requestXML = requestXML.substring(0, requestXML.indexOf("<TxnRef>")) + "<TxnRef>" +"RLOS"+ Long.toString(System.nanoTime()).substring(0, 13)
				+ requestXML.substring(requestXML.indexOf("</TxnRef>"), requestXML.length());
	    //Changing Session Token need to change for Hashing
	    requestXML = requestXML.substring(0, requestXML.indexOf("<SessToken>")) + "<SessToken>" + (String)NG_Socket_Service.prop.get("SESSIONTOKEN")
				+ requestXML.substring(requestXML.indexOf("</SessToken>"), requestXML.length());
	    loggern.info("Final Request XML" + requestXML);
	    System.out.println("Final Request XML" + requestXML);
	    
	    /**************************************************Creating Final Request XML ******************************************************************/
	    return connectURLXML(requestXML, callName);
	  }
	private String connectURLXML(String requestXML, String callName) {
	    int ResponseCode = 0;
	    String xmlResponse = "", inputLine = "";
	    try {
	      String dummyResponseFlag = (String)NG_Socket_Service.prop.get("DUMMYCALL");
	      loggern.info("Dummy Response FLAG " + dummyResponseFlag);
	      if (dummyResponseFlag == null) {
	        dummyResponseFlag = "false";
	      } 
	      if ("true".equalsIgnoreCase(dummyResponseFlag))
	        return dummyResponseXML(callName); 
	      if (requestXML == null || "".equalsIgnoreCase(requestXML))
	        return ""; 
	      String urlString = (String)NG_Socket_Service.prop.get("EndPointURL");
	      if (urlString == null || "".equalsIgnoreCase(urlString))
	        elogger.error("EndpointURL NOT FOUND : " + callName); 
	      loggern.info("urlString is: " + urlString);
	      URL url = null;
	      HttpsURLConnection s_conn = null;
	      url = new URL(urlString);
	      loggern.info("url: " + url);
	      ignoreCertificates();
	      s_conn = (HttpsURLConnection)url.openConnection();
	      loggern.info("conn: " + s_conn);
	      s_conn.setRequestProperty("Content-Type", "text/xml");
	      //s_conn.setRequestProperty("Accept-Encoding", "")
	      int connectionTimeOut = 120000;
	      int readTimeOut = 120000;
	      try {
	        connectionTimeOut = Integer.parseInt((String)NG_Socket_Service.prop.get("ConnectionTimeOut"));
	        readTimeOut = Integer.parseInt((String)NG_Socket_Service.prop.get("ReadTimeOut"));
	      } catch (Exception e) {
	        loggern.info("Exception at setting connection Timeout");
	        e.printStackTrace();
	      } 
	      s_conn.setConnectTimeout(connectionTimeOut);
	      s_conn.setReadTimeout(readTimeOut);
	      String methodName = (String)NG_Socket_Service.prop.get("METHODNAME");
	      loggern.info("methodName = " + methodName);
	      s_conn.setRequestMethod(methodName);
	      byte[] postData = requestXML.getBytes();
	      s_conn.setDoInput(true);
	      s_conn.setDoOutput(true);
	      OutputStream out = s_conn.getOutputStream();
	      out.write(postData);
	      out.close();
	      ResponseCode = s_conn.getResponseCode();
	      loggern.info("ResponseCode" + ResponseCode);
	      InputStream inputStream = null;
	      try {
	        inputStream = s_conn.getInputStream();
	      } catch (Exception e) {
	        elogger.info("getting ErrorStream now");
	        inputStream = s_conn.getErrorStream();
	        elogger.info("Got Error Stream");
	        e.printStackTrace();
	      } 
	      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,StandardCharsets.UTF_8));
	      StringBuffer content = new StringBuffer();
	      while ((inputLine = reader.readLine()) != null)
	        content.append(inputLine); 
	      reader.close();
	      loggern.info("Parsing respnse:");
	      xmlResponse = content.toString();
	      if (ResponseCode == 200)
	        xmlResponse = content.toString();
	      loggern.info("Decoded Response is"+URLDecoder.decode("xmlResponse", "utf-8"));
	      xmlResponse=StringEscapeUtils.unescapeXml(xmlResponse);
	      loggern.info("completed");
	    } catch (Exception e) {
	      loggern.info("Exception Occured in CONNECTURLXML..Response Code is: " + ResponseCode);
	      e.printStackTrace();
	    } 
	    return xmlResponse;
	  }

	
	@SuppressWarnings("resource")
	protected String dummyResponseXML(String callName) {
	   
	    	try {
	    		 loggern.info("Inside Dummy Response FLAG " + callName);
	    		Properties dummyResponseProperties = new Properties();
	    		loggern.info("Path is "+new FileReader(System.getProperty("user.dir") + File.separator + "LOS_EG_Properties"
						+ File.separator + "properties" + File.separator + "DummyResponse.properties"));
	    		dummyResponseProperties.load(new FileReader(System.getProperty("user.dir") + File.separator + "LOS_EG_Properties"
						+ File.separator + "properties" + File.separator + "DummyResponse.properties"));
	    		 loggern.info(" Dummy Response is " + dummyResponseProperties.get(callName));
	    		return (String)dummyResponseProperties.get(callName);
			} catch (Exception e) {
				e.printStackTrace();
				elogger.info("Integration.properties not found");
				return "<ERROR>DUMMY RESPONSE FILE NOT FOUND</ERROR>";
			}
	    
	     
	    	
	  }
	
	public void ignoreCertificates() throws Exception {
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

// ----------------------------------------------------------------------------------------------------------------
