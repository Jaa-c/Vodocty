package com.vodocty.update;

import android.util.Log;
import com.vodocty.data.Country;
import com.vodocty.data.Data;
import com.vodocty.data.LG;
import com.vodocty.data.River;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Parser for the update data.
 * 
 * @author Dan Princ
 * @since long time ago
 */
public class XMLParser {
    
    public static List<River> parse(InputStream in, Country c) throws ParserConfigurationException, IOException, SAXException {
    
	List<River> rivers = new ArrayList<River>();
	River currentRiver;
	LG currentLG;
	Data data;
	
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	DocumentBuilder db = dbf.newDocumentBuilder();
	Document doc = db.parse(in);
	doc.getDocumentElement().normalize();
	
	NodeList riverList = doc.getElementsByTagName("river");
	for(int i = 0; i < riverList.getLength(); i++) {
	    Node river = riverList.item(i);
	    currentRiver = new River(river.getAttributes().item(0).getTextContent(), c);
	    Element el = (Element) river;
	    NodeList lgs = el.getElementsByTagName("lg");
	    
	    
	    for(int j = 0; j < lgs.getLength(); j++) {
		Node lg = lgs.item(j);
		currentLG = new LG(lg.getAttributes().item(0).getTextContent());
		
		
		NodeList current = lg.getChildNodes();		
		data = new Data(currentLG);
		data.setDate(current.item(1).getTextContent());
		
		try {
		    data.setHeight(Integer.parseInt(current.item(3).getTextContent()));
		}
		catch(NumberFormatException e) {
		    data.setHeight(-1);
		}
		
		try {
		    data.setVolume(Float.parseFloat(current.item(5).getTextContent()));
		}
		catch(NumberFormatException e) {
		    data.setVolume(-1);
		}
		
		try {
		    data.setFlood(Integer.parseInt(current.item(7).getTextContent()));
		}
		catch(Exception e) {
		    data.setFlood(0);
		}
		
		currentLG.setData(data);
		if(data.getHeight() > 0) {
		    currentLG.setCurrentHeight(data.getHeight());
		}
		if(data.getVolume() > 0) {
		    currentLG.setCurrentVolume(data.getVolume());
		}
		
		currentLG.setCurrentFlood(data.getFlood());
		//currentLG.setRiver(currentRiver);
		currentRiver.add(currentLG);
	    }
	    rivers.add(currentRiver);
	}
	
	    
	return rivers;
    }

}
