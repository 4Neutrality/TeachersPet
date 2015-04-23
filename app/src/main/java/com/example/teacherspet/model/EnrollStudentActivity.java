package com.example.teacherspet.model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Enrolls a student into a class by placing them in the database.
 */
public class EnrollStudentActivity extends BasicActivity {
    //Holds data from activity that passed it
    Intent intent;
    //Id for background screen
    int layout;

    /**
     * Adds student into the database.
     *
     * @param savedInstanceState Most recently supplied data.
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intent = getIntent();
        layout = intent.getIntExtra(AppCSTR.LAYOUT, -1);
		startSearch();
	}

    /**
     * Gathers data needed in order to add student to database.
     */
    private void startSearch(){
        String[] itemNames = new String[]{"sid","cid","id","cName"};
        String[] itemValues = new String[]{intent.getStringExtra(AppCSTR.ALERT_SID),intent.getStringExtra(AppCSTR.CID),
                super.getID(), intent.getStringExtra(AppCSTR.COURSE_NAME)};

        sendData("", itemNames, itemValues, AppCSTR.URL_ENROLL_STUDENT, this, layout, false);
    }
	
	/**
	 * Checks to see if student was added and alert deleted successfully.
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
	    		Toast.makeText(getApplicationContext(), "Student Added", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(EnrollStudentActivity.this, DeleteAlertActivity.class);
                i.putExtra(AppCSTR.LAYOUT, layout);
                i.putExtra(AppCSTR.AID, intent.getStringExtra(AppCSTR.AID));
                startActivity(i);
	    	}else {
	    		Toast.makeText(getApplicationContext(), "No Student Added", Toast.LENGTH_SHORT).show();
	    	}
            finish();
	    }
	}
}
