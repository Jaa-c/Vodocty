package com.vodocty.update;

import com.vodocty.data.Country;
import com.vodocty.data.Data;
import com.vodocty.data.LG;
import com.vodocty.data.River;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    public static Map<String, River> parse(InputStream in, Country c, Map<String, River> rivers, int updateTime) throws ParserConfigurationException, IOException, SAXException {
    
	//List<River> rivers = new ArrayList<River>();
	if(rivers == null) {
	    rivers = new HashMap<String, River>();
	}
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
	    String name = river.getAttributes().item(0).getTextContent();
	    
	    if(rivers.containsKey(name)) {
		currentRiver = rivers.get(name);
	    }
	    else {
		currentRiver = new River(name, c);
	    }
	    
	    Element el = (Element) river;
	    NodeList lgs = el.getElementsByTagName("lg");
	    
	    for(int j = 0; j < lgs.getLength(); j++) {
		Node lg = lgs.item(j);
		String lgName = lg.getAttributes().item(0).getTextContent();
		
		if(currentRiver.getLg().containsKey(lgName)) {
		    currentLG = currentRiver.getLg().get(lgName);
		    currentLG.setData(null);
		}
		else {
		    currentLG = new LG(lgName);
		    currentRiver.add(currentLG);
		}
		
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
	    }
	    
	    currentRiver.setLastUpdate(updateTime);
	    
	    if(currentRiver.getId() == -1) {//put new into map
		rivers.put(currentRiver.getName(), currentRiver);
	    }
	}
	
	    
	return rivers;
    }

}
