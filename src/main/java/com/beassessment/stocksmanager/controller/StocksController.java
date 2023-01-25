package com.beassessment.stocksmanager.controller;

import com.beassessment.stocksmanager.model.dao.StockDetails;
import com.beassessment.stocksmanager.service.StocksRequestHandlerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/stocks")
public class StocksController {

    private final StocksRequestHandlerService stocksRequestHandlerService;

    public StocksController(StocksRequestHandlerService stocksRequestHandlerService) {
        this.stocksRequestHandlerService = stocksRequestHandlerService;
    }

    @PostMapping("/addStock")
    public ResponseEntity<String> addStock(@RequestBody String stockString) {
        return this.stocksRequestHandlerService.addStock(stockString);
    }

    @GetMapping("/getStockDetails/{stock}")
    public ResponseEntity<List<StockDetails>> getStockDetails(@PathVariable String stock) {
        return this.stocksRequestHandlerService.getStockDetails(stock);
    }

    @GetMapping("/getQuarterlyStockDetails/{quarter}/{stock}")
    public ResponseEntity<List<StockDetails>> getQuarterlyStockDetails(@PathVariable String quarter, @PathVariable String stock) {
        return this.stocksRequestHandlerService.getQuarterlyStockDetails(quarter, stock);
    }

    @PostMapping("/uploadBulkStocks")
    public ResponseEntity<String> uploadBulkStocks(@RequestBody List<String> bulkStocksList) {
        return ResponseEntity.ok("4");
    }

}
