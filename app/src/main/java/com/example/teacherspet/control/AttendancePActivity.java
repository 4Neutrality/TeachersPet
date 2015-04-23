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
 * List all users that are registered for current class.
 * 
 * @author Johnathon Malott, Kevin James
 * @version 3/24/2014
 */
public class AttendancePActivity extends BasicActivity implements AdapterView.OnItemClickListener{
	//Data collecting from web page
	String[] dataNeeded;
    //ID for layout on screen
    int layout;

	/**
	 * Set layout to attendance and look for students.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        layout = R.layout.activity_13p_attendance;
		setContentView(layout);
		startSearch();
	}
	
	/**
	 * Send data to database to get all users name, id, today's status.
	 */
	private void startSearch(){
		//Name of JSON tag storing data
		String tag = "students";
		String[] dataPassed = new String[]{"courseID", super.getCourseID(),"pid", super.getID()};
		dataNeeded = new String[]{"studentName","studentID","status"};

        sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_FIND_STUDENTS, this, layout ,true);
	}
	
	/**
	 * List all users in the class to the screen.
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
     * When student is selected change background color and add id to the list.
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
     * Submit: takes register then attendance for the day
     *
     * @param view Button that was clicked.
     */
    public void onClicked(View view){
        //Length of students that have been checked
        int registerSize = super.getViewID(AppCSTR.GREEN_IDS).size() + super.getViewID(AppCSTR.RED_IDS).size();
        final boolean studentsSelected = registerSize > AppCSTR.SIZE_ZERO;
        int viewID = view.getId();

        if(viewID == R.id.bnt_register){
            if(studentsSelected)
               takeRegister(true);
            else{
                Toast.makeText(this, "No Students Selected!", Toast.LENGTH_SHORT).show();
            }
        } else if(viewID == R.id.bnt_submit){
            //Allow register to be taken before submit attendance
            Thread timer = new Thread() {
                public void run(){
                    try {
                        takeRegister(false);
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
            timer.start();
            super.start(this, SubmitATTN.class, true);
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
        i.putExtra(AppCSTR.LAYOUT, layout);
        startActivity(i);
        if(finish){
            finish();
        }
    }
}
