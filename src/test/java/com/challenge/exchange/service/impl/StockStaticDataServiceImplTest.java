package com.challenge.exchange.service.impl;

import com.challenge.exchange.data.StaticDataDAO;
import com.challenge.exchange.data.impl.StaticDataDAOImpl;
import com.challenge.exchange.model.Stock;
import com.challenge.exchange.model.StockType;
import org.junit.After;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;



public class StockStaticDataServiceImplTest {

    @Test
    public void testAddStock() {

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

    @Test(expected = RuntimeException.class)
    public void testAddStockNull() {

        StaticDataDAO dao = new StaticDataDAOImpl();
        StockStaticDataServiceImpl dataService = new StockStaticDataServiceImpl(dao);

        dataService.addStockStaticData(null);
    }

    @Test(expected = RuntimeException.class)
    public void testAddStockSymbolNull() {
        StaticDataDAO dao = new StaticDataDAOImpl();
        StockStaticDataServiceImpl dataService = new StockStaticDataServiceImpl(dao);

        Stock s = new Stock();

        dataService.addStockStaticData(s);
    }

    @Test
    public void testGetAllExchangeListedStocks() {

        StaticDataDAO dao = new StaticDataDAOImpl();
        StockStaticDataServiceImpl dataService = new StockStaticDataServiceImpl(dao);

        assertEquals(0,  dataService.getAllExchangeListedStocks().size());
    }

    @After
    public void clearStockRepo(){
        StaticDataDAO dao = new StaticDataDAOImpl();
        StockStaticDataServiceImpl dataService = new StockStaticDataServiceImpl(dao);
        dataService.clearAllStockStaticData();
    }

}
