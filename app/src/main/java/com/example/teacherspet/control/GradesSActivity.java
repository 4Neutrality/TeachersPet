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
import com.example.teacherspet.view.ShowDetailActivity;

/**
 * Gets all the assignments that have been graded for this student.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 3/24/2015
 */
public class GradesSActivity extends BasicActivity implements AdapterView.OnItemClickListener{
    String[] dataNeeded;
    //ID for screen layout
    int layout;

    /**
	 * When screen is created set to Grades layout.
	 * Add students' grades as ListView to screen.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        layout = R.layout.activity_list;
	    setContentView(layout);

        startSearch();
	}

    /**
     * Get all assignments that have been graded for this user.
     */
    private void startSearch(){
        //Name of JSON tag storing data
        String tag = "grades";
        String[] dataPassed = new String[]{"sid", super.getID(),"cid", super.getCourseID()};
        dataNeeded = new String[]{"names","dd","da","gradet","grader","dscript"};

        sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_FIND_GRADES, this, layout, true);
    }

    /**
     * List all grades to the screen.
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
                ListView assignments = (ListView) findViewById(R.id.list);
                int layout = R.layout.list_item;
                int[] ids = new int[] {R.id.listItem};
                assignments.setAdapter(super.makeAdapterArray(data, this, layout, ids));
                assignments.setOnItemClickListener(this);

                if(super.checkEmptyList()) {
                    Toast.makeText(this, "No Grades", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

    /**
     * Sends all data to show on a different screen.
     *
     * @param parent Where clicked happen.
     * @param view View that was clicked
     * @param position Position of view in list.
     * @param id Row id of item clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String details = "Due Date: %Date Assigned: %Grade Total: %Grade Received: %Description: ";
        Intent i = new Intent(this, ShowDetailActivity.class);
        i.putExtra(AppCSTR.SHOW_NAME, super.getNameorExtra(position, AppCSTR.SHOW_NAME));
        i.putExtra(AppCSTR.SHOW_EXTRA, super.getNameorExtra(position, AppCSTR.SHOW_EXTRA));
        i.putExtra(AppCSTR.SHOW_DETAIL, details);
        startActivity(i);
    }
}
