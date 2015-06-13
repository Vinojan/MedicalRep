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
import android.widget.Toast;

public class SignupTask extends AsyncTask<String, Void, String> {
	
	ServiceHandler serviceHandler;
	InputStream is,is1;
	JSONParser parsing;
	String id,username, password,company_name,address,telephone,email,roleOfUser;
	Context context;
	Boolean isSuccess=false;
	ProgressDialog proDialog;

	public SignupTask(Context context, String username, String password,String role){
		this.username = username;
		this.password = password;
		this.roleOfUser=role;
		this.context = context;
	}
	
	public SignupTask(Context context, String username, String password,String role,String name,String address,String tele,String mail){
		this.username = username;
		this.password = password;
		this.roleOfUser=role;
		this.context = context;
		this.company_name=name;
		this.address=address;
		this.telephone=tele;
		this.email=mail;
		
	}
	
	
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		proDialog = new ProgressDialog(context);
		proDialog.setMessage("Signing up..");
		proDialog.setIndeterminate(false);
		proDialog.setCancelable(false);
		proDialog.show();
	}
	
	protected String doInBackground(String... arg0) {
		String result= "null";
		 List<NameValuePair> value= new ArrayList<NameValuePair>();			//checking for user table and there is no user with same name create entry in the user table
		 value.add(new BasicNameValuePair("username", username));
		 value.add(new BasicNameValuePair("password", password));
		 value.add(new BasicNameValuePair("role", roleOfUser));
	      serviceHandler = new ServiceHandler();
	      is = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=user/createuser", 2, value);
	      parsing = new JSONParser();
	      JSONObject json,json1;
	      try {
			 json = parsing.getJSONFromResponse(is);
			
			if(json.getString("message").matches("Successfull")){
				
				isSuccess=true;
				
				id=json.getString("id");
				//roleOfUser=json.getString("role");
				result = "success"+id;
		}else{
			result = "username is not available";
			isSuccess=false;
		}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
	      
	      if(isSuccess){
	    	  List<NameValuePair> value1= new ArrayList<NameValuePair>();	
	    	  value1.add(new BasicNameValuePair("id", id));
	    	
	    	  value1.add(new BasicNameValuePair("address", address));
	    	  value1.add(new BasicNameValuePair("telephone_no", telephone));
	    	  value1.add(new BasicNameValuePair("email",email));
	    	 if(roleOfUser.equalsIgnoreCase("manufacturer")){
	    		 value1.add(new BasicNameValuePair("company_name", company_name));
	    	 
	    	 is = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=manufacturer/createmanufacturer", 2, value1);
	    	 }
	    	 else{
	    		 value1.add(new BasicNameValuePair("pharmacy_name", company_name));
	    	  is = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=pharmacist/createpharmacist", 2, value1);
	    	  
	    	  }
	    	  try {
	    	  	 	 json = parsing.getJSONFromResponse(is);
	 			
	 			if(json.getString("message").matches("Successfull")){
	 				
	 				isSuccess=true;
	 				
	 				if(roleOfUser.equalsIgnoreCase("manufacturer")){
	 					 List<NameValuePair> value2= new ArrayList<NameValuePair>();
	 					 value2.add(new BasicNameValuePair("id", id));
	 					is1 = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=manufacturer/getmanufacturerbyid", 2, value2);
	 					json1 = parsing.getJSONFromResponse(is1);
	 					LoginTask.sharedCName=json1.getString("company_name");
	 					LoginTask.sharedAddress=json1.getString("address");
	 					LoginTask.sharedTelephone=json1.getString("telephone_no");
	 					LoginTask.sharedMail=json1.getString("email");
	 					}
	 					else if(roleOfUser.equalsIgnoreCase("pharmacist")){
	 						 List<NameValuePair> value2= new ArrayList<NameValuePair>();
	 						 value2.add(new BasicNameValuePair("id", id));
	 						is1 = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=pharmacist/getpharmacistbyid", 2, value2);
	 						json1 = parsing.getJSONFromResponse(is1);
	 						LoginTask.sharedCName=json1.getString("pharmacy_name");
	 						LoginTask.sharedAddress=json1.getString("address");
	 						LoginTask.sharedTelephone=json1.getString("telephone_no");
	 						LoginTask.sharedMail=json1.getString("email");
	 						}
	 					
	 				
	 				result = "successfully registered..";
	 		}else{
	 			result = "Try again";
	 			isSuccess=false;
	 		}
	 			
	 			
	 		} catch (IOException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		} catch (JSONException e) {
	 			// TODO Auto-generated catch block
	 			 e.printStackTrace();
	 		}
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
