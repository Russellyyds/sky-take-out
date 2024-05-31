package com.sky.service;

import com.sky.dto.DishDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


public interface DishService {
    void saveWithFlavor(DishDTO dishDTO);
}
