package com.example.madex01.dataprovider;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;

import com.example.madex01.Util.Weather;

import android.util.Log;


public class WeatherHelper {
	
	private String zip = "2459115";
	
	public WeatherHelper() {
		
	}

	public Weather getWeatherData() throws Exception {
		InputStream dataIn = retrieveWeather(zip);		
		return parseXml(dataIn);
	}
	
	private InputStream retrieveWeather(String zipcode) throws Exception {
    	Log.v("tag", "Retrieving weather data");
        String url = "http://weather.yahooapis.com/forecastrss?w=" + zipcode;
        URLConnection conn = new URL(url).openConnection();
        return conn.getInputStream();
    }
	
	private Weather parseXml(InputStream inputStream) throws DocumentException {
        SAXReader xmlReader = createXmlReader();
        Document doc = xmlReader.read(inputStream);
        Weather weather = new Weather();
        
        weather.setCity(doc.valueOf("/rss/channel/y:location/@city").toString());
        weather.setCountry(doc.valueOf("/rss/channel/y:location/@country").toString());
        weather.setCondition(doc.valueOf("/rss/channel/item/y:condition/@text").toString());
        weather.setTemp( doc.valueOf("/rss/channel/item/y:condition/@temp").toString());
        
        return weather;
	}
	
	  private SAXReader createXmlReader() {
	        Map<String,String> uris = new HashMap<String,String>();
	        uris.put("y", "http://xml.weather.yahoo.com/ns/rss/1.0");

	        DocumentFactory factory = new DocumentFactory();
	        factory.setXPathNamespaceURIs(uris);

	        SAXReader xmlReader = new SAXReader();
	        xmlReader.setDocumentFactory(factory);
	        return xmlReader;
	    }

}
