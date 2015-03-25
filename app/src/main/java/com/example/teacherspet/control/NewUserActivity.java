package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;
import com.example.teacherspet.R;

/**
 * Creates a new user.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 3/21/2014
 */
public class NewUserActivity extends BasicActivity {
    String name, accountnumber, college, email, password;
    CheckBox student;
    CheckBox professor;
	
	/**
	 * When screen is created set to new user layout.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_3_new_user);
	}
	
	/**
	 * Submits data to database if one of the boxes is checked
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
        student = (CheckBox) findViewById(R.id.checkS);
        professor = (CheckBox) findViewById(R.id.checkP);
		//Action to hold screen change.
		if(view.getId() == R.id.btn_submit){
			if((student.isChecked() || professor.isChecked()) && isValidInput()){
				sendItems();
			}
		}
	}
	
	/**
	 * Send items to Post Activity to deliver to database from php file
	 */
	public void sendItems(){
		String [] itemNames = new String[]{"name","accountnumber","college","email","password","accounttype"};
		String [] itemValues = getValues();

        super.sendData("", itemNames, itemValues, AppCSTR.URL_CREATE_USER, this, false);
	}
	
	/**
	 * Receives data from model that was received from the database.
	 * 
	 * @param requestCode Number that was assigned to the intent being called.
	 * @param resultCode RESULT_OK if successful, RESULT_CANCELED if failed
	 * @param data Intent that was just exited.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	         Intent data) {
		//Check request that this is response to
	    if (requestCode == 0) {
	    	//Tells if items where added or not. 1 mean successful
	    	int success = data.getIntExtra("success", -1);
	    	if(success == 0){
	    		Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_SHORT).show();
	    	}else {
	    		Toast.makeText(getApplicationContext(), "No account created", Toast.LENGTH_SHORT).show();
	    	}
	    }
        super.start(this, LoginActivity.class, true);
	}
	
	/**
	 * Get all data the user filled out and return it.
	 * 
	 * @return A list of all values entered by user.
	 */
	private String[] getValues(){
        //Student by default
        String accounttype = AppCSTR.STUDENT;
        
        if(student.isChecked()){
			accounttype = AppCSTR.STUDENT;
		}else if(professor.isChecked()){
			accounttype = AppCSTR.PROFESSOR;
		}
        
        return (new String[]{name,accountnumber,college,email,password,accounttype});
	}
	
	/*
	 * This method checks that the given input for the NewUserActivity is in a valid format. If any argument is not valid,
	 * then a toast will displayed to the screen containing the error message. 
	 * 
	 * @return boolean
	 * @param inputname - Name
	 * 		  inputAccountnumber - '920' student id
	 * 		  inputCollege - College Name
	 * 		  inputEmail - Email address
	 * 		  inputPassword - Password
	 **/
	public boolean isValidInput() {
        getText();
        // Holds toast response
        String response;
        // Check account number for length, and to see it contains '920'
        if (accountnumber.length() != 9) {
        	response = "Invalid account number length.";
        }
        else if (!accountnumber.matches("920\\d{6}")) {
        	response = "Account number must start with '920'.";
        }
        // Email must have '.edu' suffix
        else if (!email.matches("\\w+@.+\\.edu")) {
        	response = "Email must have '.edu' suffix.";
        }
        // Minimum password length is 4
        else if (password.length() < 4) {
        	response = "Password must be at least 4 characters long.";
        }
        else {
        	// Success
        	return true;
        }
        // Invalid input
        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
        return false;
	}

    /**
     * Gets text that user has input into fields.
     */
    private void getText(){
        name = ((EditText) findViewById(R.id.fld_name)).getText().toString();
        accountnumber = ((EditText) findViewById(R.id.fld_id)).getText().toString();
        college = ((EditText) findViewById(R.id.fld_school)).getText().toString();
        email = ((EditText) findViewById(R.id.fld_email)).getText().toString();
        password = ((EditText) findViewById(R.id.fld_password)).getText().toString();
    }

}
