package com.challenge.exchange.service;

import java.math.BigDecimal;
import java.util.Optional;

public interface StockExchangeService {

    BigDecimal getDividendYield(String symbol, BigDecimal price);

    Optional<BigDecimal> getPERatio(String symbol, BigDecimal price);

    BigDecimal getVolumeWeightedStockPrice(String symbol);

    int getGBCEIndex();


}
