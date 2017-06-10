package com.hayate.wechat.biz.mapper;

import com.hayate.wechat.biz.pojo.BizUser;
import com.hayate.wechat.biz.pojo.BizUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BizUserMapper extends Mapper<BizUser> {
    long countByExample(BizUserExample example);

    int deleteByExample(BizUserExample example);

    List<BizUser> selectByExample(BizUserExample example);

    int updateByExampleSelective(@Param("record") BizUser record, @Param("example") BizUserExample example);

    int updateByExample(@Param("record") BizUser record, @Param("example") BizUserExample example);
}