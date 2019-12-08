package com.challenge.exchange.service;

import com.challenge.exchange.model.Stock;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public interface StockExchangeService {

    BigDecimal getDividendYield(String symbol, BigDecimal price);

    Optional<BigDecimal> getPERatio(String symbol, BigDecimal price);

    Optional<BigDecimal> getVolumeWeightedStockPrice(String symbol);

    long getGBCEIndex(Map<String, Stock> stocks);


}
