package com.challenge.exchange.data.impl;

import com.challenge.exchange.model.Stock;
import com.challenge.exchange.model.StockType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StaticDataDAOImplTest {

    @Test
    void testAddStock() {

        StaticDataDAOImpl dao = new StaticDataDAOImpl();

        Stock s1 = new Stock();
        s1.setSymbol("TEA");
        s1.setType(StockType.COMMON_STOCK);
        s1.setLastDividend(BigDecimal.ZERO);
        s1.setParValue(BigDecimal.valueOf(100));

        s1 = dao.addStockStaticData(s1);

        assertNotNull(s1);

        assertEquals(1, dao.getAllExchangeListedStocks().size());

    }

    @Test
    void testAddStockNull() {

        StaticDataDAOImpl dao = new StaticDataDAOImpl();
        assertThrows(NullPointerException.class, ()-> dao.addStockStaticData(null));
    }

    @Test
    void testAddStockSymbolNull() {

        StaticDataDAOImpl dao = new StaticDataDAOImpl();
        Stock s = new Stock();
        assertThrows(NullPointerException.class, ()-> dao.addStockStaticData(s));
    }

    @Test
    void testGetAllExchangeListedStocks() {

        StaticDataDAOImpl dao = new StaticDataDAOImpl();
        assertEquals(0,  dao.getAllExchangeListedStocks().size());
    }
}
