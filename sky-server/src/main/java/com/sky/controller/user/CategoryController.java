package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Slf4j
@Api(tags = "C端 分类接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    @ApiOperation(value ="查询分类")
    public Result<List<Category>> list(@RequestParam(name = "type",required = false) Integer type) {
        System.out.println(type);
        List<Category> categories = categoryService.listCategory(type);
        return Result.success(categories);
    }
}
