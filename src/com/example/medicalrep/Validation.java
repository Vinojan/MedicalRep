package com.example.medicalrep;

import android.content.Context;
import android.widget.Toast;

public class Validation {
	
public boolean emailValidation(Context context,String email){
	String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
	
	if (email.matches(emailPattern))
	{
		//Toast.makeText(context,"valid email address",Toast.LENGTH_SHORT).show();
		return true;
	}
	else 
	{
		Toast.makeText(context,"Invalid email address", Toast.LENGTH_SHORT).show();
		return false;
	}
}

public boolean passwordValidation(Context context,String password){
	int length=password.length();
	boolean valid;
	if(length>=8){
		//Toast.makeText(context,"Medium password",Toast.LENGTH_SHORT).show();
		if(password.matches("[a-zA-Z]+")){
			Toast.makeText(context,"Password must contain at least one non-alphabet",Toast.LENGTH_SHORT).show();
			valid=false;
		}
		else{
			//Toast.makeText(context,"Strong password",Toast.LENGTH_SHORT).show();
			valid=true;
		}
	}
	else{
		Toast.makeText(context,"Password must contain atleast 8 characters",Toast.LENGTH_SHORT).show();
		valid=false;
	}
	return valid;
}
	
public boolean phoneNoValidation(Context context,String phoneNo){
	String MobilePattern = "[0-9]{10}";
	if(phoneNo.matches(MobilePattern)){
		return true;
	}
	else{
		Toast.makeText(context,"Invalid phone number",Toast.LENGTH_SHORT).show();
		return false;
	}
}
	

}
