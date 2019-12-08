package com.challenge.exchange.service.impl;

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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StockExchangeServiceImplTest {

    @Test
    public void testGetDividendYield() {
        StaticDataDAO stockDAO = new StaticDataDAOImpl();
        StockStaticDataService dataService = new StockStaticDataServiceImpl(stockDAO);

        StockTradeDAO tradeDAO = new StockTradeDAOImpl();
        StockTradeService tradeService = new StockTradeServiceImpl(tradeDAO, dataService);

        StockExchangeService exchangeService = new StockExchangeServiceImpl(dataService, tradeService);

        Stock s2 = new Stock();
        s2.setSymbol("POP");
        s2.setType(StockType.COMMON_STOCK);
        s2.setLastDividend(BigDecimal.valueOf(8));
        s2.setParValue(BigDecimal.valueOf(100));
        dataService.addStockStaticData(s2);

        BigDecimal dividendYieldCn = exchangeService.getDividendYield(s2.getSymbol(), BigDecimal.valueOf(200));

        Assertions.assertTrue(new BigDecimal("0.04").compareTo(dividendYieldCn) == 0);


    }

    @Test
    public void testGetDividendYieldNullStockNullPrice() {
        StaticDataDAO stockDAO = new StaticDataDAOImpl();
        StockStaticDataService dataService = new StockStaticDataServiceImpl(stockDAO);

        StockTradeDAO tradeDAO = new StockTradeDAOImpl();
        StockTradeService tradeService = new StockTradeServiceImpl(tradeDAO, dataService);

        StockExchangeService exchangeService = new StockExchangeServiceImpl(dataService, tradeService);

        Assertions.assertThrows(NullPointerException.class,()-> exchangeService.getDividendYield(null, new BigDecimal("10.5")));

        Assertions.assertThrows(NullPointerException.class,()-> exchangeService.getDividendYield("", new BigDecimal("10.5")));

        Assertions.assertThrows(NullPointerException.class,()-> exchangeService.getDividendYield("POP", null));

        Assertions.assertThrows(IllegalArgumentException.class,()-> exchangeService.getDividendYield("POP", BigDecimal.ZERO));

    }

    @Test
    public void testGetPERatioNoStockPresent() {
        StaticDataDAO stockDAO = new StaticDataDAOImpl();
        StockStaticDataService dataService = new StockStaticDataServiceImpl(stockDAO);

        StockTradeDAO tradeDAO = new StockTradeDAOImpl();
        StockTradeService tradeService = new StockTradeServiceImpl(tradeDAO, dataService);

        StockExchangeService exchangeService = new StockExchangeServiceImpl(dataService, tradeService);

        Optional<BigDecimal> pe = exchangeService.getPERatio("POP", new BigDecimal("100"));

        Assertions.assertFalse(pe.isPresent());
    }

    @Test
    public void testGetPERatio() {
        StaticDataDAO stockDAO = new StaticDataDAOImpl();
        StockStaticDataService dataService = new StockStaticDataServiceImpl(stockDAO);

        StockTradeDAO tradeDAO = new StockTradeDAOImpl();
        StockTradeService tradeService = new StockTradeServiceImpl(tradeDAO, dataService);

        Stock s2 = new Stock();
        s2.setSymbol("POP");
        s2.setType(StockType.COMMON_STOCK);
        s2.setLastDividend(BigDecimal.valueOf(8));
        s2.setParValue(BigDecimal.valueOf(100));
        dataService.addStockStaticData(s2);


        StockExchangeService exchangeService = new StockExchangeServiceImpl(dataService, tradeService);

        Optional<BigDecimal> pe = exchangeService.getPERatio("POP", new BigDecimal("100"));

        Assertions.assertTrue(pe.isPresent());
        Assertions.assertTrue(new BigDecimal(12).compareTo(pe.get()) == 0);
    }

    @Test
    public void testGetPERatioNullStockNullPrice() {
        StaticDataDAO stockDAO = new StaticDataDAOImpl();
        StockStaticDataService dataService = new StockStaticDataServiceImpl(stockDAO);

        StockTradeDAO tradeDAO = new StockTradeDAOImpl();
        StockTradeService tradeService = new StockTradeServiceImpl(tradeDAO, dataService);

        StockExchangeService exchangeService = new StockExchangeServiceImpl(dataService, tradeService);

        Assertions.assertThrows(NullPointerException.class, ()-> exchangeService.getPERatio(null, new BigDecimal("100")));

        Assertions.assertThrows(NullPointerException.class, ()-> exchangeService.getPERatio("POP", null));
    }

    @Test
    public void testGetVolumeWeightedStockPriceNoStockPresent() {
        StaticDataDAO stockDAO = new StaticDataDAOImpl();
        StockStaticDataService dataService = new StockStaticDataServiceImpl(stockDAO);

        StockTradeDAO tradeDAO = new StockTradeDAOImpl();
        StockTradeService tradeService = new StockTradeServiceImpl(tradeDAO, dataService);

        StockExchangeService exchangeService = new StockExchangeServiceImpl(dataService, tradeService);

        Optional<BigDecimal> price = exchangeService.getVolumeWeightedStockPrice("POP");

        Assertions.assertFalse(price.isPresent());

    }

    @Test
    public void testGetVolumeWeightedStockPrice() {
        StaticDataDAO stockDAO = new StaticDataDAOImpl();
        StockStaticDataService dataService = new StockStaticDataServiceImpl(stockDAO);

        StockTradeDAO tradeDAO = new StockTradeDAOImpl();
        StockTradeService tradeService = new StockTradeServiceImpl(tradeDAO, dataService);

        StockExchangeService exchangeService = new StockExchangeServiceImpl(dataService, tradeService);
        Trade t2 = new Trade();
        t2.setSymbol("POP");
        t2.setTimestamp(LocalDateTime.now().minusMinutes(6));
        t2.setPrice(new BigDecimal("125.5"));
        t2.setQuantity(10);
        t2.setType(TradeType.BUY);

        tradeService.recordTrade(t2);

        Optional<BigDecimal> price = exchangeService.getVolumeWeightedStockPrice("POP");

        Assertions.assertTrue(new BigDecimal("125.5").compareTo(price.get()) == 0);

    }

    @Test
    public void testGetGBCEIndex() {
        StaticDataDAO stockDAO = new StaticDataDAOImpl();
        StockStaticDataService dataService = new StockStaticDataServiceImpl(stockDAO);

        StockTradeDAO tradeDAO = new StockTradeDAOImpl();
        StockTradeService tradeService = new StockTradeServiceImpl(tradeDAO, dataService);

        StockExchangeService exchangeService = new StockExchangeServiceImpl(dataService, tradeService);

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

        dataService.addStockStaticData(s1);
        dataService.addStockStaticData(s2);

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

        tradeService.recordTrade(t1);
        tradeService.recordTrade(t2);

        Map<String, Stock> stocks = dataService.getAllExchangeListedStocks();

        assertEquals(124, exchangeService.getGBCEIndex(stocks));

    }

    @Test
    public void testGetGBCEIndexEmptyStocks() {
        StaticDataDAO stockDAO = new StaticDataDAOImpl();
        StockStaticDataService dataService = new StockStaticDataServiceImpl(stockDAO);

        StockTradeDAO tradeDAO = new StockTradeDAOImpl();
        StockTradeService tradeService = new StockTradeServiceImpl(tradeDAO, dataService);

        StockExchangeService exchangeService = new StockExchangeServiceImpl(dataService, tradeService);

        assertThrows(IllegalArgumentException.class, () -> exchangeService.getGBCEIndex(null));

        Map<String, Stock> map = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> exchangeService.getGBCEIndex(map));

    }

    }
