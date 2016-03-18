package com.adecco.emea.clients;

import javax.net.ssl.TrustManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HTTPClient {

	public static void main(String[] args) throws Exception {

		System.setProperty("http.proxyHost", "http://10.103.111.7");
		System.setProperty("http.proxyPort", "8080");
		//http://jsonplaceholder.typicode.com/todos
	    //String[] weather = HTTPClient.getReponseREST("http://jsonplaceholder.typicode.com/posts", "GET", "");
		String[] weather = HTTPClient.getReponseREST("https://uat-services-esb.emea.adecco.net:9181/MO/CNTMGT/PUTCOMMERCIALCONTRACT/v01", "GET", "");
		//String var= "<?xml version=\"1.0\" encoding=\"utf-8\"?> <soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"> <soap:Body> <GetWeather xmlns=\"http://www.webserviceX.NET\"> <CityName>Lyon</CityName> <CountryName>France</CountryName> </GetWeather> </soap:Body> </soap:Envelope>";
		//String[] weather=HTTPClient.getReponseSOAP("http://www.webservicex.net/globalweather.asmx", "POST", "http://www.webserviceX.NET/GetWeather", var);
		for (int i = 0; i < weather.length; i++) {
			System.out.println(weather[i]);
		}
		
	}

	

	public static String[] getReponseREST(String surl, String httpMethod, String message) throws Exception {
		System.setProperty("http.proxyHost", "http://10.103.111.7");
		System.setProperty("http.proxyPort", "8080");
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
        // Install the all-trusting trust manager
        final SSLContext sc = SSLContext.getInstance("SSL");
        
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        
        
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
           

			public boolean verify(String arg0, SSLSession arg1) {
				
				return true;
			}
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        URL url = new URL(surl);
        HttpURLConnection  con = (HttpURLConnection)url.openConnection();
        if(!message.equals("")){
        	
        
        con.setDoOutput(true);
        con.setRequestMethod(httpMethod);
		
		
		OutputStream os = con.getOutputStream();
		os.write(message.getBytes());
		os.flush();
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer bf=new StringBuffer();
		String inputLine;
		while ((inputLine = in.readLine()) != null) 
			bf.append(inputLine);
		
		
        con.disconnect();
		return new String[]{con.getResponseCode()+"", bf.toString()};
        
        
    }



	public static String[] getReponseSOAP(String surl, String httpMethod, String action, String message) throws NoSuchAlgorithmException, KeyManagementException, IOException {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
        // Install the all-trusting trust manager
        final SSLContext sc = SSLContext.getInstance("SSL");
        
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        
        
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
           

			public boolean verify(String arg0, SSLSession arg1) {
				
				return true;
			}
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        URL url = new URL(surl);
        HttpURLConnection  con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod(httpMethod);
        con.setRequestProperty("Content-type", "text/xml; charset=utf-8");
        con.setRequestProperty("SOAPAction", action);
        con.setDoOutput(true);
        OutputStream reqStream = con.getOutputStream();
        reqStream.write(message.getBytes());
        reqStream.flush();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer bf=new StringBuffer();
		String inputLine;
		while ((inputLine = in.readLine()) != null) 
		bf.append(inputLine);
		reqStream.close();
		in.close();
		con.disconnect();
        
		return new String[]{con.getResponseCode()+"", bf.toString()};
		
	}
}