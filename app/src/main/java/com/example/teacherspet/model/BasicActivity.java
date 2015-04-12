package com.example.teacherspet.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Basic methods that can be used in any activity class.
 *
 *  @author Johnathon Malott, Kevin James
 * @version 1/31/2015
 */
public class BasicActivity extends Activity {
    //To access and modify preference data
    SharedPreferences sharedPref;
    //Set up preference to store data
    SharedPreferences.Editor edit;
    //Store items on a list view
    ArrayList<HashMap<String, String>> alertList;
    //Ids of selected items
    ArrayList<String> redIDs = new ArrayList<>();
    ArrayList<String> greenIDs = new ArrayList<>();

    /**
     * Changes activity from fromScreen to toScreen. Also if kill is true then destroy screen
     * coming from.
     *
     * @param fromScreen Activity that you are coming from.
     * @param toScreen Activity that you are going to.
     * @param kill true if screen coming from should de destroyed
     */
    protected void start(Context fromScreen,Class<?> toScreen, Boolean kill){
        Intent intent = new Intent(fromScreen, toScreen);
        startActivity(intent);
        if(kill){
            finish();
        }
    }

    /**
     * DELETE
     * DELETE
     * DELETE
     *
     * @param tag Name of contains that user wants to receive. Leave blank if a post method needed.
     * @param data1 Get: Data needed to pass info/ Post: Values being set
     * @param data2 Get: Data needed back/ Post: Values wanting to send
     * @param url Php to get or post information
     * @param callingActivity Activity that is invoking this method
     * @param getItem True if needed to be a Get method/ False for Post method
     */
    protected void sendData(String tag, String [] data1, String [] data2, String url,
                           Context callingActivity, Boolean getItem){
        //sending data
        Intent i;
        if(getItem){
            i = new Intent(callingActivity, GetItemActivity.class);
            i.putExtra(AppCSTR.JSON, tag);
        }else{
            i = new Intent(callingActivity, PostItemActivity.class);
        }
        i.putExtra(AppCSTR.DATA_PASSED, data1);
        i.putExtra(AppCSTR.DATA_NEEDED, data2);
        i.putExtra(AppCSTR.URL, url);
        startActivityForResult(i, AppCSTR.REQUEST_CODE);
    }

    /**
     * Takes data entered and is sent to the database to be retrieved or place information.
     *
     * @param tag Name of contains that user wants to receive. Leave blank if a post method needed.
     * @param data1 Get: Data needed to pass info/ Post: Values being set.
     * @param data2 Get: Data needed back/ Post: Values wanting to send.
     * @param url Php to get or post information.
     * @param callingActivity Activity that is invoking this method.
     * @param layout Layout for background of loading screen.
     * @param getItem True if needed to be a Get method/ False for Post method.
     */
    protected void sendData(String tag, String [] data1, String [] data2, String url,
                            Context callingActivity, int layout, Boolean getItem){
        //sending data
        Intent i;
        if(getItem){
            i = new Intent(callingActivity, GetItemActivity.class);
            i.putExtra(AppCSTR.JSON, tag);
        }else{
            i = new Intent(callingActivity, PostItemActivity.class);
        }
        i.putExtra(AppCSTR.LAYOUT, layout);
        i.putExtra(AppCSTR.DATA_PASSED, data1);
        i.putExtra(AppCSTR.DATA_NEEDED, data2);
        i.putExtra(AppCSTR.URL, url);
        startActivityForResult(i, AppCSTR.REQUEST_CODE);
    }

//************************************* PREF START **************************************************

    /**
     * Sets up a shared preferences to store information that maybe needed later.
     */
    protected void makePref(){
        //Creates a shared pref called MyPref and 0-> MODE_PRIVATE
        sharedPref = getSharedPreferences(AppCSTR.PREF_NAME, Context.MODE_PRIVATE);
        //so the pref can be edit
        edit = sharedPref.edit();
    }

    /**
     * Stores users' Account Type and id.
     *
     * @param type What kind of account user has.
     * @param id Users id in the database.
     */
    protected void setPrefValue(String type, String id, String name){
        //adds a key value pair to pref
        edit.putString(AppCSTR.ACCOUNT_TYPE, type);
        edit.putString(AppCSTR.ACCOUNT_ID, id);
        edit.putString(AppCSTR.ACCOUNT_NAME, name);
        //records changes
        edit.commit();
    }

