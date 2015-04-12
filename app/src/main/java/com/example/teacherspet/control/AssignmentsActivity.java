package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;
import com.example.teacherspet.view.ShowDetailActivity;


/**
 * Show user all assignments that haven't been graded.
 *
 * @author Johnathon Malott, Kevin James
 * @version 3/24/2015
 */
public class AssignmentsActivity extends BasicActivity implements AdapterView.OnItemClickListener{
    //Data collecting from web page
    String[] dataNeeded;
    //ID for screen Layout
    int layout;


    /**
     * When screen is created set to assignment layout.
     * If professor add assignment button to screen.
     *
     * @param savedInstanceState Most recently supplied data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = R.layout.activity_19_assignments;
        setContentView(layout);
        if(getType().equals(AppCSTR.PROFESSOR)){
            ((Button)findViewById(R.id.bnt_assignment)).setVisibility(View.VISIBLE);
        }
        startSearch();
    }



    /**
     * Finds non graded assignments from database.
     */
    private void startSearch() {
        //Name of JSON tag storing data
        String tag = "assignments";
        String[] dataPassed = new String[]{"cid", super.getCourseID()};
        dataNeeded = new String[]{"name", "dd", "da", "gradet", "dscript"};

        sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_FIND_ASSIGN, this, layout,true);
    }

    /**
     * Sets the name of the assignment to be displayed in list view.
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
                ListView assignments = (ListView) findViewById(R.id.assignments);
                int layout = R.layout.list_item;
                int[] ids = new int[]{R.id.listItem};

                assignments.setAdapter(super.makeAdapterArray(data, this, layout, ids));
                assignments.setOnItemClickListener(this);
                if (super.checkEmptyList()) {
                    Toast.makeText(this, "No Assignments Not graded!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    /**
     * Send all data to show on a different screen.
     *
     * @param parent Where clicked happen.
     * @param view View that was clicked
     * @param position Position of view in list.
     * @param id Row id of item clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String details = "Due Date: %Date Assigned: %Grade Total: %Description: ";
        Intent i = new Intent(this, ShowDetailActivity.class);
        i.putExtra(AppCSTR.SHOW_NAME, super.getNameorExtra(position, AppCSTR.SHOW_NAME));
        i.putExtra(AppCSTR.SHOW_EXTRA, super.getNameorExtra(position, AppCSTR.SHOW_EXTRA));
        i.putExtra(AppCSTR.SHOW_DETAIL, details);
        startActivity(i);
    }

    /**
     * Go to add course screen.
     *
     * @param view View that was interacted with by user.
     */
    public void onClicked(View view){
        super.start(this, AddAssignmentActivity.class,false);
    }
}
