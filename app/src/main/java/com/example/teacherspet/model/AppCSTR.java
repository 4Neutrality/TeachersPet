package com.example.teacherspet.model;

import android.graphics.Color;

/**
 * Holds all App constraints for the Teacher's Pet application.
 *
 *  @author Johnathon Malott, Kevin James
 * @version 1/31/2015
 * */
public class AppCSTR {
    //Five second delay
    public static final int PAUSE = 5000;
    //Display message when loading
    public static final String GET_MESSAGE = "Loading data. Please wait...";
    public static final String POST_MESSAGE = "Creating. Please wait...";
    //Account type, id, name, course for account
    public static final String ACCOUNT_TYPE = "type";
    public static final String ACCOUNT_ID = "id";
    public static final String ACCOUNT_COURSE = "course";
    public static final String ACCOUNT_NAME = "AName";
    //Account type
    public static final String STUDENT = "s";
    public static final String PROFESSOR = "p";


    //BASIC*******************************************
    public static final int GREEN = Color.GREEN;
    public static final int WHITE = Color.WHITE;
    public static final int RED = Color.RED;
    public static final int TRANS = Color.TRANSPARENT;
    public static final String COURSE_NAME = "courseName";
    public static final String NAME = "name";
    public static final String EXTRA = "extra";
    public static final String COUNT = "count";


    //PHP sites to connect to*******************************************
    public static final String URL_LIST_USERS = "https://morning-castle-9006.herokuapp.com/find_users.php";
    public static final String URL_LIST_COURSES = "https://morning-castle-9006.herokuapp.com/list_courses.php";
    public static final String URL_CREATE_USER = "https://morning-castle-9006.herokuapp.com/create_user.php";
    public static final String URL_CREATE_COURSE = "https://morning-castle-9006.herokuapp.com/create_course.php";
    public static final String URL_ADD_COMMENT = "https://morning-castle-9006.herokuapp.com/create_comment.php";
    public static final String URL_ADD_ASSIGNMENT = "https://morning-castle-9006.herokuapp.com/create_assignment.php";

    public static final String URL_FIND_920 = "https://morning-castle-9006.herokuapp.com/find_920.php";
    public static final String URL_FIND_COURSES = "https://morning-castle-9006.herokuapp.com/find_courses.php";
    public static final String URL_FIND_ALERTS = "https://morning-castle-9006.herokuapp.com/find_alerts.php";
    public static final String URL_FIND_STUDENT = "https://morning-castle-9006.herokuapp.com/find_student.php";
    public static final String URL_FIND_STUDENTS = "https://morning-castle-9006.herokuapp.com/find_students.php";
    public static final String URL_FIND_ASSIGN = "https://morning-castle-9006.herokuapp.com/find_assignments.php";
    public static final String URL_FIND_ALL_USERS = "https://morning-castle-9006.herokuapp.com/find_all_users.php";
    public static final String URL_FIND_GRADES = "https://morning-castle-9006.herokuapp.com/find_grades.php";
    public static final String URL_FIND_GRADED = "https://morning-castle-9006.herokuapp.com/find_graded.php";
    public static final String URL_FIND_STUDENT_GRADES = "https://morning-castle-9006.herokuapp.com/find_student_grades.php";
    public static final String URL_FIND_LAB = "https://morning-castle-9006.herokuapp.com/find_lab.php";
    public static final String URL_FIND_COMMENTS = "https://morning-castle-9006.herokuapp.com/find_comments.php";
    public static final String URL_FIND_EXTRA = "https://morning-castle-9006.herokuapp.com/find_extra.php";

    public static final String URL_DELETE_ALERT = "https://morning-castle-9006.herokuapp.com/delete_alert.php";
    public static final String URL_ENROLL_STUDENT = "https://morning-castle-9006.herokuapp.com/enroll_student.php";
    public static final String URL_STUDENT_ALERT = "https://morning-castle-9006.herokuapp.com/create_student_alert.php";
    public static final String URL_REGISTER_ATTENDANCE = "https://morning-castle-9006.herokuapp.com/register_attendance.php";
    public static final String URL_SUBMIT_ATTENDANCE = "https://morning-castle-9006.herokuapp.com/submit_attendance.php";


    //INDEXING*******************************************
    public static final int REQUEST_CODE = 0;
    public static final int FIRST_ELEMENT = 0;
    public static final int SECOND_ELEMENT = 1;
    public static final int THIRD_ELEMENT = 2;
    public static final int FOURTH_ELEMENT = 3;


    //SIZE***********************************************
    public static final int SIZE_ZERO = 0;


    //TAGS***********************************************
    //Name of shared preference that is storing data
    public static final String PREF_NAME = "Pet";
    //Pass data to get method
    public static final String JSON = "JSON_TAG";
    //success of getting data from database
    public static final String SUCCESS = "success";
    //Get data passed, data need, and url to go to
    public static final String DATA_PASSED = "pass";
    public static final String DATA_NEEDED = "need";
    public static final String URL = "url";
    //Get message, row, count from Database
    public static final String DB_MESSAGE = "message";
    public static final String DB_ROW = "row";
    public static final String DB_FIRST_ROW = "row0";
    public static final String DB_ROW_COUNT= "count";
    //List view ids of views selected
    public static final String GREEN_IDS = "greenID";
    public static final String RED_IDS = "redID";
    public static final String ALL_IDS = "viewID";


    //______________________________________________________________________________________________
    //____________________________________SCREENS___________________________________________________
    //______________________________________________________________________________________________


    //ADD COURSE*********************************************************
    public static final int FIRST_ID = 0;


    //ALERT**************************************************************
    public static final String ALERT_AID = "aid";
    public static final String ALERT_ACTION = "action";
    public static final String ALERT_NAME = "alert";
    public static final String ALERT_DESCRIPTION = "descript";
    public static final String ALERT_SID = "sid";
    public static final String ALERT_CID = "cid";


    //ATTENDANCE*********************************************************
    public static final int STATUS = 7;
    public static final int DATE = 6;
    public static final int COURSE = 5;
    public static final int PROFESSOR_NAME= 4;
    public static final int TOTAL_DAYS= 3;
    public static final int TODAY_STATUS= 2;
    public static final int LATE= 1;
    public static final int PRESENT= 0;


    //LOGIN SCREEN*******************************************************
    public static final int ID = 0;
    public static final int PASSWORD = 4;
    public static final int ACCOUNTTYPE = 5;


    //SHOW***************************************************************
    public static final String SHOW_NAME = "name";
    public static final String SHOW_EXTRA = "extra";
    public static final String SHOW_DETAIL = "detail";
    public static final String SHOW_URL = "url";
    public static final String SHOW_LAB_ID = "labID";
    public static final String SHOW_RECEIVE_ID = "rid";
    public static final String SHOW_DESCRIPTION = "descript";

    //STUDENTS***************************************************************
    public static final String EMAIL = "email";
    public static final String TYPE = "type";

}
