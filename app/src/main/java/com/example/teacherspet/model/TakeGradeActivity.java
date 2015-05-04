package com.example.teacherspet.model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Record students grade in database.
 *
 * @author Johnathon Malott, Kevin James
 * @version 4/22/2015
 */
public class TakeGradeActivity extends BasicActivity {
    //Intent that called this activity
    Intent i;

    /**
     * When screen is created set to assignment layout.
     *
     * @param savedInstanceState Most recently supplied data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i = getIntent();

        sendItems();
    }

    /**
     * Sends new assignment to database.
     */
    private void sendItems() {
        String[] itemNames = new String[]{"sid", "name", "cid", "grade","pos"};
        String[] itemValues = getValues();

        sendData("", itemNames, itemValues, AppCSTR.URL_GRADE_ASSIGNMENT, this, i.getIntExtra(AppCSTR.LAYOUT,-1), false);
    }

    /**
     * Place assignments in spinner to choose from.
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
            int success = data.getIntExtra(AppCSTR.SUCCESS,-1);
            if(success == 0){
                Toast.makeText(this, "Grade added!", Toast.LENGTH_SHORT).show();
            } else {
                //Exit screen professor has no students.
                Toast.makeText(this, "No Grade Added!", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    private String[] getValues(){
        return new String[]{i.getStringExtra(AppCSTR.SID),
                i.getStringExtra(AppCSTR.NAME),
                            i.getStringExtra(AppCSTR.CID),
                i.getStringExtra(AppCSTR.GRADE),
                i.getStringExtra(AppCSTR.POSITION)};
    }
}
