package com.adminfortrain.vipAccount.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 杰
 * @since 2020-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Vip implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String vipname;

    private String sex;

    private String telephone;

    private Integer age;

    private String email;

    private Date begintime;

    private Date endtime;

    @TableField(fill = FieldFill.INSERT,value = "gmt_create")
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE,value = "gmt_modified")
    private Date gmtModified;

    /**
     * logicdelete

     */
    @TableLogic
    private Integer deleted;


}
