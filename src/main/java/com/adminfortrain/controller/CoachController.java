package com.adminfortrain.controller;


import com.adminfortrain.Coa.impl.CoachServiceImpl;
import com.adminfortrain.Coa.mapper.CoachMapper;
import com.adminfortrain.Coa.model.Coach;
import com.adminfortrain.vipAccount.model.Vip;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("/coa")
public class CoachController {

    @Autowired
    CoachMapper coachMapper;

    @Autowired
    CoachServiceImpl coachService;

    @GetMapping("/add")
    public String addCoach(){
        return "addcoach";
    }

    @PostMapping("/add")
    public String addvip1(
            HttpServletRequest request,
            Model model
    ){
        String email = request.getParameter("Email");
        String coachName = request.getParameter("coachname");
        String telephone = request.getParameter("telephone");
        String age = request.getParameter("age");



        Coach coach=new Coach();

        try {
            coach.setCoachName(coachName);
            coach.setCoachAge(Integer.parseInt(age));
            coach.setCoachTelephone(telephone);
            coach.setCoachEmail(email);
        }catch (Exception e){
            model.addAttribute("error","所有信息必填，请务必填写");
            return "addcoach";
        }


        coachService.insertVip(coach);
        return "addcoach";
    }

    @RequestMapping("/Coach")
    public String showAll(
            @RequestParam(value = "page",defaultValue = "1") int currentPage,
            Model model
    ){
        Page<Coach> coachPage = new Page<>(currentPage,5);
        coachMapper.selectPage(coachPage,null);

        //导航条页数显示
        long current = coachPage.getCurrent();
        ArrayList<Object> list = new ArrayList<>();
        list.add(current);
        for(int i=1;i<=3;i++){
            if( current- i > 0){
                list.add(0,current-i);
            }

            if(current + i <= coachPage.getPages()){
                list.add(current + i);
            }
        }

        model.addAttribute("page",coachPage);
        model.addAttribute("coachs",coachPage.getRecords());
        model.addAttribute("totalPage", list);
        return "coachpage";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(
            @RequestParam("id") int id
    ){
        try {
            coachService.deletebyid(id);
        } catch (Exception e) {
            return "null";
        }
        return "删除成功";
    }


    //弹出层编辑
    @GetMapping("/getit")
    public String getit(
            HttpServletRequest request,
            Model model,
            int coachid
    ){
        int id = coachid;
        Coach coach = coachMapper.selectById(id);
        model.addAttribute("email",coach.getCoachEmail());
        model.addAttribute("age",coach.getCoachAge());
        model.addAttribute("coachname",coach.getCoachName());
        model.addAttribute("telephone",coach.getCoachTelephone());
        model.addAttribute("coachid",id);
        return "verifypopupforcoach";
    }

    @RequestMapping("/search")
    public String check(@RequestParam("search_id") int id,
                        Model model
    ){

        Coach coach = coachMapper.selectById(id);

        if(coach == null ){
            model.addAttribute("notfind","无此会员");
            return "redirect:/coa/Coach";
        }


        model.addAttribute("coachs",coach);


        return "checkcoach";
    }

    @RequestMapping("/verifycoach/success")
    public String verifycoach(
            Model model,
            HttpServletRequest request
    ){
        int id = Integer.parseInt(request.getParameter("coachid"));
        Coach coach = new Coach();
        String email = request.getParameter("Email");
        String coachname = request.getParameter("coachname");
        String telephone = request.getParameter("telephone");
        String age = request.getParameter("age");

        try {
            coach.setCoachName(coachname);
            coach.setCoachAge(Integer.parseInt(age));
            coach.setCoachTelephone(telephone);
            coach.setCoachEmail(email);
        }catch (Exception e){
            model.addAttribute("error","所有信息必填，请务必填写");
            return "amm";
        }

        UpdateWrapper<Coach> wrapper = new UpdateWrapper<>();
        wrapper.eq("coachid",id);
        coachService.updatevip(coach, wrapper);
        return "redirect:/main";
    }
}
