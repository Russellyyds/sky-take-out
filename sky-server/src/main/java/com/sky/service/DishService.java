package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;


public interface DishService {
    void saveWithFlavor(DishDTO dishDTO);

    PageResult getDishPage(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);

    DishVO queryByIdWithFlavor(Long id);

    void updateDishWithFlavor(DishDTO dishDTO);

    List<DishVO> listWithFlavors(Dish dish);

    void startOrStop(Integer status,Long id);
}
