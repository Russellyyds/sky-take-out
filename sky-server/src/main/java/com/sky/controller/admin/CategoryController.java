package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/category")
@Api(tags = "category相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "新增分类")
    @PostMapping
    public Result<String> save(CategoryDTO categoryDTO){
        log.info("Saving category");
        categoryService.save(categoryDTO);
        return Result.success();
    }
    @ApiOperation(value = "Get category")
    @GetMapping("/page")
    public Result<PageResult> getCategory(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("Query参数:"+categoryPageQueryDTO);
        PageResult pageResult= categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }
    @DeleteMapping
    @ApiOperation("根据id删除分类")
    public Result deleteCategory(@RequestParam Long id){
        categoryService.deleteCategory(id);
        return Result.success();
    }
    @ApiOperation(value = "菜品分类启用还是禁用")
    @PostMapping("/status/{status}")
    public Result editStatus(@PathVariable("status") Integer status,@RequestParam Long id){
        log.info("菜品分类启用还是禁用");
        categoryService.startOrStopCategory(status,id);
        return Result.success();
    }
    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改分类")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO){
        categoryService.update(categoryDTO);
    return Result.success();
    }

}
