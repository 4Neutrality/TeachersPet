package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;

/**
 * Adds an assignment to the database.
 *
 * @author Johnathon Malott, Kevin James
 * @version 3/24/2015
 */
public class AddAssignmentActivity extends BasicActivity {

    /**
     * When screen is created set to assignment layout.
     *
     * @param savedInstanceState Most recently supplied data.
     * @Override
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_19_2_add_assignment);
    }

    /**
     * Submits data to database for assignment.
     *
     * @param view View that was interacted with by user.
     */
    public void onClicked(View view){
        //Action to hold screen change.
        if(view.getId() == R.id.btn_submit){
            sendItems();
        }
    }

    /**
     * Sends new assignment to database.
     */
    private void sendItems() {
        //Name of JSON tag storing data
        String[] itemNames = new String[]{"cid","name","dd","da","total","descript","pid"};
        String[] itemValues = getValues();

        sendData("", itemNames, itemValues, AppCSTR.URL_ADD_ASSIGNMENT, this, false);
    }

    /**
     * Notify user if assignment was added.
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
            int success = data.getIntExtra("success", -1);
            if (success == 0) {
                Toast.makeText(this, "Assignment Added", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Assignment Error", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * Get all data the user has entered.
     *
     * @return Data user entered.
     */
    private String[] getValues(){
        String cid = super.getCourseID();
        String name = ((EditText) findViewById(R.id.fld_aName)).getText().toString();
        String dateDue = ((EditText) findViewById(R.id.fld_dd)).getText().toString();
        String dateAssign = ((EditText) findViewById(R.id.fld_da)).getText().toString();
        String total = ((EditText) findViewById(R.id.fld_aTotal)).getText().toString();
        String descript = ((EditText) findViewById(R.id.fld_aDescript)).getText().toString();
        String pid = super.getID();

        return new String[]{cid,name,dateDue,dateAssign,total,descript,pid};
    }
}
