package com.hayate.wechat.oa.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hayate.wechat.common.base.BaseService;
import com.hayate.wechat.common.config.WeChatOaConfig;
import com.hayate.wechat.common.util.HttpClientUtil;
import com.hayate.wechat.common.util.JsonUtils;
import com.hayate.wechat.oa.service.UserManagementService;

@Service
@Lazy(false)
@Transactional(readOnly = true)
@SuppressWarnings("all")
public class UserManagementServiceImpl extends BaseService implements
		UserManagementService {

	
	/**
	 * 创建标签
	 * @param tagName 标签名
	 * @return
	 */
	@Override
	public String creatTag(String tagName){
		
		String log = "创建标签";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
		
		Map<String,Object> tagMap = new LinkedHashMap<String,Object>();
		tagMap.put("name", tagName);
		
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("tag", tagMap);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.TAGS_CREATE, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        Map<String,Object> newbodyMap = (Map<String,Object>) mapResult.get("tag");
        
        logger.debug(log+"返回信息2（开始）");
        for(Entry<String,Object> e : newbodyMap.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息2（结束）");
        String tagId = String.valueOf(newbodyMap.get("id"));
        return tagId;
	}
	
	/**
	 * 获取标签
	 * @return
	 */
	@Override
	public List<Map<String,Object>> getTags(){
		
		String log = "获取标签";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());

		String stringResult = HttpClientUtil.doGet(WeChatOaConfig.TAGS_GET, params);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());
        }
        logger.debug(log+"返回信息（结束）");
       
        List<Map<String,Object>> tagsMap = (List<Map<String,Object>>) mapResult.get("tags");
        
        logger.debug(log+"返回信息2（开始）");
        
        for(Map<String,Object> m : tagsMap){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}    
        	logger.debug("============================");
        }
        logger.debug(log+"返回信息2（结束）");

        return tagsMap;
	}
	
	@Override
	public String updateTag(int tagId,String tagName){
		
		String log = "修改标签";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
		
		Map<String,Object> tagMap = new LinkedHashMap<String,Object>();
		tagMap.put("id", tagId);
		tagMap.put("name", tagName);
		
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("tag", tagMap);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.TAGS_UPDATE, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");

        String msg = (String) mapResult.get("errmsg"); 
        return msg;
	}
	
	@Override
	public String deleteTag(int tagId){
		
		String log = "删除标签";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
		
		Map<String,Object> tagMap = new LinkedHashMap<String,Object>();
		tagMap.put("id", tagId);
		
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("tag", tagMap);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.TAGS_DELETE, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");

        String msg = (String) mapResult.get("errmsg"); 
        return msg;
	}
	
	/**
	 * @param tagId	标签ID
	 * @param startFrom	第一个拉取的OPENID，不填默认从头开始拉取
	 * @return
	 */
	@Override
	public List<String> getOpenIdsByTag(int tagId,String startFrom){
		
		String log = "获取标签下粉丝列表";
		
		if(StringUtils.isEmpty(startFrom)){
			startFrom = "";
		}
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("tagid", tagId);
		bodyMap.put("next_openid", startFrom);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.USER_TAG_GET, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        if((int)mapResult.get("count") != 0){
        	Map<String,Object> dataMap = (Map<String, Object>) mapResult.get("data");
            List<String> openIds = (List<String>) dataMap.get("openid");
            
            logger.debug(log+"返回信息2（开始）");
            for(String s : openIds){
            	logger.debug(s);       	
            }
            logger.debug(log+"返回信息2（结束）");

            return openIds;
        }
       
        return null;
				
	}
	
	@Override
	public String taggingUsers(int tagId,List<String> openIds){
		
		String log = "批量为用户打标签";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("openid_list", openIds);
		bodyMap.put("tagid", tagId);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.TAGS_MEMBERS_BATCH_TAGGING, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");

        String msg = (String) mapResult.get("errmsg"); 
        return msg;
	}
	
	@Override
	public String unTaggingUsers(int tagId,List<String> openIds){
		
		String log = "批量为用户取消标签";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("openid_list", openIds);
		bodyMap.put("tagid", tagId);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.TAGS_MEMBERS_BATCH_UNTAGGING, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");

        String msg = (String) mapResult.get("errmsg"); 
        return msg;
	}
	
	@Override
	public List<Integer> getUserTags(String openId){
		
		String log = "获取用户身上的标签列表";

		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("openid", openId);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.TAGS_GET_ID_LIST, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
    	List<Integer> tagIdList = (List<Integer>) mapResult.get("tagid_list");
       
        logger.debug(log+"返回信息2（开始）");
        for(int i : tagIdList){
        	logger.debug(String.valueOf(i));       	
        }
        logger.debug(log+"返回信息2（结束）");

        return tagIdList;

	}
	
	@Override
	public String userRemark(String openId,String remarkName){
		
		String log = "设置用户备注名";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("openid", openId);
		bodyMap.put("remark", remarkName);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.USER_INFO_UPDATE_REMARK, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");

        String msg = (String) mapResult.get("errmsg"); 
        return msg;
	}
	
	@Override
	public Map<String,Object> getUserInfo(String openId){
		
		String log = "获取用户基本信息";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
		params.put("openid", openId);
		params.put("lang", "zh_CN");

		String stringResult = HttpClientUtil.doGet(WeChatOaConfig.USER_INFO, params);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());
        }
        logger.debug(log+"返回信息（结束）");
       
        return mapResult;
		
	}
	
	@Override
	public List<Map<String,Object>> getUsersInfo(List<String> openIds){
		
		String log = "批量获取用户基本信息";
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(String s : openIds){
			Map<String,Object> m = new LinkedHashMap<String, Object>();
			m.put("openid", s);
			m.put("lang", "zh_CN");
			list.add(m);
		}
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("user_list", list);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.USER_INFO_BATCH_GET, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        List<Map<String,Object>> usersList = (List<Map<String, Object>>) mapResult.get("user_info_list");
        
        logger.debug(log+"返回信息2（开始）");
        for(Map<String,Object> m : usersList){
        	for(Entry<String,Object> e : m.entrySet()){
        		logger.debug(e.getKey()+":"+e.getValue());
        	}
        	logger.debug("=================================");
        }
        logger.debug(log+"返回信息2（结束）");
       
        return usersList;				
	}
	

	/**
	 * 获取帐号的关注者列表
	 * @param startFrom	第一个拉取的OPENID，不填默认从头开始拉取
	 * @return	OpenIDs
	 */
	@Override
	public List<String> getAllOpenIds(String startFrom){
		
		String log = "获取帐号的关注者列表";
		
		if(StringUtils.isEmpty(startFrom)){
			startFrom = "";
		}
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
		params.put("next_openid", startFrom);
				
		String stringResult = HttpClientUtil.doGet(WeChatOaConfig.USER_GET, params);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        if((int)mapResult.get("count") != 0){
        	Map<String,Object> dataMap = (Map<String, Object>) mapResult.get("data");
            List<String> openIds = (List<String>) dataMap.get("openid");
            
            logger.debug(log+"返回信息2（开始）");
            for(String s : openIds){
            	logger.debug(s);       	
            }
            logger.debug(log+"返回信息2（结束）");

            return openIds;
        }
       
        return null;
	}
	
	/**
	 * 获取公众号的黑名单列表
	 * @param count	拉取的OPENID个数，最大值为10000
	 * @param startFrom	第一个拉取的OPENID，不填默认从头开始拉取
	 * @return	OpenIDs
	 */
	@Override
	public List<String> getBlackList(String startFrom){
		
		String log = "获取公众号的黑名单列表";
		
		if(StringUtils.isEmpty(startFrom)){
			startFrom = "";
		}
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("begin_openid", startFrom);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.TAGS_MEMBERS_GET_BLACK_LIST, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");
        
        if((int)mapResult.get("count") != 0){
        	Map<String,Object> dataMap = (Map<String, Object>) mapResult.get("data");
            List<String> openIds = (List<String>) dataMap.get("openid");
            
            logger.debug(log+"返回信息2（开始）");
            for(String s : openIds){
            	logger.debug(s);       	
            }
            logger.debug(log+"返回信息2（结束）");

            return openIds;
        }
       
        return null;
	}
	
	@Override
	public String blackingUsers(List<String> openIds){
		
		String log = "批量拉黑用户";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("openid_list", openIds);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.TAGS_MEMBERS_BATCH_BLACK_LIST, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");

        String msg = (String) mapResult.get("errmsg"); 
        return msg;
	}
	
	@Override
	public String unBlackingUsers(List<String> openIds){
		
		String log = "批量取消拉黑用户";
		
		Map<String,String> params = new LinkedHashMap<String, String>();
		params.put("access_token", getAccessToken());
				
		Map<String,Object> bodyMap = new LinkedHashMap<String,Object>();
		bodyMap.put("openid_list", openIds);
		
		String json = JsonUtils.objectToJson(bodyMap);
		logger.debug("转换后的json："+json);
	
		String stringResult = HttpClientUtil.doPostJson(WeChatOaConfig.TAGS_MEMBERS_BATCH_UNBLACK_LIST, params, json);
		logger.debug("返回的json："+stringResult);
		Map<String,Object> mapResult = JsonUtils.jsonToPojo(stringResult, HashMap.class);
		
		logger.debug(log+"返回信息（开始）");
        for(Entry<String,Object> e : mapResult.entrySet()){
        	logger.debug(e.getKey()+":"+e.getValue());       	
        }
        logger.debug(log+"返回信息（结束）");

        String msg = (String) mapResult.get("errmsg"); 
        return msg;
	}
}
