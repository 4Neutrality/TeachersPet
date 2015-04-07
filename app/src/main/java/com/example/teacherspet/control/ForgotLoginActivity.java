package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;
import com.example.teacherspet.R;
import com.example.teacherspet.model.Mail;

/**
 * If user is found in database then an email with username/password will be sent
 * to email listed in database for that user.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 3/21/2015
 */
public class ForgotLoginActivity extends BasicActivity {
	//Get userid/email/username/password from item
	private final int EMAIL = 1;
	private final int USERNAME = 0;
    private final int PASSWORD = 2;
    //ID for layout
    int layout;

	/**
	 * When screen is created set to forgot login layout.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        layout = R.layout.activity_2_forgot_login;
		setContentView(layout);
	}
	
	/**
	 * Get the information that the user entered and pass along to model. 
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
		startSearch();
	}


	/**
	 * Get 920 number entered and pass it to the database.
	 *
	 */
	private void startSearch(){
		//Get id number entered
        String user920 = ((EditText) findViewById(R.id.userID)).getText().toString();
        //Set up and send data
		String tag = "user";
		String [] dataPassed = new String[]{"920", user920};
		String [] dataNeeded = new String[]{"name","email","password"};
        super.sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_FIND_920, this, layout,true);
	}
	
	/**
	 * If 920# was found then email will be sent to that account address. If not found then
     * user will be notified.
	 * 
	 * @param requestCode Number that was assigned to the intent being called.
	 * @param resultCode RESULT_OK if successful, RESULT_CANCELED if failed
	 * @param data Intent that was just exited.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	         Intent data) {
		Boolean passed = false;
		//Check request that this is response to
	    if (requestCode == 0) {
            int success = data.getIntExtra(AppCSTR.SUCCESS, -1);
            //0 Means successful
            if(success == 0){
                //Can only be one user with that 920#
                String[] item = data.getStringArrayExtra(AppCSTR.DB_FIRST_ROW);

                try {
                    sendMail(item[EMAIL], item[USERNAME], item[PASSWORD]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(getApplicationContext(), "ID not found.", Toast.LENGTH_SHORT).show();
                super.start(this, ForgotLoginActivity.class, true);
            }
	     }
	}
	
	/**
	 * Sends out an email to the user about username/password.
	 */
	private void sendMail(String email, String user, String pass){
		//Sending email
		 Mail mail = new Mail();
	     mail.setTo(new String[]{email});
	     mail.setSubject("Lost password");
	     mail.setBody("Username: " + user + " Password: " + pass);
	     mail.sendMail();
	     Toast.makeText(getApplicationContext(), "Email sent!", Toast.LENGTH_SHORT).show();
	}
}

