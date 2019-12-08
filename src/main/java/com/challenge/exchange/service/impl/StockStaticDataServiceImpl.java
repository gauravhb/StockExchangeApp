package com.challenge.exchange.service.impl;

import com.challenge.exchange.data.StaticDataDAO;
import com.challenge.exchange.model.Stock;
import com.challenge.exchange.service.StockStaticDataService;

import java.util.Map;

public class StockStaticDataServiceImpl implements StockStaticDataService {

    private StaticDataDAO stockDAO;
    public StockStaticDataServiceImpl(StaticDataDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    /* Get list of all the stocks currently listed on exchange
     * */
    @Override
    public Map<String,Stock> getAllExchangeListedStocks() {
        return stockDAO.getAllExchangeListedStocks();
    }

    /* This method is used to register stock on the exchange.
     * If same stock is already listed, it will be updated with latest details.
     * */
    @Override
    public Stock addStockStaticData(Stock stock) {
        //validate Mandatory fields
        if(stock == null || stock.getSymbol() == null || stock.getSymbol().trim().length() == 0 ||
                stock.getType() == null){
            throw new RuntimeException("Invalid stock, as mandatory stock details are not provided");
        }
        return stockDAO.addStockStaticData(stock);
    }

    /* This method removes already listed stock from exchange
     * */
    @Override
    public Stock removeStockStaticData(String symbol) {
        return getAllExchangeListedStocks().remove(symbol);
    }

    /* This method is to find details of the stock which is already list on the exchange.
     * Returns Null if stock with given symbol is not found.
     * */
    @Override
    public Stock findStock(String symbol) {
        return getAllExchangeListedStocks().get(symbol);
    }

    /* This method is used to remove all the listed stocks on exchange.
     * */
    @Override
    public void clearAllStockStaticData() {
        getAllExchangeListedStocks().clear();
    }


}
