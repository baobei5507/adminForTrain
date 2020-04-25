package com.adminfortrain.controller;


import com.adminfortrain.vipAccount.impl.VipServiceImpl;
import com.adminfortrain.vipAccount.mapper.VipMapper;
import com.adminfortrain.vipAccount.model.Vip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequestMapping("/vip")
public class vipController {

    @Autowired
    VipServiceImpl vipService;

    @Autowired
    VipMapper vipMapper;

    @GetMapping("/add")
    public String addvip(){
        return "addvip";
    }

    @PostMapping("/add")
    public String addvip1(
            HttpServletRequest request,
            Model model
    ){
        String email = request.getParameter("Email");
        String vipname = request.getParameter("vipname");
        String telephone = request.getParameter("telephone");
        String sex = request.getParameter("sex");
        String age = request.getParameter("age");
        String endtime = request.getParameter("endtime");



        Vip vip=new Vip();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date=simpleDateFormat.parse(endtime);
            vip.setEndtime(date);
        } catch (ParseException e) {
            model.addAttribute("errordate","日期格式错误");
            return "addvip";
        }

        try {
            vip.setVipname(vipname);
            vip.setAge(Integer.parseInt(age));
            vip.setTelephone(telephone);
            vip.setSex(sex);
            vip.setEmail(email);
        }catch (Exception e){
            model.addAttribute("error","所有信息必填，请务必填写");
            return "addvip";
        }

        vip.setBegintime(new Date());

        vipMapper.insert(vip);
        return "addvip";
    }
}
