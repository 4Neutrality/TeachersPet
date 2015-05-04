package com.example.teacherspet.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;
import com.example.teacherspet.model.UnRegisterATTN;

/**
 * Allows user unregister students from attendance.
 *
 * @author Johnathon Malott, Kevin James
 * @version 4/23/2015
 */
public class AttendancePRActivity extends BasicActivity implements AdapterView.OnItemClickListener{
    //Data collecting from web page
    String[] dataNeeded;

    /**
     * Set layout to undo register screen.
     *
     * @param savedInstanceState Most recently supplied data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = R.layout.activity_13p_4_attendance;
        setContentView(layout);
        startSearch(layout);
    }

    /**
     * Send data to database to get all users names, ids, their attendance status.
     */
    private void startSearch(int layout){
        //Name of JSON tag storing data
        String tag = "students";
        String[] dataPassed = new String[]{"courseID", super.getCourseID(),"pid", super.getID(),
                "status", "IS NOT NULL"};
        dataNeeded = new String[]{"studentName","studentID","status"};

        sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_FIND_STUDENTS, this, layout ,true);
    }

    /**
     * List all students in the class to the screen who have benn registered.
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
                ListView attendance = (ListView) findViewById(R.id.attnView);
                int layout = R.layout.list_item;
                int[] ids = new int[] {R.id.listItem};
                attendance.setAdapter(super.makeAdapter(data, dataNeeded, this, layout, ids));
                attendance.setOnItemClickListener(this);
            } else {
                //Exit screen professor has no students.
                Toast.makeText(this, "No Students Registered!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    /**
     * When student is selected change background color and add id to undo list.
     *
     * @param parent Where clicked happen.
     * @param view View that was clicked
     * @param position Position of view in list.
     * @param id Row id of item clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        super.changeColor(view, position, "studentID", true);
    }

    /**
     * Register: stores students current status and takes off list.
     * Submit: takes register then attendance for the day.
     *
     * @param view Button that was clicked.
     */
    public void onClicked(View view){
        //Length of students that have been checked
        int registerSize = super.getViewID(AppCSTR.GREEN_IDS).size();
        int viewID = view.getId();

        if(viewID == R.id.bnt_undo){
            if(registerSize > AppCSTR.SIZE_ZERO)
                undoRegister();
            else{
                Toast.makeText(this, "No Students Selected!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Take register if any IDs where given.
     */
    private void undoRegister(){
        Intent i = new Intent(this, UnRegisterATTN.class);
        i.putExtra(AppCSTR.GREEN_IDS, super.getViewID(AppCSTR.GREEN_IDS));
        i.putExtra(AppCSTR.LAYOUT, R.layout.activity_13p_4_attendance);
        startActivity(i);
        finish();
    }
}
