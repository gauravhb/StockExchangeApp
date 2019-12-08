package com.challenge.exchange.data.impl;

import com.challenge.exchange.data.StaticDataDAO;
import com.challenge.exchange.model.Stock;
import com.challenge.exchange.model.StockType;
import com.challenge.exchange.service.impl.StockStaticDataServiceImpl;
import org.junit.After;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class StaticDataDAOImplTest {

    @Test
    public void testAddStock() {

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

    @Test(expected = NullPointerException.class)
    public void testAddStockNull() {

        StaticDataDAOImpl dao = new StaticDataDAOImpl();
        dao.addStockStaticData(null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddStockSymbolNull() {

        StaticDataDAOImpl dao = new StaticDataDAOImpl();
        Stock s = new Stock();
        dao.addStockStaticData(s);
    }

    @Test
    public void testGetAllExchangeListedStocks() {

        StaticDataDAOImpl dao = new StaticDataDAOImpl();
        assertEquals(0,  dao.getAllExchangeListedStocks().size());
    }

    @After
    public void clearStockRepo(){
        StaticDataDAO dao = new StaticDataDAOImpl();
        StockStaticDataServiceImpl dataService = new StockStaticDataServiceImpl(dao);
        dataService.clearAllStockStaticData();
    }

}
