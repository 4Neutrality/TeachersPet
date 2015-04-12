package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
    String cid,course,section;
    Spinner term,time, days;
    //ID for screen layout
    int layout;

	/**
	 * When screen is created set to professor add course layout and populate drop down menus.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        layout = R.layout.activity_9p_add_course;
		setContentView(layout);

        setSpinnerData();
	}

	
	/**
	 * Get values for course and send to database.
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
        if(isValidInput()) {
            //Name of values to pass and their values
            String[] itemNames = new String[]{"id", "pname", "cid", "course", "section", "term","time", "days"};
            String[] itemValues = loadValues();

            super.sendData("", itemNames, itemValues, AppCSTR.URL_CREATE_COURSE, this, layout, false);
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
            response = "Course Format (xx101).";
        }else if (section.length() < 1) {
            response = "Section at least be 1 digit.";
        }else {
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
        section = ((EditText) findViewById(R.id.section)).getText().toString();
    }

    /**
     * FIll in data with what user entered.
     */
    private String[] loadValues(){
        //get all values that the user entered
        String[] fields = new String[]{super.getID(), super.getName(), cid, course, section,
                String.valueOf(term.getSelectedItem()),String.valueOf(time.getSelectedItem()),
                String.valueOf(days.getSelectedItem())};
        String[] itemValues = new String[fields.length];

        //Get input from user along with their name and id
        for(int j = 0; j < fields.length; j++){
            itemValues[j] = fields[j];
        }

        return itemValues;
    }

    /**
     * Add data to drop down menu.
     */
    private void setSpinnerData() {
        int counter = 0;
        //Drop down menus
        term = (Spinner) findViewById(R.id.term);
        time = (Spinner) findViewById(R.id.time);
        days = (Spinner) findViewById(R.id.days);

        Spinner[] spinners = new Spinner[]{term,time,days};
        int[] ids = new int[]{R.array.term,R.array.time,R.array.days};

       //Adding data to menu with an adapter
        for(Spinner spin: spinners){
            String[] list = getResources().getStringArray(ids[counter]);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                    list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spin.setAdapter(dataAdapter);
            counter ++;
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
	    		Toast.makeText(getApplicationContext(), "Course created!", Toast.LENGTH_SHORT).show();
                super.start(this, AddCoursePActivity.class, true);
	    	}else if(success == 2){
                Toast.makeText(getApplicationContext(), "Course Already Exist!", Toast.LENGTH_SHORT).show();
            }else {
	    		Toast.makeText(getApplicationContext(), "No course created!", Toast.LENGTH_SHORT).show();
	    	}
	    }
	}
}
