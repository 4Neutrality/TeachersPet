package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;

/**
 * Allows professor to add a course to database.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 3/24/2015
 */
public class AddCoursePActivity extends BasicActivity {
    String cid,course;
    //Name of field in database
    String[] itemNames;
    //Values to place into those fields
    String[] itemValues;

	/**
	 * When screen is created set to professor add course layout.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_9p_add_course);
	}
	
	/**
	 * Get values for course and send to database.
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
        if(isValidInput()) {
            itemNames = new String[]{"id", "pname", "cid", "course", "term", "time", "section", "days"};
            itemValues = new String[itemNames.length];
            loadValues();

            super.sendData("", itemNames, itemValues, AppCSTR.URL_CREATE_COURSE, this, false);
        }
	}

    /*
	 * This method checks that the given input for the AddCoursePActivity is in a valid format. If any argument is not valid,
	 * then a toast will displayed to the screen containing the error message.
	 *
	 * @return boolean
	 **/
    public boolean isValidInput() {
        getText();
        // Holds toast response
        String response;
        // Check account number for length, and to see it contains '920'
        if (cid.length() < 1) {
            response = "Course ID must be at least 1 digit.";
        }
        else if (!course.matches("[a-zA-Z]{2}\\d{3}")) {
            response = "Course Format (cs101).";
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
        cid = ((EditText) findViewById(R.id.cid)).getText().toString();
        course = ((EditText) findViewById(R.id.course)).getText().toString();
    }

    /**
     * FIll in data with what user entered.
     */
    private void loadValues(){
        //get all values that the user entered
        int[] ids = new int[]{R.id.cid,R.id.course,R.id.term,R.id.time,R.id.section,R.id.days};
        //First entry is user id, second for name
        itemValues[AppCSTR.FIRST_ELEMENT] = super.getID();
        Log.e("USERNAME: ", super.getName());
        itemValues[AppCSTR.SECOND_ELEMENT] = super.getName();
        for(int j = 2; j < itemNames.length; j++){
            itemValues[j] = ((EditText) findViewById(ids[(j - 2)])).getText().toString();
        }
    }

	/**
	 * Tells if course was added or not and warns user of the result.
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
	    	//0 means course was added
	    	int success = data.getIntExtra(AppCSTR.SUCCESS, -1);
	    	if(success == 0){
	    		Toast.makeText(getApplicationContext(), "Course created", Toast.LENGTH_SHORT).show();
	    	}else{
	    		Toast.makeText(getApplicationContext(), "No course created", Toast.LENGTH_SHORT).show();
	    	}
	    }
        super.start(this, AddCoursePActivity.class, true);
	}
}
