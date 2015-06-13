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
import android.widget.Toast;

public class DeactivateAccount extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deactivate_account);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deactivate_account, menu);
		return true;
	}
	public void onBackPressed() {
        //do nothing
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
	public void deactivateAccount(View view){
		
		ServiceHandler serviceHandler;
		InputStream is;
		JSONParser parsing;
		List<NameValuePair> value= new ArrayList<NameValuePair>();
		 value.add(new BasicNameValuePair("id", LoginTask.sharedId));
		 serviceHandler = new ServiceHandler();
		 if(LoginTask.sharedRole.equalsIgnoreCase("manufacturer")){
	      is = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=manufacturer/deactivateaccount", 2, value);
		 }
		 else{
			 is = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=pharmacist/deactivateaccount", 2, value); 
		 }
		 parsing = new JSONParser();
	      JSONObject json;
	      try {
			 json = parsing.getJSONFromResponse(is);
			
			if(json.getString("message").matches("Successfull")){
				Toast.makeText(getApplicationContext(), "Successfully Account Deactivated",Toast.LENGTH_LONG).show();
				Intent intent=new Intent(this,MainActivity.class);
				startActivity(intent);
					}
			else{
				Toast.makeText(getApplicationContext(), "Unable to deactivate account",Toast.LENGTH_LONG).show();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
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
}
