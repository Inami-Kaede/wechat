package com.hayate.wechat.common.util;

import java.util.Date;
import java.util.Random;

import org.joda.time.DateTime;

/**

 * 各种id生成策略

 * <p>Title: IDUtils</p>

 * <p>Description: </p>

 * @author	kaede
 * @version 1.0

 */
public class IDUtils {

	/**

	 * 图片名生成

	 */
	public static String genImageName() {
		//取当前时间的长整形值包含毫秒

		long millis = System.currentTimeMillis();
		//long millis = System.nanoTime();

		//加上三位随机数

		Random random = new Random();
		int end3 = random.nextInt(999);
		//如果不足三位前面补0

		String str = millis + String.format("%03d", end3);
		
		return str;
	}
	
	/**

	 * 商品id生成

	 */
	public static long genItemId() {
		//取当前时间的长整形值包含毫秒

		long millis = System.currentTimeMillis();
		//long millis = System.nanoTime();

		//加上两位随机数

		Random random = new Random();
		int end2 = random.nextInt(99);
		//如果不足两位前面补0

		String str = millis + String.format("%02d", end2);
		long id = new Long(str);
		return id;
	}
	
	public static void main(String[] args) {
		for(int i=0;i< 100;i++)
		System.out.println(genItemId());
	}
	
	/**

	 * 订单id生成

	 */
	public static String genOrderId(int userId) {
		String time = new DateTime(new Date()).toString("yyyyMMdd-HHmmss-");
		//加上两位随机数
		Random random = new Random();
		int end2 = random.nextInt(99);
		//如果不足两位前面补0
		String str = time +String.format("%08d", userId)+"-"+String.format("%02d", end2);
		return str;
	}
	
	/**
	 * 提现订单id生成
	 */
	public static String genWithdrawOrderId(int userId) {
		String time = new DateTime(new Date()).toString("yyyyMMddHHmmss");
		//加上两位随机数

		Random random = new Random();
		int end2 = random.nextInt(99);
		//如果不足两位前面补0

		String str = time +String.format("%08d", userId)+String.format("%02d", end2);
		return str;
	}
}
