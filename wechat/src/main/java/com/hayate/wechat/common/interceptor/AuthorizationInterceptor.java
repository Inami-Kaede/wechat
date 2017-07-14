package com.hayate.wechat.common.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hayate.wechat.common.annotation.Authorization;


/**
 * token拦截器
 */
public class AuthorizationInterceptor implements HandlerInterceptor {


	/**
	 * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
	 * SpringMVC中的Interceptor拦截器是链式的，可以同时存在
	 * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行
	 * ，而且所有的Interceptor中的preHandle方法都会在
	 * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的
	 * ，这种中断方式是令preHandle的返 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		// 如果是带Authorization注解的方法才验证Token
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		Authorization authorizationClass = method.getAnnotation(Authorization.class);
		if (authorizationClass == null) {
			// 没有注解放行
			return true;
		}

		// 从header中得到token
		String authorization = request.getHeader("auth_code");

		boolean mustValid = authorizationClass.mustValid();
		if (mustValid) {
			// 1.必须带鉴权码的情况
			if (StringUtils.isBlank(authorization)) {
				throw new RuntimeException("authorization error, authorization code is null.");
			}
		} else {
			// 2.不是必须带鉴权码的情况

			// 游客不带鉴权码
			if (StringUtils.isBlank(authorization)) {
				return true;
			}
		}

		//TODO 验证token 从redis取
		
		//TODO 把UserID放入request，以便在controller里边直接获取
		request.setAttribute("", "");

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {
	}
	

}
