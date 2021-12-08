package com.example.demo2.controller;

import java.util.ArrayList;
import java.util.Date;

import com.example.demo2.model.Food;
import com.example.demo2.service.FoodService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodController {

    @Autowired
    FoodService foodService;

    @GetMapping("/foods")
    public ArrayList<Food> foods() {
        return foodService.getAllFood();
    }

    @GetMapping("/food/{id}")
    public Food food(@PathVariable int id) {
        return foodService.getFoodById(id);
    }

    @PostMapping("/addFood")
    public String addFood(@RequestBody Food food) {
        food.date_created = new Date();
        if (food.date_created == null || food.calories == 0 || food.currency == "" || food.name == ""
                || food.price == 0)
            return "One or more fields is/are empty, please have all of the fields filled up.";
        foodService.addNewFood(food);
        return "Successfully added into the database.";
    }

    @PutMapping("/updateFood")
    public String updateFood(@RequestBody Food food) {
        int numUpdated = foodService.updateExistingFood(food);
        return numUpdated > 0 ? "Update successful." : "Update failed. Check if your id is correct.";
    }

    @DeleteMapping("/deleteFood/{id}")
    public String deleteFood(@PathVariable int id) {
        int numDeleted = foodService.deleteExistingFood(id);
        return numDeleted > 0 ? "Successfully deleted id " + id + "." : "Id doesn't exist.";
    }
}
