package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;
import com.example.teacherspet.model.StudentAlertActivity;

/**
 * Allows student user to search for a course, join course, and send alert to professor to join it.
 * 
 * @author Johnathon Malott, Kevin James
 * @version 3/21/2015
 */
public class AddCourseSActivity extends BasicActivity implements AdapterView.OnItemClickListener{
    //data returned from database
    String [] dataNeeded;
    //Id of background screen
    int layout;

	/**
	 * When screen is create set to student add course layout.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 */
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        layout = R.layout.activity_10s_add_course;
		setContentView(layout);
	}

    /**
     * Finds the class that the user entered and submit enrollment if user submits a course
     * that is found.
     *
     * @param view View that was interacted with by user.
     */
    public void onClicked(View view){
        switch(view.getId()){
            case R.id.btn_find:
                if( ((EditText)findViewById(R.id.cID)).getText().toString().equals(""))
                   Toast.makeText(this, "Enter ID #", Toast.LENGTH_LONG).show();
                else
                   startSearch();
                break;
            case R.id.btn_submit:
                if(super.getViewID(AppCSTR.GREEN_IDS).size() != 0) {
                    Intent i = new Intent(this, StudentAlertActivity.class);
                    i.putStringArrayListExtra(AppCSTR.ALL_IDS, super.getViewID(AppCSTR.GREEN_IDS));
                    i.putExtra(AppCSTR.LAYOUT, layout);
                    startActivity(i);
                    finish();
                }
                else
                    Toast.makeText(this, "No course selected!", Toast.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * Searches database for class user entered.
     */
    private void startSearch(){
        //Get pid
        String cid = ((EditText) findViewById(R.id.cID)).getText().toString();
        //Name of JSON tag storing data
        String tag = "course";
        String [] dataPassed = new String[]{"cid",cid};
        dataNeeded = new String[]{"cid","pname","course","term","time","section"};

        sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_FIND_COURSES, this, layout,true);
    }
	
	/**
	 * Adds any classes to the list view if found.
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
                // updating list view
                ListView courseView = (ListView) findViewById(R.id.courseView);
                //Get and pass data to make list adapter
                int layout = R.layout.list_course;
                int[] ids = new int[] {R.id.id, R.id.pname, R.id.course,
                           R.id.term, R.id.time, R.id.section};
                courseView.setAdapter(super.makeAdapter(data, dataNeeded, this, layout ,ids));
                courseView.setOnItemClickListener(this);
            }else{
                Toast.makeText(this, "Course not found", Toast.LENGTH_SHORT).show();
                ((EditText) findViewById(R.id.cID)).setText("");
            }
	     }
	}

    /**
     * Finds view that user has selected and adds/removes that view from a listed of views already
     * selected.
     *
     * @param parent Where clicked happen.
     * @param view View that was clicked
     * @param position Position of view in list.
     * @param id Row id of item clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        super.changeColor(view, position, "cid", true);
    }
}
