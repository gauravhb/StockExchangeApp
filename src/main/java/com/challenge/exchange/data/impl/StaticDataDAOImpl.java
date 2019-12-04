package com.challenge.exchange.data.impl;

import com.challenge.exchange.data.StaticDataDAO;
import com.challenge.exchange.model.Stock;

import java.util.HashMap;
import java.util.Map;

public class StaticDataDAOImpl implements StaticDataDAO {

    private static Map<String, Stock> stockRepo = new HashMap<>();


    @Override
    public Stock addStockStaticData(Stock stock) {
        stockRepo.put(stock.getSymbol(), stock);
        return stock;
    }

    @Override
    public Map<String, Stock> getAllExchangeListedStocks() {
        return stockRepo;
    }
}
