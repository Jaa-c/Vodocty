package com.vodocty.update;

import android.util.Log;
import com.vodocty.data.Country;
import com.vodocty.data.Data;
import com.vodocty.data.LG;
import com.vodocty.data.River;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Dan Princ
 * @since 14.8.2013
 */
public class SAXXMLParser extends DefaultHandler {
	
	private River currentRiver;
	private LG currentLG;
	private Data data;
	private String temp;

	private Country c;
	private Map<String, River> rivers;
	private int updateTime;
	
	public SAXXMLParser(Country c, Map<String, River> r, int updateTime) {
		this.rivers = r;
		if (this.rivers == null) {
			this.rivers = new HashMap<String, River>();
		}
		this.c = c;
		this.updateTime = updateTime;
	}
	
	public Map<String, River> parse(InputStream in) throws ParserConfigurationException, IOException, SAXException {
		
		SAXParserFactory spfac = SAXParserFactory.newInstance();
		SAXParser sp = spfac.newSAXParser();
		sp.parse(in, this);
		
		return rivers;
	}
	
	/*
	 * When the parser encounters plain text (not XML elements),
	 * it calls(this method, which accumulates them in a string buffer
	 */
	@Override
   public void characters(char[] buffer, int start, int length) {
		  temp = new String(buffer, start, length);
   }
      

	/*
	 * Every time the parser encounters the beginning of a new element,
	 * it calls this method, which resets the string buffer
	 */ 
	@Override
	public void startElement(String uri, String localName,
				  String tag, Attributes attributes) throws SAXException {
				  
		if (tag.equals("river")) {
			String name = attributes.getValue("name");
			if (rivers.containsKey(name)) {
				currentRiver = rivers.get(name);
			} else {
				currentRiver = new River(name, c);
			}
		} else if(tag.equals("lg")) {
			String name = attributes.getValue("name");
			//check date here TODO - get rid of db errors

			if (currentRiver.getLg().containsKey(name)) {
				currentLG = currentRiver.getLg().get(name);
				currentLG.setData(null);
			} else {
				currentLG = new LG(name);
				currentRiver.add(currentLG);
			}
			
			data = new Data(currentLG);
		}
	}

	/*
	 * When the parser encounters the end of an element, it calls this method
	 */
	@Override
	public void endElement(String uri, String localName, String tag)
				  throws SAXException {

		if (tag.equals("river")) {
			if(currentRiver.getName().length() == 0) {
				return;
			}
			currentRiver.setLastUpdate(updateTime);
			if (currentRiver.getId() == -1) {//put new into map
				rivers.put(currentRiver.getName(), currentRiver);
			}
//			Log.d(this.getClass().getName(), currentRiver.toString());
		} else if(tag.equals("lg")) {
			currentLG.setData(data);
			if (data.getHeight() > 0) {
				currentLG.setCurrentHeight(data.getHeight());
			}
			if (data.getVolume() > 0) {
				currentLG.setCurrentVolume(data.getVolume());
			}

			currentLG.setCurrentFlood(data.getFlood());
			
//			Log.i(this.getClass().getName(), currentLG.getData().toString());
//			Log.i(this.getClass().getName(), currentLG.toString());
			
		} else if(tag.equals("date")) {
			try {
				data.setDate(Integer.parseInt(temp));
			} catch (NumberFormatException e) {
				data.setDate(temp); //transition from old data, to be removed
			}
		} else if(tag.equals("height")) {
			try {
				data.setHeight(Integer.parseInt(temp));
			} catch (NumberFormatException e) {
				data.setHeight(-1);
			}
		} else if(tag.equals("volume")) {
			try {
				data.setVolume(Float.parseFloat(temp));
			} catch (NumberFormatException e) {
				data.setVolume(-1);
			}
		} else if(tag.equals("flood")) {
			try {
				data.setFlood(Integer.parseInt(temp));
			} catch (Exception e) {
				data.setFlood(0);
			}
		}

	}
}
