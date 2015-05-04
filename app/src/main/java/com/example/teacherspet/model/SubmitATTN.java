package com.example.teacherspet.model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.teacherspet.R;

/**
 * Takes attendance for the day.
 *
 * @author Johnathon Malott, Kevin James
 * @version 3/24/2015
 */
public class SubmitATTN extends BasicActivity{

    /**
     * Submit the attendance of all users in the course.
     *
     * @param savedInstanceState Most recently supplied data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = R.layout.activity_13p_2_attendance;
        String[] itemNames = new String[]{"cid"};
        String[] itemValues = new String[]{super.getCourseID()};

        super.sendData("", itemNames, itemValues, AppCSTR.URL_SUBMIT_ATTENDANCE, this, layout, false);
    }

    /**
     * Warns user how attendance taking turned out.
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
                Toast.makeText(this,"Attendance Taken",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Attendance Not taken",Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }
}
