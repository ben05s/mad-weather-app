package com.example.madex01.activities;

import android.app.Activity;
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
	private static final int progr[] = {50, 40, 10};
	private int index = 0;


	public MyAsyncTask(ObtainerActivity hostOA) {
		oa = hostOA;
	}

	@Override
	public void onPreExecute() {

		progressBar = oa.getProgressBar();
		wh = new WeatherHelper();
 	}

	@Override
	protected Weather doInBackground(String... params) {
		String city = params[MyConstants.CITY_ARRAY_INDEX];
		String country = params[MyConstants.COUNTRY_ARRAY_INDEX];

		Weather weather = null;
		try {
			weather = wh.requestWeather(country, city);
		} catch (Exception e) {
			Log.e(TAG, "error getting weather data.");
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

		if (result != null) {
			Log.d(TAG, "Result: " + result.toString());
			
			// fill intent with data and send intent
			Intent intent = oa.prepareIntent(result);

			// set result
			oa.setResult(Activity.RESULT_OK, intent);
		}else {
			Log.e(TAG, "Result: null");
			
			oa.setResult(-1);
		}

		// force finish
		oa.finish();	
	}


}
