package com.example.medicalrep;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Pharmacist extends ActionBarActivity {
	
	TextView tv_pharmacyName;
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
		setContentView(R.layout.activity_pharmacist);
	}
	public void onBackPressed() {
        //do nothing
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pharmacist, menu);
		tv_pharmacyName=(TextView)findViewById(R.id.tv_pharmacyName);
		tv_address=(TextView)findViewById(R.id.tv_address);
		tv_telephoe=(TextView)findViewById(R.id.tv_telephone);
		tv_mail=(TextView)findViewById(R.id.tv_mail);
		tv_pharmacyName.setText(sharedCName);
		tv_address.setText(sharedAddress);
		tv_telephoe.setText(sharedTelephone);
		tv_mail.setText(sharedMail);
		return true;
	}
	

	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle logout button on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_logout:
	            openLogin();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	public void editDetail(View view){
		Intent intent = new Intent(this, EditPharmacistDetail.class);
    	startActivity(intent);
	}
	public void viewOrders(View view){
		Intent intent = new Intent(this, PharmacyViewOrders.class);
    	startActivity(intent);
	}
	public void orderProduct(View view){
		Intent intent = new Intent(this, OrderingProducts.class);
    	startActivity(intent);
	}
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
