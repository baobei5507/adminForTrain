package com.adminfortrain.Coa.impl;

import com.adminfortrain.Coa.model.Coach;
import com.adminfortrain.Coa.mapper.CoachMapper;
import com.adminfortrain.Coa.service.ICoachService;
import com.adminfortrain.vipAccount.model.Vip;
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
 * @since 2020-06-02
 */
@Service
public class CoachServiceImpl extends ServiceImpl<CoachMapper, Coach> implements ICoachService {

    @Autowired
    CoachMapper coachMapper;

    @Transactional
    public void deletebyid(int id) {
        coachMapper.deleteById(id);
    }

    @Transactional
    public void insertVip(Coach coach) {
       coachMapper.insert(coach);
    }

    @Transactional
    public void updatevip(Coach coach, UpdateWrapper<Coach> wrapper) {
        coachMapper.update(coach,wrapper);
    }
}
