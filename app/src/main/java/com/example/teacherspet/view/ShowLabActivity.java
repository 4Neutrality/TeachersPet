package com.example.teacherspet.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.control.AddCommentActivity;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;

/**
 * Sets up the screen for that lab showing detail information, PDF of lab, and comments for that
 * lab.
 *
 * @author Johnathon Malott, Kevin James
 * @version 3/25/2015
 */
public class ShowLabActivity extends BasicActivity implements AdapterView.OnItemClickListener{
    //Intent that is passing data
    Intent i;
    String[] dataNeeded;
    int layout;

    /**
     * When screen is created set to lab layout.
     *
     * @param savedInstanceState Most recently supplied data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = R.layout.activity_18_2_lab;
        setContentView(layout);

        i = getIntent();
        //Set title
        ((TextView) findViewById(R.id.title)).setText(i.getStringExtra(AppCSTR.SHOW_NAME));

        if(super.getType().equals(AppCSTR.PROFESSOR)){
            ((Button) findViewById(R.id.bnt_submit)).setVisibility(View.VISIBLE);
            ((ScrollView) findViewById(R.id.addScroll)).setVisibility(View.VISIBLE);
        }else{
            ((Button) findViewById(R.id.bnt_pdf)).setVisibility(View.VISIBLE);
            ((ListView) findViewById(R.id.commentView)).setVisibility(View.VISIBLE);
            startSearch();
        }
    }

    /**
     * Get comments for the lab that was selected.
     */
    private void startSearch() {
        //Name of JSON tag storing data
        String tag = "comments";
        String[] dataPassed = new String[]{"uid", super.getID(),"cid", super.getCourseID(),
                                           "lid", i.getStringExtra(AppCSTR.SHOW_LAB_ID)};
        dataNeeded = new String[]{"name","descript"};

        super.sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_FIND_COMMENTS, this, layout, true);
    }

    /**
     * Creates a list of all comments for that lab in database.
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
                ListView courseView = (ListView) findViewById(R.id.commentView);
                //Get and pass data to make list adapter
                int layout = R.layout.list_item;
                int[] ids = new int[] {R.id.listItem};
                courseView.setAdapter(super.makeAdapter(data, dataNeeded, this, layout ,ids));
                courseView.setOnItemClickListener(this);
            } else {
                //Do nothing, user will see no alerts in his box.
                Toast.makeText(this, "No Comments!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Finds comments that user has selected and sends to a screen to show more detailed information
     * on that comment;
     *
     * @param parent Where clicked happen.
     * @param view View that was clicked
     * @param position Position of view in list.
     * @param id Row id of item clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent i = new Intent(this, ShowDetailActivity.class);
        i.putExtra(AppCSTR.SHOW_NAME, super.getNameorExtra(position, AppCSTR.SHOW_NAME));
        i.putExtra(AppCSTR.SHOW_EXTRA, super.getNameorExtra(position, AppCSTR.SHOW_DESCRIPTION));
        i.putExtra(AppCSTR.SHOW_DETAIL, "Lab Description:\n");
        startActivity(i);
    }

    /**
     * Open PDF of the lab or add a new comment.
     *
     * @param view View that was interacted with by user.
     */
    public void onClicked(View view){
        if(view.getId() == R.id.bnt_pdf) {
            String url = i.getStringExtra(AppCSTR.SHOW_URL);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }else{
            Intent i = new Intent(this, AddCommentActivity.class);
            i.putExtra(AppCSTR.SHOW_RECEIVE_ID, ((EditText) findViewById(R.id.student)).getText().toString());
            i.putExtra(AppCSTR.SHOW_NAME, ((EditText) findViewById(R.id.cName)).getText().toString());
            i.putExtra(AppCSTR.SHOW_LAB_ID, i.getStringExtra(AppCSTR.SHOW_LAB_ID));
            i.putExtra(AppCSTR.SHOW_DESCRIPTION, ((EditText) findViewById(R.id.cDescript)).getText().toString());
            startActivity(i);
        }
    }
}
