package com.adecco.emea.projects;

import com.adecco.emea.clients.HTTPClient;
import com.adecco.emea.model.ServiceConfiguration;

import junit.framework.TestCase;

public class ServicesHTTPTest extends TestCase implements TestCommand{
	
	private ServiceConfiguration service;

	/**
	 * @param service
	 */
	public ServicesHTTPTest(ServiceConfiguration service) {
		super("test");
		this.service = service;
	}

	@Override
	public void test() throws Exception {
		setName(service.getServiceName());
		System.out.println("Testing service: "+service.getServiceName());
		assertTrue(service.assertTest());
		System.out.println("The response was: "+service.getServiceResponse()[1]);
		
		
	}
}
