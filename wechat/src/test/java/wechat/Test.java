package wechat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.springframework.web.client.RestTemplate;

public class Test {

	public static void main(String[] args) {
		RestTemplate rt = new RestTemplate();
	}

}
