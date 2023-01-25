package com.beassessment.stocksmanager.service;

import com.beassessment.stocksmanager.model.dao.StockDetails;
import com.beassessment.stocksmanager.repository.StockDetailsRepository;
import com.beassessment.stocksmanager.service.util.StocksUtilsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StocksRequestHandlerService {

    private final StocksUtilsService stocksUtilsService;
    private final StockDetailsRepository stockDetailsRepository;

    public StocksRequestHandlerService(StocksUtilsService stocksUtilsService, StockDetailsRepository stockDetailsRepository) {
        this.stocksUtilsService = stocksUtilsService;
        this.stockDetailsRepository = stockDetailsRepository;
    }

    public ResponseEntity<String> addStock(String stockString) {
        ResponseEntity<String> responseEntity = null;
        try {
            StockDetails stockDetails = this.stocksUtilsService.getStockFromStockString(stockString);
            this.stockDetailsRepository.saveAndFlush(stockDetails);
            responseEntity = ResponseEntity.ok("Stock added");
        } catch (Exception e) {
            log.error("Exception occurred when adding stock", e);
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }

    public ResponseEntity<List<StockDetails>> getStockDetails(String stock) {
        ResponseEntity<List<StockDetails>> responseEntity = null;
        try {
            List<StockDetails> stockDetailsList = this.stockDetailsRepository.findStockDetailsByStock(stock);
            log.info("Results size: " + stockDetailsList.size());
            responseEntity = ResponseEntity.ok(stockDetailsList);
        } catch (Exception e) {
            log.error("Exception when getting stock details for stock: " + stock, e);
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }

    public ResponseEntity<List<StockDetails>> getQuarterlyStockDetails(String quarter, String stock) {
        ResponseEntity<List<StockDetails>> responseEntity = null;
        try {
            List<StockDetails> stockDetailsList = this.stockDetailsRepository.findStockDetailsByQuarterAndStock(quarter, stock);
            log.info("Results size: " + stockDetailsList.size());
            responseEntity = ResponseEntity.ok(stockDetailsList);
        } catch (Exception e) {
            log.error("Exception when getting quarterly stock details for quarter " + quarter + "for stock: " + stock, e);
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }
}
