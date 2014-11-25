/**
 * 
 */
package common;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * @author mendz
 *	2014年10月20日
 */
public class Config {

	private static Logger log = LoggerFactory.getLogger(Config.class);
	private static HashMap<String, String> configMap=new HashMap<String, String>();
	/**
	 * 从缓存取配置
	 * @param xPath
	 * @param defaultValue
	 * @return
	 * @author mengdz
	 * 2014年10月23日
	 */
	public static String get(String xPath,String defaultValue) {
		String value=null;
		try{
			value=configMap.get(xPath);
			if(value==null){
				value=getNoCache(xPath, defaultValue);
	        	if(value!=null){
	        		configMap.put(xPath, value);
	        	}
			}
		}catch(Exception e){
			log.error("从缓存取配置出错",e);
		}
		return value;
	}
	/**
	 * 从配置文件取配置，不缓存
	 * @param xPath
	 * @param defaultValue
	 * @return
	 * @author mengdz
	 * 2014年10月23日
	 */
	public static String getNoCache(String xPath,String defaultValue) {
		String value=null;
		try{
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
//	        factory.setNamespaceAware(false);  
//	        DocumentBuilder builder = factory.newDocumentBuilder();  
//			String path=Config.class.getClassLoader().getResource("").getPath().substring(1)+"config/config.xml";				
//	        Document doc = builder.parse(path);		        
//	        XPathFactory xFactory = XPathFactory.newInstance();  
//	        XPath xpath = xFactory.newXPath();  
//	        XPathExpression expr = xpath  
//	                .compile(xPath);  
//	        Object result = expr.evaluate(doc, XPathConstants.NODESET);  
//	        NodeList nodes = (NodeList) result;    
//	        if(nodes.getLength()>0){		        	 
//	        	value=nodes.item(0).getNodeValue();		        	
//	        }
//	        if(value==null){
//	    		value=defaultValue;
//	    	}
			if("config/domain/text()".equals(xPath)){
//				return "wxgongan.duapp.com";
				return "www.weixin.com";
			}else if("config/superManager/text()".equals(xPath)){
				return "xusy,mengdz";
			}else if("config/uploadPicConfig/rootPicDrectoty/text()".equals(xPath)){
				return "uploadFile/uploadPic";
			}else{
				value=defaultValue;
			}
		}catch(Exception e){
			log.error("从配置文件取配置出错",e);
		}
		return value;
	}
	
//	public static void main(String[] args) {
//		System.out.println(get("config/uploadPicConfig/size/text()"));		
//	}
}
