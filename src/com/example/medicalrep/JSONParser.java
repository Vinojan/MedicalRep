package com.example.medicalrep;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
//import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import android.annotation.SuppressLint;
import android.os.Message;
import android.util.JsonReader;
import android.util.Log;

public class JSONParser {

	InputStream is = null;	
	//static String json = null;
	

	// constructor
	public JSONParser() {

	}

	public JSONObject getJSONFromResponse(InputStream is) throws IOException {
		String theString = null;
		JSONObject jObj = null;
		String json = null;
		StringWriter writer = null;
		try {			
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);			
			StringBuilder sb = new StringBuilder();
			String line = null;	
			while ((line = reader.readLine()) != null) {				
				sb.append(line + "\n");				
			}
//			reader.close();
			Log.d("memoryTest", "bc");
//			writer = new StringWriter();
//			IOUtils.copy(is, writer, "UTF-8");
//			theString = writer.toString();
			Log.d("Response", sb.toString());
			jObj = new JSONObject(sb.toString());
			//jObj = (JSONObject) new JSONTokener(sb.toString()).nextValue();
			Log.d("memoryTest", "gc");
			//json = sb.toString();
			//Log.d("REsponse", json);
		} catch (IOException e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
			 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		/*finally{
			is.close();
			//writer.close();
			
		}*/
		
		return jObj;

	}
	

}
