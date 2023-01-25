package com.beassessment.stocksmanager.service.util;

import com.beassessment.stocksmanager.model.dao.StockDetails;
import org.springframework.stereotype.Service;

@Service
public class StocksUtilsService {

    public StockDetails getStockFromStockString(String stockString) {
        String[] stockDetailsInput = stockString.split(",");
        StockDetails stockDetails = new StockDetails();
        stockDetails.setQuarter(stockDetailsInput[0]);
        stockDetails.setStock(stockDetailsInput[1]);
        stockDetails.setDate(stockDetailsInput[2]);
        stockDetails.setOpen(stockDetailsInput[3]);
        stockDetails.setHigh(stockDetailsInput[4]);
        stockDetails.setLow(stockDetailsInput[5]);
        stockDetails.setClose(stockDetailsInput[6]);
        stockDetails.setVolume(stockDetailsInput[7]);
        stockDetails.setPercentChangePrice(stockDetailsInput[8]);
        stockDetails.setPercentChangeVolumeOverLastWk(stockDetailsInput[9]);
        stockDetails.setPreviousWeeksVolume(stockDetailsInput[10]);
        stockDetails.setNextWeeksOpen(stockDetailsInput[11]);
        stockDetails.setNextWeeksClose(stockDetailsInput[12]);
        stockDetails.setPercentChangeNextWeeksPrice(stockDetailsInput[13]);
        stockDetails.setDaysToNextDividend(stockDetailsInput[14]);
        stockDetails.setPercentReturnNextDividend(stockDetailsInput[15]);
        return stockDetails;
    }
}
