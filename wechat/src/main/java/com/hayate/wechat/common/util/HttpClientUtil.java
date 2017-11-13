package com.hayate.wechat.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

public class HttpClientUtil {

	public static String createUrl(String url, Map<String, String> params) {
		
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (params != null && !params.isEmpty()) {
				for (String key : params.keySet()) {
					builder.addParameter(key, params.get(key));
				}
			}
			URI uri = builder.build();
			return uri.toString();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public static String doGet(String url) {
		return doGet(url, null);
	}

	public static String doGet(String url, Map<String, String> params) {

		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();

		String resultString = null;
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (params != null && !params.isEmpty()) {
				for (String key : params.keySet()) {
					builder.addParameter(key, params.get(key));
				}
			}
			
			URI uri = builder.build();

			//System.out.println(uri);
			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);

			// 执行请求
			response = httpClient.execute(httpGet);
			HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
            	resultString = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
					httpClient = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (response != null) {
					response.close();
					response = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		return resultString;
	}
	
	public static byte[] doGetReceiveBytes(String url, Map<String, String> params) {

		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;

		byte[] resultBytes = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (params != null && !params.isEmpty()) {
				for (String key : params.keySet()) {
					builder.addParameter(key, params.get(key));
				}
			}
			
			URI uri = builder.build();

			//System.out.println(uri);
			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);

			// 执行请求
			response = httpClient.execute(httpGet);
			HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字节数组
            	resultBytes = EntityUtils.toByteArray(responseEntity);
            }
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
					httpClient = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (response != null) {
					response.close();
					response = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		return resultBytes;
	}

	public static String doPost(String url) {
		return doPost(url, null);
	}

	public static String doPost(String url, Map<String, String> params) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = null;
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (String key : params.keySet()) {
					paramList.add(new BasicNameValuePair(key, params.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
            	resultString = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
					httpClient = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (response != null) {
					response.close();
					response = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}

		return resultString;
	}

	public static String doPostXml(String url, String xml) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = null;
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(xml, ContentType.create("application/xml", Consts.UTF_8));
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
            	resultString = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
					httpClient = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (response != null) {
					response.close();
					response = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}

		return resultString;
	}
	
	public static String doCertPostXml(String url, String xml,String certPath,String certPassword){
		
		FileInputStream instream = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		String resultString = null;
		
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			instream = new FileInputStream(new File(certPath));
			keyStore.load(instream, certPassword.toCharArray());
			
			// Trust own CA and all self-signed certs
	        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, certPassword.toCharArray())
	                									.build();
	        // Allow TLSv1 protocol only
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	                sslcontext,
	                new String[] { "TLSv1" },
	                null,
	                //SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
	        
	        httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
	        								 .build();		        
	        // 创建Http Post请求
 			HttpPost httpPost = new HttpPost(url);
 			// 创建请求内容
 			StringEntity entity = new StringEntity(xml, ContentType.create("application/xml", Consts.UTF_8));
 			httpPost.setEntity(entity);
 			// 执行http请求
 			response = httpClient.execute(httpPost);
 			HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
            	resultString = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
			
		} catch (KeyStoreException e1) {
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}finally {			
			try {
				if(instream != null){
	        		instream.close();
	        		instream = null;
	        	}
			} catch (IOException e) {
				e.printStackTrace();
			}			
			try {
				if (httpClient != null) {
					httpClient.close();
					httpClient = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (response != null) {
					response.close();
					response = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}

		return resultString;
	}

	/**
	 * 推送消息到极光
	 * 
	 * @param url
	 *            接口地址
	 * @param json
	 *            推送数据（json格式）
	 * @param header
	 *            头信息key
	 * @param headerCode
	 *            头信息value
	 * @return
	 */
	public static String doPushPostJson(String url, String json,
			String header, String headerCode) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = null;
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json,
					ContentType.APPLICATION_JSON);
			httpPost.setHeader(header, headerCode);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
            	resultString = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close();
					httpClient = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (response != null) {
					response.close();
					response = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}						
		}
		return resultString;
	}
	

	/**
	 * @param url
	 * @param params	普通参数
	 * @param name		上传文件（name value）对应的name
	 * @param data		文件本体
	 * @param fileFormat	文件后缀名
	 * @return
	 */
	public static String uploadFile(String url,Map<String,String> params,String name,byte[] data,String fileFormat) {
		return uploadFile(url, params, name, data, fileFormat, null);
    }
	

	/**
	 * @param url
	 * @param params	普通参数
	 * @param name		上传文件（name value）对应的name
	 * @param data		文件本体
	 * @param fileFormat	文件后缀名
	 * @param description (上传类型为video时的视频描述信息 json格式)
	 * @return
	 */
	public static String uploadFile(String url,Map<String,String> params,String name,byte[] data,String fileFormat,String description) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = null;
		
        try {
            HttpPost httpPost = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody(name, data,ContentType.create("multipart/form-data", Consts.UTF_8),UUID.randomUUID().toString()+"."+fileFormat);
           
            if(!StringUtils.isEmpty(description)){
            	builder.addTextBody("description", description, ContentType.APPLICATION_JSON);
            }
            
            if(params != null && !params.isEmpty()){
            	for(Entry<String, String> e : params.entrySet()){
            		builder.addTextBody(e.getKey(), e.getValue());
            	}
            }
                       
            //builder.addBinaryBody("file", multipartFile.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
           // builder.addTextBody("filename", fileName);// 类似浏览器表单提交，对应input的name和value
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
            	resultString = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
				if (httpClient != null) {
					httpClient.close();
					httpClient = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (response != null) {
					response.close();
					response = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
        }
        return resultString;
    }
	
	public static String doPostJson(String url, String json) {
		
		return doPostJson(url, null, json);
	}

	public static String doPostJson(String url,Map<String,String> params,String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = null;
		
        try {
        	      	
        	HttpPost httpPost = null;
        	if(params != null && !params.isEmpty()){
        		httpPost = new HttpPost(createUrl(url, params));
        	}else{
        		httpPost = new HttpPost(url);
        	}
                      
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
           
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
            	resultString = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
				if (httpClient != null) {
					httpClient.close();
					httpClient = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (response != null) {
					response.close();
					response = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
        }
        return resultString;
    }
	
	public static byte[] doPostJsonReceiveBytes(String url,Map<String,String> params,String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		byte[] resultBytes = null;
	
        try {
        	      	
        	HttpPost httpPost = null;
        	if(params != null && !params.isEmpty()){
        		httpPost = new HttpPost(createUrl(url, params));
        	}else{
        		httpPost = new HttpPost(url);
        	}
                      
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
           
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字节数组
            	resultBytes = EntityUtils.toByteArray(responseEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
				if (httpClient != null) {
					httpClient.close();
					httpClient = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (response != null) {
					response.close();
					response = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
        }
        return resultBytes;
    }
}
