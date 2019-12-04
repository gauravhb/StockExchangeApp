package com.challenge.exchange.data;

import com.challenge.exchange.model.Stock;

import java.util.Map;

public interface StaticDataDAO {
    Stock addStockStaticData(Stock stock);
    Map<String,Stock> getAllExchangeListedStocks();
}
