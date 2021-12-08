package com.example.demo2.mapper;

import java.util.ArrayList;

import com.example.demo2.model.Food;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FoodMapper {
    ArrayList<Food> getAllFood();
    Food getFoodById(int id);
    int addNewFood(Food food);
    int updateExistingFood(Food food);
    int deleteExistingFood(int id);
}