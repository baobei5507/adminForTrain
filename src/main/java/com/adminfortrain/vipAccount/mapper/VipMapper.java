package com.adminfortrain.vipAccount.mapper;

import com.adminfortrain.admin.model.User;
import com.adminfortrain.vipAccount.model.Vip;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杰
 * @since 2020-04-25
 */
@Repository
public interface VipMapper extends BaseMapper<Vip> {

    @Select("select * from vip  ${ew.customSqlSegment}")
    List<Vip> getDeleted(@Param(Constants.WRAPPER) Wrapper wrapper);
}
