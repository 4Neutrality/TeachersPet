package com.example.teacherspet.view;

import android.content.Intent;
import android.net.Uri;
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
 * Find all extra information for the course.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 2/27/2015
 */
public class InformationActivity extends BasicActivity implements AdapterView.OnItemClickListener{
    //Data collecting from web page
    String[] dataNeeded;
    //Web page to connect to
    private static String url_find_extra = "https://morning-castle-9006.herokuapp.com/find_extra.php";

	/**
	 * When screen is created set to information layout.
     * Then start search for extra information.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_14_information);

        startSearch();
	}

    /**
     * Send data to database to get back extra names and address.
     */
    private void startSearch(){
        //Name of JSON tag storing data
        String tag = "extras";
        //Log.d("CourseID: ", super.getCourseID());
        String[] dataPassed = new String[]{"cid", super.getCourseID()};
        dataNeeded = new String[]{"name","address"};

        sendData(tag, dataPassed, dataNeeded, url_find_extra, this, true);
    }

    /**
     * List all extras in the course to the screen.
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
                ListView attendance = (ListView) findViewById(R.id.extra);

                int layout = R.layout.list_item;
                int[] ids = new int[] {R.id.listItem};
                attendance.setAdapter(super.makeAdapterArray(data, this, layout, ids));
                attendance.setOnItemClickListener(this);
            } else {
                //Do nothing, user will see no alerts in his box.
                Toast.makeText(this, "No extras!!", Toast.LENGTH_SHORT);
            }
        }
    }

    /**
     * When extra is selected send to web page.
     *
     * @param parent Where clicked happen.
     * @param view View that was clicked
     * @param position Position of view in list.
     * @param id Row id of item clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String url = "http://" + super.getNameorExtra(position, "extra").replaceAll("%", "");
        Log.d("URL", url);

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
