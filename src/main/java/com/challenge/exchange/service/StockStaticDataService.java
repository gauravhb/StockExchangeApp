package com.challenge.exchange.service;

import com.challenge.exchange.model.Stock;

import java.util.Map;

public interface StockStaticDataService {

    Map<String,Stock> getAllExchangeListedStocks();

    Stock addStockStaticData(Stock stock);

    Stock removeStockStaticData(String symbol);

    Stock findStock(String symbol);

    void clearAllStockStaticData();


}
