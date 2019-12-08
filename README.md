# StockExchangeApp

This is the implementation of GBCE Stock Market Application.

Features provided :
1. Register any new Stock on Exchange
2. Record Trades done on stocks which are registered on exchange
3. Calculate GBCE Share Index value
4. Calculate various ratios like PE, Divident yield etc related to 
listed stocks.

--------------------------------------------------------

#####Package Structure :

- *com.challenge.exchange.service.** : This package contains all the 
Service Layer classes which does validation, implement Business logic
and provide access to Data Access Layer

- *com.challenge.exchange.data.** : This package contains all the Data
Access Layer classes which stores the data objects. In this implementation
data is stored in Java collections but by implementing given interfaces
database persistence can be implemented easily.

- *com.challenge.exchange.model.** :  This package contains model entities 
like Stock and Trade which holds domain data. This package also contains
ENUM used by this models.

--------------------------------------------------------------------

#####Typical Usage Flow:

1. Register the stocks on the exchange by providing required 
mandatory attributes related to stocks using method provided in 
interface : 
*com.challenge.exchange.service.StockStaticDataService*

2. Record the Trades in this stocks registered above using method in 
interface :
*com.challenge.exchange.service.StockTradeService*

3. Get Index value, dividend yield, PE Ratio, VolumeWeightedPrice 
using corresponding method provided in interface :
*com.challenge.exchange.service.StockExchangeService*


Below example main class be executed to see all the features mentioned above :
**com.challenge.exchange.SampleStockMarketApp**
