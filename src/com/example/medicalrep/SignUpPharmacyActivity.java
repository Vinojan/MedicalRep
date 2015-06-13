package com.example.medicalrep;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SignUpPharmacyActivity extends ActionBarActivity {
	SignupTask task;
	Validation validate;
	EditText pharmacy_name;
	EditText user_name;
	EditText pass_word;
	EditText address;
	EditText tel_no;
	EditText e_mail;
	String role="pharmacist";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up_pharmacy);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up_pharmacy, menu);
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
	
	 public void signup(View view)
	    {
	    	
	    	pharmacy_name=(EditText)findViewById(R.id.edit_pharmacyname);
		 	user_name=(EditText)findViewById(R.id.edit_username);
	        pass_word=(EditText)findViewById(R.id.edit_password);
	       address=(EditText)findViewById(R.id.edit_adress);
	    	tel_no=(EditText)findViewById(R.id.edit_telephone);
	    	e_mail=(EditText)findViewById(R.id.edit_email);
	    	
	    	String pharmacyname=pharmacy_name.getText().toString();
	    	String username=user_name.getText().toString();
	    	String password=pass_word.getText().toString().trim();
	    	String addr=address.getText().toString();
	    	String tele=tel_no.getText().toString().trim();
	    	String mail=e_mail.getText().toString().trim();
	    	
	    	validate=new Validation();
	    	if(validate.passwordValidation(this, password)){
	    		if(validate.emailValidation(this,mail)){
	    			if(validate.phoneNoValidation(this,tele)){
	    				task=new SignupTask(this, username, password,role,pharmacyname,addr,tele,mail);
	    				task.execute();
	    			}
	    			else{
	    				tel_no.setText("");
	    			}
	    		}
	    		else{
	    			e_mail.setText("");
	    		}
	    	}
	    	else{
	    		pass_word.setText("");
	    	}
	    }
}
