package com.hayate.wechat.common.base;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import com.hayate.wechat.common.config.WeChatOaConfig;
import com.hayate.wechat.common.util.HttpClientUtil;
import com.hayate.wechat.common.util.JsonUtils;


public abstract class BaseService{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
		
	@Autowired
	protected RedisTemplate<String,String> redisTemplate;

	public String getAccessToken() {
		
		logger.debug("准备从redis获取accessToken");
		BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(WeChatOaConfig.REDIS_KEY_ACCESS_TOKEN);
		String accessToken = ops.get();
		
		if(StringUtils.isEmpty(accessToken)){		
			
			logger.info("accessToken过期，重新从腾讯获得");
			Map<String, String> param = new LinkedHashMap<String, String>();
			param.put("grant_type", "client_credential");
			param.put("appid", WeChatOaConfig.APP_ID);
			param.put("secret", WeChatOaConfig.APP_SECRET);
			
			String stringResult = HttpClientUtil.doGet(WeChatOaConfig.ACCESS_TOKEN, param);
			HashMap<String,Object> result = JsonUtils.jsonToPojo(stringResult, HashMap.class);
			accessToken = (String) result.get("access_token");
			
			if(accessToken == null){								
				logger.error("获取accessToken失败（公众号）");				
				for(Entry<String,Object> e : result.entrySet()){									
					logger.error(e.getKey()+":"+e.getValue());					
				}
			
			}else{				
				logger.debug("注册到redis");
				int expire = (Integer) result.get("expires_in");				
				ops.set(accessToken, expire-10, TimeUnit.SECONDS);
				
			}
		}
		logger.debug("从redis获得accessToken："+accessToken);
		return accessToken;
	}

}
