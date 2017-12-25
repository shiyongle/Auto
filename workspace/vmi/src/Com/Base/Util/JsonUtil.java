package Com.Base.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

public class JsonUtil {

	/**
	 * 将unicode字符串转中文
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {
	    char aChar;      
	    int len = theString.length();      
	    StringBuffer outBuffer = new StringBuffer(len);      
	    for (int x = 0; x < len;) {      
	    	aChar = theString.charAt(x++);      
	    	if (aChar == '\\') {      
	    		aChar = theString.charAt(x++);      
	   
	      if (aChar == 'u') {      
	       int value = 0;      
	       for (int i = 0; i < 4; i++) {      
	        aChar = theString.charAt(x++);      
	        switch (aChar) {      
		        case '0':      
		        case '1':      
		        case '2':      
		        case '3':      
		        case '4':      
		        case '5':      
		        case '6':      
		        case '7':      
		        case '8':      
		        case '9':      
		           value = (value << 4) + aChar - '0';      
		        break;      
	          case 'a':      
	          case 'b':      
	          case 'c':      
	          case 'd':      
	          case 'e':      
	          case 'f':      
	           value = (value << 4) + 10 + aChar - 'a';      
	          break;      
	          case 'A':      
	          case 'B':      
	          case 'C':      
	          case 'D':      
	          case 'E':      
	          case 'F':      
	           value = (value << 4) + 10 + aChar - 'A';      
	          break;      
	          default:      
	           throw new IllegalArgumentException(   
	             "Malformed   \\uxxxx   encoding.");      
	          }      
	        }      
	         outBuffer.append((char) value);      
	        } else {      
	         if (aChar == 't')      
	          aChar = '\t';      
	         else if (aChar == 'r')      
	          aChar = '\r';      
	         else if (aChar == 'n')      
	          aChar = '\n';      
	         else if (aChar == 'f')      
	          aChar = '\f';      
	         outBuffer.append(aChar);      
	        }      
	       }else     
	       outBuffer.append(aChar);      
	      }      
	      return outBuffer.toString();      
	}     
	public static String toFirstLetterUpperCase(String str) {
		if (str == null || str.length() < 2) {
			return str;
		}
		String firstLetter = str.substring(0, 1).toUpperCase();
		return firstLetter + str.substring(1, str.length());
	}

	public static String result(boolean success, String msg, ListResult l) {
		String result = "{\"success\":" + success;
		if (!l.getTotal().isEmpty()) {
			result = result + ",\"total\":" + l.getTotal() + "";
		}
		if (!msg.isEmpty()) {
			result = result + ",\"msg\":\"" + msg + "\"";
		}
		if (!l.getData().isEmpty()) {
			result = result + ",\"data\":[" + List2Json(l.getData()) + "]";
		}

		return result + "}";
	}

	public static String result(boolean success, String msg, String total,
			String data) {
		String result = "{\"success\":" + success;
		if (!total.isEmpty()) {
			result = result + ",\"total\":" + total + "";
		}
		if (!msg.isEmpty()) {
			result = result + ",\"msg\":\""
					+ msg.replace("'", "").replace("\"", "") + "\"";
		}
		if (!data.isEmpty()) {
			result = result + ",\"data\":" + data + "";
		}

		return result + "}";
	}

	public static String result(boolean success, String msg, String total,
			List<HashMap<String, Object>> data) {
		String result = "{\"success\":" + success;
		if (!total.isEmpty()) {
			result = result + ",\"total\":" + total + "";
		}
		if (!msg.isEmpty()) {
			result = result + ",\"msg\":\"" + msg + "\"";
		}
		if (!data.isEmpty()) {
			result = result + ",\"data\":[" + List2Json(data) + "]";
		}

		return result + "}";
	}
	
	public static String resultIO(boolean success, String msg, String total,
			List<HashMap<String, Object>> data) {
		String result = "{\"success\":" + success;
		if (!total.isEmpty()) {
			result = result + ",\"total\":" + total + "";
		}
		if (!msg.isEmpty()) {
			result = result + ",\"msg\":\"" + msg + "\"";
		}
		if (!data.isEmpty()) {
			result = result + ",\"dataList\":[" + List2Json(data) + "]";
		}

		return result + "}";
	}

	public static String List2Json(List<HashMap<String, Object>> slist) {
		String result = "";
		if (slist.size() < 1) {
			return "";
		}
		int index = 0;
		String val;
		Iterator<String> it;
		HashMap<String, Object> h = slist.get(0);
		Set<String> set = h.keySet();
		for (HashMap<String, Object> o : slist) {
			it = set.iterator();
			if (index > 0) {
				result = result + ",";
			}
			result = result + "{";
			int keyindex = 0;
			while (it.hasNext()) {
				if (keyindex > 0) {
					result = result + ",";
				}
				String key = it.next();
				val = o.get(key)!=null?o.get(key).toString().replace("\t", "").replace("\r\n", "<br/>").replace("\r", "<br/>").replace("\n", "<br/>").replace("\\", "\\\\").replace("\"", "\\\""):"";
				result = result + "\"" + key + "\"" + ":\"" + val + "\"";
				keyindex++;
			}
			result = result + "}";
			index++;
		}
		return result;
	}

	/**
	 * 改版，用StringBuilder，估计能加快不少速度，另外，能避免过多的内存占用
	 * 
	 * @param objs
	 * @return,和老方法统一，格式是伪json的“，”号分隔的json对象串
	 * 
	 * @date 2014-6-22 下午3:14:42 (ZJZ)
	 */
	public static String transformListToJson(List<HashMap<String, Object>> objs) {
		StringBuilder result = new StringBuilder("");

		if (objs == null || objs.size() == 0) {
			return "";
		}

		boolean isFirstObj = true;

		for (HashMap<String, Object> obj : objs) {

			if (!isFirstObj) {

				result.append(",");

			} else {

				isFirstObj = false;

			}

			boolean isFirstKey = true;
			result.append("{");
			for (String key : obj.keySet()) {

				if (!isFirstKey) {
					result.append(",");
				} else {
					isFirstKey = false;
				}

				result.append("\"");
				result.append(key);
				result.append("\"");
				result.append(":");
				result.append("\"");
				
				String val = obj.get(key) != null ? obj.get(key).toString()
						.replace("\r\n", "<br/>").replace("\r", "<br/>")
						.replace("\n", "<br/>").replace("\\", "\\\\")
						.replace("\"", "\\\"") : "";

				result.append(val);
				
				
				
				result.append("\"");
			}
			result.append("}");

		}
		return result.toString();
	}

	
	public static Object jsToObject(String objson, String classname) {
		Object o = null;
		try {
			JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpherEx(new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd't'HH:mm:ss","yyyy-MM-dd" }, (Date) null));
			JSONObject jsonobject = JSONObject.fromObject(objson);
			Iterator it = jsonobject.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (jsonobject.get(key) instanceof JSONArray) {
					it.remove();
					// jsonobject.remove(key);
				}
			}
			o = JSONObject.toBean(jsonobject, Class.forName(classname));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	public static List jsTolist(String mtjson, String classname) {
		List list = new ArrayList();
		try {
			if (mtjson == null || mtjson.equals("")) {
				return list;
			} else {
				JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpherEx(new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd't'HH:mm:ss","yyyy-MM-dd" }, (Date) null));
				JSONArray jsa = JSONArray.fromObject(mtjson);

				list = (List) JSONArray.toCollection(jsa,
						Class.forName(classname));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String result(boolean success, String msg, String total,
			ResultSet data) throws SQLException {
		String result = "{\"success\":" + success;
		if (!total.isEmpty()) {
			result = result + ",\"total\":" + total + "";
		}
		if (!msg.isEmpty()) {
			result = result + ",\"msg\":\"" + msg + "\"";
		}
		if (data != null) {
			result = result + ",\"data\":[" + ResultSet2Json(data) + "]";
		}

		return result + "}";
	}

	public static String ResultSet2Json(ResultSet rs) throws SQLException {
		String result = "";
		if (rs.getRow() < 1) {
			return "";
		}
		int columncount = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			if (result.length() > 0) {
				result = result + ",";
			}
			result = result + "{";
			int keyindex = 0;
			for (int i = 0; i < columncount; i++) {
				if (keyindex > 0) {
					result = result + ",";
				}
				String key = rs.getMetaData().getColumnLabel(i);
				result = result + "\"" + key + "\"" + ":\"" + rs.getString(key)
						+ "\"";
				keyindex++;
			}
			result = result + "}";
		}

		// String result = "";
		// if (slist.size() < 1) {
		// return "";
		// }
		// int index = 0;
		// HashMap<String, Object> h = slist.get(0);
		// Set<String> set = h.keySet();
		// for (HashMap<String, Object> o : slist) {
		// Iterator<String> it = set.iterator();
		// if (index > 0) {
		// result = result + ",";
		// }
		// result = result + "{";
		// int keyindex = 0;
		// while (it.hasNext()) {
		// if (keyindex > 0) {
		// result = result + ",";
		// }
		// String key = it.next();
		// result = result +"\""+ key+"\"" + ":\"" + o.get(key) + "\"";
		// keyindex++;
		// }
		// result = result + "}";
		// index++;
		// }
		return result;
	}

}
