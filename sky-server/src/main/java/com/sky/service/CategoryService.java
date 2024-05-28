package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Service;

public interface CategoryService {
    void deleteCategory(Long id);

    void startOrStopCategory(Integer status, Long id);

    void save(CategoryDTO categoryDTO);

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void update(CategoryDTO categoryDTO);
}
