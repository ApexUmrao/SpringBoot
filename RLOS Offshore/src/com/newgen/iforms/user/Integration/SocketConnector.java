package com.newgen.iforms.user.Integration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.newgen.iforms.user.LOS_EG;



public class SocketConnector {
	
	@SuppressWarnings("unchecked")
	public static String getSocketResponse(String  xmlRequest,String serverIP,String serverPort) {
		LOS_EG.mLogger.info("Inside getSocketResponse ");
		Socket socket = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		String xmlResponse = null;
		
		try {
			socket = new Socket(serverIP, Integer.parseInt(serverPort));
			
			socket.setSoTimeout(120000);//socket.set// getting connection object(to connect with socket utility)
			LOS_EG.mLogger.info("socket value "+socket);
			oos = new ObjectOutputStream(socket.getOutputStream());
			LOS_EG.mLogger.info("oos value "+oos);
			ois = null;
			oos.writeObject(xmlRequest);//Writing request xml
			ois = new ObjectInputStream(socket.getInputStream());
			LOS_EG.mLogger.info("ois value "+ois);
			xmlResponse = (String)ois.readObject(); //reading response xml
			LOS_EG.mLogger.info("jsonResponse "+xmlResponse);
		}catch(Exception e) {
			LOS_EG.mLogger.info("exception  :"+e);
			e.printStackTrace();
		}finally {
				try {
					if(socket!=null) { socket.close(); socket = null; }
					if(oos != null) { oos.close(); oos = null; }
					if(ois != null) { ois.close(); ois = null; }
				} catch (IOException e) {
					LOS_EG.mLogger.info("exception  :"+e);
				}
		}
		return xmlResponse;
	}
}
