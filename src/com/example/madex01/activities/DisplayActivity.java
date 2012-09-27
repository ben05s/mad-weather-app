package com.example.madex01.activities;


import com.example.madex01.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DisplayActivity extends Activity {
	private static final String TAG = "WeatherDisplay";

	public static final int REQUEST_CODE = 1;

	public static final String STRING_EXTRA_TEMPERATURE = "Temperature";
	public static final String STRING_EXTRA_TEMPUNIT = "TempUnit";
	public static final String STRING_EXTRA_CONDITION = "Condition";
	public static final String STRING_EXTRA_CITY = "City";

	private TextView mTvTemp;
	private TextView mTvCondition;
	private TextView mTvCity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);

		mTvTemp = (TextView) findViewById(R.id.weatherDisplay_tv_temperature);
		mTvCondition = (TextView) findViewById(R.id.weatherDisplay_tv_condition);    
		mTvCity = (TextView) findViewById(R.id.weatherDisplay_tv_city);

		Button btnChoose = (Button) findViewById(R.id.weatherDisplay_btn_obtainActivity_call);
		btnChoose.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ObtainerActivity.class);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {    	
		//super.onActivityResult(requestCode, resultCode, data);

		if (requestCode != REQUEST_CODE) {
			Log.d(TAG, "request code " + requestCode + " is not handled here");
			return;
		}

		if (resultCode == RESULT_OK) {
			// get data from intent
			Bundle bundle = new Bundle(data.getExtras());
			String temp = bundle.getString(STRING_EXTRA_TEMPERATURE);
			String condition = bundle.getString(STRING_EXTRA_CONDITION);
			String city = bundle.getString(STRING_EXTRA_CITY);
			String tempunit = bundle.getString(STRING_EXTRA_TEMPUNIT);
			
			// set data to UI views
			mTvTemp.setText(getString(R.string.title_view_tv_temperature) + " " + temp + " " + tempunit);
			mTvCondition.setText(getString(R.string.title_view_tv_condition) + " " + condition);
			mTvCity.setText(getString(R.string.title_view_tv_city) + " " + city);
		}	
	}
}
