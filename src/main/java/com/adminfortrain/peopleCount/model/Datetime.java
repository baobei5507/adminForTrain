package com.adminfortrain.peopleCount.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Datetime implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "tdate", type = IdType.AUTO)
    private Date tdate;

    private Integer count;


}
