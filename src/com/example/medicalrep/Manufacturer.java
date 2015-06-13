package com.example.medicalrep;

import android.support.v7.app.ActionBarActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Manufacturer  extends ActionBarActivity {
    TextView tv_companyName;
    TextView tv_address;
    TextView tv_telephoe;
    TextView tv_mail;
    
    String sharedId = LoginTask.sharedId;
    String sharedCName = LoginTask.sharedCName;
    String sharedAddress = LoginTask.sharedAddress;
    String sharedTelephone= LoginTask.sharedTelephone;
    String sharedMail = LoginTask.sharedMail;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manufacturer);
	}
	public void onBackPressed() {
        //do nothing
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manufacturer, menu);
		tv_companyName=(TextView)findViewById(R.id.tv_companyName);
		tv_address=(TextView)findViewById(R.id.tv_address);
		tv_telephoe=(TextView)findViewById(R.id.tv_telephone);
		tv_mail=(TextView)findViewById(R.id.tv_mail);
		tv_companyName.setText(sharedCName);
		tv_address.setText(sharedAddress);
		tv_telephoe.setText(sharedTelephone);
		tv_mail.setText(sharedMail);
		return true;
		
	}

	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_logout:
	            openLogin();
	            return true;
	        
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	public void updateProducts(View view){
    	Intent intent = new Intent(this, ProductUpdate.class);
    	startActivity(intent);
    	
    }
	public void editDetail(View view){
		Intent intent = new Intent(this, EditManufacturerDetail.class);
    	startActivity(intent);
	}
	
	public void viewOrders(View view){
		Intent intent = new Intent(this, ManufacturerViewOrders.class);
    	startActivity(intent);
	}
	//Intent intent = getIntent();
	public void openLogin(){
		Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
	}
	public void changePassword(View view){
		Intent intent=new Intent(this,ChangePassword.class);
		startActivity(intent);
	}
	public void deactivateAccount(View view){
		Intent intent=new Intent(this,DeactivateAccount.class);
		startActivity(intent);
	}
}
