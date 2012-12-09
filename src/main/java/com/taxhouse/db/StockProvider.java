package com.taxhouse.db;

import java.io.InputStream;
import java.util.ArrayList;

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

	public static double GetCurrentRate(ArrayList<String> symbol) {

		String request = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22";
		String requestEnd = "%22%2C%22MSFT%22)&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
		double price = 0;
		System.out.println(symbol.size());

		for (int j = 0; j < symbol.size(); j++) {
			if (j == (symbol.size()) - 1) {
				request = request.concat(symbol.get(j));
			} else {
				request = request.concat(symbol.get(j) + "%22%2C%22");
			}
			System.out.println(symbol.get(j));

		}
		request = request.concat(requestEnd);
		System.out.println(request);
		String dayshigh = null, dayslow = null;
		try {
			HttpClient client = new HttpClient();
			GetMethod method = new GetMethod(request);
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			} else {
				InputStream rstream = null;
				rstream = method.getResponseBodyAsStream();
				// Process response
				Document response = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder().parse(rstream);

				XPathFactory factory = XPathFactory.newInstance();
				XPath xPath = factory.newXPath();
				// Get all search Result nodes
				NodeList nodes = (NodeList) xPath
						.evaluate("query/results/quote", response,
								XPathConstants.NODESET);
				int nodeCount = nodes.getLength();
				// iterate over search Result nodes

				for (int i = 0; i < nodeCount - 1; i++) {
					// Get each xpath expression as a string
					dayshigh = (String) xPath.evaluate("DaysHigh",
							nodes.item(i), XPathConstants.STRING);
					dayslow = (String) xPath.evaluate("DaysLow", nodes.item(i),
							XPathConstants.STRING);
					price = price
							+ (Double.parseDouble(dayshigh) + Double
									.parseDouble(dayslow)) / 2;
				}
			}
			return price;
		} catch (Exception e){
			return 0;
		}
	}

}
