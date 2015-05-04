package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;
import com.example.teacherspet.model.RegisterATTN;
import com.example.teacherspet.model.SubmitATTN;

/**
 * List all students that are signed up for the current class so that their attendance can be taken.
 * 
 * @author Johnathon Malott, Kevin James
 * @version 3/24/2014
 */
public class AttendancePActivity extends BasicActivity implements AdapterView.OnItemClickListener{
	//Data collecting from web page
	String[] dataNeeded;

	/**
	 * Set layout to take attendance and look for students.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        int layout = R.layout.activity_13p_2_attendance;
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
                                          "status", "IS NULL"};
		dataNeeded = new String[]{"studentName","studentID","status"};

        sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_FIND_STUDENTS, this, layout ,true);
	}
	
	/**
	 * List all students in the class to the screen.
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
                Toast.makeText(this, "No Students Signed UP!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
	}

    /**
     * When student is selected change background color and add id to present or late list.
     *
     * @param parent Where clicked happen.
     * @param view View that was clicked
     * @param position Position of view in list.
     * @param id Row id of item clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        super.changeColor(view, position, "studentID", false);
    }

    /**
     * Register: stores students current status and takes off list.
     * Submit: takes register then attendance for the day.
     *
     * @param view Button that was clicked.
     */
    public void onClicked(View view){
        //Length of students that have been checked
        int registerSize = super.getViewID(AppCSTR.GREEN_IDS).size() + super.getViewID(AppCSTR.RED_IDS).size();
        int viewID = view.getId();

        if(viewID == R.id.bnt_register){
            if(registerSize > AppCSTR.SIZE_ZERO)
               takeRegister(true);
            else{
                Toast.makeText(this, "No Students Selected!", Toast.LENGTH_SHORT).show();
            }
        } else if(viewID == R.id.bnt_submit){
            takeRegister(false);
            new android.os.Handler().postDelayed(new Runnable() {
                /** Starts the main screen after 5 seconds.*/
                @Override
                public void run() {
                    Intent menu = new Intent(AttendancePActivity.this, SubmitATTN.class);
                    startActivity(menu);
                    // Close activity
                    finish();
                }
                // Delay for 5 seconds
            }, AppCSTR.PAUSE2);
            finish();
        }
    }

    /**
     * Take register if any IDs where given.
     *
     * @param finish End activity when this is over.
     */
    private void takeRegister(Boolean finish){
        Intent i = new Intent(this, RegisterATTN.class);
        i.putExtra(AppCSTR.GREEN_IDS, super.getViewID(AppCSTR.GREEN_IDS));
        i.putExtra(AppCSTR.RED_IDS, super.getViewID(AppCSTR.RED_IDS));
        i.putExtra(AppCSTR.LAYOUT, R.layout.activity_13p_2_attendance);
        startActivity(i);
        if(finish){
            finish();
        }
    }
}
