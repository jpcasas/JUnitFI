package com.adecco.emea.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public abstract class ServiceConfiguration {
	private static final String SEPARATOR = "/";
	private String ServiceName;
	private String requestMessage;
	private String responseMessage;
	private boolean checkResponse;
	private String messagesPath;
	
	
	/**
	 * @param serviceName
	 * @param requestMessage
	 * @param responseMessage
	 * @param checkResponse
	 */
	public ServiceConfiguration(String serviceName, String requestMessage,
			String responseMessage, boolean checkResponse, String messagesPath) {
		super();
		ServiceName = serviceName;
		this.requestMessage = requestMessage;
		this.responseMessage = responseMessage;
		this.checkResponse = checkResponse;
		this.messagesPath=messagesPath;
	}

	

	/**
	 * 
	 */
	public ServiceConfiguration() {
		super();
	}

	public String getMessagesPath() {
		return messagesPath;
	}
	public void setMessagesPath(String messagesPath) {
		this.messagesPath = messagesPath;
	}

	public boolean assertMessage(String response){
		if(checkResponse)
			return responseMessage.equals(response);
		return false;
	}
	
	public abstract boolean assertTest();
	
	
	public String getServiceName() {
		return ServiceName;
	}

	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}
	public String getRequestMessage() {
		try {
			return readFileAsString(getMessagesPath()+ServiceConfiguration.SEPARATOR+requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}
	public String getResponseMessage() {
		try {
			return readFileAsString(getMessagesPath()+ServiceConfiguration.SEPARATOR+responseMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public boolean isCheckResponse() {
		return checkResponse;
	}
	public void setCheckResponse(boolean checkResponse) {
		this.checkResponse = checkResponse;
	}
	
	private String readFileAsString(String string) throws IOException {
		LineNumberReader in = new LineNumberReader(new FileReader(string));
        StringBuffer bf=new StringBuffer();
		String inputLine;
		while ((inputLine = in.readLine()) != null) 
		bf.append(inputLine);
		return bf.toString();
	}

	public abstract String[] getServiceResponse();
	
	
	

}
