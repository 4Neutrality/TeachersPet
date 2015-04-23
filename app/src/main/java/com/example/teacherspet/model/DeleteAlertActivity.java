package com.example.teacherspet.model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.teacherspet.control.AlertsActivity;

/**
 * Delete alerts from the database.
 */
public class DeleteAlertActivity extends BasicActivity {
    //Holds data from activity that passed it
    Intent intent;
    //ID background for screen
    int layout;

    /**
     * Store alerts that need to be deleted and searched database for those alerts.
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
     * Gathers data needed in order to delete alert and sends to database.
     */
    private void startSearch(){
        String[] itemNames = new String[]{AppCSTR.AID};
        String[] itemValues = new String[]{intent.getStringExtra(AppCSTR.AID)};

        sendData("", itemNames, itemValues, AppCSTR.URL_DELETE_ALERT, this, layout, false);
    }
	
	/**
	 * Checks to see if the alert was deleted successfully.
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
	    	if(success == 0){
	    		Toast.makeText(getApplicationContext(), "Alert(s) Deleted", Toast.LENGTH_SHORT).show();
                super.start(this, AlertsActivity.class, true);
	    	}else {
	    		Toast.makeText(getApplicationContext(), "No Alert(s) Deleted", Toast.LENGTH_SHORT).show();
	    	}
	    }
        super.start(this, AlertsActivity.class, true);
	}
}
