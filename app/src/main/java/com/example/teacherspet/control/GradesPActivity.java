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
import com.example.teacherspet.view.ShowGradesActivity;

/**
 * Finds assignments that have been graded.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 3/24/2015
 */
public class GradesPActivity extends BasicActivity implements AdapterView.OnItemClickListener{
    //Web page to connect to
    String[] dataNeeded;
    //ID for screen layout
    int layout;

    /**
	 * When screen is created set to Grades layout.
	 * Add students' grades as ListView to screen.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        layout = R.layout.activity_list;
	    setContentView(layout);

        startSearch();
	}

    /**
     * Gets all the assignments that have been graded.
     */
    private void startSearch(){
        //Name of JSON tag storing data
        String tag = "names";
        String[] dataPassed = new String[]{"cid", super.getCourseID()};
        dataNeeded = new String[]{"assignment", "position"};

        sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_FIND_GRADED, this, layout, true);
    }

    /**
     * Set graded assignments to the screen.
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

                ListView show = (ListView) findViewById(R.id.list);
                int layout = R.layout.list_item;
                int[] ids = new int[] {R.id.listItem};
                show.setAdapter(super.makeAdapterArray(data, this, layout, ids));
                show.setOnItemClickListener(this);

                if(super.checkEmptyList()) {
                    Toast.makeText(this, "No Assignments Graded", Toast.LENGTH_LONG).show();
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
        Intent i = new Intent(this, ShowGradesActivity.class);
        i.putExtra(AppCSTR.SHOW_NAME, super.getNameorExtra(position, AppCSTR.SHOW_NAME));
        i.putExtra(AppCSTR.SHOW_EXTRA, super.getNameorExtra(position, AppCSTR.SHOW_EXTRA));
        startActivity(i);
    }
}
