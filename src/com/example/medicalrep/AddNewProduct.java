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
import android.content.Intent;

public class AddNewProduct extends ActionBarActivity {
	String company_id=LoginTask.sharedId;
	String company_name=LoginTask.sharedCName;
	String product_name,unit_price,description;
	TextView tv_cname;
	TextView tv_result;
	EditText et_prname;
	EditText et_price;
	EditText et_desc;
	
	ServiceHandler serviceHandler;
	InputStream is;
	JSONParser parsing;
	JSONObject json;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_product);
	}
	public void onBackPressed() {
        //do nothing
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_product, menu);
		tv_cname=(TextView)findViewById(R.id.tv_companyName);
		
		tv_cname.setText(company_name);
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
	
	public void addNewProduct(View view){
		et_prname=(EditText)findViewById(R.id.et_productName);
		et_price=(EditText)findViewById(R.id.et_unitPrice);
		et_desc=(EditText)findViewById(R.id.et_description);
		tv_result=(TextView)findViewById(R.id.tv_result);
		product_name=et_prname.getText().toString();
		unit_price=et_price.getText().toString();
		description=et_desc.getText().toString();
		String result=" ";
		Boolean success=false;
		List<NameValuePair> value= new ArrayList<NameValuePair>();			//checking for user table and there is no user with same name create entry in the user table
		 value.add(new BasicNameValuePair("product_name", product_name));
		 value.add(new BasicNameValuePair("manufacturer_id", company_id));
		 value.add(new BasicNameValuePair("unit_price", unit_price));
		 value.add(new BasicNameValuePair("description", description));
	      serviceHandler = new ServiceHandler();
	      is = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=products/createproduct", 2, value);
	      parsing = new JSONParser();
	      
	      try {
			 json = parsing.getJSONFromResponse(is);
			
			if(json.getString("message").matches("Successfull")){
										
				result = "success";
				success=true;
		}else{
			result = "product already available";
			success=false;
		}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
	     
	      if(success){
	    	  tv_result.setText(result);
	    	  Intent intent=new Intent(this,ProductUpdate.class);
	    	  startActivity(intent);
	      }
	      else{
	    	  tv_result.setText(result);
	      }
		
	}
	public void cancel(View view){
		Intent intent=new Intent(this,ProductUpdate.class);
  	     startActivity(intent);
	}
}
