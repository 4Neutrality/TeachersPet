package com.example.teacherspet.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.teacherspet.R;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;


/**
 * Displays all data given to the screen.
 *
 * @author Johnathon Malott, Kevin James
 * @version 2/24/2015
 */
public class ShowGradesActivity extends BasicActivity {
    //Intent that passed data
    Intent i;
    String[] dataNeeded;
    int layout;

    /**
     * When screen is created set to show layout.
     * Get intent that passed data.
     *
     * @param savedInstanceState Most recently supplied data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout= R.layout.activity_show_detail;
        setContentView(layout);

        i = getIntent();
        startSearch();
    }

    /**
     * Gets all grades that are for that assignment.
     */
    private void startSearch(){
        //Name of JSON tag storing data
        String tag = "grades";
        String[] dataPassed = new String[]{"cid", super.getCourseID(), "pos", i.getStringExtra(AppCSTR.SHOW_EXTRA)};
        dataNeeded = new String[]{"grade","name"};

        sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_FIND_STUDENT_GRADES, this, layout, true);
    }

    /**
     * Sets all grades for that assignment to the screen.
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
                String descript = "";
                int count = Integer.parseInt(data.getStringExtra(AppCSTR.COUNT));
                for(int i = 0; i < count; i++){
                    String[] item = data.getStringArrayExtra(AppCSTR.DB_ROW + i);
                    descript += "\n\n" + item[1] + ": " + item[0];
                }
                ((TextView) findViewById(R.id.title)).setText(i.getStringExtra(AppCSTR.SHOW_NAME));
                ((TextView) findViewById(R.id.descript)).setText(descript);
            }
        }
    }
}
