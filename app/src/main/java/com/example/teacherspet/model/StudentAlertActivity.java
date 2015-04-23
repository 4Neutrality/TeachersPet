package com.example.teacherspet.model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.teacherspet.control.CoursesActivity;

import java.util.ArrayList;

/**
 * Adds an alert to alerts database warning professor someone is waiting to enroll in course.
 *
 * @author Johnathon Malott, Kevin James
 * @version 3/21/2015
 */
public class StudentAlertActivity extends BasicActivity {
    //Holds student ID
    ArrayList<String> viewIDs;
    //Data to pass to web page
  	String[] itemNames;
  	//Data collecting from web page
  	String[] itemValues;
    //ID for background layout
    int layout;

    /**
     * Get data that was passed to this intent and stores it.
     *
     * @param savedInstanceState Most recently supplied data
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		viewIDs = intent.getStringArrayListExtra(AppCSTR.ALL_IDS);
        layout = intent.getIntExtra(AppCSTR.LAYOUT, -1);
		startSearch();
	}

    /**
     * Send data to database to receive some information.
     */
    private void startSearch(){
        itemNames = new String[]{"sid", "cid", "sName"};
        itemValues = new String[]{super.getID() , viewIDs.get(AppCSTR.FIRST_ELEMENT), super.getName()};
        super.sendData("", itemNames, itemValues, AppCSTR.URL_STUDENT_ALERT, this, layout, false);
    }
	
	/**
	 * Receives data from database and notifies user if course has been submitted .
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
	    	int success = data.getIntExtra(AppCSTR.SUCCESS, -1);
	    	//Log.d("Alert Success", "" + success);
	    	if(success == 0){
	    		Toast.makeText(getApplicationContext(), "Course submitted", Toast.LENGTH_SHORT).show();
	    	}else if(success == 3){
	    		Toast.makeText(getApplicationContext(), "Course already submitted", Toast.LENGTH_SHORT).show();
	    	}else {
                Toast.makeText(getApplicationContext(), "Course not submitted", Toast.LENGTH_SHORT).show();
            }
	    }
        super.start(this, CoursesActivity.class, true);
	}
}
