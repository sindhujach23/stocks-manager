package com.beassessment.stocksmanager.service;

import com.beassessment.stocksmanager.model.dao.StockDetails;
import com.beassessment.stocksmanager.repository.StockDetailsRepository;
import com.beassessment.stocksmanager.service.util.StocksUtilsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BulkStocksUploadConsumerService {

    private final ObjectMapper objectMapper;
    private final StockDetailsRepository stockDetailsRepository;
    private final StocksUtilsService stocksUtilsService;

    public BulkStocksUploadConsumerService(ObjectMapper objectMapper, StockDetailsRepository stockDetailsRepository, StocksUtilsService stocksUtilsService) {
        this.objectMapper = objectMapper;
        this.stockDetailsRepository = stockDetailsRepository;
        this.stocksUtilsService = stocksUtilsService;
    }

    @KafkaListener(topics = "#{'${stocks.kafka.topic}'}", groupId = "#{'${stocks.kafka.topic.consumer.group.id}'}", containerFactory = "stringStringConcurrentKafkaListenerContainerFactory")
    public void uploadBulkStocks(@Payload String bulkStockString) {
        this.uploadStocksFromStocksString(bulkStockString);
    }

    private void uploadStocksFromStocksString(String bulkStockString) {
        try {
            List<String> stocksList = this.objectMapper.readValue(bulkStockString, List.class);
            List<StockDetails> stockDetailsList = stocksList
                    .stream()
                    .map(this.stocksUtilsService::getStockFromStockString)
                    .toList();
            this.stockDetailsRepository.saveAllAndFlush(stockDetailsList);
        } catch (Exception e) {
            log.error("Exception when uploading bulk stocks to db", e);
        }
    }
}
