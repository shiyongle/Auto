package com.pc.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;


//2016-4-25 by les  用户计算报表 底部合计
public class CalcTotalField {

	//计算 map 指定字段的汇总数
	public static HashMap<String, String> docalTotal(List<Map<String, Object>> list,String[] strArr){
		//定义计算的合计数组
		HashMap<String, String> resmap = new HashMap();
		BigDecimal[] resArr = new BigDecimal[strArr.length];
		//计算结果初始化
		for(int i=0;i<strArr.length;i++){
			resArr[i] =BigDecimal.ZERO;
		}
		for(Map<String, Object> eachMap : list){
			for(int i=0;i<strArr.length;i++){
				//根据索引取值
				BigDecimal eachTotal = resArr[i];
				if(eachMap.containsKey(strArr[i])){				
					String resStr = eachMap.get(strArr[i])==null?"0":eachMap.get(strArr[i]).toString();
					//累加
					eachTotal = eachTotal.add(new BigDecimal(resStr));
					resArr[i] = eachTotal;
				}
			}
		}
		for(int i=0;i<strArr.length;i++){
			resmap.put(strArr[i], resArr[i].toString());
		}
		return resmap;
	}
	
	//计算司机运费
	public static BigDecimal calDriverFee(BigDecimal price)
	{
		if(price.compareTo(new BigDecimal("100"))<=0)
		{
			return new BigDecimal("0.95");
		}else if(price.compareTo(new BigDecimal("100"))==1&&price.compareTo(new BigDecimal("200"))<=0)
		{
			return new BigDecimal("0.94");
		}else if(price.compareTo(new BigDecimal("200"))==1&&price.compareTo(new BigDecimal("350"))<=0)
		{
			return new BigDecimal("0.93");
		}else if(price.compareTo(new BigDecimal("350"))==1&&price.compareTo(new BigDecimal("500"))<=0)
		{
			return new BigDecimal("0.92");
//		}else if(price.compareTo(new BigDecimal(500))==1)
		}else
		{
			return new BigDecimal("0.9");
		}
	}
}
