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
import android.widget.Toast;

public class ChangePassword extends ActionBarActivity {
	TextView tv_cname,tv_uname;
	EditText et_oldPass,et_newPass,et_retypePass;
	String oldPassword=LoginTask.sharedPassword;
	String userName=LoginTask.sharedUserName;
	String newPassword,retypePassword;
	Validation validate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		tv_cname=(TextView)findViewById(R.id.tv_companyName);
		tv_cname.setText(LoginTask.sharedCName);
		tv_uname=(TextView)findViewById(R.id.tv_setUserName);
		tv_uname.setText(userName);
	}
	public void onBackPressed() {
        //do nothing
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
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
	
	public void cancel(View view){
		if(LoginTask.sharedRole.equalsIgnoreCase("manufacturer")){
			Intent intent=new Intent(this,Manufacturer.class);
			startActivity(intent);
		}
		else{
			Intent intent=new Intent(this,Pharmacist.class);
			startActivity(intent);
		}
	}
	public void changePassword(View view){
		String oldpw,newpw,rtpw;
		et_oldPass=(EditText)findViewById(R.id.et_oldPassword);
		oldpw=et_oldPass.getText().toString();
		if(oldPassword.equals(oldpw)){
			et_newPass=(EditText)findViewById(R.id.et_newPassword);
			newpw=et_newPass.getText().toString().trim();
			et_retypePass=(EditText)findViewById(R.id.et_retypePassword);
			rtpw=et_retypePass.getText().toString().trim();
			validate=new Validation();
			if(validate.passwordValidation(this,newpw)){
			if(newpw.equals(rtpw)){
				saveNewPassword(newpw);
			}
			else{
				Toast.makeText(getApplicationContext(), "New password not match",Toast.LENGTH_LONG).show();
			}
			}
			else{
				et_retypePass.setText("");
				et_newPass.setText("");
			}
		}
		else{
			Toast.makeText(getApplicationContext(), "Old password is incorrect",Toast.LENGTH_LONG).show();
		}
		
	}
	public void saveNewPassword(String password){
		ServiceHandler serviceHandler;
		InputStream is;
		JSONParser parsing;
		List<NameValuePair> value= new ArrayList<NameValuePair>();
		 value.add(new BasicNameValuePair("username", userName));
		 value.add(new BasicNameValuePair("password", password));
	      serviceHandler = new ServiceHandler();
	      is = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=user/changepassword", 2, value);
	      parsing = new JSONParser();
	      JSONObject json;
	      try {
			 json = parsing.getJSONFromResponse(is);
			
			if(json.getString("message").matches("Successfull")){
				Toast.makeText(getApplicationContext(), "Successfully password changed",Toast.LENGTH_LONG).show();
				Intent intent=new Intent(this,Manufacturer.class);
				startActivity(intent);
					}
			else{
				Toast.makeText(getApplicationContext(), "Unable to change password",Toast.LENGTH_LONG).show();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
	     
	}
}
