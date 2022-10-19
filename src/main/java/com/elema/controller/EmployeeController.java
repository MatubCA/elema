package com.elema.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elema.common.Results;
import com.elema.entity.Employee;
import com.elema.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.time.LocalDateTime;


/**
 * 员工控制器类
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * 员工登录
     * @param employee
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Results<Employee> login(@RequestBody Employee employee, HttpServletRequest request){
        // 用户密码加密为md5格式
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 根据用户名(唯一)查询出用户
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(wrapper);

        // 判断用户名是否正确(是否查询出对象)
        if (emp == null) {
            return Results.error("用户名不存在,请输入正确用户名!");
        }

        // 判断密码是否正确
        if (!emp.getPassword().equals(password)){
            return Results.error("密码错误,请输入正确密码!");
        }

        // 判断用户的禁用状态
        if (emp.getStatus() == 0){
            return Results.error("该员工已被禁用!");
        }

        // 成功登录
        request.getSession().setAttribute("employee",emp.getId());
        return Results.success(emp);
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Results<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return Results.success("退出成功!");
    }


    /**
     * 新增员工
     * @param employee
     * @param request
     * @return
     */
    @PostMapping
    public Results<String> save(@RequestBody Employee employee,HttpServletRequest request){
        // 设置初始密码,并使用MD5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("12345".getBytes()));

        // 设置创建时间,修改时间
        /*employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());*/

        //设置创建人,修改人
        /*Long emp = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(emp);
        employee.setUpdateUser(emp);*/

        // 插入数据库
        employeeService.save(employee);
        return Results.success("新增员工成功");
    }

    /**
     * 分页查询以及模糊查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Results<Page> page(int page, int pageSize, String name){
        // 分页构造器
        Page pageInfo = new Page(page, pageSize);
        // 条件查询构造器
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper();
        wrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        wrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo,wrapper);
        return Results.success(pageInfo);
    }

    /**
     *更新员工信息,重新设置ObjectMapper,解决前端传递Long类型数据进度丢失,而导致传递的id不一致的问题
     * @param employee
     * @param request
     * @return
     */
    @PutMapping
    public Results<String> update(@RequestBody Employee employee,HttpServletRequest request){
        // 更新修改人和修改时间
        /*employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));*/

        // 调用service
        employeeService.updateById(employee);

        return Results.success("更新成功!");
    }

    /**
     * 在修改时,需要跳转到add.html页面,并发送id进行用户查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Results<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if (employee != null){
            return Results.success(employee);
        }
        return Results.error("没有查询到员工信息");
    }
}
