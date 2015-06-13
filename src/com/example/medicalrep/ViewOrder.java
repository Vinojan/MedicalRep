package com.example.medicalrep;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewOrder extends ActionBarActivity {

	String pharmacy_id=LoginTask.sharedId;
	TextView tv_order_id,tv_manufacturerName,tv_productName,tv_quantity,tv_state;
	Button back;
	String order_id,manufacturerName,productName,quantity,state;
		@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_order);
		
		tv_order_id=(TextView)findViewById(R.id.tv_setOrderId);
		tv_manufacturerName=(TextView)findViewById(R.id.tv_setManufacturerName);
		tv_productName=(TextView)findViewById(R.id.tv_setProductName);
		tv_quantity=(TextView)findViewById(R.id.tv_setQuantity);
		tv_state=(TextView)findViewById(R.id.tv_state);
		order_id=getIntent().getStringExtra("order_id");
		manufacturerName=getIntent().getStringExtra("manufacturer_name");
		productName=getIntent().getStringExtra("product_name");
		quantity=getIntent().getStringExtra("quantity");
		state=getIntent().getStringExtra("state");
		tv_order_id.setText(order_id);
		tv_manufacturerName.setText(manufacturerName);
		tv_productName.setText(productName);
		tv_quantity.setText(quantity);
		tv_state.setText(state);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_order, menu);
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
	public void back(View view){
		Intent intent=new Intent(this,PharmacyViewOrders.class);
		startActivity(intent);
	}
}
