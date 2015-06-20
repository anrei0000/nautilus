package com.anrei0000.robot_industrial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


/**
 * This class should manage passing from activity to activity
 * @author Ciuc
 *
 */
public class P_Manager extends Activity{
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
//	        setContentView(R.layout.layout_manager);
	        
	        Intent intent = getIntent();
	        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
	        
	        TextView textView = new TextView(this);
	        textView.setTextSize(40);
	        textView.setText(message);
	        
	        
//	        if (savedInstanceState == null) {
//	            getSupportFragmentManager().beginTransaction()
//	                .add(R.id.container, new PlaceholderFragment()).commit();
//	        }
	    }

}
