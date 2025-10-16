package com.sxt.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.bus.domain.Goods;
import com.sxt.bus.domain.Sales;
import com.sxt.bus.mapper.GoodsMapper;
import com.sxt.bus.mapper.SalesMapper;
import com.sxt.bus.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
@Transactional
public class SalesServiceImpl extends ServiceImpl<SalesMapper, Sales> implements SalesService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public boolean save(Sales entity) {
        Goods goods = goodsMapper.selectById(entity.getGoodsid());
        if (goods != null) {
            goods.setNumber(goods.getNumber() - entity.getNumber());
            goodsMapper.updateById(goods);
        }
        return super.save(entity);
    }

    @Override
    public boolean updateById(Sales entity) {
        Sales oldSales = this.baseMapper.selectById(entity.getId());
        Goods goods = this.goodsMapper.selectById(entity.getGoodsid());
        if (goods != null) {
            goods.setNumber(goods.getNumber() + oldSales.getNumber() - entity.getNumber());
            this.goodsMapper.updateById(goods);
        }
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        Sales sales = this.baseMapper.selectById(id);
        Goods goods = this.goodsMapper.selectById(sales.getGoodsid());
        if (goods != null) {
            goods.setNumber(goods.getNumber() + sales.getNumber());
            this.goodsMapper.updateById(goods);
        }
        return super.removeById(id);
    }
}
