package com.adminfortrain.admin.mapper;

import com.adminfortrain.admin.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杰
 * @since 2020-04-24
 */


@Repository
public interface UserMapper extends BaseMapper<User> {
    public List<User> selectAll();
}
