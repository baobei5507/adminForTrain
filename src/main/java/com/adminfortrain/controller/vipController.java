package com.adminfortrain.controller;


import com.adminfortrain.vipAccount.impl.VipServiceImpl;
import com.adminfortrain.vipAccount.mapper.VipMapper;
import com.adminfortrain.vipAccount.model.Vip;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @GetMapping("/verifyvip")
    public String vervip(
            HttpServletRequest request,
                          Model model
    ){
        int id = Integer.parseInt(request.getParameter("id"));

        Vip vip = vipMapper.selectById(id);
        model.addAttribute("email",vip.getEmail());
        model.addAttribute("age",vip.getAge());
        model.addAttribute("sex",vip.getSex());
        model.addAttribute("vipname",vip.getVipname());
        model.addAttribute("telephone",vip.getTelephone());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String endtime = simpleDateFormat.format(vip.getEndtime());
        model.addAttribute("endtime",endtime);

        request.getSession().setAttribute("id",id);
        return "verifyvip";
    }

    @PostMapping("/verifyvip/success")
    public String verifyvip(
            Model model,
            HttpServletRequest request
    ){
        int id = (int) request.getSession().getAttribute("id");

        Vip vip = new Vip();
        String email = request.getParameter("Email");
        String vipname = request.getParameter("vipname");
        String telephone = request.getParameter("telephone");
        String sex = request.getParameter("sex");
        String age = request.getParameter("age");
        String endtime = request.getParameter("endtime");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date=simpleDateFormat.parse(endtime);
            vip.setEndtime(date);
        } catch (ParseException e) {
            model.addAttribute("errordate","日期格式错误");
            return "verifyvip";
        }

        try {
            vip.setVipname(vipname);
            vip.setAge(Integer.parseInt(age));
            vip.setTelephone(telephone);
            vip.setSex(sex);
            vip.setEmail(email);
        }catch (Exception e){
            model.addAttribute("error","所有信息必填，请务必填写");
            return "verifyvip";
        }

        UpdateWrapper<Vip> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        vipService.update(vip, wrapper);
        return "redirect:/main";
    }

    @GetMapping("/delete")
    public String delete(
            HttpServletRequest request,
            Model model
    ){
        int id = Integer.parseInt(request.getParameter("deleteid"));
        Vip vip = vipMapper.selectById(id);


        model.addAttribute("vip",vip);
        request.getSession().setAttribute("deleteid",id);
        return "deletevip";
    }

    @GetMapping("/delete/commit")
    public String suredelete(
            HttpServletRequest request
    ){
        int id = Integer.parseInt(request.getParameter("deleteid"));
        vipMapper.deleteById(id);

        return "redirect:/main";
    }

}
