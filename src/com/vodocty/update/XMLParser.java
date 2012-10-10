package com.vodocty.update;

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

public class XMLParser {
    
    public static List<River> parse(InputStream in) throws ParserConfigurationException, IOException, SAXException {
    
	List<River> rivers = new ArrayList<River>();
	River currentRiver;
	LG currentLG;
	
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	DocumentBuilder db = dbf.newDocumentBuilder();
	Document doc = db.parse(in);
	doc.getDocumentElement().normalize();
	
	NodeList riverList = doc.getElementsByTagName("river");
	for(int i = 0; i < riverList.getLength(); i++) {
	    Node river = riverList.item(i);
	    currentRiver = new River(river.getAttributes().item(0).getTextContent());
	    Element el = (Element) river;
	    NodeList lgs = el.getElementsByTagName("lg");
	    for(int j = 0; j < lgs.getLength(); j++) {
		Node lg = lgs.item(j);
		currentLG = new LG(lg.getAttributes().item(0).getTextContent());
		NodeList data = lg.getChildNodes();
		currentLG.setDate(data.item(0).getTextContent());
		try {
		    currentLG.setHeight(Integer.parseInt(data.item(1).getTextContent()));
		}
		catch(NumberFormatException e) {
		    currentLG.setHeight(-1);
		}
		try {
		    currentLG.setVolume(Float.parseFloat(data.item(2).getTextContent()));
		}
		catch(NumberFormatException e) {
		    currentLG.setVolume(-1);
		}
		try {
		    currentLG.setFlood(Integer.parseInt(data.item(3).getTextContent()));
		}
		catch(NumberFormatException e) {
		    currentLG.setFlood(0);
		}
		currentRiver.add(currentLG);
	    }
	    rivers.add(currentRiver);
	}
	
	return rivers;
    }

}
