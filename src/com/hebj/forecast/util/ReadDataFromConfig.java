package com.hebj.forecast.util;

import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;












public class ReadDataFromConfig {

	public static String getValue(String name)  {
		SAXBuilder sb = new SAXBuilder();
		Document doc = null;
		try {
			doc = sb.build("config/config.xml");
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		} 
		Element root = doc.getRootElement();
		List list = root.getChildren();
		
		for (int j = 0; j < list.size(); j++) {
			if (((Element) list.get(j)).getChildText("name").equals(name)) {
				return ((Element) list.get(j)).getChildText("value");
			}
        }
		return null;

	}
}
