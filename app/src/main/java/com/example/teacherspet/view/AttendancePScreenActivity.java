package com.example.teacherspet.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.teacherspet.R;
import com.example.teacherspet.control.AttendancePActivity;
import com.example.teacherspet.model.BasicActivity;

/**
 * Allows user to take attendance, unregister students, or restart attendance.
 *
 * @author Johnathon Malott, Kevin James
 * @version 4/23/2015
 */
public class AttendancePScreenActivity extends BasicActivity {

    /**
     * Set layout to attendance.
     *
     * @param savedInstanceState Most recently supplied data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_13p_attendance);
    }

    /**
     * Register: stores students current status and takes off list.
     * Submit: takes register then attendance for the day.
     *
     * @param view Button that was clicked.
     */
    public void onClicked(View view){
        //Action to change screens.
        Intent intent;
        //Screen that will be changed to.
        Class<?> toScreen;

        switch(view.getId()){
            case R.id.btn_take:
                toScreen = AttendancePActivity.class;
                break;
            case R.id.btn_undo_r:
                toScreen = AttendancePRActivity.class;
                break;
            case R.id.btn_undo_s:
                toScreen = AttendancePSActivity.class;
                finish();
                break;
            default :
                toScreen = AttendancePScreenActivity.class;
        }

        intent = new Intent(this, toScreen);
        startActivity(intent);
    }
}
