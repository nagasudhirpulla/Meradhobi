package com.example.administrator.meradhobi;

import org.json.JSONException;
import org.json.JSONObject;

public class DhobiDBHelper {

	public static String signupUser(String Id, String email, String name, String picurl){

		String Res = null;
		try {
			Res = CustomHttpClient.executeHttpPost(UrlBuilder.toUrl("googleBase"), new JSONObject().put("Id", Id).put("Email", email).put("displayname", name).put("picURL", picurl));
			return Res;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Res;
	}

	public static JSONObject lookForUser(String[] iDparam)
	{
		try {
			String response = CustomHttpClient.executeHttpGet(UrlBuilder.paramsToUrl(iDparam, "googleBase"));

			JSONObject json = HttpResponseParser.getField(response,"Id");
			if(json!=null)
				return json;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}

	public static JSONObject addAddress(String[] iDparam) {
		try {
			String response = CustomHttpClient.executeHttpGet(UrlBuilder.paramsToUrl(iDparam, "googleBase"));
			JSONObject json = HttpResponseParser.getField(response,"Id");
			if(json!=null)
				CustomHttpClient.executeHttpPut(UrlBuilder.paramsToUrl(iDparam, "googleBase"), new JSONObject().put("$push", new JSONObject().put("address", "randval")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	

	}
}