    /**
     * Stores what course the user is under from the courses screen.
     *
     * @param courseID ID for course that user is currently under.
     */
    protected void setCourseID(String courseID){
        //adds a key value pair to pref
        edit.putString(AppCSTR.ACCOUNT_COURSE, courseID);
        //records changes
        edit.commit();
    }

    /**
     * Return the users' ID for what course they are under.
     *
     * @return CourseID for the class the user is currently under.
     */
    protected String getCourseID(){
        sharedPref = getSharedPreferences(AppCSTR.PREF_NAME, Context.MODE_PRIVATE);
        //Log.d("ID", sharedPref.getString("id", null));
        return sharedPref.getString(AppCSTR.ACCOUNT_COURSE, null);
    }

    /**
     * Return the User id store in device memory.
     *
     * @return User ID.
     */
    protected String getID(){
        sharedPref = getSharedPreferences(AppCSTR.PREF_NAME, Context.MODE_PRIVATE);
        //Log.d("ID", sharedPref.getString("id", null));
        return sharedPref.getString(AppCSTR.ACCOUNT_ID, null);
    }

    /**
     * Return the User id store in device memory.
     *
     * @return User ID.
     */
    protected String getType(){
        sharedPref = getSharedPreferences(AppCSTR.PREF_NAME, Context.MODE_PRIVATE);
        //Log.d("TYPE", sharedPref.getString("accountType", null));
        return sharedPref.getString(AppCSTR.ACCOUNT_TYPE, null);
    }

    /**
     * Return the User's name store in device memory.
     *
     * @return Users Name.
     */
    protected String getName(){
        sharedPref = getSharedPreferences(AppCSTR.PREF_NAME, Context.MODE_PRIVATE);
        //Log.d("TYPE", sharedPref.getString("accountType", null));
        return sharedPref.getString(AppCSTR.ACCOUNT_NAME, null);
    }

//************************************* PREF END **************************************************

//********************************* SELECT ITEM START *********************************************

