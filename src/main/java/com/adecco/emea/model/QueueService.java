package com.adecco.emea.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.jms.JMSException;

import com.adecco.emea.clients.JMSClient;


public class QueueService extends ServiceConfiguration {
	private String connectionString;
	private String user;
	private String password;
	private String queueToSend;
	private String responseQueue;
	private String response;
	private boolean writeResponse;
	private String stringToFind;
	private boolean checkString;
	
	
	/**
	 * @param serviceName
	 * @param requestMessage
	 * @param responseMessage
	 * @param checkResponse
	 * @param messagesPath
	 * @param connectionString
	 * @param user
	 * @param password
	 * @param queueToSend
	 * @param responseQueue
	 * @param response
	 */
	public QueueService(String serviceName, String requestMessage,
			String responseMessage, boolean checkResponse, String messagesPath,
			String connectionString, String user, String password,
			String queueToSend, String responseQueue, String response) {
		super(serviceName, requestMessage, responseMessage, checkResponse,
				messagesPath);
		this.connectionString = connectionString;
		this.user = user;
		this.password = password;
		this.queueToSend = queueToSend;
		this.responseQueue = responseQueue;
		this.response = response;
	}
	


	/**
	 * @param connectionString
	 * @param user
	 * @param password
	 * @param queueToSend
	 * @param responseQueue
	 * @param response
	 */
	public QueueService(String connectionString, String user, String password,
			String queueToSend, String responseQueue, String response) {
		super();
		this.connectionString = connectionString;
		this.user = user;
		this.password = password;
		this.queueToSend = queueToSend;
		this.responseQueue = responseQueue;
		this.response = response;
	}



	/**
	 * 
	 */
	public QueueService() {
		super();
	}

	
	


	public boolean isWriteResponse() {
		return writeResponse;
	}



	public void setWriteResponse(boolean writeResponse) {
		this.writeResponse = writeResponse;
	}



	public String getStringToFind() {
		return stringToFind;
	}



	public void setStringToFind(String stringToFind) {
		this.stringToFind = stringToFind;
	}



	public boolean isCheckString() {
		return checkString;
	}



	public void setCheckString(boolean checkString) {
		this.checkString = checkString;
	}



	public String getQueueToSend() {
		return queueToSend;
	}
	public void setQueueToSend(String queueToSend) {
		this.queueToSend = queueToSend;
	}
	public String getResponseQueue() {
		return responseQueue;
	}
	public void setResponseQueue(String responseQueue) {
		this.responseQueue = responseQueue;
	}
	@Override
	public boolean assertTest()  {
		try {
			response=JMSClient.getClient(connectionString, user, password).sendMessageReplay(queueToSend, responseQueue, getRequestMessage());
			
			boolean isString = true;
			boolean isResponse = true;
			
			if(this.checkString)
				isString=response.contains(this.stringToFind);
			if(isCheckResponse())
				isResponse=response.equals(getResponseMessage());

			if(writeResponse){
				PrintWriter p=new PrintWriter(new File(getServiceName()+"-response.xml"));
				p.print(response);
				p.flush();
				p.close();
			}
			return isString && isResponse;
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	@Override
	public String[] getServiceResponse() {
		return new String[]{"", response};
	}



	public String getConnectionString() {
		return connectionString;
	}



	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}



	public String getUser() {
		return user;
	}



	public void setUser(String user) {
		this.user = user;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	

}
