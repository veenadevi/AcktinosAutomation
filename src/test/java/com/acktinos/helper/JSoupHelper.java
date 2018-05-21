package com.acktinos.helper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JSoupHelper {
	
	public static Document doPageScrap(String url)  {
		Document resultsPage = null;
		try {
			resultsPage = Jsoup.connect("https://dev57903.service-now.com/sp").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return resultsPage;
	}

}
