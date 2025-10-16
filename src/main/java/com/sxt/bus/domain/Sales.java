package com.sxt.bus.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品销售实体类
 * </p>
 *
 * @author 老雷
 * @since 2019-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_sales")
public class Sales implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 销售单ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** 客户ID */
    private Integer customerid;

    /** 支付方式 */
    private String paytype;

    /** 销售时间 */
    private Date salestime;

    /** 操作员 */
    private String operateperson;

    /** 销售数量 */
    private Integer number;

    /** 备注 */
    private String remark;

    /** 销售价格 */
    private Double saleprice;

    /** 商品ID */
    private Integer goodsid;

    /** 非表字段 —— 客户名称 */
    @TableField(exist = false)
    private String customername;

    /** 非表字段 —— 商品名称 */
    @TableField(exist = false)
    private String goodsname;

    /** 非表字段 —— 商品规格 */
    @TableField(exist = false)
    private String size;
}
