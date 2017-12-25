package com.pc.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCluster;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import com.pc.appInterface.api.NewDongjingClient;
@Component
public class SendMsgUtil {
	@Autowired
	private JedisCluster jedisCluster;
	public  JSONObject sendMsg(String phone,String type,String msg){
		NewDongjingClient client = ServerContext.createSendMsgClient();
		Random rand=new Random();
		String code = String.valueOf(rand.nextInt(899999) + 100000);
		client.setMethod("sendmsgphone");
		client.setRequestProperty("phone", phone);
		client.setRequestProperty("msg", msg+"："+code);
		client.setRequestProperty("appSystem", "djcrm");
		client.SubmitData();
		JSONObject callbackObject = net.sf.json.JSONObject.fromObject(client.getResponse().getResultString());
		if (!callbackObject.getBoolean("success")) {
			return callbackObject;
		}
		SendMsgRedisModel model=new SendMsgRedisModel();
		model.setCode(code);
		model.setCreateTime(new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a", Locale.ENGLISH).format(new Date()));
//		model.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		String setex = jedisCluster.setex("commons:outeruserserver:phonecode:"+type+":"+phone,5*60,JSONObject.fromObject(model).toString());
		
		return callbackObject;
	}
}
