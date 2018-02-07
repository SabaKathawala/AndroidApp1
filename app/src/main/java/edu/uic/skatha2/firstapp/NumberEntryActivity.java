package edu.uic.skatha2.firstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class NumberEntryActivity extends Activity {

    protected EditText textField;
    private static String PHONE_NUMBER_FORMAT = "\\(\\d{3}\\)\\s\\d{3}-\\d{4}";

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
            if(keyCode == EditorInfo.IME_NULL) {

                //get the value from EditText and trim whitespaces
                String phoneNumber = textField.getText().toString().trim();
                Intent intent = getIntent();

                //pass the value of phone number using Intent Extras
                intent.putExtra("phoneNumber", phoneNumber);

                //check if phone number is in the right format
                if (validatePhoneNumber(phoneNumber)) {
                    setResult(RESULT_OK, intent);
                }
                else {
                    setResult(RESULT_CANCELED, intent);
                }
                //finish the activity
                finish();
            }
            return true;
        }
    };

    private static boolean validatePhoneNumber(String phoneNumber) {
        //regular expression used to test for correct format
        Pattern pattern = Pattern.compile(PHONE_NUMBER_FORMAT);
        Matcher match = pattern.matcher(phoneNumber);
        return match.matches();
    }
}
