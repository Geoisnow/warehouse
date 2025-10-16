package com.sxt.bus.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxt.bus.domain.Customer;
import com.sxt.bus.domain.Goods;
import com.sxt.bus.domain.Sales;
import com.sxt.bus.service.CustomerService;
import com.sxt.bus.service.GoodsService;
import com.sxt.bus.service.SalesService;
import com.sxt.bus.vo.SalesVo;
import com.sxt.sys.common.DataGridView;
import com.sxt.sys.common.ResultObj;
import com.sxt.sys.common.WebUtils;
import com.sxt.sys.domain.User;

/**
 * <p>
 *  销售管理前端控制器
 * </p>
 *
 * @author 老雷
 * @since 2019-09-28
 */
@RestController
@RequestMapping("sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 查询所有销售记录
     */
    @RequestMapping("loadAllSales")
    public DataGridView loadAllSales(SalesVo salesVo) {
        IPage<Sales> page = new Page<>(salesVo.getPage(), salesVo.getLimit());
        QueryWrapper<Sales> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(salesVo.getCustomerid() != null && salesVo.getCustomerid() != 0,
                "customerid", salesVo.getCustomerid());
        queryWrapper.eq(salesVo.getGoodsid() != null && salesVo.getGoodsid() != 0,
                "goodsid", salesVo.getGoodsid());
        queryWrapper.ge(salesVo.getStartTime() != null, "salestime", salesVo.getStartTime());
        queryWrapper.le(salesVo.getEndTime() != null, "salestime", salesVo.getEndTime());
        queryWrapper.like(StringUtils.isNotBlank(salesVo.getOperateperson()),
                "operateperson", salesVo.getOperateperson());
        queryWrapper.like(StringUtils.isNotBlank(salesVo.getRemark()),
                "remark", salesVo.getRemark());
        queryWrapper.orderByDesc("salestime");

        this.salesService.page(page, queryWrapper);
        List<Sales> records = page.getRecords();

        for (Sales sales : records) {
            Customer customer = this.customerService.getById(sales.getCustomerid());
            if (null != customer) {
                sales.setCustomername(customer.getCustomername());
            }
            Goods goods = this.goodsService.getById(sales.getGoodsid());
            if (null != goods) {
                sales.setGoodsname(goods.getGoodsname());
                sales.setSize(goods.getSize());
            }
        }

        return new DataGridView(page.getTotal(), records);
    }

    /**
     * 添加销售记录
     */
    @RequestMapping("addSales")
    public ResultObj addSales(SalesVo salesVo) {
        try {
            salesVo.setSalestime(new Date());
            User user = (User) WebUtils.getSession().getAttribute("user");
            salesVo.setOperateperson(user.getName());
            // ✅ 调用销售服务层，由它自动扣库存
            this.salesService.save(salesVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }
    /**
     * 修改销售记录
     */
    @RequestMapping("updateSales")
    public ResultObj updateSales(SalesVo salesVo) {
        try {
            this.salesService.updateById(salesVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除销售记录
     */
    @RequestMapping("deleteSales")
    public ResultObj deleteSales(Integer id) {
        try {
            this.salesService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
