package com.example.demo2.service;

import java.util.ArrayList;

import com.example.demo2.model.Food;

public interface FoodService {
    ArrayList<Food> getAllFood();
    Food getFoodById(int id);
    int addNewFood(Food food);
    int updateExistingFood(Food food);
    int deleteExistingFood(int id);
}
