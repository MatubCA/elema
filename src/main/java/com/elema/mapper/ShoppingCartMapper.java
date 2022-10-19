package com.elema.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elema.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}