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
 * Displays all users in the class to the screen.
 *
 * @author Johnathon Malott, Kevin James
 * @version 3/24/2014
 */
public class StudentsActivity extends BasicActivity implements AdapterView.OnItemClickListener{
    //Data collecting from web page
    String[] dataNeeded;
    //ID for screen layout
    int layout;

	/**
	 * Set layout to students activity then start search for student
     * information.
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
     * Get all  users from database.
     *
     */
    private void startSearch(){
        String tag = "users";
        String[] dataPassed = new String[]{"cid", super.getCourseID()};
        dataNeeded = new String[]{"name","email","type"};

        sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_FIND_ALL_USERS, this, layout, true);
    }

    /**
     * List all users in the course to the screen.
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
                ListView showUsers = (ListView) findViewById(R.id.list);

                int layout = R.layout.list_item;
                int[] ids = new int[] {R.id.listItem};
                showUsers.setAdapter(super.makeAdapter(data, dataNeeded, this, layout, ids));
                showUsers.setOnItemClickListener(this);
            } else {
                //Do nothing, user will see no alerts in his box.
                Toast.makeText(this, "No users!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * When user is selected display that user information on different screen.
     *
     * @param parent Where clicked happen.
     * @param view View that was clicked
     * @param position Position of view in list.
     * @param id Row id of item clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String details = "Email: %Account: ";
        //Check if there account type is Student of Professor
        String type = "Professor";
        if(super.getNameorExtra(position, AppCSTR.TYPE).equals(AppCSTR.STUDENT)){
            type = "Student";
        }

        //Data has to be split by a % to be parsed
        String extra = super.getNameorExtra(position, AppCSTR.EMAIL) +"%"+ type;
        Intent i = new Intent(this, ShowDetailActivity.class);
        i.putExtra(AppCSTR.SHOW_NAME , super.getNameorExtra(position, AppCSTR.SHOW_NAME));
        i.putExtra(AppCSTR.SHOW_EXTRA , extra);
        i.putExtra(AppCSTR.SHOW_DETAIL , details);
        startActivity(i);
    }
}
