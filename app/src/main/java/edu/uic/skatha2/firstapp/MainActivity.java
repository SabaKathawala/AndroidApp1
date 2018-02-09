package edu.uic.skatha2.firstapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    //GUI Widgets
    protected Button enterButton;
    protected Button dialButton;
    private String phoneNumber;
    //flag to check if "Dial Number" should work
    private boolean isPhoneNumberValid = false;

    private static final String URI_TEL_SCHEME = "tel:";
    private static final int REQUEST_CODE_PHONE_NUMBER = 101;
    private static final String EXTRA_PARAM_PHONE_NUMBER = "extra_param_phone_number";
    private static final String SAVED_INSTANCE_PHONE_NUMBER = "phone_number";
    private static final String SAVED_INSTANCE_IS_NUMBER_VALID = "is_phone_number_present";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //1. Call super.onCreate()
        super.onCreate(savedInstanceState);

        //2. Set layout configuration for activity
        setContentView(R.layout.activity_main);

        //3. Get references to views, e.g., findViewById(R.id.viewName)
        // Bind the interface elements to the corresponding fields
        enterButton = (Button) findViewById(R.id.enterButton);
        dialButton = (Button) findViewById(R.id.dialButton);

        //4. Check saved instance state, act upon saved state if needed
        // Are we running from scratch?  No, get the phoneNumber
        if (savedInstanceState != null) {
            phoneNumber = savedInstanceState.getString(SAVED_INSTANCE_PHONE_NUMBER);
            isPhoneNumberValid = savedInstanceState.getBoolean(SAVED_INSTANCE_IS_NUMBER_VALID);
        }

        //5. Set up listeners for interactive views
        // Actual listeners are created below as instances of anonymous classes
        enterButton.setOnClickListener(enterButtonListener);
        dialButton.setOnClickListener(dialButtonListener);

        //6. Configure views and other initialization actions

    }

    // Listener for the "Enter Number" butto n
    public View.OnClickListener enterButtonListener = new View.OnClickListener() {

        // Called when "Enter Number" button is clicked
        @Override
        public void onClick(View v) {
            //start second Activity
            //Explicit Intent
            Intent intent = new Intent(MainActivity.this, NumberEntryActivity.class) ;
            startActivityForResult(intent,REQUEST_CODE_PHONE_NUMBER);
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //get phoneNumber from Intent Extras
        phoneNumber = intent.getStringExtra(EXTRA_PARAM_PHONE_NUMBER);
        //to decide what to display on click of "Dial"
        isPhoneNumberValid = resultCode == RESULT_OK;
    }

    public View.OnClickListener dialButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(isPhoneNumberValid) {
                //implicit intent
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(URI_TEL_SCHEME + phoneNumber));
                startActivity(intent);
            }
            else {
                String message = "Please enter a number first";
                if(phoneNumber != null) {
                    message = "The number " + phoneNumber+ " entered is incorrect. " +
                            "Please try again with the correct input format: (xxx) xxx-xxxx";
                }
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    };

    // This will be called when the app loses focus, configuration changes;
    // save current state
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Always do this
        super.onSaveInstanceState(outState)  ;
        // Save the phone number
        outState.putString(SAVED_INSTANCE_PHONE_NUMBER, phoneNumber);
        outState.putBoolean(SAVED_INSTANCE_IS_NUMBER_VALID, isPhoneNumberValid);
    }
}
