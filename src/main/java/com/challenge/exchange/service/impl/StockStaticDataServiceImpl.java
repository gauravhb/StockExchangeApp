package com.challenge.exchange.service.impl;

import com.challenge.exchange.data.StaticDataDAO;
import com.challenge.exchange.model.Stock;
import com.challenge.exchange.service.StockStaticDataService;

import java.util.Map;

public class StockStaticDataServiceImpl implements StockStaticDataService {

    private StaticDataDAO stockDAO;
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
        if(stock == null || stock.getSymbol() == null || stock.getSymbol().trim().length() == 0 ||
                stock.getType() == null){
            throw new RuntimeException("Invalid stock, as mandatory stock details are not provided");
        }
        return stockDAO.addStockStaticData(stock);
    }

    @Override
    public Stock removeStockStaticData(String symbol) {
        return getAllExchangeListedStocks().remove(symbol);
    }

    @Override
    public Stock findStock(String symbol) {
        return getAllExchangeListedStocks().get(symbol);
    }


}
