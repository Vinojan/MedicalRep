package com.example.medicalrep;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ManufacturerViewOrders extends ActionBarActivity implements View.OnClickListener{
	TextView tvCName;
    String company_id=LoginTask.sharedId;
    String pharmacy_id,product_id,quantity,order_id;
    String pharmacy_name,product_name,state,telephone_no;
    ServiceHandler serviceHandler;
	InputStream is,is1,is2,is3;
	JSONParser parsing;
	JSONObject json,json1,json2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manufacturer_orders);
	}
	public void onBackPressed() {
        //do nothing
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manufacturer_orders, menu);
		
		tvCName=(TextView)findViewById(R.id.tv_companyName );
		tvCName.setText(LoginTask.sharedCName);
		
		String result= "null";
		 List<NameValuePair> value= new ArrayList<NameValuePair>();
		 value.add(new BasicNameValuePair("manufacturer_id", company_id));
		 
	      serviceHandler = new ServiceHandler();
	      is = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=orders/getorderbymanufacturerid", 2, value);
	      parsing = new JSONParser();
	    
	      try {
			 json = parsing.getJSONFromResponse(is);
			
			if(json.getString("message").matches("Successfull")){
				result = "success";
			
			JSONArray ord=json.getJSONArray("orders");
			
			int count = ( ord).length();
			TableLayout table=(TableLayout)findViewById(R.id.orderTable);
			/* Create a new row to be added. */
	          for (int i = 0; i < count; i++) {
	            JSONObject jsonArr = ord.getJSONObject(i);
	            state=jsonArr.getString("state");
	           
	            TableRow tr = new TableRow(this);
	            tr.setClickable(true);
	            tr.setOnClickListener(this);
	            if(state.equalsIgnoreCase("rejected")){
	            	tr.setBackgroundColor(Color.CYAN);
	            }
	            else if(state.equalsIgnoreCase("accepted")){
	            	tr.setBackgroundColor(Color.GREEN);
	            }
	            TextView labelPharmacy_id = new TextView(this);
	            labelPharmacy_id.setText(jsonArr.getString("pharmacy_id"));
	            labelPharmacy_id.setPadding(3, 0, 3, 0);
	            tr.addView(labelPharmacy_id);
	            TextView labelProduct_id = new TextView(this);
	            labelProduct_id.setText(jsonArr.getString("product_id"));
	            labelProduct_id.setPadding(33, 0, 3, 0);
	            tr.addView(labelProduct_id);
	            TextView labelQuantity = new TextView(this);
	            labelQuantity.setText(jsonArr.getString("quantity"));
	           labelQuantity.setPadding(63, 0, 3, 0);
	            tr.addView(labelQuantity);
	            TextView labelOrderId= new TextView(this);
	            labelOrderId.setText(jsonArr.getString("order_id"));
	            labelOrderId.setPadding(83, 0, 3, 0);
	            labelOrderId.setVisibility(View.GONE);
	            tr.addView(labelOrderId);
	            TextView state= new TextView(this);
	            state.setText(jsonArr.getString("state"));
	            state.setPadding(103, 0, 3, 0);
	            state.setVisibility(View.GONE);
	            tr.addView(state);
	            
	            table.addView(tr);
		       
	          }
	       
			
			}
			else{
			result = "failure";
			
		}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
	     
		
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

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		view.setBackgroundColor(Color.LTGRAY);
		TableRow t = (TableRow) view;
	      TextView firstTextView = (TextView) t.getChildAt(0);
	      TextView secondTextView = (TextView) t.getChildAt(1);
	      TextView thirdTextView = (TextView) t.getChildAt(2);
	      TextView fourthTextView = (TextView) t.getChildAt(3);
	      TextView fifthTextView = (TextView) t.getChildAt(4);
	       pharmacy_id = firstTextView.getText().toString();
	       product_id = secondTextView.getText().toString();
	       quantity = thirdTextView.getText().toString();
	       order_id = fourthTextView.getText().toString();
	       state = fifthTextView.getText().toString();
	       getOrderDetails();
	       Intent intent=new Intent(this,AcceptOrder.class);
			intent.putExtra("product_name",product_name);
			intent.putExtra("order_id",order_id);
			intent.putExtra("pharmacy_name",pharmacy_name);
			intent.putExtra("quantity",quantity);
			intent.putExtra("state",state);
			intent.putExtra("telephone_no",telephone_no);
			
	  	  startActivity(intent);
	}
	
	public void getOrderDetails(){
		  List<NameValuePair> value1= new ArrayList<NameValuePair>();
		  value1.add(new BasicNameValuePair("id", pharmacy_id));
		  
		  is1 = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=pharmacist/getpharmacistbyid", 2, value1);
		      parsing = new JSONParser();
		      
		      		
		      try {
					 json1 = parsing.getJSONFromResponse(is1);
					
					if(json1.getString("message").matches("Successfull")){
						pharmacy_name=json1.getString("pharmacy_name");
						telephone_no=json1.getString("telephone_no");
														System.out.println(pharmacy_name);
						}
					}
						
			      catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						 e.printStackTrace();
					} 
		      List<NameValuePair> value2= new ArrayList<NameValuePair>();
			  value2.add(new BasicNameValuePair("product_id", product_id));
			  
			  is2 = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=products/getproductbyid", 2, value2);
			      parsing = new JSONParser();
			     
			      		
			      try {
						 json2 = parsing.getJSONFromResponse(is2);
						
						if(json2.getString("message").matches("Successfull")){
							product_name=json2.getString("product_name");
							
															//System.out.println(product_name);
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
