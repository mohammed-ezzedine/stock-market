package com.example.stockmarket.budget.core;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class BudgetGenerator {

    public double generate() {
        return 1000 + new Random().nextInt(500);
    }
}
