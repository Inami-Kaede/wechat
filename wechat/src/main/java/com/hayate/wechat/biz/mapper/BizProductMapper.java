package com.hayate.wechat.biz.mapper;

import com.hayate.wechat.biz.pojo.BizProduct;
import tk.mybatis.mapper.common.Mapper;

public interface BizProductMapper extends Mapper<BizProduct> {
	
	void superDel();
}