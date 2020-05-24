package com.adminfortrain.controller;


import com.adminfortrain.admin.impl.UserServiceImpl;
import com.adminfortrain.admin.mapper.UserMapper;
import com.adminfortrain.admin.model.User;
import com.adminfortrain.task.Schedule;
import com.adminfortrain.vipAccount.impl.VipServiceImpl;
import com.adminfortrain.vipAccount.mapper.VipMapper;
import com.adminfortrain.vipAccount.model.Vip;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Controller
public class indexController {



    @Resource
    private RedisTemplate<String,Integer> template;

    @Autowired
    VipServiceImpl vipService;

    @Autowired
    VipMapper vipMapper;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    Schedule schedule;

    //退出页面
    @RequestMapping("/logout")
    public String logout(){
        //获取当前session中用户并清除
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();

        return "redirect:/";
    }

    //登录页面
    @GetMapping({"/","/index"})
    public String index(){
        return "index";
    }

    //主页
    @GetMapping("/main")
    public String mainpage(
            Model model,
            @RequestParam(value = "pageNum", defaultValue = "1") int currentpage
    ){

        //分页查询
        Page<Vip> vipPage = new Page<>(currentpage,5);
         vipMapper.selectPage(vipPage, null);
        List<Vip> vips = vipPage.getRecords();


        // 验证是否过期
        for (Vip vip : vips) {

            if(vip.getEndtime().compareTo(new Date()) == -1)
                vipService.deletebyid(vip.getId());
        }


        //导航条页数显示
        long current = vipPage.getCurrent();
        ArrayList<Object> list = new ArrayList<>();
        list.add(current);
        for(int i=1;i<=3;i++){
            if( current- i > 0){
                list.add(0,current-i);
            }

            if(current + i <= vipPage.getPages()){
                list.add(current + i);
            }
        }

        model.addAttribute("page",vipPage);
        model.addAttribute("vips",vipPage.getRecords());
        model.addAttribute("totalPage", list);

        return "mainpage";
    }

    @RequestMapping("/login")
    public String login(String username, String password, Model model, HttpServletRequest request){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();

        //封装用户的登录信息
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        //session获取当前用户名
        request.getSession().setAttribute("user",token.getUsername());
        request.getSession().setAttribute("token",token);

        try {
            //如果登录成功，进入主页
            subject.login(token);
            return "redirect:/main";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg","账号错误或不存在");
            return "index";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return  "index";
        }

    }

    @RequestMapping("/sign")
    public String sign(){
        return "sign";
    }

    @RequestMapping("/handlesign")
    public String handlesign(
            HttpServletRequest request,
            Model model){
        User user = new User();
        String username = request.getParameter("username");

        HashMap<String, Object> map = new HashMap<>();
        map.put("username",username);
        if(userMapper.selectByMap(map).size() !=0 ){
            model.addAttribute("error","该用户已存在，请换一个用户名");
            return "sign";
        }

        if(
               ! ((request.getParameter("password"))
                 .equals(request.getParameter("password2")))
        ){
            model.addAttribute("error","两次密码输入不同，请再次尝试");
            return "sign";
        }

        if( StringUtils.isBlank(request.getParameter("password") )|| StringUtils.isBlank(request.getParameter("password2") )){
            model.addAttribute("error","密码不能为空");
            return "sign";
        }
        user.setUsername(username);
        user.setPassword(request.getParameter("password"));
        user.setDeleted(0);
        user.setPerms("user:add");
        userService.insertUser(user);
        return "redirect:/";
    }

    @RequestMapping("/checkout")
    @ResponseBody
    public String checkout(String username){

        System.out.println(username);

        User user = null;
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",username);
            user=userMapper.selectOne(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            return user.getUsername();
        } catch (Exception e) {
            return "null";
        }
    }

    @RequestMapping("/data")
    public  String data(){
        return "data";
    }

    @RequestMapping("/agesource")
    @ResponseBody
    public List<Integer> datasource(){

        Integer ten = null;
        Integer two = null;
        Integer three = null;

        if(template.opsForValue().get("ten") == null){
            List<Object> vip1 = new ArrayList<>();
            schedule.setRediskey("ten");
            schedule.setVip(vip1);
            schedule.setStart(10);
            schedule.setEnd(20);
            schedule.timeup();
            System.out.println("1没走缓存");
            ten = vip1.size();
        }else {
            System.out.println("1走了缓存");
            ten = template.opsForValue().get("ten");
        }

        if(template.opsForValue().get("two") == null){
            List<Object> vip2 = new ArrayList<>();
            schedule.setRediskey("two");
            schedule.setVip(vip2);
            schedule.setStart(20);
            schedule.setEnd(30);
            schedule.timeup();
            System.out.println("2没走缓存");
        two = vip2.size();
        }else {
            System.out.println("2走了缓存");
           two = template.opsForValue().get("two");
        }

        if(template.opsForValue().get("three") == null){
            List<Object> vip3 = new ArrayList<>();
            schedule.setRediskey("three");
            schedule.setVip(vip3);
            schedule.setStart(30);
            schedule.setEnd(40);
            schedule.timeup();
        System.out.println("3没走缓存");
        three = vip3.size();
        } else {
            System.out.println("3走了缓存");
            three = template.opsForValue().get("three");
        }
        ArrayList<Integer> list = new ArrayList<>();
        list.add(ten);
        list.add(two);
        list.add(three);

        return list;
    }



    @RequestMapping("/sexsource")
    @ResponseBody
    public List<Integer> sexsource(){
        Integer men = null;
        Integer girls = null;

        if (template.opsForValue().get("man") == null) {
            QueryWrapper<Vip> man = new QueryWrapper<>();
            man.eq("sex","男");
            List<Object> manCou = vipMapper.selectObjs(man);
            template.opsForValue().set("man",manCou.size());
            men = manCou.size();
        }else {
            men = template.opsForValue().get("man");
        }

        if (template.opsForValue().get("girl") == null) {
        QueryWrapper<Vip> gril = new QueryWrapper<>();
        gril.eq("sex","女");
        List<Object> girlCou = vipMapper.selectObjs(gril);
        template.opsForValue().set("girl",girlCou.size());
        girls = girlCou.size();
        }else {
            girls = template.opsForValue().get("girl");
        }

        ArrayList<Integer> list = new ArrayList<>();
        list.add(men);
        list.add(girls);

        return list;
    }

    @RequestMapping("/clock")
    public String clock(){
        return "clockin";
    }

    @RequestMapping("/clock/check")
    @ResponseBody
    public Vip clockcheck(int VIPID){
        Vip vip1 = new Vip();

        Vip vip = vipMapper.selectById(VIPID);

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        String format1 = dateFormat1.format(new Date());
        Date parse=null;
        try {
            parse= dateFormat1.parse(format1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(vip.getSigncation() != 0 && vip.getSigndate().compareTo(parse) == 0){
                return null;
            }

        UpdateWrapper<Vip> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",vip.getId());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(new Date());
        System.out.println(format);
        Date date = null;
        try {
            date = dateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        vip1.setSigncation(1);
        vip1.setSigncount(vip.getSigncount()+1);
        vip1.setSigndate(date);

        vipService.updatevip(vip1, updateWrapper);
        return vip;
    }

    @RequestMapping("/unknow")
    @ResponseBody
    public String unknown(){

        return "权限不足";
    }

    /*用于给swagger扫描到此实体类！*/
    @RequestMapping("/scan")
    public User scan(){
        return new User();
    }



}
