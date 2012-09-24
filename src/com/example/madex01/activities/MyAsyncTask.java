package com.example.madex01.activities;

import com.example.madex01.Util.MyConstants;
import com.example.madex01.dataprovider.WeatherHelper;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

public class MyAsyncTask extends AsyncTask<String, Integer, String> {

	private ObtainerActivity oa = null;
	private WeatherHelper wh = null;
	private ProgressBar progressBar = null;
	private static final int progr[] = {50, 40, 10};
	private int index = 0;
	
	@Override
	public void onPreExecute() {
		oa = new ObtainerActivity();
		progressBar = oa.getProgressBar();
		wh = new WeatherHelper();
	}

	@Override
	protected String doInBackground(String... params) {
		String city = params[MyConstants.CITY_ARRAY_INDEX];
		String country = params[MyConstants.COUNTRY_ARRAY_INDEX];
		
		//TODO: translate city and country data into id to search for weather
		/*
		 * Obtain the condition and temperature
		 */
		try {
			wh.getWeatherData();
		} catch (Exception e) {
			Log.e("tag", "error getting weather data.");
			e.printStackTrace();
		}
		publishProgress();
		
		/*
		 * format result String
		 */
		publishProgress();
		
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		progressBar.incrementProgressBy(progr[index]);
		index++;
	}
	
	@Override
	protected void onPostExecute(String result) {
		oa.setAsyncResult(result);
		oa.finish();
	}
}
