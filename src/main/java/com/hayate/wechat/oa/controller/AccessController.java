package com.hayate.wechat.oa.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hayate.wechat.common.util.AesException;
import com.hayate.wechat.common.util.CommonUtil;
import com.hayate.wechat.common.util.SHA1;
import com.hayate.wechat.common.util.WXBizMsgCrypt;
import com.hayate.wechat.oa.config.WeChatOaConfig;
import com.hayate.wechat.oa.service.AccessService;

@Api(tags="微信公众号-接入及消息返回")
@Controller
@RequestMapping(value = "wechat/oa")
public class AccessController extends BaseController {
		
	@Autowired
	private AccessService accessService;
	
	/**
	 * 公众号接入
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	@ApiOperation(value = "微信后台接入-启用连接地址") 
	@RequestMapping(value = "access",method=RequestMethod.GET,produces = MediaType.APPLICATION_XML_VALUE + ";charset=UTF-8")
	@ResponseBody
	public String accessServer(@RequestParam(required=true)String signature,@RequestParam(required=true)String timestamp,@RequestParam(required=true)String nonce,String echostr) {
		
		logger.info("----------收到微信后台接入-启用连接地址请求------------");
		logger.debug("==============接收到的参数（开始）==================");
		logger.debug("signature:"+signature);
		logger.debug("timestamp:"+timestamp);
		logger.debug("nonce:"+nonce);
		logger.debug("echostr:"+echostr);
		logger.debug("==============接收到的参数（结束）==================");
		
		try {									
			String sha1 = SHA1.getSHA1(WeChatOaConfig.TOKEN, timestamp, nonce, null);
			
			if(sha1.equals(signature)){										
				logger.info("成功接入！");								
				return echostr;									
			}else{								
				logger.error("接入失败！");								
				return null;				
			}				
		} catch (AesException e) {			
			e.printStackTrace();
			logger.error(e.getMessage());
			logger.error("验签失败！");									
			return null;		
		}
	}
	
	/**
	 * 公众号接收消息微信转发到服务器
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "微信后台接入-接收转发消息") 
	@RequestMapping(value = "access",method=RequestMethod.POST,produces = MediaType.APPLICATION_XML_VALUE + ";charset=UTF-8")
	@ResponseBody
	public String receiveMsg(HttpServletRequest request,HttpServletResponse response,@RequestParam(required=true)String signature,
			@RequestParam(required=true)String timestamp,@RequestParam(required=true)String nonce,String echostr,String encrypt_type,String msg_signature) {
		
		logger.info("-----------收到微信后台接入-接收转发消息------------");
		logger.debug("==============接收到的参数（开始）==================");
		logger.debug("signature:"+signature);
		logger.debug("timestamp:"+timestamp);
		logger.debug("nonce:"+nonce);
		logger.debug("echostr:"+echostr);
		logger.debug("encrypt_type:"+encrypt_type);
		logger.debug("msg_signature:"+msg_signature);
		logger.debug("==============接收到的参数（结束）==================");

		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		
		try {			
			
			//读取消息（如果是安全模式需要解密消息）
			ServletInputStream servletInputStream = request.getInputStream();
			inputStreamReader = new InputStreamReader(servletInputStream);
			bufferedReader = new BufferedReader(inputStreamReader);
			StringBuffer sb = new StringBuffer();
			String line = null;
			
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}			
			String data = sb.toString();
			logger.debug("content:"+data);
			
			//如果开启了加密
			if(WeChatOaConfig.OPEN_ENCODING_AES_KEY){				
				//解密消息
				WXBizMsgCrypt pc = new WXBizMsgCrypt(WeChatOaConfig.TOKEN, WeChatOaConfig.ENCODING_AES_KEY, WeChatOaConfig.APP_ID);
				String decryptMsg = pc.decryptMsg(msg_signature, timestamp, nonce,data);
				//logger.debug(decryptMsg);
				
				Map<String, Object> parseXml = CommonUtil.parseXml(decryptMsg);
				
				logger.debug("==============开启加密解密后的消息（开始）==================");
				for(Entry<String, Object> e : parseXml.entrySet()){
					logger.debug(e.getKey()+":"+e.getValue());
				}
				logger.debug("==============开启加密解密后的消息（结束）==================");
				
				Map<String, Object> firstToLowerCase = CommonUtil.firstToLowerCase(parseXml);
				
				//返回信息
				String result = accessService.receiveMsg(firstToLowerCase);
				
				if(!StringUtils.isEmpty(result)){
					logger.debug("返回消息(加密前)："+result);			
					String back = pc.encryptMsg(result, timestamp, nonce);
					logger.debug("返回消息(加密后)："+back);						
					return back;
				}else{
					logger.error("返回信息为空！");
				}
				
			}else{					//未开启加密
				Map<String, Object> parseXml = CommonUtil.parseXml(data);
				
				logger.debug("==============未加密的消息（开始）==================");
				for(Entry<String, Object> e : parseXml.entrySet()){
					logger.debug(e.getKey()+":"+e.getValue());
				}
				logger.debug("==============未加密的消息（结束）==================");
				
				Map<String, Object> firstToLowerCase = CommonUtil.firstToLowerCase(parseXml);
				
				//返回信息
				String result = accessService.receiveMsg(firstToLowerCase);
				
				return result;
			}
			

		} catch (IOException e1) {
			e1.printStackTrace();		
		} catch (AesException e2) {
			e2.printStackTrace();
		} finally{		
			try {
				if(inputStreamReader != null){
					inputStreamReader.close();
					inputStreamReader = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}			
			try {
				if(bufferedReader != null){
					bufferedReader.close();
					bufferedReader = null;
				}
			} catch (IOException e) {				
				e.printStackTrace();
			}					
		}
		return "success";					
	}
}
