package wechat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hayate.wechat.oa.service.AuthService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath*:/spring/spring*.xml"})
public class SpringTest {

	@Autowired
	private AuthService authService;
	
	@Test
	public void contextTest(){
		System.out.println(authService);
	}
}
