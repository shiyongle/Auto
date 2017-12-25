package com.util;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Set;

public class Params implements java.io.Serializable {
	private static final long serialVersionUID = -1692564139043517072L;
	
	private HashMap<String, Object> h;
	public Params()
	{
		h=new HashMap<>();
	}
	public Params(HashMap<String, Object> v)
	{
		h=v;
	}
	public void setData(HashMap<String, Object> v)
	{
		h=v;
	}
	public HashMap<String, Object> getData()
	{
		return h;
	}
	public void setBoolean(String key, boolean value) {
		h.put(key, Boolean.valueOf(value));
	}
	public void put(String key, String value) {
		h.put(key, value);
	}
	public void put(String key, Object value) {
		h.put(key, value);
	}
	public Object get(String key) {
		return h.get(key);
	}
	public  Set<String> keySet() {
		return h.keySet();
	}
	public boolean getBoolean(String key) {
		Object bb = h.get(key);
		if (bb == null)
			return false;
		if (bb instanceof Boolean)
			return ((Boolean) bb).booleanValue();

		return Boolean.valueOf(String.valueOf(bb)).booleanValue();
	}

	public void setInt(String key, int value) {
		h.put(key, new Integer(value));
	}

	public int getInt(String key) {
		return ((Integer) h.get(key)).intValue();
	}

	public void setString(String key, String value) {
		h.put(key, value);
	}

	public String getString(String key) {
		return ((String) h.get(key));
	}

	public void setBigDecimal(String key, BigDecimal value) {
		h.put(key, value);
	}

	public BigDecimal getBigDecimal(String key) {
		return ((BigDecimal) h.get(key));
	}

	public void setLong(String key, long value) {
		h.put(key, new Long(value));
	}

	public long getLong(String key) {
		return ((Long) h.get(key)).longValue();
	}

	public void setDouble(String key, double value) {
		h.put(key, new Double(value));
	}

	public double getDouble(String key) {
		return ((Double) h.get(key)).doubleValue();
	}

	public void setFloat(String key, float value) {
		h.put(key, new Float(value));
	}

	public float getFloat(String key) {
		return ((Float) h.get(key)).floatValue();
	}

	public boolean containsKey(String key) {
		return containsKey(key);
	}

	public boolean containsValue(Object value) {
		return h.containsKey(value);
	}

	public int size() {
		return h.size();
	}

	@Override
	public boolean equals(Object obj) {
		return h.equals(obj);
	}

	@Override
	public int hashCode() {
		return h.hashCode();
	}
	@Override
	public String toString() {
		return h.toString();
	}

	
}
