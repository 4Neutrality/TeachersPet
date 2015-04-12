package com.example.teacherspet.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;
/**
 * Find all Lab information for the course.
 *
 * @author Johnathon Malott, Kevin James
 * @version 3/25/2015
 */
public class LabActivity extends BasicActivity implements AdapterView.OnItemClickListener {
    //Data collecting from web page
    String[] dataNeeded;
    //ID for screen layout
    int layout;

	/**
	 * When screen is created set to lab layout.
	 * Start searching for labs.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        layout = R.layout.activity_list;
		setContentView(layout);

        startSearch();
	}

    /**
     * Get lab data from the database.
     */
    private void startSearch(){
        //Name of JSON tag storing data
        String tag = "labs";
        //Log.d("CourseID: ", super.getCourseID());
        String[] dataPassed = new String[]{"cid", super.getCourseID()};
        dataNeeded = new String[]{"name","address","lid"};

        sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_FIND_LAB, this, layout, true);
    }

    /**
     * List all lab to the course to the screen.
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
                ListView labs = (ListView) findViewById(R.id.list);
                int layout = R.layout.list_item;
                int[] ids = new int[] {R.id.listItem};

                labs.setAdapter(super.makeAdapterArray(data, this, layout, ids));
                labs.setOnItemClickListener(this);
                if(super.checkEmptyList()){
                    Toast.makeText(this, "No Labs!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    /**
     * When lab is select take that lab information and send to show lab screen.
     *
     * @param parent Where clicked happen.
     * @param view View that was clicked
     * @param position Position of view in list.
     * @param id Row id of item clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String[] extra = super.getNameorExtra(position, AppCSTR.EXTRA).split("%");
        String url = "http://" + extra[AppCSTR.FIRST_ELEMENT];
        Log.e("URL: ", url);

        Intent i = new Intent(this, ShowLabActivity.class);
        i.putExtra(AppCSTR.SHOW_NAME, super.getNameorExtra(position, AppCSTR.SHOW_NAME));
        i.putExtra(AppCSTR.SHOW_URL, url);
        i.putExtra(AppCSTR.SHOW_LAB_ID, extra[AppCSTR.SECOND_ELEMENT]);
        startActivity(i);
    }
}
