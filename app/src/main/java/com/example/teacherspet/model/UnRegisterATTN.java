package com.example.teacherspet.model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.teacherspet.view.AttendancePRActivity;

import java.util.ArrayList;

/**
 * Allows professor to undo and student from the register which will place them back onto the attendance
 * list.
 *
 * @author Johnathon Malott, Kevin James
 * @version 4/23/2015
 */
public class UnRegisterATTN extends BasicActivity{
    //Ids of students to undo in register.
    ArrayList<String> undoIDs;
    //Id for background screen
    int layout;

    /**
     * Gets the ids of students who need to be undone from register.
     *
     * @param savedInstanceState Most recently supplied data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        undoIDs = intent.getStringArrayListExtra(AppCSTR.GREEN_IDS);
        layout = intent.getIntExtra(AppCSTR.LAYOUT, -1);

        startSearch();
    }

    /**
     * Load data to pass to database and pass it.
     */
    private void startSearch() {
        //2 for the first two initial entries
        int lengthP = undoIDs.size();
        String [] itemNames = new String[lengthP + 2];
        String [] itemValues = new String[lengthP + 2];

        //Store data that needs to be sent
        itemNames[AppCSTR.FIRST_ELEMENT] = "countP";
        itemValues[AppCSTR.FIRST_ELEMENT] = "" + lengthP;
        itemNames[AppCSTR.SECOND_ELEMENT] = "courseID";
        itemValues[AppCSTR.SECOND_ELEMENT] = super.getCourseID();

        int start = AppCSTR.THIRD_ELEMENT;
        int end = lengthP + 2;
        for(int i = start; i < end; i++){
            itemNames[i] = "pid" + (i-start);
            itemValues[i] = undoIDs.get((i-start));
        }

        super.sendData("", itemNames, itemValues, AppCSTR.URL_UNREGISTER_ATTENDANCE, this, layout, false);
    }

    /**
     * Warns user if register was undone or not.
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
                Toast.makeText(this, "Student(s) UnRegistered", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Not UnRegistered",Toast.LENGTH_SHORT).show();
            }
        }

        super.start(this, AttendancePRActivity.class, true);
    }
}
