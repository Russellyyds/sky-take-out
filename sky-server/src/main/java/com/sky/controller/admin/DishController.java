package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "菜品相关接口")
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @PostMapping
    @ApiOperation(value = "新增菜品")
    public Result<String> save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品");
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation(value = "菜品分页查询")
    public Result<PageResult> getDishPage(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询功能执行");
        log.info("{}",dishPageQueryDTO);
        PageResult dishPage = dishService.getDishPage(dishPageQueryDTO);
        return Result.success(dishPage);
    }
    @DeleteMapping
    @ApiOperation(value = "菜品批量删除")
    public Result<String> deleteBatch(@RequestParam("ids")List<Long> ids){
        log.info("delete批量删除被执行{}",ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询菜品")
    public Result<DishVO> queryById(@PathVariable("id") Long id){
        DishVO dishVo= dishService.queryByIdWithFlavor(id);
        return Result.success(dishVo);
    }
    @PutMapping
    @ApiOperation(value = "修改菜品")
    public Result<String> updateDish(@RequestBody DishDTO dishDTO){
        log.info("{}","修改菜品");
        dishService.updateDishWithFlavor(dishDTO);
        return Result.success();
    }

}
