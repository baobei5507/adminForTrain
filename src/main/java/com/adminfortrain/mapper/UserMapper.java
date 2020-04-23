package com.adminfortrain.mapper;

import com.adminfortrain.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper  {

     User queryUserByName(String name);
}
