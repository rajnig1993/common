package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Util {

	/** Requests a specified URL and returns the response received.
	 * @param urlString URL as String
	 * @return response as String
	 * @throws IOException if either URL is malformed or unable to connect 
	 */
	public static String getResponse(String urlString) throws IOException {
		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(10000);
		String response = readFromStream(connection.getInputStream());
		return response;
	}

	/** Reads data from an input stream.
	 * @param is input stream
	 * @return data received from input stream.
	 * @throws IOException if error occurs while reading data
	 */
	public static String readFromStream(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder str = new StringBuilder();
		String line;
		
	    while ((line = br.readLine()) != null) {
	    	str.append(line);
	    }
	    br.close();
	    
	    return str.toString();
	}
	
	/** Indexes JSON objects by specified keys
	 * @param arr Array of JSON objects
	 * @param key Key property to be used as index
	 * @return A JSON object containing all JSON objects indexed by key
	 */
	public static JSONObject getIndexedObjects(JSONArray arr, String key) {
		JSONObject indexedObj = new JSONObject();
		
		for (int i = 0; i < arr.length(); i++) {
			try {
				JSONObject obj = arr.getJSONObject(i);
				indexedObj.put(obj.getString(key), obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return indexedObj;
	}
	
	/** Generates a JSON object from list of JSON objects where index is <code>keyProp</code> and value is <code>valueProp</code>.
	 * @param arr Array of JSON objects
	 * @param keyProp Field in every JSON object to be used as index
	 * @param valueProp Field denoting value for index <code>keyProp</code>
	 * @return A JSON object containing all JSON objects with index as value of <code>keyProp</code> and value as value of <code>valueProp</code>
	 */
	public static JSONObject getIndexedValues(JSONArray arr, String keyProp, String valueProp) {
		JSONObject indexedObj = new JSONObject();
		
		for (int i = 0; i < arr.length(); i++) {
			try {
				JSONObject obj = arr.getJSONObject(i);
				indexedObj.put(obj.getString(keyProp), obj.optString(valueProp));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return indexedObj;
	}
	
	/** Derives JSON object where index is <code>keyProp</code> and value is <code>valueProp</code>.
	 * @param obj JSON object
	 * @param keyProp Field in JSON object to be used as index
	 * @param valueProp Field denoting value for index <code>keyProp</code>
	 * @return A JSON object with index as value of <code>keyProp</code> and value as value of <code>valueProp</code>
	 */
	public static JSONObject getIndexedJSON(JSONObject obj, String keyProp, String valueProp) {
		return getIndexedValues(new JSONArray().put(obj), keyProp, valueProp);
	}
	
	/** Appends a JSON object to another JSON object.
	 * @param obj JSON object to which other JSON object is to be appended
	 * @param toBeAppended JSON object to be appended
	 * @throws JSONException if error occurs in appending JSON object
	 */
	public static void appendJSONObject(JSONObject obj, JSONObject toBeAppended) throws JSONException {
		Iterator<?> keys = toBeAppended.keys();
		
		while (keys.hasNext()) {
			String key = (String) keys.next();
			obj.put(key, toBeAppended.get(key));
		}
	}
}
