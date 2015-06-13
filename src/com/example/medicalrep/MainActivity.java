package com.example.medicalrep;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	LoginTask task;
	 EditText un;
	 EditText pw;
    String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    public void signupManufacturer(View view){
    	Intent intent = new Intent(this, SignUpAsManufacturerActivity.class);
    	startActivity(intent);
    }
    public void signupPharmacy(View view){
    	Intent intent = new Intent(this, SignUpPharmacyActivity.class);
    	startActivity(intent);
    }
    /*
    public void openPharmacist(View view){
    	Intent intent = new Intent(this, SignUpPharmacyActivity.class);
    	startActivity(intent);
    }
    */
    public void signin(View view)
    {
    	un=(EditText)findViewById(R.id.edit_userName);
        pw=(EditText)findViewById(R.id.edit_password);
    	String username=un.getText().toString();
    	String password=pw.getText().toString();
   
    	task=new LoginTask(this, username, password);    //do the authentication task
    	task.execute();
    	
    }
    
}
