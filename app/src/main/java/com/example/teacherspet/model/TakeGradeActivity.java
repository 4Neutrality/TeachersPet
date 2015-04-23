package com.example.teacherspet.model;

import android.content.Intent;
import android.os.Bundle;

/**
 * Record students grade in database.
 *
 * @author Johnathon Malott, Kevin James
 * @version 4/22/2015
 */
public class TakeGradeActivity extends BasicActivity {
    //Intent that called this activity
    Intent i;

    /**
     * When screen is created set to assignment layout.
     *
     * @param savedInstanceState Most recently supplied data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();

        sendItems();
    }

    /**
     * Sends new assignment to database.
     */
    private void sendItems() {
        String[] itemNames = new String[]{"sid", "name", "cid", "grade"};
        String[] itemValues = getValues();

        sendData("", itemNames, itemValues, AppCSTR.URL_GRADE_ASSIGNMENT, this, i.getIntExtra(AppCSTR.LAYOUT,-1), false);
    }

    private String[] getValues(){
        return new String[]{i.getStringExtra(AppCSTR.SID), i.getStringExtra(AppCSTR.NAME),
                            i.getStringExtra(AppCSTR.CID), i.getStringExtra(AppCSTR.GRADE)};
    }
}
