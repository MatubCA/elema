package com.elema.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.elema.dto.SetmealDto;
import com.elema.entity.Setmeal;
import com.elema.entity.SetmealDish;
import com.elema.mapper.SetmealDishMapper;
import com.elema.mapper.SetmealMapper;
import com.elema.service.SetmealDishService;
import com.elema.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {

}
