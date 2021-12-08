package com.example.demo2.model;

import java.util.Date;

import lombok.Data;

@Data
public class Food {
    public int id;
    public String name;
    public int calories;
    public double price;
    public String currency;
    public Date date_created;
}
