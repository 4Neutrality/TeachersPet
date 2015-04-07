
package com.example.teacherspet.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teacherspet.R;
import com.example.teacherspet.model.AppCSTR;
import com.example.teacherspet.model.BasicActivity;

/**
 * Login in a user to application, go to make new account screen, or go to forgot login screen.
 *  
 * @author Johnathon Malott, Kevin James
 * @version 3/21/2014
 */
public class LoginActivity extends BasicActivity {
	//New user button
	Button btn_newUser;
	//User's username
	String username;
	//User's password
	String password;

	/**
	 * When screen is created set to login layout.
	 * 
	 * @param savedInstanceState Most recently supplied data.
	 * @Override
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_1_login);
	}
	
	/**
	 * Finds what button the user has pressed and preforms action for that button.
     * Login: Go to home screen if account information is correct
     * Forgot: Go to forgot account information screen
     * New User: Go to new user screen.
	 * 
	 * @param view View that was interacted with by user.
	 */
	public void onClicked(View view){
		switch(view.getId()){
			case R.id.btn_login:
				super.makePref();
				startSearch();
				break;
			case R.id.btn_forgot:
				super.start(this, ForgotLoginActivity.class, false);
				break;
            case R.id.btn_newUser:
                super.start(this, NewUserActivity.class, false);
                break;
		}
	}
	
	/**
	 * Sets up and sends data to be passed to model for a database search.
	 *
	 */
	private void startSearch(){
		//Get username/password text fields
        username = ((EditText) findViewById(R.id.fld_username)).getText().toString();
        password = ((EditText) findViewById(R.id.fld_pwd)).getText().toString();
		//JSON name data is stored under
		String tag = "user";
		String [] dataPassed = new String[]{"user", username};
		String [] dataNeeded = new String[]{"id","name","accountnumber","email","password", "accounttype"};

        super.sendData(tag, dataPassed, dataNeeded, AppCSTR.URL_LIST_USERS, this, true);
	}
	
	/**
	 * Receives data from model to check if password is a match to the username given. If so proceed with application
     * if not then warn user.
	 * 
	 * @param requestCode Number that was assigned to the intent being called.
	 * @param resultCode RESULT_OK if successful, RESULT_CANCELED if failed
	 * @param data Intent that was just exited.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	         Intent data) {
		//Check request that this is a response to
	    if (requestCode == 0) {
            int success = data.getIntExtra(AppCSTR.SUCCESS, -1);
            //0 means successful
            if(success == 0) {
                //Can only be one user stored with that login name
                String[] userInfo = data.getStringArrayExtra(AppCSTR.DB_FIRST_ROW);
                if(password.equals(userInfo[AppCSTR.PASSWORD])){
                    super.setPrefValue(userInfo[AppCSTR.ACCOUNTTYPE], userInfo[AppCSTR.ID], username);
                    super.start(this, HomeActivity.class, false);
                }else{
                    //Password didn't match name
                    showToast("Username/Password No Match", false);
                }
            } else {
                //User not found
                showToast("No User Found", true);
            }
	     }
	}

    /**
     * Displays a toast on the screen.
     *
     * @param toast Message to be displayed.
     * @param resetAll True: clear out all fields.
     */
    private void showToast(String toast, Boolean resetAll){
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
        //reset username/password field to nothing
        ((EditText)findViewById(R.id.fld_pwd)).setText("");
        if(resetAll) {
            ((EditText) findViewById(R.id.fld_username)).setText("");
        }
    }
}
