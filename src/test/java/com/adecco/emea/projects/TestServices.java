package com.adecco.emea.projects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.adecco.emea.model.HTTPService;
import com.adecco.emea.model.QueueService;
import com.adecco.emea.model.Configuration;
import com.adecco.emea.projects.ServicesHTTPTest;


public class TestServices extends TestSuite{
	
	public static Test suite() throws FileNotFoundException, IOException, JAXBException{
		TestSuite suite = new TestSuite("Junit Test for Tibco BW");
    	File file = new File("./profile.properties");
    	Properties properties=new Properties();
    	properties.load(new FileReader(file));
    	
    	String configurationFile=properties.getProperty("configuraiton.file");
    	
    	Configuration config=getConfiguration(configurationFile);
    	
    	ArrayList<HTTPService> hservices=config.getHttpServices();
    	for (HTTPService httpService : hservices) {
    		ServicesHTTPTest httpservice=new ServicesHTTPTest(httpService);
       		suite.addTest(httpservice);
			
		}
    	ArrayList<QueueService> qservices=config.getQueueServices();
    	for (QueueService queueService : qservices) {
			suite.addTest(new ServicesJMSTest( queueService));
		}
    	    
    	
    	return suite;
	}
	
	private static Configuration getConfiguration(String configurationFile) throws JAXBException {
		File file = new File(configurationFile);
		JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Configuration config = (Configuration) jaxbUnmarshaller.unmarshal(file);
		return config;
	}

	
	public static void main(String[] args) throws Exception {
		File file = new File("config/tat-configuration.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Configuration config = (Configuration) jaxbUnmarshaller.unmarshal(file);
		ArrayList<HTTPService> hservices=config.getHttpServices();
    	for (HTTPService httpService : hservices) {
    		ServicesHTTPTest httpservice=new ServicesHTTPTest(httpService);
       		httpservice.test();
			
		}
    	ArrayList<QueueService> qservices=config.getQueueServices();
    	for (QueueService queueService : qservices) {
    		queueService.setConnectionString(config.getEmsStringConnection());
    		queueService.setUser(config.getEmsUserName());
    		queueService.setPassword(config.getEmsPassword());
			new ServicesJMSTest( queueService).test();
		}
    
	}
	

}
