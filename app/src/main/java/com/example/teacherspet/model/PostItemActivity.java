package com.example.teacherspet.model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Collects data from user and inserts into database.
 * 
 * Return Extras:  success: 0 successful, 1 and up is fail
 *
 * @author Johnathon Malott, Kevin James
 * @version 3/21/2015
 */
public class PostItemActivity extends Activity{
	private ProgressDialog pDialog;
	//Names of the fields in the database
	String[] itemNames;
	//Values to be placed into names
	String[] itemValues;
	//URL to perform action on database
	String url_to_go;
	//Calling Activity to web pages
	Intent calling = new Intent();
	
    /**
	 * When screen is created get all data from intent and set to its
	 * corresponding value. Then connect to database through PHP file.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		itemNames = intent.getStringArrayExtra(AppCSTR.DATA_PASSED);
		itemValues = intent.getStringArrayExtra(AppCSTR.DATA_NEEDED);
		url_to_go = intent.getStringExtra(AppCSTR.URL);
		new PostItem().execute();
	}
	
	class PostItem extends AsyncTask<String, String, String>{
		/**
		 * Done before starting background thread
		 */
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(PostItemActivity.this);
			pDialog.setMessage(AppCSTR.POST_MESSAGE);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		/**
         * After completing background task Dismiss the progress dialog and sends back data the
         * user needs.
         * **/
        @Override
		protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
        	//Send data back to calling activity
           setResult(AppCSTR.REQUEST_CODE, calling);
           //Close this activity
           finish();
        }
        
        /**
		 * Creates a new row or rows in database.
		 * 
		 * @param args Parameters sent to task on execution.
		 * @return Nothing
		 */
		@Override
		protected String doInBackground(String... args){
			// Building Parameters
            List<NameValuePair> params = new ArrayList<>();
			for(int i = 0; i < itemNames.length; i++){
	            params.add(new BasicNameValuePair(itemNames[i], itemValues[i]));
	            Log.e("Param: ", "" + itemValues[i]);
            }
            //Reads data from web page
            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(url_to_go, "POST", params);
            
            //Log.d("Course: ", json.toString());
            try {
            	//Find out if row was created
                int success = json.getInt(AppCSTR.SUCCESS);
                String sMessage = json.getString(AppCSTR.DB_MESSAGE);
                Log.e("Success: ", "" + success);
                Log.e("Message: ", "" + sMessage);
                //1 means that it passed
                calling.putExtra(AppCSTR.SUCCESS, success);
                calling.putExtra(AppCSTR.DB_MESSAGE, sMessage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
		}
	}
}
