package com.elema.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.elema.dto.DishDto;
import com.elema.entity.Dish;

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dto);
}