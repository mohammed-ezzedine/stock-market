package com.example.stockmarket.market.core.item;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ItemPriceChangeGenerator {

    public double generatePriceIncrease(int oldQuantity, int soldQuantity, double originalPrice) {
        return ((double)soldQuantity / (double)oldQuantity) * originalPrice * new Random().nextDouble();
    }
}