    /**
     * Changes the color to white/green/red and stores id.
     *
     * @param view View of list being pressed
     * @param position position of view in list view
     * @param key Item to be stored
     * @param twoColors True if only using two colors
     */
    protected void changeColor(View view, int position, String key, Boolean twoColors){
        int color = AppCSTR.TRANS;
        Drawable background = view.getBackground();

        if(background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();

        if(color == AppCSTR.GREEN){
            if(twoColors)
                view.setBackgroundColor(AppCSTR.WHITE);
            else {
                view.setBackgroundColor(AppCSTR.RED);
                redIDs.add(getList().get(position).get(key));
            }
            greenIDs.remove(getList().get(position).get(key));
        }else if(color == AppCSTR.RED){
            view.setBackgroundColor(AppCSTR.WHITE);
            redIDs.remove(getList().get(position).get(key));
        }else{
            view.setBackgroundColor(AppCSTR.GREEN);
            greenIDs.add(getList().get(position).get(key));
        }
    }

    /**
     * Returns the ids of item that was selected.
     *
     * @return Ids for selected items.
     */
    protected ArrayList<String> getViewID(String color){
        if(color.equals(AppCSTR.GREEN_IDS)){
            return greenIDs;
        }
        return redIDs;
    }

//********************************** SELECT ITEM END ***********************************************

//********************************** ADAPTER START *************************************************

    /**
     * Makes adapter for a list view to display rows of data.
     *
     * @param data Intent being returned
     * @param dataNeeded Names of all data being stored in mapping
     * @param activity Activity that called this method
     * @param layout Layout of how each view will look
     * @param ids Ids on layout to place data onto
     *
     * @return Adapter being attached to View
     */
    protected ListAdapter makeAdapter(Intent data, String[] dataNeeded, Context activity,
                                    int layout, int[] ids){
        //Get items that where returned
        int count = Integer.parseInt(data.getStringExtra(AppCSTR.DB_ROW_COUNT));
        //holds rows from database
        String[] row;
        //Log.e("Count: ", "" + count);
        alertList = new ArrayList<>();

        for(int j = 0; j < count; j++){
            row = data.getStringArrayExtra(AppCSTR.DB_ROW + j);
            // creating new HashMap
            HashMap<String, String> map = new HashMap<>();
            for(int i = 0; i < dataNeeded.length; i++){
                map.put(dataNeeded[i], row[i]);
                //Log.e("NAME: ", dataNeeded[i]);
                //Log.e("VALUE: ",items[i]);
            }
            alertList.add(map);
        }

        /**
         * Details of how the adapter will be laid out.
         */
        return new SimpleAdapter(
                activity, alertList,
                layout, dataNeeded, ids);
    }

    /**
     * Makes  an adapter for a list view to display an array of rows.
     *
     * @param data Intent being returned
     * @param activity Activity that called this method
     * @param layout Layout of how each view will look
     * @param ids Ids on layout to place data onto
     *
     * @return Adapter being attached to View
     */
    protected ListAdapter makeAdapterArray(Intent data, Context activity, int layout, int[] ids){

        alertList = new ArrayList<>();
        //Key values for the mapping
        String[] keyNames = new String[]{AppCSTR.NAME, AppCSTR.EXTRA};
        //A row with array values in table
        String[] item = data.getStringArrayExtra(AppCSTR.DB_FIRST_ROW);
        //Take off brackets of string array from database, what is to be displayed in list view.
        String[] names = arrayParser(item[AppCSTR.FIRST_ELEMENT]);
        int count = names.length;
        String[] extras = getExtraInfo(item, count);

        //checkEmptyArray(names[AppCSTR.FIRST_ELEMENT]);
        for(int i = 0; i < count; i++){
            // creating new HashMap
            HashMap<String, String> map = new HashMap<>();
            map.put(AppCSTR.NAME, names[i]);
            map.put(AppCSTR.EXTRA, extras[i]);
            alertList.add(map);
        }
        /**
         * Details of how the adapter will be laid out.
         */
        return new SimpleAdapter(
                activity, alertList,
                layout, keyNames, ids);
    }

    /**
     * Check if anything was added to the adapter.
     * "" means nothing is in array from database but the database will count an empty array as
     * a success. However a empty array is a fail so this checks for that.
     *
     * @return True if items were added.
     */
    protected boolean checkEmptyList(){
        boolean emptyArray = true;
        String  empty= alertList.get(AppCSTR.FIRST_ELEMENT).get(AppCSTR.NAME);
        Log.e("CHECK: ", "" + empty.length());
        if(!empty.equals("null") && !empty.equals("")) {
            emptyArray = false;
        }
        return emptyArray;
    }


    /**
     * Takes array in each column and turns them into rows.
     *
     * @param item Holds all assignment data for student.
     * @param colLength Length that each array is going to be.
     *
     * @return All assignments grouped by assignment.
     */
    private String[] getExtraInfo(String[] item, int colLength){
        String[][] data = new String[item.length][colLength];
        String[] extras  = new String[item.length];
        String dataString = "";

        //getting all the data back except grade names
        for(int i = 0; i < item.length - 1; i++) {
            data[i] = arrayParser(item[i+1]);
        }

        //Store all data for 1 assignment together
        for(int i = 0; i < colLength; i++) {
            for(int j = 0; j < (item.length - 1); j++) {
                dataString += data[j][i] + "%";
            }
            extras[i] = dataString;
            dataString = "";
        }

        return extras;
    }

    /**
     * Takes array from database and turns into string array in java.
     *
     * @param item Array String from database
     *
     * @return Array String in java
     */
    protected String[] arrayParser(String item){
       return item.replaceAll("\\{|\\}|\\[|\\]|(\\d+\":)|\"", "").split(",");
    }


    /**
     * List that holds all data needed from the list view
     *
     * @return List of data stored.
     */
    private ArrayList<HashMap<String, String>>  getList(){
        return alertList;
    }

    /**
     * Gets the name for an item or extras stored with it
     *
     * @param position Location in the view.
     * @param key Value that user is trying to reach.
     *
     * @return Information stored in a list view element
     */
    protected String getNameorExtra(int position, String key){
        return getList().get(position).get(key);
    }

    /**
     * @return True if array from database is empty.
     *
    protected Boolean getArrayStatus(){return emptyArray;}*/

//********************************** ADAPTER END ****************************************************
}
