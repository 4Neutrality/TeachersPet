package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;

/**
 * Creates a comment for a lab in the database.
 *
 * @author Johnathon Malott, Kevin James
 * @version 3/25/2015
 */
public class AddCommentActivity extends BasicActivity {

    /**
     * Send data needed to the database.
     *
     * @param savedInstanceState Most recently supplied data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       sendItems();
    }

    /**
     * Updates database with comment information.
     */
    private void sendItems() {
        //Name of JSON tag storing data
        String[] itemNames = new String[]{"cid","rid","lid","name","descript","pid"};
        String[] itemValues = getValues();

        sendData("", itemNames, itemValues, AppCSTR.URL_ADD_COMMENT, this, false);
    }

    /**
     * Notify user if the comment was added.
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
                Toast.makeText(this, "Comment Added", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Comment Error", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    /**
     * Get the values that were passed by the intent.
     *
     * @return All values needed to pass to database.
     */
    private String[] getValues(){
        Intent i = getIntent();

        String cid = super.getCourseID();
        String rid = i.getStringExtra(AppCSTR.SHOW_RECEIVE_ID);
        String lid = i.getStringExtra(AppCSTR.SHOW_LAB_ID);
        String name = i.getStringExtra(AppCSTR.SHOW_NAME);
        String descript = i.getStringExtra(AppCSTR.SHOW_DESCRIPTION);
        String pid = super.getID();

        return new String[]{cid,rid,lid,name,descript,pid};
    }
}
