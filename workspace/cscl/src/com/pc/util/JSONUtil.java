package com.pc.util;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;


public class JSONUtil {
	
	public static String getJson(Object o)  {
		 if(o instanceof String){
			  o=(o==null)?"":o;
		 }
		ObjectMapper om = new ObjectMapper();
		StringWriter sw = new StringWriter();
		JsonGenerator jg;
		try {
			jg = new JsonFactory().createJsonGenerator(sw);
			om.writeValue(jg, o);
			jg.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}		
 		return sw.toString();
 		//		.replace("null", "\"\"") ;
 	}

	public static String getSortStr(String sort,String order){
		if(sort == null || order == null){
			return null;
		}else{
			String[] sorts = sort.split(",");
			String[] orders = order.split(",");
			StringBuffer sortSB = new StringBuffer("");
			for(int i=0;i<sorts.length;i++){
				sortSB.append(sorts[i]).append(" ").append(orders[i]);
				if(i!= sorts.length-1){
					sortSB.append(",");
				}
			}
			return sortSB.toString();
		}
	}
	
	/**
	 * @author zzm
	 * @return get ordernum
	 * @throws Exception
	 */
	public static String generateSequenceID(){
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
	   String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 5);
       String ranEight=sdf.format(new Date());
       return ranEight+uuid;
	}
}
