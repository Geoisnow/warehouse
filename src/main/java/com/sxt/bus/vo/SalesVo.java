package com.sxt.bus.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.sxt.bus.domain.Sales;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 销售模块的前端传输对象（VO）
 * </p>
 *
 * @author
 * @since 2019-09-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SalesVo extends Sales {

    private static final long serialVersionUID = 1L;

    /** 当前页 */
    private Integer page = 1;

    /** 每页条数 */
    private Integer limit = 10;

    /** 开始时间（查询区间） */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间（查询区间） */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
