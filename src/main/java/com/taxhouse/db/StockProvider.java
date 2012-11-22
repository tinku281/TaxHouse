package com.taxhouse.db;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class StockProvider {

	public static double GetCurrentRate(String symbol) throws Exception {
		String request = "http://query.yahooapis.com/v1/public/yql?q=select%20DaysHigh%2CDaysLow%20from%20yahoo.finance.quoteslist%20where%20symbol%3D"
				+ "'" + symbol + "'" + "&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

		String dayshigh = null, dayslow = null;
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(request);
		int statusCode = client.executeMethod(method);
		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("Method failed: " + method.getStatusLine());
		} else {
			InputStream rstream = null;
			rstream = method.getResponseBodyAsStream();
			// Process response
			Document response = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(rstream);

			XPathFactory factory = XPathFactory.newInstance();
			XPath xPath = factory.newXPath();
			// Get all search Result nodes
			NodeList nodes = (NodeList) xPath.evaluate("query/results/quote", response, XPathConstants.NODESET);
			int nodeCount = nodes.getLength();
			// iterate over search Result nodes

			for (int i = 0; i < nodeCount; i++) {
				// Get each xpath expression as a string
				dayshigh = (String) xPath.evaluate("DaysHigh", nodes.item(i), XPathConstants.STRING);
				dayslow = (String) xPath.evaluate("DaysLow", nodes.item(i), XPathConstants.STRING);

				// System.out.println("Rate: " + rate);
				System.out.println("-----------");
			}

		}
		return (Double.parseDouble(dayshigh) + Double.parseDouble(dayslow)) / 2;
	}

	public static double GetHistoricRate(String symbol, String date) throws Exception {
		String request = "http://query.yahooapis.com/v1/public/yql?q=select%20Close%20from%20yahoo.finance.historicaldata%20where%20symbol%20%3D%20%22"
				+ symbol
				+ "%22%20and%20startDate%20%3D%20%22"
				+ date
				+ "%22%20and%20endDate%20%3D%20%22"
				+ date
				+ "%22&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
		String rate = null;
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(request);
		int statusCode = client.executeMethod(method);
		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("Method failed: " + method.getStatusLine());
		} else {
			InputStream rstream = null;
			rstream = method.getResponseBodyAsStream();
			// Process response
			Document response = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(rstream);

			XPathFactory factory = XPathFactory.newInstance();
			XPath xPath = factory.newXPath();
			// Get all search Result nodes
			NodeList nodes = (NodeList) xPath.evaluate("query/results/quote", response, XPathConstants.NODESET);
			int nodeCount = nodes.getLength();
			// iterate over search Result nodes

			for (int i = 0; i < nodeCount; i++) {
				// Get each xpath expression as a string
				rate = (String) xPath.evaluate("Close", nodes.item(i), XPathConstants.STRING);

			}

		}
		return Double.parseDouble(rate);
	}

}
