package com.adecco.emea.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Configuration {
	
	
	
	private ArrayList<QueueService> queueServices;
	private ArrayList<HTTPService> httpServices;
	private String emsStringConnection;
	private String emsUserName;
	private String emsPassword;	
	
	
	
	
	/**
	 * @param messagesPath
	 * @param queueServices
	 * @param httpServices
	 * @param emsStringConnection
	 * @param emsUserName
	 * @param emsPassword
	 */
	public Configuration(
			ArrayList<QueueService> queueServices,
			ArrayList<HTTPService> httpServices, String emsStringConnection,
			String emsUserName, String emsPassword) {
		super();
		
		this.queueServices = queueServices;
		this.httpServices = httpServices;
		this.emsStringConnection = emsStringConnection;
		this.emsUserName = emsUserName;
		this.emsPassword = emsPassword;
	}

	/**
	 * 
	 */
	public Configuration() {
		super();
	}

	
	
	public String getEmsStringConnection() {
		return emsStringConnection;
	}
	public void setEmsStringConnection(String emsStringConnection) {
		this.emsStringConnection = emsStringConnection;
	}
	public String getEmsUserName() {
		return emsUserName;
	}
	public void setEmsUserName(String emsUserName) {
		this.emsUserName = emsUserName;
	}
	public String getEmsPassword() {
		return emsPassword;
	}
	public void setEmsPassword(String emsPassword) {
		this.emsPassword = emsPassword;
	}
	
	
	
	
	public ArrayList<QueueService> getQueueServices() {
		return queueServices;
	}

	public void setQueueServices(ArrayList<QueueService> queueServices) {
		this.queueServices = queueServices;
	}

	public ArrayList<HTTPService> getHttpServices() {
		return httpServices;
	}

	public void setHttpServices(ArrayList<HTTPService> httpServices) {
		this.httpServices = httpServices;
	}

	

	
	
	

}
