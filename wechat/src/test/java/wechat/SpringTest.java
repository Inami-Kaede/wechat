package wechat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hayate.wechat.biz.mapper.BizProductMapper;
import com.hayate.wechat.biz.mapper.BizUserMapper;
import com.hayate.wechat.biz.pojo.BizProduct;
import com.hayate.wechat.biz.pojo.BizUser;
import com.hayate.wechat.oa.controller.AuthController;
import com.hayate.wechat.oa.service.AccessService;
import com.hayate.wechat.oa.service.AuthService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath*:/spring/spring*.xml"})
public class SpringTest {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private AuthService authService;
	
	@Autowired
	private AccessService accessService;
	
	@Autowired
	private AuthController authController;
	
	@Autowired
	private BizUserMapper bizUserMapper;
	
	@Autowired
	private BizProductMapper bizProductMapper;
	
	@Test
	public void contextTest(){
		System.out.println(authService);
		System.out.println(authController);
		System.out.println(accessService);
	}
	
	@Test
	public void dataBaseTest(){
		
		BizUser user = new BizUser();
		user.setAccount(new BigDecimal("465156.2316"));
		user.setAccount2(new BigDecimal("123123.123132213132"));
		user.setAge((byte) 25);
		user.setCreateDate(new Date());
		user.setPassword("123456");
		user.setSex((byte)0);
		user.setUpdateDate(new Date());
		user.setUrl("www.baidu.com");
		user.setUsername("zhangsan15");
		
		bizUserMapper.insert(user);
		
		System.out.println("回写的ID为："+user.getId());
				
		PageHelper.startPage(2, 5);		
		List<BizUser> list = bizUserMapper.selectAll();
		PageInfo<BizUser> page = new PageInfo<BizUser>(list);
		System.out.println("getNavigateFirstPage:"+page.getNavigateFirstPage());
		System.out.println("getNavigateLastPage:"+page.getNavigateLastPage());
		System.out.println("getNavigatePages:"+page.getNavigatePages());
		System.out.println("getNextPage:"+page.getNextPage());
		System.out.println("getPageNum:"+page.getPageNum());
		
		System.out.println("getPages:"+page.getPages());
		System.out.println("getPageSize:"+page.getPageSize());
		System.out.println("getPrePage:"+page.getPrePage());
		System.out.println("getSize:"+page.getSize());
		System.out.println("getStartRow:"+page.getStartRow());
		System.out.println("getTotal:"+page.getTotal());
				
		List<BizUser> list2 = page.getList();
		for (BizUser bizUser : list2) {
			System.out.println(bizUser.getUsername()+":"+bizUser.getPassword());
		}
		
		int[] navigatepageNums = page.getNavigatepageNums();
		for (int i : navigatepageNums) {
			System.out.println("getNavigatepageNums:"+i);
		}
	}
	
	@Test
	public void databaseTest2(){
		BizProduct bizProduct = new BizProduct();
		bizProduct.setName("自行车");
		bizProduct.setCategory(233);
		bizProduct.setCreateDate(new Date());
		bizProduct.setUpdateDate(new Date());		
		bizProduct.setId(UUID.randomUUID().toString());
		bizProductMapper.insert(bizProduct);
	}
	
	@Test
	public void databaseTest3(){

		bizProductMapper.superDel();
		
	}
}
