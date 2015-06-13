package com.example.medicalrep;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditManufacturerDetail extends ActionBarActivity {
	ServiceHandler serviceHandler;
	InputStream is;
	JSONParser parsing;
	JSONObject json;
	String id=LoginTask.sharedId;
	String company_name=LoginTask.sharedCName;
	String user_name=LoginTask.sharedUserName;
	String password;
	String address=LoginTask.sharedAddress;
	String telephone_no=LoginTask.sharedTelephone;
	String email=LoginTask.sharedMail;
	Validation validate;
	EditText et_cname,et_pword,et_address,et_tele,et_mail;
	TextView tv_userName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_manufacturer_detail);
	}
	public void onBackPressed() {
        //do nothing
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_manufacturer_detail, menu);
		
		et_cname=(EditText)findViewById(R.id.et_pharmacyName);
		tv_userName=(TextView)findViewById(R.id.et_username);
		//et_pword=(EditText)findViewById(R.id.et_password);
		et_address=(EditText)findViewById(R.id.et_address);
		et_tele=(EditText)findViewById(R.id.et_telephone);
		et_mail=(EditText)findViewById(R.id.et_email);
		
		et_cname.setText(company_name);
		tv_userName.setText(user_name);
		et_address.setText(address);
		et_tele.setText(telephone_no);
		et_mail.setText(email);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void updateManufacturer(View view){
		LoginTask.sharedCName=et_cname.getText().toString();
		company_name=LoginTask.sharedCName;
		LoginTask.sharedAddress=et_address.getText().toString();
		address=LoginTask.sharedAddress;
		LoginTask.sharedTelephone=et_tele.getText().toString().trim();
		telephone_no=LoginTask.sharedTelephone;
		LoginTask.sharedMail=et_mail.getText().toString().trim();
		email=LoginTask.sharedMail;
		
		validate=new Validation();
		if(validate.emailValidation(this, email)){
													//System.out.println(telephone_no);
			if(validate.phoneNoValidation(this,telephone_no)){
				List<NameValuePair> value= new ArrayList<NameValuePair>();
				value.add(new BasicNameValuePair("id",id));
				value.add(new BasicNameValuePair("company_name",company_name));
				value.add(new BasicNameValuePair("address",address));
				value.add(new BasicNameValuePair("telephone_no",telephone_no));
				value.add(new BasicNameValuePair("email",email));
				serviceHandler = new ServiceHandler();
				is = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=manufacturer/updatemanufacturer", 2, value);
				parsing = new JSONParser();
	      
				try {
					json = parsing.getJSONFromResponse(is);
					System.out.println(json.getString("message"));
			
					if(json.getString("message").matches("Successfull")){
				
						Intent intent = new Intent(this, Manufacturer.class);
						startActivity(intent);
					}else{			
			
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			else{
				et_tele.setText("");
				}
		}
		else{
	    	et_mail.setText("");  
	      }
		
	}
	public void cancel(View view){
		Intent intent=new Intent(this,Manufacturer.class);
		startActivity(intent);
	}
}
