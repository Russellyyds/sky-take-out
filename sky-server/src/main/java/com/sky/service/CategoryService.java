package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    void deleteCategory(Long id);

    void startOrStopCategory(Integer status, Long id);

    void save(CategoryDTO categoryDTO);

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void update(CategoryDTO categoryDTO);

    List<Category> listCategory(Integer type);
}
