package com.elema.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.elema.entity.User;
import com.elema.mapper.UserMapper;
import com.elema.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}