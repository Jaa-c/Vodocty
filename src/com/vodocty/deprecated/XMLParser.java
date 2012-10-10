package com.vodocty.deprecated;

import android.util.Log;
import android.util.Xml;
import com.vodocty.data.LG;
import com.vodocty.data.River;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XMLParser {
    
    private static final String ns = null;
    
    public static List<River> parse(InputStream in) throws IOException, XmlPullParserException {
	
	/*InputStreamReader is = new InputStreamReader(in);
	StringBuilder sb=new StringBuilder();
	BufferedReader br = new BufferedReader(is);
	String read;
	while((read = br.readLine()) != null) {
	    sb.append(read);
	    Log.d("all", read);
	}
	Log.d("all", sb.toString().length() + "");*/

	XmlPullParser parser = getParser(in);
	List<River> rivers = new ArrayList<River>();
	River current;

	parser.require(XmlPullParser.START_TAG, ns, "data");
	while (parser.next() != XmlPullParser.END_TAG) {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
		continue;
	    }
	    String name = parser.getName();
	    Log.d("river", parser.getName());
	    // Starts by looking for the entry tag
	    if (name.equals("river")) {
		current = new River(parser.getAttributeValue(0));
		current.addAll(readLG(parser));
	    } else {
		skip(parser);
	    }
	}
	in.close();
	return rivers;
    }
    
    private static List<LG> readLG(XmlPullParser parser) throws XmlPullParserException, IOException {
	List<LG> data = new ArrayList<LG>();
	LG current;
	while (parser.next() != XmlPullParser.END_TAG) {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
		continue;
	    }
	    String name = parser.getName();
	    if(name.equals("lg")) {
		current = new LG(parser.getAttributeValue(0));
		Log.d("lg", parser.getAttributeValue(0));
		while (parser.next() != XmlPullParser.END_TAG) {
		    if (parser.getEventType() != XmlPullParser.START_TAG) {
			continue;
		    }
		    name = parser.getName();
		    if(name.equals("date")) {
			try {
			    current.setDate(readText(parser));
			}
			catch(Exception e) {
			    current.setDate(new Date());
			}
			Log.d("lg date", current.getDate() + "");
		    }
		    else if(name.equals("height")) {
			try {
			    current.setHeight(Integer.parseInt(readText(parser)));
			}
			catch(NumberFormatException e) {
			    current.setHeight(-1);
			}
			Log.d("lg height", current.getHeight() + "");		
		    }
		    else if(name.equals("volume")) {
			try {
			    current.setVolume(Float.parseFloat(readText(parser)));
			}
			catch(NumberFormatException e) {
			    current.setVolume(-1);
			}
			Log.d("lg volume", current.getVolume() + "");	
		    }
		    else if(name.equals("flood")) {
			try {
			    current.setFlood(Integer.parseInt(readText(parser)));
			}
			catch(NumberFormatException e) {
			    current.setFlood(0);
			}
			Log.d("lg flood", current.getFlood() + "");
		    }
		    else {
			skip(parser);
		    }
		}
		Log.d("lg end while", parser.getDepth() + "");
	    }
	    else {
		skip(parser);
	    }
	}
	
	Log.d("lg", "return z readLG");
	return data;
    }
    
    private static XmlPullParser getParser(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
	    return parser;
        } finally {
            in.close();
        }
    }
    
    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	if (parser.getEventType() != XmlPullParser.START_TAG) {
	    throw new IllegalStateException();
	}
	int depth = 1;
	while (depth != 0) {
	    switch (parser.next()) {
	    case XmlPullParser.END_TAG:
		depth--;
		break;
	    case XmlPullParser.START_TAG:
		depth++;
		break;
	    }
	}
     }
    
    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
	String result = "";
	if (parser.next() == XmlPullParser.TEXT) {
	    result = parser.getText();
	    parser.nextTag();
	}
	return result;
    }
    
}
