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

import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ProductUpdate extends ActionBarActivity implements View.OnClickListener{
    TextView tvCName;
    String company_id=LoginTask.sharedId;
	
	ServiceHandler serviceHandler;
	InputStream is;
	JSONParser parsing;
	String product_id,unit_price,description,product_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_update);
	}
	public void onBackPressed() {
        //do nothing
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_update, menu);
		tvCName=(TextView)findViewById(R.id.tv_companyName );
		tvCName.setText(LoginTask.sharedCName);
		//task=new ProductManupulator(this,company_id);
    	//task.execute();
		String result= "null";
		 List<NameValuePair> value= new ArrayList<NameValuePair>();
		 value.add(new BasicNameValuePair("manufacturer_id", company_id));
		 
	      serviceHandler = new ServiceHandler();
	      is = serviceHandler.makeServiceCall("http://vinojan.byethost33.com/medicalrep/index.php?r=products/getproductbymanufacturerid", 2, value);
	      parsing = new JSONParser();
	      JSONObject json;
	      try {
			 json = parsing.getJSONFromResponse(is);
			
			if(json.getString("message").matches("Successfull")){
				result = "success";
			
			JSONArray pro=json.getJSONArray("products");
			
			int count = ( pro).length();
			TableLayout table=(TableLayout)findViewById(R.id.productTable);
			/* Create a new row to be added. */
	          for (int i = 0; i < count; i++) {
	            JSONObject jsonArr = pro.getJSONObject(i);
	                   
	            TableRow tr = new TableRow(this);
	            tr.setClickable(true);
	            tr.setId(i);
	            tr.setOnClickListener(this);
	           // tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
	            TextView labelName = new TextView(this);
	            labelName.setId(2*i);
	            labelName.setText(jsonArr.getString("product_name"));
	            labelName.setPadding(3, 0, 3, 0);
	            tr.addView(labelName);
	            TextView labelPrice = new TextView(this);
	            labelPrice.setId(2*i+1);
	            labelPrice.setText(jsonArr.getString("unit_price"));
	            labelPrice.setPadding(53, 0, 3, 0);
	            tr.addView(labelPrice);
	            TextView labelDesc = new TextView(this);
	           /*
	            labelDesc.setText(jsonArr.getString("description"));
	            labelDesc.setPadding(2, 0, 5, 0);
	            tr.addView(labelDesc);
	            */
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
	
	public void addProduct(View view){
		Intent intent=new Intent(this,AddNewProduct.class);
  	  startActivity(intent);
		
	}
	public void backToMain(View view){
		Intent intent=new Intent(this,Manufacturer.class);
  	  startActivity(intent);
		
	}
	public void editProduct(View view){
		Intent intent=new Intent(this,Manufacturer.class);
  	  startActivity(intent);
		
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		view.setBackgroundColor(Color.LTGRAY);
		TableRow t = (TableRow) view;
	      TextView firstTextView = (TextView) t.getChildAt(0);
	      TextView secondTextView = (TextView) t.getChildAt(1);
	      String product_name = firstTextView.getText().toString();
	      String unit_price = secondTextView.getText().toString();
	      editProductData(product_name);
	      }
	public void editProductData(String name){
		Intent intent=new Intent(this,EditProductDetail.class);
		intent.putExtra("product_name",name);
  	  startActivity(intent);		
	}
	
}
