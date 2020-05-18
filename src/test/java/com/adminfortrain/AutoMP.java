package com.adminfortrain;




import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

public class AutoMP {
    public static void main(String[] args) {

        //mp自动生成器
        AutoGenerator mpg = new AutoGenerator();

        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor("杰");//设置作者名
        String projectPath = System.getProperty("user.dir");//当前项目目录
        globalConfig.setOutputDir(projectPath+"/src/main/java");//设置生成文件目录
        globalConfig.setOpen(false); //设置生成完毕后是否打开文件夹
        globalConfig.setFileOverride(true);//是否覆盖原本生成的代码
        globalConfig.setIdType(IdType.AUTO);//设置id自增策略
        globalConfig.setDateType(DateType.ONLY_DATE);//设置日期类型
//        globalConfig.setSwagger2(); 还未学习swagger
        mpg.setGlobalConfig(globalConfig);

        //设置数据源
        DataSourceConfig sourceConfig = new DataSourceConfig();
        sourceConfig.setUsername("root");
        sourceConfig.setPassword("root");
        sourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        sourceConfig.setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false");
        sourceConfig.setDbType(DbType.MYSQL);//设置数据库类型
        mpg.setDataSource(sourceConfig);


        //包配置
        PackageConfig packageInfo = new PackageConfig();
        packageInfo.setModuleName("peopleCount");//设置模块名字,生成的代码会放在这里面
        packageInfo.setParent("com.adminfortrain"); //父类
        packageInfo.setEntity("model");//存放实体类包名
        packageInfo.setService("service");
        packageInfo.setServiceImpl("impl");
        packageInfo.setMapper("mapper");
        mpg.setPackageInfo(packageInfo);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("datetime");//设置需要构建的数据库表名
        strategy.setNaming(NamingStrategy.underline_to_camel);//转驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//转驼峰命名
        strategy.setEntityLombokModel(true); //开启驼峰命名
//        strategy.setRestControllerStyle(true); //controller自己写 不需要配置
//        strategy.setLogicDeleteFieldName("deleted");//设置逻辑删除
        TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);//设置创建时间填充策略
        TableFill gmtModified = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);//修改时间
        ArrayList<TableFill> list = new ArrayList<>();
        list.add(gmtCreate);
        list.add(gmtModified);
        strategy.setTableFillList(list);
        mpg.setStrategy(strategy);

        //执行
        mpg.execute();



    }
}
