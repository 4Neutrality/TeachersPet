package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.Alert;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;

/**
 * Warns user of a update to the database if it applies to them.
 * 
 * @author Johnathon Malott, Kevin James
 * @version 3/21/2015
 */
public class AlertsActivity extends BasicActivity implements AdapterView.OnItemClickListener {
    //Data needed from database
    String[] dataNeeded;
    //ID of layout
    int layout;

    /**
     * When screen is created set to alert layout, find alert info.
     * If no alerts found warns user and returns to screen that called this one.
     *
     * @param savedInstanceState Most recently supplied data.
     * @Override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = R.layout.activity_list;
        setContentView(layout);

        startSearch();
    }

    /**
     * Find alert from database.
     */
    private void startSearch() {
        //Name of JSON tag storing data
        String tag = "alerts";
        String[] dataPassed = new String[]{"id", super.getID()};
        dataNeeded = new String[]{AppCSTR.ALERT_NAME, AppCSTR.AID, AppCSTR.ALERT_SID,
                     AppCSTR.CID, AppCSTR.ALERT_ACTION, AppCSTR.ALERT_DESCRIPTION};

        super.sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_FIND_ALERTS, this, layout,true);
    }

    /**
     * Creates a list of all alerts that the user has in the database.
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
                ListView courseView = (ListView) findViewById(R.id.list);
                //Get and pass data to make list adapter
                int layout = R.layout.list_item;
                int[] ids = new int[] {R.id.listItem};
                courseView.setAdapter(super.makeAdapter(data, dataNeeded, this, layout ,ids));
                courseView.setOnItemClickListener(this);
            } else {
                //Do nothing, user will see no alerts in his box.
                Toast.makeText(this,"No Alerts!!",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    /**
     * When view selected go to new screen to list all detail information about that alert.
     *
     * @param parent Where clicked happen.
     * @param view View that was clicked
     * @param position Position of view in list.
     * @param id Row id of item clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent i = new Intent(this, Alert.class);
        i.putExtra(AppCSTR.AID, super.getNameorExtra(position, AppCSTR.AID));
        i.putExtra(AppCSTR.ALERT_SID, super.getNameorExtra(position, AppCSTR.ALERT_SID));
        i.putExtra(AppCSTR.CID, super.getNameorExtra(position, AppCSTR.CID));
        i.putExtra(AppCSTR.ALERT_ACTION, super.getNameorExtra(position, AppCSTR.ALERT_ACTION));
        i.putExtra(AppCSTR.ALERT_NAME, super.getNameorExtra(position, AppCSTR.ALERT_NAME));
        i.putExtra(AppCSTR.ALERT_DESCRIPTION, super.getNameorExtra(position, AppCSTR.ALERT_DESCRIPTION));
        startActivity(i);
        finish();
    }
}
