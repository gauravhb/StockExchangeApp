package com.challenge.exchange.service.impl;

import com.challenge.exchange.model.Stock;
import com.challenge.exchange.model.StockType;
import com.challenge.exchange.model.Trade;
import com.challenge.exchange.service.StockExchangeService;
import com.challenge.exchange.service.StockStaticDataService;
import com.challenge.exchange.service.StockTradeService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class StockExchangeServiceImpl implements StockExchangeService {
    private static final int TIME_SPAN = 15 * 60;
    private StockStaticDataService stockDataService;
    private StockTradeService stockTradeService;

    public StockExchangeServiceImpl(StockStaticDataService stockDataService, StockTradeService stockTradeService) {
        this.stockDataService = stockDataService;
        this.stockTradeService = stockTradeService;
    }

    /* This method calculates dividend yield of given stock, based on the given price.
    * Method requires that given stock is listed on exchange already.
    * */
    @Override
    public BigDecimal getDividendYield(String symbol, BigDecimal price) {
        if(symbol == null){
            throw new IllegalArgumentException("Stock symbol cannot be null");
        }
        if(price == null){
            throw new IllegalArgumentException("Price cannot be null");
        }
        if(price.compareTo(BigDecimal.ZERO) < 1){
            throw new IllegalArgumentException("Price should greater than zero");
        }

        BigDecimal dividendYield;
        Stock stock = stockDataService.findStock(symbol);
        if (stock.getType().equals(StockType.COMMON_STOCK)) {
            dividendYield = stock.getLastDividend().divide(price, 4, BigDecimal.ROUND_HALF_EVEN);

        } else if (stock.getType().equals(StockType.PREFERRED_STOCK)) {

            dividendYield = stock.getFixedDividend().multiply(stock.getParValue()).
                    divide(BigDecimal.valueOf(100).multiply(price), 4, BigDecimal.ROUND_HALF_EVEN);

        } else {
            throw new RuntimeException("Invalid Stock Type");
        }

        return dividendYield;
    }

    /* This method calculates PE Ratio of given stock, based on the given price.
     * Method requires that given stock is listed on exchange already.
     * */
    @Override
    public Optional<BigDecimal> getPERatio(String symbol, BigDecimal price) {
        if(symbol == null){
            throw new IllegalArgumentException("Stock symbol cannot be null");
        }
        if(price == null){
            throw new IllegalArgumentException("Price cannot be null");
        }
        Optional<BigDecimal> pe = Optional.empty();

        Stock stock = stockDataService.findStock(symbol);
        if(stock == null){
            return pe;
        }

        BigDecimal lastDividend = stock.getLastDividend();
        if (!lastDividend.equals(BigDecimal.ZERO)) {
            pe = Optional.of(price.divide(lastDividend, RoundingMode.HALF_EVEN));
        }
        return pe;
    }

    /* This method calculates volume weighted stock price based on trades happened in last 15 mins on this stock.
    * If this stock is not traded in last 15 mins, empty optional will be returned.
    * */
    @Override
    public Optional<BigDecimal> getVolumeWeightedStockPrice(String symbol) {
        Optional<BigDecimal> price = Optional.empty();
        if(symbol == null){
            throw new IllegalArgumentException("Stock symbol cannot be null");
        }

        Set<Trade> trades = stockTradeService.getTrades(symbol);

        if(trades == null || trades.size() == 0){
            return price;
        }

        trades = trades.stream().filter(trade -> trade.getTimestamp().
                isAfter(LocalDateTime.now().minus(TIME_SPAN + 1, ChronoUnit.SECONDS)))
                .collect(Collectors.toSet());

        BigDecimal sumOfPriceIntoQuantity = trades.stream()
                .map(trade -> trade.getPrice().multiply(BigDecimal.valueOf(trade.getQuantity())))
                .reduce(BigDecimal::add)
                .orElseThrow(() -> new RuntimeException("Unable to get Price of Trades"));


        long totalQuantity = trades.stream()
                .map(Trade::getQuantity)
                .reduce(Long::sum)
                .orElseThrow(() -> new RuntimeException("Unable to get Price of Trades"));

        return Optional.of(sumOfPriceIntoQuantity.divide(BigDecimal.valueOf(totalQuantity), 2, RoundingMode.HALF_EVEN));

    }

    /* This method calculates Index value based on latest traded price of stocks registered on exchange
     * */
    @Override
    public long getGBCEIndex(Map<String, Stock> stocks) {

        if(stocks == null || stocks.size()==0){
            throw new IllegalArgumentException("Unable to calculate Index without stocks");
        }

        BigDecimal stockPriceProduct = stocks.keySet().stream()
                .filter(k -> stockTradeService.getLatestPrice(k).compareTo(BigDecimal.ZERO) > 0)
                .map(stockTradeService::getLatestPrice)
                .reduce(BigDecimal::multiply)
                .orElseThrow(() -> new RuntimeException("Unable to get Price of shares"));

        double priceProduct = Double.parseDouble(stockPriceProduct.toPlainString());
        double exponent = (double) 1 / stocks.size();

        //Rounding to int as Index values are not in decimal..
        double index =  Math.pow(priceProduct, exponent);

        return Math.round(index);

    }
}
