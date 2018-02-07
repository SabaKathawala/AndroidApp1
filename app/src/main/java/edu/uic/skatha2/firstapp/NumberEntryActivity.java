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

    public String getTextField() {
        //remove whitespaces
        return textField.getText().toString().trim();
    }

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
            // check if key pressed is Return/Done
            Log.i("Key", "press");
            if(keyCode == EditorInfo.IME_ACTION_DONE) {
                returnToParentActivity();
            }
            return true;
        }
    };

    private void returnToParentActivity() {
        //get the value from EditText
        String phoneNumber = getTextField();
        //set Intent Extra
        setIntentExtra(phoneNumber);
        //setResult
        setResultOnValidate(phoneNumber);
        //finish the activity
        finish();
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        //regular expression used to test for correct format
        Pattern pattern = Pattern.compile(PHONE_NUMBER_FORMAT);
        Matcher match = pattern.matcher(phoneNumber);
        return match.matches();
    }

    private void setResultOnValidate(String phoneNumber) {
        //check if phone number is in the right format
        if (validatePhoneNumber(phoneNumber)) {
            setResult(RESULT_OK, getIntent());
        }
        else {
            setResult(RESULT_CANCELED, getIntent());
        }
    }

    //onPause
    public void onBackPressed() {
        returnToParentActivity();
    }

    private void setIntentExtra(String phoneNumber) {

        Intent intent = getIntent();
        //pass the value of phone number using Intent Extras
        intent.putExtra(EXTRA_PARAM_PHONE_NUMBER, phoneNumber);
    }
}
