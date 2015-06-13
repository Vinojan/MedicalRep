package com.example.medicalrep;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.telephony.gsm.SmsManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderingProducts extends ActionBarActivity implements OnItemSelectedListener {
	String pName=LoginTask.sharedCName;
	TextView tv_pname;
	EditText et_quantity;
	ServiceHandler serviceHandler;
	InputStream is,is1,is2,is3,is4;
	JSONParser parsing;
	JSONObject json;
	String id=LoginTask.sharedId;
	private Spinner spinner1, spinner2;
    String manufacturer_name,manufacturer_id,telephone_no,product_name,product_id,quantity,date,state;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ordering_products);
		tv_pname=(TextView)findViewById(R.id.tv_pharmacyName);
		tv_pname.setText(pName);
		addItemsOnSpinnerManufacturer() ;
		spinner1 = (Spinner) findViewById(R.id.spinner_manufacturers);
		spinner1.setOnItemSelectedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ordering_products, menu);
		
		return true;
	}
	
	public void addItemsOnSpinnerManufacturer() {
		spinner1 = (Spinner) findViewById(R.id.spinner_manufacturers);
		List list = new ArrayList();
		list.add("");
		List<NameValuePair> value= new ArrayList<NameValuePair>();
		  value.add(new BasicNameValuePair("account_state", "active"));
		serviceHandler = new ServiceHandler();
	    is = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=manufacturer/getmanufacturerbystate", 2, value);
	      parsing = new JSONParser();
	      JSONObject json;
	      try {
			 json = parsing.getJSONFromResponse(is);
			
			if(json.getString("message").matches("Successfull")){
			//	result = "success";
				JSONArray man=json.getJSONArray("manufacturers");
				int count = (man).length();
				for (int i = 0; i < count; i++) {
					JSONObject jsonArr = man.getJSONObject(i);
					list.add(jsonArr.getString("company_name"));
				}
			}
				ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, list);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner1.setAdapter(dataAdapter);
		
			}
	      catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				 e.printStackTrace();
			}
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
	
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			   long id) {
			  spinner1.setSelection(position);
			  manufacturer_name = (String) spinner1.getSelectedItem();
			  															//System.out.println(manufacturer_name);
			  List list1 = new ArrayList();
			  list1.add("Choose your product");
			  spinner2 = (Spinner) findViewById(R.id.spinner_products);
			  if(!(manufacturer_name.equalsIgnoreCase(""))){
			  List<NameValuePair> value= new ArrayList<NameValuePair>();
			  value.add(new BasicNameValuePair("manufacturer_name", manufacturer_name));
			  serviceHandler = new ServiceHandler();
			  is1 = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=manufacturer/getmanufacturerbyname", 2, value);
			      parsing = new JSONParser();
			      JSONObject json,json1;
			      		
			      try {
						 json = parsing.getJSONFromResponse(is1);
						
						if(json.getString("message").matches("Successfull")){
						//	result = "success";
							manufacturer_id=json.getString("manufacturer_id");
							telephone_no=json.getString("telephone_no");
														System.out.println(telephone_no);
															//System.out.println(manufacturer_id);
							}
						}
							
				      catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							 e.printStackTrace();
						} 
			      List<NameValuePair> value1= new ArrayList<NameValuePair>();
					 value1.add(new BasicNameValuePair("manufacturer_id", manufacturer_id));  
					// serviceHandler = new ServiceHandler();
					    is2 = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=products/getproductbymanufacturerid", 2, value1);
					    try {
							 json1 = parsing.getJSONFromResponse(is2);
							
							if(json1.getString("message").matches("Successfull")){
								JSONArray pro=json1.getJSONArray("products");
															System.out.println( pro);
								int count = ( pro).length();
								for (int i = 0; i < count; i++) {
						            JSONObject jsonArr = pro.getJSONObject(i);
						            list1.add(jsonArr.getString("product_name"));
								}
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
					    ArrayAdapter dataAdapter1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, list1);
						dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spinner2.setAdapter(dataAdapter1);
						
						 spinner2.setSelection(position);
						  product_name =  spinner2.getSelectedItem().toString();
						  															 System.out.println(product_name);
			 }


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void cancel(View view){
		Intent intent=new Intent(this,Pharmacist.class);
	  	  startActivity(intent);
		
	}
	public void orderProduct(View view){
		et_quantity=(EditText)findViewById(R.id.et_quantity);
		quantity=et_quantity.getText().toString();
		
		serviceHandler = new ServiceHandler();
		List<NameValuePair> value3= new ArrayList<NameValuePair>();
		value3.add(new BasicNameValuePair("product_name",product_name));  
	    is3 = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=products/getproductbyname", 2,value3);
	      parsing = new JSONParser();
	      JSONObject json1,json2;
	      try {
			 json1 = parsing.getJSONFromResponse(is3);
			
			if(json1.getString("message").matches("Successfull")){
			//	result = "success";
				product_id=json1.getString("product_id");
				
			}
			
			}
	      catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				 e.printStackTrace();
			}
	      date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	      state="undecided";
	      List<NameValuePair> value4= new ArrayList<NameValuePair>();
			value4.add(new BasicNameValuePair("pharmacy_id",id)); 
			value4.add(new BasicNameValuePair("manufacturer_id",manufacturer_id));
			value4.add(new BasicNameValuePair("product_id",product_id));
			value4.add(new BasicNameValuePair("quantity",quantity));
			value4.add(new BasicNameValuePair("date",date));
			value4.add(new BasicNameValuePair("state",state));
			is4 = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=orders/createorder", 2,value4);
			try {
				 json2 = parsing.getJSONFromResponse(is4);
				
				if(json2.getString("message").matches("Successfull")){
					//Toast.makeText(this, "Successfully ordered", Toast.LENGTH_SHORT).show();
					String message="MedicalRep : \n"+pName+" ordered for the product "+product_name+" on "+date+".";
					
					SmsManager sms = SmsManager.getDefault();
					sms.sendTextMessage(telephone_no, null, message, null, null);
					Intent intent=new Intent(this,Pharmacist.class);
				  	  startActivity(intent);
					
				}
				else{
					//Toast.makeText(this, "Failed to order", Toast.LENGTH_SHORT).show();
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
	
}
