package com.newgen.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.newgen.log.LogMe;

public class ConnectSocket extends LogMe {
	private static String socketIP;
	private static int socketPort;
	private static ConnectSocket socket = null;

	private ConnectSocket(String socketIP, int socketPort){
		initSocket(socketIP, socketPort);
	}
	
	public static ConnectSocket getReference(String socketIP, int socketPort){
		if(socket == null){
			socket = new ConnectSocket(socketIP, socketPort);
		}
		return socket;
	}
	
	private void setSocketIP(String socketIP) {
		this.socketIP = socketIP;
	}

	private void setSocketPort(int socketPort) {
		this.socketPort = socketPort;
	}
	
	public static String connectToSocket(String inputXml){
		String response = "";
		try(Socket s = new Socket(socketIP, socketPort)){
			logger.info("Socket- socketIP: "+ socketIP +
					"; socketPort:"+ socketPort);
			DataInputStream din = new DataInputStream(s.getInputStream());
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			writeDataToSocket(dout, inputXml);
			response = readDataFromSocket(din);
			logger.info( "connectToSocket response: "+response);
		} catch (Exception e) {
			logger.error("Error in socket connection: ");	
			logger.error(e);			
		}
		return response;
	}
	
	private static boolean writeDataToSocket(DataOutputStream dataOutputStream, String data) {
		boolean bFlag = false;
		try {
			if (data != null && data.length() > 0) {
				dataOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
				bFlag = true;
			}
		} catch (Exception e) {
			logger.error("Error in socket read/write: ");
			logger.error(e);
		}
		return bFlag;
	}

	private static String readDataFromSocket(DataInputStream dataInputStream) {
		StringBuilder data = new StringBuilder();
		try {
			byte[] buffer = new byte[99999];
			int length = dataInputStream.read(buffer, 0, 99999);
			byte[] arrayBytes = new byte[length];
			System.arraycopy(buffer, 0, arrayBytes, 0, length);
			data.append(new String(arrayBytes, StandardCharsets.UTF_8));
			int len = 0;
			while ((len = dataInputStream.read(buffer)) > 0) {
				arrayBytes = new byte[len];
				System.arraycopy(buffer, 0, arrayBytes, 0, len);
				data.append(new String(arrayBytes, StandardCharsets.UTF_8));
				if (dataInputStream.available() <= 0) {
					break;
				}				
			}
		} catch (Exception e) {
			logger.error("Error in socket read/write: ");
			logger.error(e);
		}
		return data.toString();
	}
	
	public void initSocket(String socketIP, int socketPort) {
		setSocketIP(socketIP);
		setSocketPort(socketPort);
	}

}