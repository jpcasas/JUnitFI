package com.adecco.emea.model;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;




import com.adecco.emea.clients.HTTPClient;

public class HTTPService extends ServiceConfiguration {
	private static final Object SOAP = "SOAP";
	private static final Object REST = "REST";
	private boolean checkCode;
	private String url;
	private String type;
	private String httpMethod;
	private String actionSOAP;
	private String codeAssert;
	private String[] responseService;

	
	


	/**
	 * @param serviceName
	 * @param requestMessage
	 * @param responseMessage
	 * @param checkResponse
	 * @param messagesPath
	 * @param checkCode
	 * @param url
	 * @param type
	 * @param httpMethod
	 * @param actionSOAP
	 * @param codeAssert
	 * @param responseService
	 */
	public HTTPService(String serviceName, String requestMessage,
			String responseMessage, boolean checkResponse, String messagesPath,
			boolean checkCode, String url, String type, String httpMethod,
			String actionSOAP, String codeAssert, String[] responseService) {
		super(serviceName, requestMessage, responseMessage, checkResponse,
				messagesPath);
		this.checkCode = checkCode;
		this.url = url;
		this.type = type;
		this.httpMethod = httpMethod;
		this.actionSOAP = actionSOAP;
		this.codeAssert = codeAssert;
		this.responseService = responseService;
	}
	
	


	/**
	 * @param checkCode
	 * @param url
	 * @param type
	 * @param httpMethod
	 * @param actionSOAP
	 * @param codeAssert
	 * @param responseService
	 */
	public HTTPService(boolean checkCode, String url, String type,
			String httpMethod, String actionSOAP, String codeAssert,
			String[] responseService) {
		super();
		this.checkCode = checkCode;
		this.url = url;
		this.type = type;
		this.httpMethod = httpMethod;
		this.actionSOAP = actionSOAP;
		this.codeAssert = codeAssert;
		this.responseService = responseService;
	}




	/**
	 * 
	 */
	public HTTPService() {
		super();
	}


	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public boolean isCheckCode() {
		return checkCode;
	}

	public void setCheckCode(boolean checkCode) {
		this.checkCode = checkCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCodeAssert() {
		return codeAssert;
	}

	public void setCodeAssert(String codeAssert) {
		this.codeAssert = codeAssert;
	}

	public String[] sendSOAPRequest() throws KeyManagementException,
			NoSuchAlgorithmException, IOException {
		return HTTPClient.getReponseSOAP(url, httpMethod, actionSOAP,
				getRequestMessage());
	}

	public String[] sendRESTRequest() throws Exception {
		return HTTPClient.getReponseREST(url, httpMethod, getRequestMessage());
	}

	public boolean assertTest() {
		try {
			responseService = null;
			boolean code = true;
			boolean response = true;
			if (type.equals(HTTPService.SOAP)) {

				responseService = HTTPClient.getReponseSOAP(url, httpMethod, actionSOAP,
						getRequestMessage());

			}
			if (type.equals(HTTPService.REST)) {

				responseService = HTTPClient.getReponseREST(url, httpMethod,
						getRequestMessage());

			}

			if (checkCode)
				code = responseService[0].equals(codeAssert);
			if (isCheckResponse())
				response = responseService[1].equals(getResponseMessage());

			return code && response;
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String[] getServiceResponse() {
		return responseService;
	}


	public String getActionSOAP() {
		return actionSOAP;
	}


	public void setActionSOAP(String actionSOAP) {
		this.actionSOAP = actionSOAP;
	}
	

}
