package com.elema.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.elema.entity.Orders;

public interface OrderService extends IService<Orders> {
    void submit(Orders orders);
}