package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.teacherspet.R;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Show attendance for a single student.
 * 
 * @author Johnathon Malott, Kevin James
 * @version 3/21/2015
 */
public class AttendanceSActivity extends BasicActivity {
	String[] dataNeeded;
    //Id for the screen background
    int layout;

	/**
	 * When screen is created set to attendance layout.
	 * Then search for student attendance information.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        layout = R.layout.activity_show_detail;
		setContentView(layout);
		startSearch();
	}
	
	/**
	 * Send student info to get attendance data.
	 */
	private void startSearch(){
		//Name of JSON tag storing data
		String tag = "student";
		String[] dataPassed = new String[]{"cid", super.getCourseID(), "sid", super.getID()};
        //Log.e("CourseID: ", super.getCourseID());
        //Log.e("ID: ", super.getID());
		dataNeeded = new String[]{"present","late","t_status","total_days","pname","course","date","status"};

        super.sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_FIND_STUDENT, this, layout,true);
	}
	
	/**
	 * Set content of page to information that was returned.
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
            int success = data.getIntExtra(AppCSTR.SUCCESS, -1);
            if(success == 0){
                String[] item = data.getStringArrayExtra(AppCSTR.DB_FIRST_ROW);
                getATTNPage(item);
            }
	    }
	}

    /**
     * Takes information from database and displays it onto the screen.
     *
     * @param item Information from database.
     */
    private void getATTNPage(String[] item){
        //Get today's date in the right format
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        //Take off brackets of string array from database
        String today = item[AppCSTR.DATE].replaceAll("\\{|\\}","");
        String storeStatus = item[AppCSTR.STATUS].replaceAll("\\{|\\}","");
        //If more than one day is record, then get all days and there status
        String[] dates = today.split(",");
        String[] allStatus = storeStatus.split(",");

        //Turn database Strings into Integers
        int total = Integer.parseInt(item[AppCSTR.TOTAL_DAYS]);
        int present = Integer.parseInt(item[AppCSTR.PRESENT]);
        int late = Integer.parseInt(item[AppCSTR.LATE]);

        //Check if attendance is already taken and get status for the day
        String taken, status;
        if(total != 0 && dates[(total - 1)].equals(date.format(new Date()))){
            taken = "Taken";
            status = allStatus[(total - 1)];
        }else{
            status = item[AppCSTR.TODAY_STATUS];
            taken = "Not Taken";
        }

        //Get user's status for the day
        status = getStatus(status);

        //Make message to display on screen
        String message = "Professor: \t" + item[AppCSTR.PROFESSOR_NAME] +
                "\n\nCurrent Status: 	" + status +
                "\n\nAttandance Taken: \t" + taken +
                "\n\nDays Present: \t" + present +
                "\n\nDays Late: \t" + late +
                "\n\nDays Absent: \t" + (total - (present + late)) +
                "\n\nDays Total: \t" + total;

        //display on screen
        ((TextView) findViewById(R.id.title)).setText(item[AppCSTR.COURSE]);
        ((TextView) findViewById(R.id.descript)).setText(message);
    }

    /**
     * Find out the user's status for the day.
     *
     * @param status Indicates is user present, late, or absent
     * @return
     */
    private String getStatus(String status){
        switch(status){
            case "p":
                status = "Present";
                break;
            case "l":
                status = "Late";
                break;
            case "t":
                status = "Absent";
                break;
            default:
                status = "Not Taken";
        }

        return status;
    }
}
