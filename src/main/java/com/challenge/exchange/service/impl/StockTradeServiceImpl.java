package com.challenge.exchange.service.impl;

import com.challenge.exchange.data.StockTradeDAO;
import com.challenge.exchange.model.Trade;
import com.challenge.exchange.service.StockStaticDataService;
import com.challenge.exchange.service.StockTradeService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class StockTradeServiceImpl implements StockTradeService {
    private StockTradeDAO stockTradeDAO;
    private StockStaticDataService stockStaticDataService;
    public StockTradeServiceImpl(StockTradeDAO stockTradeDAO, StockStaticDataService stockStaticDataService) {
        this.stockTradeDAO = stockTradeDAO;
        this.stockStaticDataService = stockStaticDataService;

    }

    @Override
    public Trade recordTrade(Trade trade) {
        //Validate Trade object
        if(trade == null || trade.getQuantity() < 1 || trade.getPrice() == null ||
                trade.getSymbol() == null || trade.getSymbol().trim().length() == 0 ||
                trade.getType() == null){
            throw new RuntimeException("Invalid Trade, as mandatory trade details are not provided");
        }
        if(stockStaticDataService.findStock(trade.getSymbol()) == null){
            throw new RuntimeException("Stock :" + trade.getSymbol() + " is not yet listed on exchange");
        }
        if(trade.getTimestamp() == null){
            trade.setTimestamp(LocalDateTime.now());
        }
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
