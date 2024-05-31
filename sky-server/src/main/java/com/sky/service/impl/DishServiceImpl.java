package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Override
    @Transactional //涉及多个数据表操作 加上Transactional
    //事务是一组要么全部执行成功要么全部执行失败的操作。
    // 如果事务中的任何一个操作失败，整个事务都会回滚到事务开始前的状态。
    // 这对于保证数据的一致性和完整性非常重要。
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //向菜品表插入一条数据
        dishMapper.save(dish);
        //获取insert语句生成的组件值
        Long id = dish.getId();
        //向口味表插入n条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();

        if (flavors !=null && flavors.size()>0){
            flavors.forEach(flavor -> flavor.setDishId(id));
            dishFlavorMapper.insertBatch(flavors);
        }
    }

}
