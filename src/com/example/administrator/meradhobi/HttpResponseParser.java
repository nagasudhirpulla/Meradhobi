package com.example.administrator.meradhobi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class HttpResponseParser {

	/* Parse username and id of a user from a user get to the database
	 * Param: response converted to String
	 * Return array with username and id
	 */
	public static String[] getUserAndId(String res) {
		JSONTokener tokener = new JSONTokener( res );
		String username = null;
		String id = null;
		String name = null;

		try{
			JSONArray array = new JSONArray( tokener );
			JSONObject json = array.getJSONObject(0);    
			if (json.has("user")) {
				username = json.getString("user");
			}
			if(json.has("name")){
				name = json.getString("name");
			}
			if(json.has("_id")){
				json = json.getJSONObject("_id");
				if(json.has("$oid")){
					id = json.getString("$oid");
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		String [] r = {username, id, name};
		return  r;   
	}

	public static String getEmail(String res){
		JSONTokener tokener = new JSONTokener(res);
		String email = null;

		try{
			JSONArray array = new JSONArray( tokener );
			JSONObject json = array.getJSONObject(0);    
			if (json.has("email")) {
				email = json.getString("email");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return email;
	}

	public static JSONObject getField(String res, String field){
		JSONTokener tokener = new JSONTokener(res);
		JSONObject json = null;
		try{
			JSONArray array = new JSONArray( tokener );
			json = array.getJSONObject(0);    
			if (json.has(field)) {
				return json;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
