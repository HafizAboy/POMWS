package com.example.demo2.service.impl;

import java.util.ArrayList;

import com.example.demo2.mapper.FoodMapper;
import com.example.demo2.model.Food;
import com.example.demo2.service.FoodService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("FoodServiceImpl")
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodMapper productMapper;

    @Override
    public ArrayList<Food> getAllFood() {
        return productMapper.getAllFood();
    }

    @Override
    public Food getFoodById(int id) {
        return productMapper.getFoodById(id);
    }

    @Override
    public int addNewFood(Food food) {
        return productMapper.addNewFood(food);
    }

    @Override
    public int updateExistingFood(Food food) {
        return productMapper.updateExistingFood(food);
    }

    @Override
    public int deleteExistingFood(int id) {
        return productMapper.deleteExistingFood(id);
    }
}
