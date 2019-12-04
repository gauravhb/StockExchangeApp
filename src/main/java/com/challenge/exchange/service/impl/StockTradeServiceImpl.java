package com.challenge.exchange.service.impl;

import com.challenge.exchange.data.StockTradeDAO;
import com.challenge.exchange.model.Trade;
import com.challenge.exchange.service.StockTradeService;

import java.math.BigDecimal;
import java.util.Set;

public class StockTradeServiceImpl implements StockTradeService {
    StockTradeDAO stockTradeDAO;
    public StockTradeServiceImpl(StockTradeDAO stockTradeDAO) {
        this.stockTradeDAO = stockTradeDAO;
    }

    @Override
    public Trade recordTrade(Trade trade) {
        //Validate Trade object
        return stockTradeDAO.saveTrade(trade);
    }

    @Override
    public Set<Trade> getTrades(String symbol) {
        return stockTradeDAO.getTrades(symbol);
    }

    @Override
    public BigDecimal getLatestPrice(String symbol) {
        return stockTradeDAO.getLatestPrice(symbol);
    }
}
