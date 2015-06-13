package com.example.medicalrep;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.inputmethod.InputBinding;
import android.widget.Toast;

public class LoginTask extends AsyncTask<String, Void, String>{
	
	//private static final Context context = null;
	ServiceHandler serviceHandler;
	InputStream is,is1,is2;
	JSONParser parsing;
	String username, password,roleOfUser;
	Context context;
	Boolean isSuccess=false;
	public static String sharedId;
	public static String sharedCName;
	public static String sharedAddress;
	public static String sharedTelephone;
	public static String sharedMail;
	public static String sharedUserName;
	public static String sharedPassword;
	public static String sharedRole;
	public static String sharedState;
	ProgressDialog proDialog;
	
	public LoginTask(Context context, String username, String password){
		this.username = username;
		this.password = password;
		this.context = context;
		sharedUserName=username;
		sharedPassword=password;
	}
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		proDialog = new ProgressDialog(context);
		proDialog.setMessage("Loging in..");
		proDialog.setIndeterminate(false);
		proDialog.setCancelable(false);
		proDialog.show();
	}
	@Override
	protected String doInBackground(String... arg0) {
		String result= "null";
		 List<NameValuePair> value= new ArrayList<NameValuePair>();
		 value.add(new BasicNameValuePair("username", username));
		 value.add(new BasicNameValuePair("password", password));
	      serviceHandler = new ServiceHandler();
	      is = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=user/getuserbyname", 2, value);
	      parsing = new JSONParser();
	      JSONObject json,json1;
	      try {
			 json = parsing.getJSONFromResponse(is);
			
			if(json.getString("message").matches("Successfull")){
				result = "success";
				isSuccess=true;
				roleOfUser=json.getString("role");
				sharedRole=roleOfUser;
				sharedId=json.getString("id");
				if(roleOfUser.equalsIgnoreCase("manufacturer")){
				 List<NameValuePair> value1= new ArrayList<NameValuePair>();
				 value1.add(new BasicNameValuePair("id", sharedId));
				is1 = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=manufacturer/getmanufacturerbyid", 2, value1);
				json1 = parsing.getJSONFromResponse(is1);
				sharedCName=json1.getString("company_name");
				sharedAddress=json1.getString("address");
				sharedTelephone=json1.getString("telephone_no");
				sharedMail=json1.getString("email");
				sharedState=json1.getString("account_state");
				if(sharedState.equalsIgnoreCase("inactive")){
					List<NameValuePair> valueA= new ArrayList<NameValuePair>();
					 valueA.add(new BasicNameValuePair("id", sharedId));
					is2 = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=manufacturer/activateAccount", 2, valueA);
				}
				}
				else if(roleOfUser.equalsIgnoreCase("pharmacist")){
					 List<NameValuePair> value1= new ArrayList<NameValuePair>();
					 value1.add(new BasicNameValuePair("id", sharedId));
					is1 = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=pharmacist/getpharmacistbyid", 2, value1);
					json1 = parsing.getJSONFromResponse(is1);
					sharedCName=json1.getString("pharmacy_name");
					sharedAddress=json1.getString("address");
					sharedTelephone=json1.getString("telephone_no");
					sharedMail=json1.getString("email");
					sharedState=json1.getString("account_state");
					if(sharedState.equalsIgnoreCase("inactive")){
					List<NameValuePair> valueA= new ArrayList<NameValuePair>();
					 valueA.add(new BasicNameValuePair("id", sharedId));
					is2 = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=pharmacist/activateAccount", 2, valueA);
					}
					}
				
		}else{
			result = "User name or password is incorrect";
			
			isSuccess=false;
			//result="success";
			
		}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
	     
	      			
		return result;
	}
	
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
		if(isSuccess){
		openUI();
		}
		else{
			proDialog.hide();
		}
	}
	

	private void openUI() {
		// TODO Auto-generated method stub
		if(roleOfUser.equalsIgnoreCase("pharmacist")){
    		Intent intent = new Intent(context, Pharmacist.class);
    		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		context.startActivity(intent);
    	}
    	else if(roleOfUser.equalsIgnoreCase("manufacturer")){
    		Intent intent = new Intent(context, Manufacturer.class);
    		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		context.startActivity(intent);
    	}	
    	
	}
	
}
