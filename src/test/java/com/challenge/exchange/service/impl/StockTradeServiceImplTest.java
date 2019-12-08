package com.challenge.exchange.service.impl;

import com.challenge.exchange.data.StaticDataDAO;
import com.challenge.exchange.data.StockTradeDAO;
import com.challenge.exchange.data.impl.StaticDataDAOImpl;
import com.challenge.exchange.data.impl.StockTradeDAOImpl;
import com.challenge.exchange.model.Stock;
import com.challenge.exchange.model.StockType;
import com.challenge.exchange.model.Trade;
import com.challenge.exchange.model.TradeType;
import com.challenge.exchange.service.StockStaticDataService;
import org.junit.After;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.Assert.*;


public class StockTradeServiceImplTest {

    @Test
    public void testSaveTrade() {

        StaticDataDAO stockDAO = new StaticDataDAOImpl();
        StockStaticDataService dataService = new StockStaticDataServiceImpl(stockDAO);
        Stock s1 = new Stock();
        s1.setSymbol("TEA");
        s1.setType(StockType.COMMON_STOCK);
        s1.setLastDividend(BigDecimal.ZERO);
        s1.setParValue(BigDecimal.valueOf(100));

        s1 = dataService.addStockStaticData(s1);



        StockTradeDAO dao = new StockTradeDAOImpl();
        StockTradeServiceImpl tradeService = new StockTradeServiceImpl(dao, dataService);

        Trade t1 = new Trade();
        t1.setTimestamp(LocalDateTime.of(2019, 12, 2, 11, 20, 20));
        t1.setSymbol("TEA");
        t1.setPrice(new BigDecimal("10"));
        t1.setQuantity(10);
        t1.setType(TradeType.BUY);



        t1 = tradeService.recordTrade(t1);

        Set<Trade> trades = dao.getTrades("TEA");

        assertEquals(1, t1.getTradeId());
        assertEquals(1, trades.size());


    }

    @Test(expected = RuntimeException.class)
    public void testValidateMandatoryDetailsOfTrade() {

        StaticDataDAO stockDAO = new StaticDataDAOImpl();
        StockStaticDataService dataService = new StockStaticDataServiceImpl(stockDAO);
        Stock s1 = new Stock();
        s1.setSymbol("TEA");
        s1.setType(StockType.COMMON_STOCK);
        s1.setLastDividend(BigDecimal.ZERO);
        s1.setParValue(BigDecimal.valueOf(100));

        s1 = dataService.addStockStaticData(s1);


        StockTradeDAO dao = new StockTradeDAOImpl();
        StockTradeServiceImpl tradeService = new StockTradeServiceImpl(dao,dataService);

        Trade t1 = new Trade();

        tradeService.recordTrade(t1);


        Trade t2 = new Trade();
        t2.setTimestamp(LocalDateTime.of(2019, 12, 2, 11, 20, 20));
        t2.setSymbol("TEA");
        tradeService.recordTrade(t2);



        Trade t3 = new Trade();
        t3.setTimestamp(LocalDateTime.of(2019, 12, 2, 11, 20, 20));
        t3.setSymbol("TEA");
        t3.setPrice(new BigDecimal("10"));
        tradeService.recordTrade(t3);



        Trade t4 = new Trade();
        t4.setTimestamp(LocalDateTime.of(2019, 12, 2, 11, 20, 20));
        t4.setSymbol("TEA");
        t4.setPrice(new BigDecimal("10"));
        t4.setQuantity(10);
        tradeService.recordTrade(t4);



    }

    @Test(expected = RuntimeException.class)
    public void testSaveTradeNull() {
        StaticDataDAO stockDAO = new StaticDataDAOImpl();
        StockStaticDataService dataService = new StockStaticDataServiceImpl(stockDAO);

        StockTradeDAO dao = new StockTradeDAOImpl();
        StockTradeServiceImpl tradeService = new StockTradeServiceImpl(dao, dataService);
        tradeService.recordTrade(null);

    }


    @Test
    public void testGetTradesEmpty() {
        StaticDataDAO stockDAO = new StaticDataDAOImpl();
        StockStaticDataService dataService = new StockStaticDataServiceImpl(stockDAO);

        StockTradeDAO dao = new StockTradeDAOImpl();
        StockTradeServiceImpl tradeService = new StockTradeServiceImpl(dao, dataService);

        assertNull(tradeService.getTrades(""));
    }

    @Test
    public void testGetLatestTradePrice() {
        StaticDataDAO stockDAO = new StaticDataDAOImpl();
        StockStaticDataService dataService = new StockStaticDataServiceImpl(stockDAO);
        Stock s1 = new Stock();
        s1.setSymbol("TEA");
        s1.setType(StockType.COMMON_STOCK);
        s1.setLastDividend(BigDecimal.ZERO);
        s1.setParValue(BigDecimal.valueOf(100));

        s1 = dataService.addStockStaticData(s1);


        StockTradeDAO dao = new StockTradeDAOImpl();
        StockTradeServiceImpl tradeService = new StockTradeServiceImpl(dao, dataService);

        Trade t1 = new Trade();
        t1.setTimestamp(LocalDateTime.of(2019, 12, 2, 11, 20, 20));
        t1.setSymbol("TEA");
        t1.setPrice(new BigDecimal("101.23"));
        t1.setQuantity(10);
        t1.setType(TradeType.BUY);

        Trade t2 = new Trade();
        t2.setTimestamp(LocalDateTime.of(2019, 12, 2, 10, 30, 20));
        t2.setSymbol("TEA");
        t2.setPrice(new BigDecimal("101.24"));
        t2.setQuantity(200);
        t2.setType(TradeType.BUY);


        tradeService.recordTrade(t2);
        tradeService.recordTrade(t1);


        BigDecimal latestPrice =  tradeService.getLatestPrice("TEA");

        assertTrue(new BigDecimal("101.23").compareTo(latestPrice) == 0 );

    }

    @After
    public void clearStockRepo(){
        StaticDataDAO dao = new StaticDataDAOImpl();
        StockStaticDataServiceImpl dataService = new StockStaticDataServiceImpl(dao);
        dataService.clearAllStockStaticData();
    }
}
