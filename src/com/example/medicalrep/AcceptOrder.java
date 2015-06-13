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
import android.telephony.gsm.SmsManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AcceptOrder extends ActionBarActivity {
	String company_id=LoginTask.sharedId;
	TextView tv_order_id,tv_pharmacyName,tv_productName,tv_quantity,tv_state;
	Button accept,reject,cancel;
	String order_id,pharmacyName,productName,quantity,state,telephone_no;
	ServiceHandler serviceHandler;
	InputStream is1,is2;
	JSONParser parsing;
	JSONObject json1,json2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accept_order);
		tv_order_id=(TextView)findViewById(R.id.tv_setOrderId);
		tv_pharmacyName=(TextView)findViewById(R.id.tv_setPharmacyName);
		tv_productName=(TextView)findViewById(R.id.tv_setProductName);
		tv_quantity=(TextView)findViewById(R.id.tv_setQuantity);
		tv_state=(TextView)findViewById(R.id.tv_state);
		order_id=getIntent().getStringExtra("order_id");
		pharmacyName=getIntent().getStringExtra("pharmacy_name");
		productName=getIntent().getStringExtra("product_name");
		quantity=getIntent().getStringExtra("quantity");
		state=getIntent().getStringExtra("state");
		telephone_no=getIntent().getStringExtra("telephone_no");
		tv_order_id.setText(order_id);
		tv_pharmacyName.setText(pharmacyName);
		tv_productName.setText(productName);
		tv_quantity.setText(quantity);
		tv_state.setText(state);
		if(!(state.equalsIgnoreCase("undecided")))
		{
			accept=(Button)findViewById(R.id.bt_accept);
			accept.setVisibility(View.GONE);
			reject=(Button)findViewById(R.id.bt_reject);
			reject.setVisibility(View.GONE);
			cancel=(Button)findViewById(R.id.bt_cancel);
			cancel.setVisibility(View.GONE);
		}
		
	}
	public void onBackPressed() {
        //do nothing
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accept_order, menu);
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
		Intent intent=new Intent(this,ManufacturerViewOrders.class);
		startActivity(intent);
	}
	public void acceptOrder(View view){
		acceptUpdate();
		
		Intent intent=new Intent(this,ManufacturerViewOrders.class);
		startActivity(intent);
		
	}
	
	public void acceptUpdate(){
		String state="accepted";
		
		List<NameValuePair> value1= new ArrayList<NameValuePair>();
		  value1.add(new BasicNameValuePair("order_id", order_id));
		  value1.add(new BasicNameValuePair("state",state));
		  serviceHandler = new ServiceHandler();
	      is1 = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=orders/updateorder", 2, value1);
	  
	      parsing = new JSONParser();
		         		
		      try {
					 json1 = parsing.getJSONFromResponse(is1);
					
					if(json1.getString("message").matches("Successfull")){
						Toast.makeText(getApplicationContext(), "Order is accepted",Toast.LENGTH_LONG).show();
						String message="MedicalRep : \n "+LoginTask.sharedCName+" has accepted your order for the product "+productName+".";
						
						SmsManager sms = SmsManager.getDefault();
						sms.sendTextMessage(telephone_no, null, message, null, null);
					}
					else if(json1.getString("message").matches("Accepted")){
						Toast.makeText(getApplicationContext(), "Order is already accepted",Toast.LENGTH_LONG).show();
					}
					else{
						Toast.makeText(getApplicationContext(), "Order is already rejected",Toast.LENGTH_LONG).show();
					}
					}
						
			      catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						 e.printStackTrace();
					} 
					
	}
	public void rejectOrder(View view){
		 String state="rejected";
		List<NameValuePair> value2= new ArrayList<NameValuePair>();
		  value2.add(new BasicNameValuePair("order_id", order_id));
		  value2.add(new BasicNameValuePair("state",state));
		  serviceHandler = new ServiceHandler();
		  is2 = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=orders/updateorder", 2, value2);
		      parsing = new JSONParser();
		           		
		      try {
					 json2 = parsing.getJSONFromResponse(is2);
					
					if(json2.getString("message").matches("Successfull")){
						Toast.makeText(getApplicationContext(), "Order is rejected",Toast.LENGTH_LONG).show();
						
						String message="MedicalRep : \n "+LoginTask.sharedCName+" has rejected your order for the product "+productName+".";
						
						SmsManager sms = SmsManager.getDefault();
						sms.sendTextMessage(telephone_no, null, message, null, null);
					}
					else if(json2.getString("message").matches("Accepted")){
						Toast.makeText(getApplicationContext(), "Order is already accepted",Toast.LENGTH_LONG).show();
					}
					else{
						Toast.makeText(getApplicationContext(), "Order is already rejected",Toast.LENGTH_LONG).show();
					}
					}
						
			      catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						 e.printStackTrace();
					} 
		  	
		Intent intent=new Intent(this,ManufacturerViewOrders.class);
		startActivity(intent);
	}
}
