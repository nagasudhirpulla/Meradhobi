package com.example.administrator.meradhobi;

public class QueryBuilder {
	
	/**
	 * Specify your database name here
	 * @return
	 */
	public String getDatabaseName() {
		return "dhobi_base_1";
	}

	/**
	 * Specify your MongoLab API here
	 * @return
	 */
	public String getApiKey() {
		return "nxslG9WNoFOHU38iB9OmGqxndQx7AfiL";
	}
	
	/**
	 * This constructs the URL that allows you to manage your database, 
	 * collections and documents
	 * @return
	 */
	public String getBaseUrl()
	{
		return "https://api.mongolab.com/api/1/databases/"+getDatabaseName()+"/collections/";
	}
	
	/**
	 * Completes the formating of your URL and adds your API key at the end
	 * @return
	 */
	public String docApiKeyUrl()
	{
		return "?apiKey="+getApiKey();
	}
	
	/**
	 * Returns the userBase collection
	 * @return
	 */
	public String documentRequest()
	{
		return "userBase";
	}
	
	/**
	 * Builds a complete URL using the methods specified above
	 * @return
	 */
	public String buildContactsSaveURL()
	{
		return getBaseUrl()+documentRequest()+docApiKeyUrl();
	}
	
	/**
	 * Formats the contact details for MongoHQ Posting
	 * @param contact: Details of the person 
	 * @return
	 */
	public String createContact(String Id)
	{
		return String
		.format("{\"Id\": \"%s\"}",
				Id);
	}
	
	public String checkContact(String Id)
	{
		//return String.format("https://api.mongolab.com/api/1/databases/dhobi_base_1/collections/userBase?q={\"Id\": \"%s\"}&apiKey=nxslG9WNoFOHU38iB9OmGqxndQx7AfiL",Id);
		
		//return "https://api.mongolab.com/api/1/databases/dhobi_base_1/collections/userBase?q={\"Id\":\"113669339194963770872\"}&apiKey=nxslG9WNoFOHU38iB9OmGqxndQx7AfiL";
		return "http://date.jsontest.com";
		//return "http://10.0.2.2/JSON/";
		
	}
	
	
	
	

}