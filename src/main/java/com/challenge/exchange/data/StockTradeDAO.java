package com.challenge.exchange.data;

import com.challenge.exchange.model.Trade;

import java.math.BigDecimal;
import java.util.Set;

public interface StockTradeDAO {

     Trade saveTrade(Trade trade);

    Set<Trade> getTrades(String symbol);

    BigDecimal getLatestPrice(String symbol);
}
