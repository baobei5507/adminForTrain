package com.adminfortrain.Coa.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Coach implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "coachId", type = IdType.AUTO)
    private Integer coachId;

    @TableField("coachName")
    private String coachName;

    @TableField("coachAge")
    private Integer coachAge;

    @TableField("coachTelephone")
    private String coachTelephone;

    @TableField("coachEmail")
    private String coachEmail;


}
