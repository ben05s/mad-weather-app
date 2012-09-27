package com.example.madex01.dataprovider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.example.madex01.Util.Weather;


public class WeatherHelper {
	
	private String zip = "2459115";
	private static final String TAG = "WeatherHelper";
	private String city;
	private String country;
	
	public WeatherHelper() {
		
	}

//	public Weather getWeatherData() throws Exception {
//		InputStream dataIn = retrieveWeather(zip);		
//		return parseXml(dataIn);
//	}
	
//	private InputStream retrieveWeather(String zipcode) throws Exception {
//    	Log.v("tag", "Retrieving weather data");
//        String url = "http://weather.yahooapis.com/forecastrss?w=" + zipcode;
//        URLConnection conn = new URL(url).openConnection();
//        return conn.getInputStream();
//    }
//	
//	private Weather parseXml(InputStream inputStream) throws DocumentException {
//        SAXReader xmlReader = createXmlReader();
//        Document doc = xmlReader.read(inputStream);
//        Weather weather = new Weather();
//        
//        weather.setCity(doc.valueOf("/rss/channel/y:location/@city").toString());
//        weather.setCountry(doc.valueOf("/rss/channel/y:location/@country").toString());
//        weather.setCondition(doc.valueOf("/rss/channel/item/y:condition/@text").toString());
//        weather.setTemp( doc.valueOf("/rss/channel/item/y:condition/@temp").toString());
//        
//        return weather;
//	}
//	
//	private SAXReader createXmlReader() {
//	      Map<String,String> uris = new HashMap<String,String>();
//	      uris.put("y", "http://xml.weather.yahoo.com/ns/rss/1.0");
//
//	      DocumentFactory factory = new DocumentFactory();
//	      factory.setXPathNamespaceURIs(uris);
//
//	      SAXReader xmlReader = new SAXReader();
//	      xmlReader.setDocumentFactory(factory);
//	      return xmlReader;
//	  }
	  
	  public Weather requestWeather(String country, String city) {
		    this.city = city;
		    this.country = country;
			String url = "http://query.yahooapis.com/v1/public/yql?q=select%20item.condition.text%2C%20item.condition.temp%2C%20units.temperature%20from%20weather.forecast%20where%20location%20in%20(%0A%20%20select%20id%20from%20weather.search%20where%20query%3D%22" +
					city + "%2C%20" + country +
					"%22%0A)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=cbfunc";

			HttpEntity entity = null;
			try {
				entity = getHttpEntity(url);
			} catch (ClientProtocolException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			if (entity != null) {
				// a simple JSON response read
				InputStream in = null;
				try {
					in = entity.getContent();
				} catch (IllegalStateException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				BufferedReader bReader = new BufferedReader(new InputStreamReader(in));
				StringBuilder sb = new StringBuilder();
					
				try {
					String line;
					while ((line = bReader.readLine()) != null) {
						sb.append(line + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						bReader.close();
					}catch (IOException e) {
						e.printStackTrace();
					}
				}
					
				String jsonString = sb.substring(7, sb.length() - 3);
					
				Log.i(TAG, "RESULT: " + sb.toString() + "\n result: " +  jsonString);

				Weather weather = null;
				try {
					weather = getWeatherObjectFromJsonResponse(jsonString);
				} catch (JSONException e) {
					Log.e(TAG, "json exception");
					e.printStackTrace();
				}
				return weather;
			}
			return null;
	  }

	  private HttpEntity getHttpEntity(String url) throws ClientProtocolException, IOException {
		  HttpClient httpclient = new DefaultHttpClient();

			// prepare a request object
			HttpGet httpget = new HttpGet(url);

			// execute the request
			HttpResponse response = httpclient.execute(httpget);
				
			// examine the response status
			Log.i(TAG, response.getStatusLine().toString());

			
			// if the response does not enclose an entity, there is no ned
			// to worry about the connection release
			// get hold of the response entity
			return response.getEntity();
	  }
	  
	  private Weather getWeatherObjectFromJsonResponse(String jsonString) throws JSONException {
		  JSONObject obj = new JSONObject(jsonString);
			
		  JSONObject channel = obj.getJSONObject("query").getJSONObject("results").getJSONObject("channel");
				
		  JSONObject condition = channel.getJSONObject("item").getJSONObject("condition");	
				
		  JSONObject units = channel.getJSONObject("units");
													
		  long temp = condition.getLong("temp");
		  String txt = condition.getString("text");
		  String tempUnit = units.getString("temperature");	
		  String temperatur = String.valueOf(temp);
		  Log.i(TAG, "JSON RES: " + temp + tempUnit + " "  + txt );
			
		  return new Weather(city, country, temperatur, tempUnit, txt);
	  }
}
