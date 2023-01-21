package com.example.stockmarket.market.core.item;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ItemPriceChangeGenerator {

    public double generatePriceChange(int oldQuantity, int transactionQuantity, double originalPrice) {
        return ((double)transactionQuantity / (double)oldQuantity) * originalPrice * new Random().nextDouble();
    }
}
