package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Finds courses that user is enrolled in or allows user to go to add course screen.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 3/21/2015
 */
public class CoursesActivity extends BasicActivity {
	ArrayList<HashMap<String, String>> courseList;
    //ID for screen layout
    int layout;
    
	/**
	 * Sets screen to courses layout, finds user ID, and start searching for
     * user enrolled courses..
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        layout = R.layout.activity_6_courses;
		setContentView(layout);
		courseList = new ArrayList<>();
		
		startSearch();
	}

    /**
     * Send data to database that it needs to retrieve information.
     */
    private void startSearch(){
        //Name of JSON tag storing data
        String tag = "courseNumber";
        String [] dataPassed = new String[]{"ID", super.getID()};
        String [] dataNeeded = new String[]{"course","courseID"};

        super.sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_LIST_COURSES, this, layout, true);
    }
	
	/**
	 * Takes courses that user is enrolled in and displays them on the screen.
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
            //0 means successful
	    	if(success == 0){
                //Find all buttons that are hidden on screen
	    		Button [] buttons = {(Button) findViewById(R.id.btn_course0), (Button) findViewById(R.id.btn_course1),
                        (Button) findViewById(R.id.btn_course2), (Button) findViewById(R.id.btn_course3)};
                //number of courses found
	    		int count = Integer.parseInt(data.getStringExtra(AppCSTR.DB_ROW_COUNT));
	    		
	    		for(int i = 0; i < count; i++){
	    	        String[] item = data.getStringArrayExtra(AppCSTR.DB_ROW + i);
	    	        //Button cButton = (Button) findViewById(R.id.btn_course0);
	    	        buttons[i].setText(item[AppCSTR.FIRST_ELEMENT]);
                    buttons[i].setTag(item[AppCSTR.SECOND_ELEMENT]);
	    	        buttons[i].setVisibility(View.VISIBLE);

                    //if class selected take to next screen
	    	        buttons[i].setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
                            //From basic activity
                            makePref();
                            setCourseID((String) v.getTag());
							start(CoursesActivity.this, CourseActivity.class, false);
						}
	    	        });
	    		}
	    	}else{
                //User is enrolled in no classes at this time
                Toast.makeText(this, "Need to Add Course", Toast.LENGTH_SHORT).show();
            }
	     }
	}

    /**
     * If add course button is hit find out account type and go
     * to add course screen for that type.
     *
     * @param view View that was interacted with by user.
     */
    public void onClicked(View view){
        //Default screen to change to.
        Class<?> toScreen = HomeActivity.class;
        if(R.id.btn_addCourse == view.getId()){
            //Type of account it is
            String type = super.getType();
            if(type.equals(AppCSTR.STUDENT))
                toScreen = AddCourseSActivity.class;
            else if(type.equals(AppCSTR.PROFESSOR)){
                toScreen = AddCoursePActivity.class;
            }
        }
        super.start(this, toScreen, false);
    }
}
