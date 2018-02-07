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
    private boolean dialNumber = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //1. Call super.onCreate()
        super.onCreate(savedInstanceState);

        //2. Set layout configuration for activity (call setContentView() on
        //        appropriate layout file)
        setContentView(R.layout.activity_main);

        //3. Get references to views, e.g., findViewById(R.id.viewName)
        // Bind the interface elements to the corresponding fields
        enterButton = (Button) findViewById(R.id.enterButton);
        dialButton = (Button) findViewById(R.id.dialButton);

        //4. Check saved instance state, act upon saved state if needed

        //5. Set up listeners for interactive views
        // Actual listeners are created below as instances of anonymous classes
        enterButton.setOnClickListener(enterListener);
        dialButton.setOnClickListener(dialListener);

        //6. Configure views and other initialization actions

    }

    // Listener for the "Enter Number" button
    public View.OnClickListener enterListener = new View.OnClickListener() {

        // Called when "Enter Number" button is clicked
        @Override
        public void onClick(View v) {
            //start second Activity
            //Explicit Intent
            Intent intent = new Intent(MainActivity.this, NumberEntryActivity.class) ;
            startActivityForResult(intent,1);
        }
    };

    protected void onActivityResult(int code, int result_code, Intent intent) {
        super.onActivityResult(code, result_code, intent);
        switch (code) {
            case 1:
                //get phoneNumber from Intent Extras
                phoneNumber = intent.getStringExtra("phoneNumber");
                if (result_code == RESULT_OK) {
                    dialNumber = true;
                }
                else if (result_code == RESULT_CANCELED) {
                    dialNumber = false;
                }
        }
    }

    public View.OnClickListener dialListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(dialNumber) {
                //implicit intent
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNumber));
                startActivity(intent);
            }
            else {
                Context context = getApplicationContext();
                String message = "Please enter a number first";
                if(phoneNumber != null) {
                    message = "The number " + phoneNumber+ " entered is incorrect. " +
                            "Please try again with the correct input format: (xxx) xxx-xxxx";
                }
                int duration = Toast.LENGTH_LONG;
                Toast.makeText(context, message, duration).show();
            }
        }
    };
}
