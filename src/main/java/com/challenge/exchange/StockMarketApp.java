package com.challenge.exchange;

import com.challenge.exchange.data.StaticDataDAO;
import com.challenge.exchange.data.StockTradeDAO;
import com.challenge.exchange.data.impl.StaticDataDAOImpl;
import com.challenge.exchange.data.impl.StockTradeDAOImpl;
import com.challenge.exchange.model.Stock;
import com.challenge.exchange.model.StockType;
import com.challenge.exchange.model.Trade;
import com.challenge.exchange.model.TradeType;
import com.challenge.exchange.service.StockExchangeService;
import com.challenge.exchange.service.StockStaticDataService;
import com.challenge.exchange.service.StockTradeService;
import com.challenge.exchange.service.impl.StockExchangeServiceImpl;
import com.challenge.exchange.service.impl.StockStaticDataServiceImpl;
import com.challenge.exchange.service.impl.StockTradeServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockMarketApp {
    public static void main(String[] args) {
        StaticDataDAO stockDAO = new StaticDataDAOImpl();
        StockStaticDataService dataService = new StockStaticDataServiceImpl(stockDAO);

        StockTradeDAO tradeDAO = new StockTradeDAOImpl();
        StockTradeService tradeService = new StockTradeServiceImpl(tradeDAO);

        Stock s1 = new Stock();
        s1.setSymbol("TEA");
        s1.setType(StockType.COMMON_STOCK);
        s1.setLastDividend(BigDecimal.ZERO);
        s1.setParValue(BigDecimal.valueOf(100));


        Stock s2 = new Stock();
        s2.setSymbol("POP");
        s2.setType(StockType.COMMON_STOCK);
        s2.setLastDividend(BigDecimal.valueOf(8));
        s2.setParValue(BigDecimal.valueOf(100));


        Stock s3 = new Stock();
        s3.setSymbol("ALE");
        s3.setType(StockType.COMMON_STOCK);
        s3.setLastDividend(BigDecimal.valueOf(23));
        s3.setParValue(BigDecimal.valueOf(100));


        Stock s4 = new Stock();
        s4.setSymbol("GIN");
        s4.setType(StockType.PREFERRED_STOCK);
        s4.setLastDividend(BigDecimal.valueOf(8));
        s4.setFixedDividend(BigDecimal.valueOf(2));
        s4.setParValue(BigDecimal.valueOf(100));

        Stock s5 = new Stock();
        s5.setSymbol("JOE");
        s5.setType(StockType.COMMON_STOCK);
        s5.setLastDividend(BigDecimal.valueOf(13));
        s5.setParValue(BigDecimal.valueOf(100));



        dataService.addStockStaticData(s1);
        dataService.addStockStaticData(s2);
        dataService.addStockStaticData(s3);
        dataService.addStockStaticData(s4);
        dataService.addStockStaticData(s5);

        System.out.println("Number of stocks listed on exchange :" + dataService.getAllExchangeListedStocks().size());

        StockExchangeService exchangeService = new StockExchangeServiceImpl(dataService, tradeService);

        BigDecimal dividendYieldCn = exchangeService.getDividendYield(s2.getSymbol(), BigDecimal.valueOf(200));
        System.out.println("dividendYield of common stock "+ s2.getSymbol() + " is : " + dividendYieldCn);

        BigDecimal dividendYieldPref = exchangeService.getDividendYield(s4.getSymbol(), BigDecimal.valueOf(250));
        System.out.println("dividendYield of preferred stock :" + s4.getSymbol() + " is : "+ dividendYieldPref);

        Trade t1 = new Trade();
        t1.setSymbol(s1.getSymbol());
        t1.setTimestamp(LocalDateTime.now().minusMinutes(5));
        t1.setPrice(new BigDecimal("123.5"));
        t1.setQuantity(10);
        t1.setType(TradeType.BUY);

        Trade t2 = new Trade();
        t2.setSymbol(s2.getSymbol());
        t2.setTimestamp(LocalDateTime.now().minusMinutes(6));
        t2.setPrice(new BigDecimal("125.5"));
        t2.setQuantity(10);
        t2.setType(TradeType.BUY);

        Trade t6 = new Trade();
        t6.setSymbol(s2.getSymbol());
        t6.setTimestamp(LocalDateTime.now().minusMinutes(8));
        t6.setPrice(new BigDecimal("1255"));
        t6.setQuantity(10);
        t6.setType(TradeType.BUY);



        Trade t3 = new Trade();
        t3.setSymbol(s3.getSymbol());
        t3.setTimestamp(LocalDateTime.now().minusMinutes(7));
        t3.setPrice(new BigDecimal("126.5"));
        t3.setQuantity(10);
        t3.setType(TradeType.BUY);


        Trade t4 = new Trade();
        t4.setSymbol(s4.getSymbol());
        t4.setTimestamp(LocalDateTime.now().minusMinutes(5));
        t4.setPrice(new BigDecimal("127.5"));
        t4.setQuantity(10);
        t4.setType(TradeType.BUY);


        Trade t5 = new Trade();
        t5.setSymbol(s5.getSymbol());
        t5.setTimestamp(LocalDateTime.now().minusMinutes(5));
        t5.setPrice(new BigDecimal("128.5"));
        t5.setQuantity(10);
        t5.setType(TradeType.BUY);


        tradeService.recordTrade(t1);
        tradeService.recordTrade(t2);
        tradeService.recordTrade(t3);
        tradeService.recordTrade(t4);
        tradeService.recordTrade(t5);
        tradeService.recordTrade(t6);


        System.out.println("Trades done for stock " + s1.getSymbol() +" are :" + tradeService.getTrades(s1.getSymbol()));
        System.out.println("Trades done for stock " + s2.getSymbol() +" are :" + tradeService.getTrades(s2.getSymbol()));
        System.out.println("Trades done for stock " + s3.getSymbol() +" are :" + tradeService.getTrades(s3.getSymbol()));
        System.out.println("Trades done for stock " + s4.getSymbol() +" are :" + tradeService.getTrades(s4.getSymbol()));
        System.out.println("Trades done for stock " + s5.getSymbol() +" are :" + tradeService.getTrades(s5.getSymbol()));

        System.out.println("Latest price for stock " + s1.getSymbol() +" is :" + tradeService.getLatestPrice(s1.getSymbol()));
        System.out.println("Latest price for stock " + s2.getSymbol() +" is :" + tradeService.getLatestPrice(s2.getSymbol()));
        System.out.println("Latest price for stock " + s3.getSymbol() +" is :" + tradeService.getLatestPrice(s3.getSymbol()));
        System.out.println("Latest price for stock " + s4.getSymbol() +" is :" + tradeService.getLatestPrice(s4.getSymbol()));
        System.out.println("Latest price for stock " + s5.getSymbol() +" is :" + tradeService.getLatestPrice(s5.getSymbol()));




        System.out.println("GBCE all share index is :" + exchangeService.getGBCEIndex());



        BigDecimal volumeWeightedStockPrice = exchangeService.getVolumeWeightedStockPrice(s2.getSymbol());
        System.out.println("volumeWeightedStockPrice of stock "+ s2.getSymbol() +" is :"+ volumeWeightedStockPrice);




    }
}
