package com.challenge.exchange.data.impl;

import com.challenge.exchange.data.StockTradeDAO;
import com.challenge.exchange.model.Trade;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class StockTradeDAOImpl implements StockTradeDAO {

    private AtomicLong tradeIDSeq = new AtomicLong(0);
    private Map<String, TreeSet<Trade>> tradeMap = new TreeMap<>();

    @Override
    public Trade saveTrade(Trade trade) {
        Objects.requireNonNull(trade,"Trade object can't be null");
        Objects.requireNonNull(trade.getSymbol(),"Symbol object can't be null in Trade object");
        Objects.requireNonNull(trade.getTimestamp(),"Trade timestamp can't be null in Trade object");

        Comparator<Trade> byTimeStamp =
                Comparator.comparing(Trade::getTimestamp).reversed();
        trade.setTradeId(tradeIDSeq.incrementAndGet());
        tradeMap.computeIfAbsent(trade.getSymbol(), k -> new TreeSet<>(byTimeStamp)).add(trade);

        return trade;
    }

    @Override
    public Set<Trade> getTrades(String symbol) {
        return tradeMap.get(symbol);
    }

    @Override
    public BigDecimal getLatestPrice(String symbol) {
        return tradeMap.get(symbol).first().getPrice();
    }
}
