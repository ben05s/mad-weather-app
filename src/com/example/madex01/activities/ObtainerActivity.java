package com.example.madex01.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.madex01.R;

public class ObtainerActivity extends Activity {

	private MyAsyncTask myAsyncTask = null;
	private ProgressBar progressBar = null;
	private String city = null;
	private String country = null;
	private String asyncResult = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.obtainer_activity);
		
		myAsyncTask = new MyAsyncTask();
		
		/*
		 * Set up UI components
		 */
		
		//auto-complete text view for countries
		String[] countries = getResources().getStringArray(R.array.countries_array);
		AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.autocomplete_country);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, countries);
		autoComplete.setAdapter(adapter);
	
		//progress bar
		setProgressBar((ProgressBar) findViewById(R.id.progressBar));
		
		EditText editCity = (EditText) findViewById(R.id.edit_city);
		city = editCity.getText().toString();

		country = autoComplete.getText().toString();
		
		Button weatherButton = (Button) findViewById(R.id.button);
		weatherButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(v.getId() == R.id.button) {
					Log.d("System.out", "Search button klicked. Performing weather search.");
					Log.d("System.out", "Search Params: city: " + city + " | country: " + country);
					String[] params = new String[] { city, country };
					myAsyncTask.execute(params);
				}		
			}
		});
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public String getAsyncResult() {
		return asyncResult;
	}

	public void setAsyncResult(String asyncResult) {
		this.asyncResult = asyncResult;
	}
}
