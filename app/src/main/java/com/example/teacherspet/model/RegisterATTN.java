package com.example.teacherspet.model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Allows professor to take register meaning some students can already be processed which will take them
 * off the list before attendance is taken.
 *
 * @author Johnathon Malott, Kevin James
 * @version 3/24/2015
 */
public class RegisterATTN extends BasicActivity{
    //Ids of students in course who are present.
    ArrayList<String> presentIDs;
    ArrayList<String> lateIDs;
    //Id for background screen
    int layout;

    /**
     * Gets the ids of students who are present and place them on rooster.
     *
     * @param savedInstanceState Most recently supplied data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        presentIDs = intent.getStringArrayListExtra(AppCSTR.GREEN_IDS);
        lateIDs = intent.getStringArrayListExtra(AppCSTR.RED_IDS);
        layout = intent.getIntExtra(AppCSTR.LAYOUT, -1);

        startSearch();
    }

    /**
     * Load data to pass to database and pass it..
     */
    private void startSearch() {
        //2 for the first two initial entries
        int lengthP = presentIDs.size();
        int total = lengthP + lateIDs.size();
        int start;
        int end;
        String [] itemNames = new String[total + 3];
        String [] itemValues = new String[total + 3];

        //Store data that needs to be sent
        itemNames[AppCSTR.FIRST_ELEMENT] = "countP";
        itemValues[AppCSTR.FIRST_ELEMENT] = "" + lengthP;
        itemNames[AppCSTR.SECOND_ELEMENT] = "total";
        itemValues[AppCSTR.SECOND_ELEMENT] = "" + total;
        itemNames[AppCSTR.THIRD_ELEMENT] = "courseID";
        itemValues[AppCSTR.THIRD_ELEMENT] = super.getCourseID();

        start = AppCSTR.FOURTH_ELEMENT;
        end = lengthP + 3;
        for(int i = start; i < end; i++){
            itemNames[i] = "pid" + (i-start);
            itemValues[i] = presentIDs.get((i-start));
        }
        start = end;
        end = total + AppCSTR.FOURTH_ELEMENT;
        for(int i = start; i < end; i++){
            itemNames[i] = "lid" + (i-start);
            itemValues[i] = lateIDs.get((i-start));
        }

        super.sendData("", itemNames, itemValues, AppCSTR.URL_REGISTER_ATTENDANCE, this, layout, false);
    }

    /**
     * Warns user if register was taken or not.
     *
     * @param requestCode Number that was assigned to the intent being called.
     * @param resultCode  RESULT_OK if successful, RESULT_CANCELED if failed
     * @param data        Intent that was just exited.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        //Check request that this is response to
        if (requestCode == 0) {
            int success = data.getIntExtra(AppCSTR.SUCCESS, -1);
            if (success == 0) {
                Toast.makeText(this,"Register Taken",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Register Not taken",Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }
}
