package com.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import com.model.PageModel;

public class JSONUtil {

	public static String getJson(Object o) {
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
	}

	public static String getSortStr(String sort, String order) {
		if (sort == null || order == null) {
			return null;
		} else {
			String[] sorts = sort.split(",");
			String[] orders = order.split(",");
			StringBuffer sortSB = new StringBuffer("");
			for (int i = 0; i < sorts.length; i++) {
				sortSB.append(sorts[i]).append(" ").append(orders[i]);
				if (i != sorts.length - 1) {
					sortSB.append(",");
				}
			}
			return sortSB.toString();
		}
	}

	/**
	 * 将字符串转换成json
	 * 
	 * @param success
	 * @param msg
	 * @param total
	 * @param data
	 * @return
	 */
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

	/**
	 * 分页组件将字符串转换成json
	 * 
	 * @param success
	 * @param msg
	 * @param total
	 * @param data
	 * @return
	 */
	public static String result(List list, int totalRecords, int pageNo,
			int pageSize) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalRecords", totalRecords);// 总条数
		map.put("pageNo", pageNo);// 第几页
		map.put("pageSize", pageSize);// 每页显示多少条
		return getJson(map);
	}

	/**
	 * 分页组件将字符串转换成json
	 * 
	 * @param success
	 * @param msg
	 * @param total
	 * @param data
	 * @return
	 */
	public static String result(List list, int totalRecords, int pageNo,
			int pageSize, String info) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalRecords", totalRecords);// 总条数
		map.put("pageNo", pageNo);// 第几页
		map.put("pageSize", pageSize);// 每页显示多少条
		map.put("info", info);
		return getJson(map);
	}

	public static String result(PageModel mode) {
		return result(mode.getList(), mode.getTotalRecords(), mode.getPageNo(),
				mode.getPageSize());
	}

	public static String result(PageModel mode, String info) {
		return result(mode.getList(), mode.getTotalRecords(), mode.getPageNo(),
				mode.getPageSize(), info);
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
				val = o.get(key) != null ? o.get(key).toString()
						.replace("\t", "").replace("\r\n", "<br/>")
						.replace("\r", "<br/>").replace("\n", "<br/>")
						.replace("\\", "\\\\").replace("\"", "\\\"") : "";
				result = result + "\"" + key + "\"" + ":\"" + val + "\"";
				keyindex++;
			}
			result = result + "}";
			index++;
		}
		return result;
	}

	/**
	 * 转换bean或bean集合为json格式的字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String getJsonString(Object obj) {
		if (obj instanceof List) {
			return getJsonArray((List<Object>) obj);
		} else if (obj instanceof Map) {
			return JSONObject.fromObject(obj).toString();
		} else {
			return getJsonObject(obj);
		}
	}

	/**
	 * 转换bean为json数组对象
	 * 
	 * @param obj
	 * @return
	 */
	public static String getJsonArray(List<Object> list) {
		List<HashMap<String, Object>> nList = new ArrayList<>();
		for (Object o : list) {
			nList.add(transBean2Map(o));
		}
		return JSONArray.fromObject(nList).toString();
	}

	/**
	 * 转换bean为json对象
	 * 
	 * @param obj
	 * @return
	 */
	public static String getJsonObject(Object obj) {
		return JSONObject.fromObject(transBean2Map(obj)).toString();
	}

	// ** * 日期转换 * <p> * format [yyyy-MM-dd] * format [yyyy-MM-dd HH:mm:ss] *
	// format [yyyy-MM-dd HH:mm:ss.SSS] * @author yangjian1004 * @Date Aug 4,
	// 2014 */@SuppressWarnings({ "rawtypes" })
	public static class DateTimeConverter implements Converter {
		private static final String DATE = "yyyy-MM-dd";
		private static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
		private static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";

		@Override
		public Object convert(Class type, Object value) {
			return toDate(type, value);
		}

		public Object toDate(Class type, Object value) {
			if (value == null || "".equals(value))
				return null;
			if (value instanceof String) {
				String dateValue = value.toString().trim();
				int length = dateValue.length();
				if (type.equals(java.util.Date.class)) {
					try {
						DateFormat formatter = null;
						if (length <= 10) {
							formatter = new SimpleDateFormat(DATE,new DateFormatSymbols(Locale.CHINA));
							return formatter.parse(dateValue);
						}
						if (length <= 19) {
							formatter = new SimpleDateFormat(DATETIME,
									new DateFormatSymbols(Locale.CHINA));
							return formatter.parse(dateValue);
						}
						if (length <= 23) {
							formatter = new SimpleDateFormat(TIMESTAMP,
									new DateFormatSymbols(Locale.CHINA));
							return formatter.parse(dateValue);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return value;
		}
	}
	/**
	 * map转bean，
	 * 
	 * @param obj
	 * @param notime
	 * @return
	 */
	public static void mapToObject(HashMap<String, Object> map, Object o) {
		try {
			DateTimeConverter dtConverter = new DateTimeConverter();
			ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
			convertUtilsBean.deregister(Date.class);
			convertUtilsBean.register(dtConverter, Date.class);
			convertUtilsBean.register(new BigDecimalConverter(null), BigDecimal.class); 
			BeanUtilsBean beanUtilsBean = new BeanUtilsBean(convertUtilsBean,
					new PropertyUtilsBean());
			beanUtilsBean.populate(o, map);
		} catch (Exception e) {
		}
		return;
	}

	public static Object jsToObject(String objson, Class classname) {
		Object o = null;
		try {
			JSONUtils.getMorpherRegistry().registerMorpher(
					new DateMorpherEx(new String[] { "yyyy-MM-dd HH:mm:ss",
							"yyyy-MM-dd't'HH:mm:ss", "yyyy-MM-dd" },
							(Date) null));
			JSONObject jsonobject = JSONObject.fromObject(objson);
			Iterator it = jsonobject.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (jsonobject.get(key) instanceof JSONArray) {
					it.remove();
					// jsonobject.remove(key);
				}
			}
			o = JSONObject.toBean(jsonobject, classname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * bean转map，处理Bigdecimal、Date、float等类型
	 * 
	 * @param obj
	 * @param notime
	 * @return
	 */
	private static HashMap<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		String format = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if (value instanceof BigDecimal) {
						value = ((BigDecimal) value).doubleValue();
					} else if (value instanceof Date) {
						value = sdf.format(value);
					}
					map.put(key, String.valueOf(value));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Bean转换成Map出错！");
		}
		return map;

	}
}
