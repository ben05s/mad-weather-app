package com.example.madex01.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.madex01.Util.MyConstants;
import com.example.madex01.Util.Weather;
import com.example.madex01.dataprovider.WeatherHelper;

public class MyAsyncTask extends AsyncTask<String, Integer, Weather> {
	private static final String TAG = "MyAsyncTask";
	
	private ObtainerActivity oa = null;
	private WeatherHelper wh = null;
	private ProgressBar progressBar = null;
	private ProgressDialog progressDialog;
	private static final int progr[] = {50, 40, 10};
	private int index = 0;
	
	@Override
	public void onPreExecute() {
		oa = new ObtainerActivity();
		progressBar = oa.getProgressBar();
		wh = new WeatherHelper();
		
		//progressDialog = ProgressDialog.show(ObtainerActivity.this, "WeatherAsyncTask", "Waiting for Results");
	}

	@Override
	protected Weather doInBackground(String... params) {
		String city = params[MyConstants.CITY_ARRAY_INDEX];
		String country = params[MyConstants.COUNTRY_ARRAY_INDEX];

		Weather weather = null;
		try {
			weather = wh.requestWeather(country, city);
		} catch (Exception e) {
			Log.e("tag", "error getting weather data.");
			e.printStackTrace();
		}
		publishProgress();
		publishProgress();
		
		return weather;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		progressBar.incrementProgressBy(progr[index]);
		index++;
	}
	
	@Override
	protected void onPostExecute(Weather result) {
		progressDialog.dismiss();			
		Log.d(TAG, "Result: " + result.toString());

		// fill intent with data and send intent
		Intent intent = oa.prepareIntent(result);

		// set result
		oa.setResult(Activity.RESULT_OK, intent);

		// force finish
		oa.finish();	
	}
}
