package com.beassessment.stocksmanager.service;

import com.beassessment.stocksmanager.model.dao.StockDetails;
import com.beassessment.stocksmanager.repository.StockDetailsRepository;
import com.beassessment.stocksmanager.service.util.StocksUtilsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class StocksRequestHandlerService {

    private final StocksUtilsService stocksUtilsService;
    private final StockDetailsRepository stockDetailsRepository;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> stringStringKafkaTemplate;
    private final String stocksKafkaTopic;

    public StocksRequestHandlerService(StocksUtilsService stocksUtilsService, StockDetailsRepository stockDetailsRepository, ObjectMapper objectMapper, KafkaTemplate<String, String> stringStringKafkaTemplate, @Value("${stocks.kafka.topic}") String stocksKafkaTopic) {
        this.stocksUtilsService = stocksUtilsService;
        this.stockDetailsRepository = stockDetailsRepository;
        this.objectMapper = objectMapper;
        this.stringStringKafkaTemplate = stringStringKafkaTemplate;
        this.stocksKafkaTopic = stocksKafkaTopic;
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

    public ResponseEntity<String> uploadBulkStocks(List<String> bulkStocksList) {
        ResponseEntity<String> responseEntity = null;
        try {
            UUID uuid = UUID.randomUUID();
            String bulkStocksString = this.objectMapper.writeValueAsString(bulkStocksList);
            this.stringStringKafkaTemplate.send(this.stocksKafkaTopic, uuid.toString(), bulkStocksString);
            responseEntity = ResponseEntity.ok("Bulk stocks upload request received");
        } catch (Exception e) {
            log.error("Exception occurred when producing bulk stock details to stocks topic", e);
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }
}
