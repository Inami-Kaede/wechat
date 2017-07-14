package com.hayate.wechat.common.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 统一异常处理
 */
public class CustomHandlerExceptionResolver implements HandlerExceptionResolver {

	private static final String UNKNOW_ERROR = "Unknow error.";

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView mv = new ModelAndView();

		// 各种异常处理
		int errorCode = -1;
		// 状态码
		int statusCode = HttpStatus.OK.value();
		String errorMsg;
		if (ex instanceof DataAccessException) {
			errorMsg = ex.getCause().getMessage();
			logger.error(errorMsg, ex);
			errorMsg = "数据填写出错";
		} else if (ex instanceof IllegalArgumentException) {
			errorMsg = ex.getMessage();
			logger.error(errorMsg);
		} else if (ex instanceof MaxUploadSizeExceededException) {
			errorMsg = "文件上传大小超过最大值";
		} else if (ex instanceof StringIndexOutOfBoundsException) {
			errorMsg = "参数格式错";
		} else if (ex instanceof MultipartException) {
			errorMsg = "文件上传出错 : " + ex.getMessage();
		} else {
			errorMsg = UNKNOW_ERROR;
			logger.error("Unknow error.", ex);
		}

		/* 使用response返回 */
		response.setStatus(statusCode); // 设置状态码
		response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
		response.setCharacterEncoding("UTF-8"); // 避免乱码
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		try {
			//TODO
			String errorJson = "";
			response.getWriter().write(errorJson);
		} catch (IOException e) {
			logger.error("Write error json message error.", e);
		}
		return mv;
	}
}
