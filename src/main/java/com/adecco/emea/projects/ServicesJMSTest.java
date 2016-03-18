package com.adecco.emea.projects;

import com.adecco.emea.model.Configuration;
import com.adecco.emea.model.ServiceConfiguration;

import junit.framework.TestCase;

public class ServicesJMSTest extends TestCase implements TestCommand{
	
	
	private ServiceConfiguration service;
	
	

	

	/**
	 * @param config
	 * @param service
	 */
	public ServicesJMSTest( ServiceConfiguration service) {
		super("test");
		
		this.service = service;
	}





	@Override
	public void test() throws Exception {
		setName(service.getServiceName());
		System.out.println("Testing service: "+service.getServiceName());
		boolean test=service.assertTest();
		System.out.println(test);
		assertTrue(test);
		System.out.println("The response was: "+service.getServiceResponse()[1]);		
	}

}
