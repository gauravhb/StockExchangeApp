package com.challenge.exchange.service.impl;

import com.challenge.exchange.data.StaticDataDAO;
import com.challenge.exchange.model.Stock;
import com.challenge.exchange.service.StockStaticDataService;

import java.util.List;
import java.util.Map;

public class StockStaticDataServiceImpl implements StockStaticDataService {

    StaticDataDAO stockDAO;
    public StockStaticDataServiceImpl(StaticDataDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    @Override
    public Map<String,Stock> getAllExchangeListedStocks() {
        return stockDAO.getAllExchangeListedStocks();
    }

    @Override
    public Stock addStockStaticData(Stock stock) {
        //validate Mandatory fields

        return stockDAO.addStockStaticData(stock);
    }

    @Override
    public boolean removeStockStaticData(String symbol) {
        return false;
    }

    @Override
    public Stock findStock(String symbol) {
        return getAllExchangeListedStocks().get(symbol);
    }


}
