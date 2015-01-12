package com.example.administrator.meradhobi;

import org.json.JSONException;
import org.json.JSONObject;

public class DhobiDBHelper {

	public static String signupUser(String Id){

		String Res = null;
		try {
			Res = CustomHttpClient.executeHttpPost(UrlBuilder.toUrl("googleBase"), new JSONObject().put("Id", Id));
			return Res;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Res;
	}

	public static JSONObject lookForUser(String[] params)
	{
		try {
			String response = CustomHttpClient.executeHttpGet(UrlBuilder.paramsToUrl(params, "userBase"));

			JSONObject json = HttpResponseParser.getField(response,"Id");
			if(json!=null)
				return json;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
}