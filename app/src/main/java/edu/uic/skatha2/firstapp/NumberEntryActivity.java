package edu.uic.skatha2.firstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class NumberEntryActivity extends Activity {


    protected EditText textField;
    private static String PHONE_NUMBER_FORMAT = "\\(\\d{3}\\)\\s\\d{3}-\\d{4}";
    private static final String EXTRA_PARAM_PHONE_NUMBER = "extra_param_phone_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //1. Call super.onCreate()
        super.onCreate(savedInstanceState);

        //2. Set layout configuration for activity (call setContentView() on
        //   appropriate layout file)
        setContentView(R.layout.activity_number_entry);

        //3. Get references to views, e.g., findViewById(R.id.viewName)
        // Bind the interface elements to the corresponding fields
        textField = (EditText) findViewById(R.id.editText1);

        //4. Check saved instance state, act upon saved state if needed

        //5. Set up listeners for interactive views
        // Actual listeners are created below as instances of anonymous classes
        textField.setOnEditorActionListener(numberListener);

        //6. Configure views and other initialization actions
    }

    public TextView.OnEditorActionListener numberListener = new TextView.OnEditorActionListener(){

        @Override
        public boolean onEditorAction(TextView v, int keyCode, KeyEvent event) {

            // check if key pressed is Done
            if(keyCode == EditorInfo.IME_ACTION_DONE || keyCode == KeyEvent.KEYCODE_BACK) {

                //get phone number and remove trailing and leading whitespaces
                String phoneNumber = textField.getText().toString().trim();

                //set value in Intent  Extras
                getIntent().putExtra(EXTRA_PARAM_PHONE_NUMBER, phoneNumber);

                //set resultCode according in validation
                setResult(isvalidPhoneNumber(phoneNumber) ? RESULT_OK : RESULT_CANCELED, getIntent());

                //finish activity
                finish();
            }
            return true;
        }
    };

    private boolean isvalidPhoneNumber(String phoneNumber) {
        //regular expression used to test for correct format
        Pattern pattern = Pattern.compile(PHONE_NUMBER_FORMAT);
        Matcher match = pattern.matcher(phoneNumber);
        return match.matches();
    }

    //handle back key
    public void onBackPressed() {
        //get phone number and remove trailing and leading whitespaces
        String phoneNumber = textField.getText().toString().trim();

        //set value in Intent  Extra
        getIntent().putExtra(EXTRA_PARAM_PHONE_NUMBER, phoneNumber);

        //set resultCode according in validation
        setResult(isvalidPhoneNumber(phoneNumber) ? RESULT_OK : RESULT_CANCELED, getIntent());

        super.onBackPressed();

    }

}
