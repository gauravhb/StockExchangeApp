package com.challenge.exchange.service.impl;

import com.challenge.exchange.data.StaticDataDAO;
import com.challenge.exchange.data.impl.StaticDataDAOImpl;
import com.challenge.exchange.model.Stock;
import com.challenge.exchange.model.StockType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class StockStaticDataServiceImplTest {

    @Test
    void testAddStock() {

        StaticDataDAO dao = new StaticDataDAOImpl();
        StockStaticDataServiceImpl dataService = new StockStaticDataServiceImpl(dao);

        Stock s1 = new Stock();
        s1.setSymbol("TEA");
        s1.setType(StockType.COMMON_STOCK);
        s1.setLastDividend(BigDecimal.ZERO);
        s1.setParValue(BigDecimal.valueOf(100));

        s1 = dataService.addStockStaticData(s1);

        assertNotNull(s1);

        assertEquals(1, dao.getAllExchangeListedStocks().size());

    }

    @Test
    void testAddStockNull() {

        StaticDataDAO dao = new StaticDataDAOImpl();
        StockStaticDataServiceImpl dataService = new StockStaticDataServiceImpl(dao);

        assertThrows(RuntimeException.class, ()-> dataService.addStockStaticData(null));
    }

    @Test
    void testAddStockSymbolNull() {

        StaticDataDAO dao = new StaticDataDAOImpl();
        StockStaticDataServiceImpl dataService = new StockStaticDataServiceImpl(dao);

        Stock s = new Stock();
        assertThrows(RuntimeException.class, ()-> dataService.addStockStaticData(s));
    }

    @Test
    void testGetAllExchangeListedStocks() {

        StaticDataDAO dao = new StaticDataDAOImpl();
        StockStaticDataServiceImpl dataService = new StockStaticDataServiceImpl(dao);

        assertEquals(0,  dataService.getAllExchangeListedStocks().size());
    }

}
