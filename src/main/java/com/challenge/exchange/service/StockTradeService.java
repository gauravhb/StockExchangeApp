package com.challenge.exchange.service;

import com.challenge.exchange.model.Trade;

import java.math.BigDecimal;
import java.util.Set;

public interface StockTradeService {

    Trade recordTrade(Trade trade);

    Set<Trade> getTrades(String symbol);

    BigDecimal getLatestPrice(String symbol);

}
