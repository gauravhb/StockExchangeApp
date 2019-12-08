package com.challenge.exchange.data.impl;

import com.challenge.exchange.model.Trade;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StockTradeDAOImplTest {

    @Test
    void testSaveTrade() {

        StockTradeDAOImpl dao = new StockTradeDAOImpl();
        Trade t1 = new Trade();
        t1.setTimestamp(LocalDateTime.of(2019, 12, 2, 11, 20, 20));
        t1.setSymbol("TEA");

        Trade t2 = new Trade();
        t2.setTimestamp(LocalDateTime.of(2019, 12, 2, 10, 30, 20));
        t2.setSymbol("TEA");

        Trade t3 = new Trade();
        t3.setTimestamp(LocalDateTime.of(2019, 12, 2, 23, 59, 20));
        t3.setSymbol("TEA");

        dao.saveTrade(t1);
        dao.saveTrade(t2);
        dao.saveTrade(t3);

        Set<Trade> trades = dao.getTrades("TEA");

        assertEquals(3, trades.size());


    }

    @Test
    void testSaveTradeNull() {
        StockTradeDAOImpl dao = new StockTradeDAOImpl();
        assertThrows(NullPointerException.class, () -> dao.saveTrade(null));

    }

    @Test
    void testSaveTradeEmpty() {
        StockTradeDAOImpl dao = new StockTradeDAOImpl();
        assertThrows(NullPointerException.class, () -> dao.saveTrade(new Trade()));
    }

    @Test
    void testSaveTradeTimepstampNull() {
        StockTradeDAOImpl dao = new StockTradeDAOImpl();
        Trade t3 = new Trade();
        t3.setSymbol("TEA");

        assertThrows(NullPointerException.class, () -> dao.saveTrade(t3));
    }

    @Test
    void testGetTradesEmpty() {
        StockTradeDAOImpl dao = new StockTradeDAOImpl();
        assertNull(dao.getTrades(""));
    }

    @Test
    void testGetLatestTradePrice() {

        StockTradeDAOImpl dao = new StockTradeDAOImpl();
        Trade t1 = new Trade();
        t1.setTimestamp(LocalDateTime.of(2019, 12, 2, 11, 20, 20));
        t1.setSymbol("TEA");
        t1.setPrice(new BigDecimal("101.23"));

        Trade t2 = new Trade();
        t2.setTimestamp(LocalDateTime.of(2019, 12, 2, 10, 30, 20));
        t2.setSymbol("TEA");
        t2.setPrice(new BigDecimal("101.24"));

        dao.saveTrade(t2);
        dao.saveTrade(t1);


        BigDecimal latestPrice =  dao.getLatestPrice("TEA");

        assertTrue(new BigDecimal("101.23").compareTo(latestPrice) == 0 );

    }
}