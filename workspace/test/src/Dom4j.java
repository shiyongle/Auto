import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class Dom4j {

	/**
	 * @param args
	 * @throws DocumentException 
	 */
	public static void main(String[] args) throws DocumentException {
		// TODO Auto-generated method stub
		SAXReader reader = new SAXReader();  
		Document  document = reader.read(new File("D:/workspace/test/src/data.xml"));  
		Element rootElm = document.getRootElement();  
		Element root1Elm = rootElm.element("userlist");  
		List nodes = root1Elm.elements("item");  
		    for (Iterator it = nodes.iterator(); it.hasNext();) {  
		      Element elm = (Element) it.next();  
		      System.out.println("index:"+elm.attributeValue("index")+" level:"+elm.attributeValue("level")+" nickname:"+elm.attributeValue("nickname")+
		    		  " country:"+elm.attributeValue("country")+" weiwang:"+elm.attributeValue("weiwang"));       
		   } 
	}

}
