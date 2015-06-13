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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProductDetail extends ActionBarActivity {
	Button edit,save,cancel;
	EditText pname,uprice,desc;
	String product_name=null; 
	String product_id,manufacturer_id,unit_price,description;
	ServiceHandler serviceHandler;
	InputStream is1,is2;
	JSONParser parsing;
	JSONObject json1,json2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_product_detail);
		save=(Button)findViewById(R.id.bt_save);
		save.setVisibility(View.GONE);
		product_name=getIntent().getStringExtra("product_name");
		getProductDetails(product_name);
	
		pname=(EditText)findViewById(R.id.et_productName);
		pname.setText(product_name);
		pname.setEnabled(false);
		uprice=(EditText)findViewById(R.id.et_unitPrice);
		uprice.setText(unit_price);
		uprice.setEnabled(false);
		desc=(EditText)findViewById(R.id.et_description);
		desc.setText(description);
		desc.setEnabled(false);
		
		}
	public void onBackPressed() {
        //do nothing
    }
	public void getProductDetails(String name){
		//if(name!=null){
		
		  List<NameValuePair> value= new ArrayList<NameValuePair>();
		  value.add(new BasicNameValuePair("product_name",name));
		  serviceHandler = new ServiceHandler();
		  is1 = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=products/getproductbyname", 2, value);
		      parsing = new JSONParser();
		   	      		
		      try {
					 json1 = parsing.getJSONFromResponse(is1);
					
					if(json1.getString("message").matches("Successfull")){
						product_id=json1.getString("product_id");
						manufacturer_id=json1.getString("manufacturer_id");
						unit_price=json1.getString("unit_price");
						description=json1.getString("description");
						//System.out.println("suc"+product_name+unit_price+description);								//System.out.println(manufacturer_id);
						}
					else{
						System.out.println("Can not retrieve data");
					}
					}
						
			      catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						 e.printStackTrace();
					}
	//	}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_product_detail, menu);
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
	
	public void editDetail(View view){
		pname=(EditText)findViewById(R.id.et_productName);	
		pname.setEnabled(true);
		uprice=(EditText)findViewById(R.id.et_unitPrice);
		uprice.setEnabled(true);
		desc=(EditText)findViewById(R.id.et_description);
		desc.setEnabled(true);
		save=(Button)findViewById(R.id.bt_save);
		save.setVisibility(View.VISIBLE);
		edit=(Button)findViewById(R.id.bt_edit);
		edit.setVisibility(View.GONE);
	}
	public void saveDetail(View view){
		pname=(EditText)findViewById(R.id.et_productName);	
		String product_name_edited=pname.getText().toString();
		String is_name_edited=null;
		if(product_name.equalsIgnoreCase(product_name_edited)){
			is_name_edited="no";
		}
		else{
			is_name_edited="yes";
		}
															
		uprice=(EditText)findViewById(R.id.et_unitPrice);
		unit_price=uprice.getText().toString();
		desc=(EditText)findViewById(R.id.et_description);
		description=desc.getText().toString();
														//	System.out.println(product_id+product_name_edited+unit_price+description+is_name_edited);
		  List<NameValuePair> value1= new ArrayList<NameValuePair>();
		  value1.add(new BasicNameValuePair("product_id",product_id));
		  value1.add(new BasicNameValuePair("product_name",product_name_edited));
		  value1.add(new BasicNameValuePair("unit_price",unit_price));
		  value1.add(new BasicNameValuePair("description",description));
		  value1.add(new BasicNameValuePair("is_name_edited",is_name_edited));
		  serviceHandler = new ServiceHandler();
		  is2 = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=products/updateproduct", 2, value1);
		      parsing = new JSONParser();
		   	      		
		      try {
					 json2 = parsing.getJSONFromResponse(is2);
					
					if(json2.getString("message").matches("Successfull")){
						Toast.makeText(getApplicationContext(), "Successfully updated",Toast.LENGTH_LONG).show();							
						Intent intent=new Intent(this,ProductUpdate.class);
						startActivity(intent);
					}
					else{
						Toast.makeText(getApplicationContext(), "Product name has taken",Toast.LENGTH_LONG).show();
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
	public void cancel(View view){
		Intent intent=new Intent(this,ProductUpdate.class);
		startActivity(intent);
	}
}
