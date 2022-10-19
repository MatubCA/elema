package com.elema.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.elema.common.Results;
import com.elema.entity.Category;
import com.elema.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public Results<String> save(@RequestBody Category category){
        log.info("category:{}",category);
        categoryService.save(category);
        return Results.success("新增分类成功");
    }

    @GetMapping("/page")
    public Results<Page> page(int page,int pageSize){
        Page<Category> categoryPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        Page<Category> page1 = categoryService.page(categoryPage, wrapper);
        return Results.success(page1);
    }

    @DeleteMapping
    public Results<String> delete(Long id){
        /*LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getId,id);
        categoryService.remove(wrapper);*/
        categoryService.remove(id);
        return Results.success("删除成功");
    }

    @PutMapping
    public Results<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return Results.success("修改成功");
    }

    /**
     * 根据条件查询分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public Results<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return Results.success(list);
    }
}
