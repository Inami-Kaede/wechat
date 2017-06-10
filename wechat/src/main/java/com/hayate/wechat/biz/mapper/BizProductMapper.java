package com.hayate.wechat.biz.mapper;

import com.hayate.wechat.biz.pojo.BizProduct;
import com.hayate.wechat.biz.pojo.BizProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BizProductMapper extends Mapper<BizProduct> {
    long countByExample(BizProductExample example);

    int deleteByExample(BizProductExample example);

    List<BizProduct> selectByExample(BizProductExample example);

    int updateByExampleSelective(@Param("record") BizProduct record, @Param("example") BizProductExample example);

    int updateByExample(@Param("record") BizProduct record, @Param("example") BizProductExample example);
}