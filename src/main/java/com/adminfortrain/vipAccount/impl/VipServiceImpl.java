package com.adminfortrain.vipAccount.impl;

import com.adminfortrain.vipAccount.model.Vip;
import com.adminfortrain.vipAccount.mapper.VipMapper;
import com.adminfortrain.vipAccount.service.IVipService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杰
 * @since 2020-04-25
 */
@Service
public class VipServiceImpl extends ServiceImpl<VipMapper, Vip> implements IVipService {
    @Autowired
    VipMapper vipMapper;

    @Transactional
    public void deletebyid(int id){
        vipMapper.deleteById(id);
    }

    @Transactional
    public void updatevip(Vip vip, UpdateWrapper updateWrapper){
        vipMapper.update(vip,updateWrapper);
    }

    @Transactional
    public void insertVip(Vip vip){
        vipMapper.insert(vip);
    }
}
