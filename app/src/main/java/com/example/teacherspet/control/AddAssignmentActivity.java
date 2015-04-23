package com.example.teacherspet.control;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;

import java.util.Calendar;

/**
 * Adds an assignment to the database.
 *
 * @author Johnathon Malott, Kevin James
 * @version 3/24/2015
 */
public class AddAssignmentActivity extends BasicActivity implements DatePickerDialog.OnDateSetListener{
    //ID for background screen
    int layout;
    //Tells that the due date picker is visible.
    boolean ddPicker = true;

    /**
     * When screen is created set to assignment layout.
     *
     * @param savedInstanceState Most recently supplied data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = R.layout.activity_19_add_assignment;
        setContentView(layout);
    }

    /**
     * Submits data to database for assignment.
     *
     * @param view View that was interacted with by user.
     */
    public void onClicked(View view){
        //Action to hold screen change.
        switch(view.getId()){
            case R.id.btn_submit:
                if(isValidInput()) {
                    sendItems();
                }
                break;
            case R.id.btn_da:
               ddPicker = false;
            case R.id.btn_dd:
                datePicker();
                break;
        }
    }

    /**
     * Sends new assignment to database.
     */
    private void sendItems() {
        String[] itemNames = new String[]{"cid","name","dd","da","total","descript","pid"};
        String[] itemValues = getValues();

        sendData("", itemNames, itemValues, AppCSTR.URL_ADD_ASSIGNMENT, this, layout, false);
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
        String dateDue = ((TextView) findViewById(R.id.fld_dd)).getText().toString();
        String dateAssign = ((TextView) findViewById(R.id.fld_da)).getText().toString();
        String total = ((EditText) findViewById(R.id.fld_aTotal)).getText().toString();
        String descript = ((EditText) findViewById(R.id.fld_aDescript)).getText().toString();
        String pid = super.getID();

        return new String[]{cid,name,dateDue,dateAssign,total,descript,pid};
    }

    /**
     *
     */
    protected void datePicker(){
        Calendar today = Calendar.getInstance();
        int day = today.get(Calendar.DAY_OF_MONTH);
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);

        DatePickerDialog dpd = new DatePickerDialog(this,this,year,month,day);
        dpd.show();
    }

    /**
     * Once date is enter, takes that date and displays it on screen.
     *
     * @param view Date picker dialog showing on screen.
     * @param year Year assignment takes place.
     * @param monthOfYear Month assignment takes place.
     * @param dayOfMonth Day assignment takes place
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
         if(ddPicker)
           ((TextView) findViewById(R.id.fld_dd)).setText(year + "-" + monthOfYear + "-" + dayOfMonth);
        else
           ((TextView) findViewById(R.id.fld_da)).setText(year + "-" + monthOfYear + "-" + dayOfMonth);
    }

    /**
     * Checks that input of the user on screen is valid for database.
     *
     * @return True if all input is valid.
     */
    private boolean isValidInput(){
        String[] values = getValues();
        boolean valid = true;
        String message = "";

        for(String value: values){
            if(value.equals("")){ valid = false; Toast.makeText(this, "Input is blank!", Toast.LENGTH_SHORT).show();}
        }

        return valid;
    }
}
