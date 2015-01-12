package com.example.administrator.meradhobi;

/*
 * Middleware for the construction of URLs. This way its easier to use REST API.
 * We can receive directly the parameters or the Debt instance for constructing a query to the database.
 * 
 * Author: Lola Priego. me@lolapriego.com
 */

public class UrlBuilder {
	
	// Every query goesto this URL. URL of the app DB
	public final static String BASE_URL = "https://api.mongolab.com/api/1/databases/dhobi_base_1/collections/";
	
	// Lola Priego API key to access MongoLab services. Not very secure. ToDo: cipher someway
	private final static String URL_API_KEY = "apiKey=nxslG9WNoFOHU38iB9OmGqxndQx7AfiL";
	
	// It takes a collection and the parameters and give you the right url. For example, for the debts collection and debttor name paramether it will return its debts
	public static String paramsToUrl (String [] params, String collection){
		String path = "%22" + params[0] + "%22%3A%20%20%22" + replaceSpaces(params[1]) + "%22";
		for(int i=2; i<params.length; i= i+2){
			path += ",%20%22" + params[i] + "%22%3A%20%20%22" + replaceSpaces(params[i+1]) + "%22";
		}
		return BASE_URL + collection + "?q=%7B" + path + "%7D&" + URL_API_KEY;
	}
	
	public static String replaceSpaces(String s){
		int count = 0;
		for(int i = 0; i<s.length(); i++)
			if(s.charAt(i) == ' ') count++;
		
		char [] array = new char[s.length() + count * 2];
		int j = 0;
		for(int i = 0; i<s.length(); i++){
			if(s.charAt(i) == ' '){
				array[j++] = '%';
				array[j++] = '2';
				array[j++] = '0';
			}
			else
				array[j++] = s.charAt(i);
		}
		
		return new String(array);
	}
	
	// It provide access to one collection
	public static String toUrl(String collection){
		return BASE_URL + collection + "?" + URL_API_KEY;
	}
}
