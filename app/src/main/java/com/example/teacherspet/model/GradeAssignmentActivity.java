package com.example.teacherspet.model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teacherspet.R;

import java.util.HashMap;

/**
 * Adds an assignment to the database.
 *
 * @author Johnathon Malott, Kevin James
 * @version 3/24/2015
 */
public class GradeAssignmentActivity extends BasicActivity {
    //ID for layout screen
    int layout;
    //Max points user can receive on a assignment.
    int maxPoints;
    //Row that has all assignment information, extra data in that row
    String[] assgRow, extras, names;
    HashMap<String, String> map = new HashMap<>();
    Spinner assignments;
    String aid;
    int pos;

    /**
     * When screen is created set to assignment layout.
     *
     * @param savedInstanceState Most recently supplied data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = R.layout.activity_28_2_grade;
        setContentView(layout);

        getAssignments();
    }

    /**
     * Submits data to database for assignment.
     *
     * @param view View that was interacted with by user.
     */
    public void onClicked(View view){
        switch(view.getId()){
            case R.id.gSubmit:
                if(isValidInput()){
                    String name = ((AutoCompleteTextView) findViewById(R.id.student)).getText().toString();
                    Intent i = new Intent(this, TakeGradeActivity.class);
                    i.putExtra(AppCSTR.SID, map.get(name));
                    i.putExtra(AppCSTR.NAME, name);
                    i.putExtra(AppCSTR.CID, super.getCourseID());
                    i.putExtra(AppCSTR.POSITION, pos);
                    Log.e("POS", "" + pos);
                    i.putExtra(AppCSTR.LAYOUT, layout);
                    i.putExtra(AppCSTR.GRADE, ((EditText) findViewById(R.id.grade)).getText().toString());
                    startActivity(i);
                } else {
                   Toast.makeText(this, "Invalid Name or Grade", Toast.LENGTH_SHORT).show();
                }
              break;
        }
    }

    private void getAssignments(){
        //Name of JSON tag storing data
        String tag = AppCSTR.ASSIGNMENTS;
        String[] dataPassed = new String[]{"cid", super.getCourseID()};
        String[] dataNeeded= new String[]{"graden","gradet","dscript","aid","students"};

        sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_FIND_ASSIGN, this, layout, true);
    }

    /**
     * Place assignments in spinner to choose from.
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
                //Get assignment names from database
                assgRow = data.getStringArrayExtra(AppCSTR.DB_FIRST_ROW);
                String[] names = super.arrayParser(assgRow[AppCSTR.FIRST_ELEMENT]);
                extras = super.getExtraInfo(assgRow,names.length, 2);

                aid = assgRow[AppCSTR.FOURTH_ELEMENT];
                setAuto(assgRow[AppCSTR.FIFTH_ELEMENT]);

                //Add those names to the spinner
                assignments = (Spinner) findViewById(R.id.assignment);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                        names);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                assignments.setAdapter(dataAdapter);
                setListener();
            } else {
                //Exit screen professor has no students.
                Toast.makeText(this, "No Students Registered!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    /**
     * Displays information to screen about assignment that is being graded.
     */
    private void setListener(){
        assignments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * Called when item has been selected.
             *
             * @param parentView Where the selection happen.
             * @param view The view that was clicked
             * @param position Position of view in the adapter.
             * @param id Row id of item that was clicked.
             */
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view,
                                       int position, long id) {
                String[] details = extras[position].split("%");
                maxPoints = Integer.parseInt(details[AppCSTR.FIRST_ELEMENT]);
                pos = position + 1;
                String message = "Grade Total: " + details[AppCSTR.FIRST_ELEMENT] +
                        "\n\n Description: " + details[AppCSTR.SECOND_ELEMENT];
                ((TextView) findViewById(R.id.gradeInfo)).setText(message);
            }

            /**
             * Called when adapter becomes empty.
             *
             * @param parentView Where the selection happen.
             */
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    /**
     * Setups up the auto-complete feature on students name.
     *
     * @param studentInfo Data holding the students name.
     */
    private void setAuto(String studentInfo){
        String[] data = studentInfo.split("#");
        String[] sids = new String[data.length];
        names = new String[data.length];

        String[] info;
        for(int i = 0; i < data.length; i++){
            info = data[i].split("!");
            sids[i] = info[AppCSTR.FIRST_ELEMENT];
            names[i] = info[AppCSTR.SECOND_ELEMENT];
            map.put(info[AppCSTR.SECOND_ELEMENT], info[AppCSTR.FIRST_ELEMENT]);
        }

        ArrayAdapter<String> adapter  = new ArrayAdapter<> (this,
                android.R.layout.simple_dropdown_item_1line, names);
        ((AutoCompleteTextView) findViewById(R.id.student)).setAdapter(adapter);
    }


    /**
     * Checks that students name and grade are valid input.
     *
     * @return True if name and grade are valid input.
     */
    private boolean isValidInput(){
        boolean validName = false;
        boolean validGrade = false;
        String[] values = getValues();

        //check username
       String name =  ((AutoCompleteTextView) findViewById(R.id.student)).getText().toString();
        for(String student : names){
            if(student.equals(name)){validName = true;}
        }

        //check grade given
        int grade =  Integer.parseInt(((EditText) findViewById(R.id.grade)).getText().toString());
        if(grade <= maxPoints){validGrade = true;}

        return (validGrade && validName);
    }

    private String[] getValues(){
        return new String[]{};
    }
}
