package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.teacherspet.R;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;
import com.example.teacherspet.view.AttendancePScreenActivity;
import com.example.teacherspet.view.InformationActivity;
import com.example.teacherspet.view.LabActivity;

/**
 * Allows user to access attendance, assignments, student roster, grades, lab, and additional data.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 3/21/2015
 */
public class CourseActivity extends BasicActivity {
	/**
	 * When screen is created set to course layout.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_11_course);
	}

	/**
	 * Find which view is being pressed and change to that screen. 
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
		//Action to change screens.
		Intent intent;
		//Screen that will be changed to.
		Class<?> toScreen;
        //
        Boolean typeP = super.getType().equals(AppCSTR.PROFESSOR);
		
		switch(view.getId()){
		    case R.id.btn_attendance:
                if(typeP)
		    	    toScreen = AttendancePScreenActivity.class;
                else{
                    toScreen = AttendanceSActivity.class;
                }
		    	break;
		    case R.id.btn_assignments:
		    	toScreen = AssignmentsActivity.class;
		    	break;
		    case R.id.btn_students:
		    	toScreen = StudentsActivity.class;
		    	break;
		    case R.id.btn_grades:
		    	//Screen is different depending if user is student or professor
		    	if(typeP) {
                    toScreen = GradesPActivity.class;
                }
		    	else
		    		toScreen = GradesSActivity.class;
		    	break;
		    case R.id.btn_lab:
		    	//Screen is different depending if user is student or professor
		        toScreen = LabActivity.class;
		    	break;
		    case R.id.btn_info:
		    	toScreen = InformationActivity.class;
		    	break;
		    default:
		   		toScreen = HomeActivity.class;
		}
		
		 intent = new Intent(this, toScreen);
		 startActivity(intent);
	}
}
