package com.beassessment.stocksmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/stocks")
public class StocksController {

    @PostMapping("/addStock")
    public ResponseEntity<String> addStock(@RequestBody String stockString) {
        return ResponseEntity.ok("1");
    }

    @GetMapping("/getStockDetails/{stock}")
    public ResponseEntity<String> getStockDetails(@PathVariable String stock) {
        return ResponseEntity.ok("2");
    }

    @GetMapping("/getQuarterlyStockDetails/{quarter}/{stock}")
    public ResponseEntity<String> getQuarterlyStockDetails(@PathVariable String quarter, @PathVariable String stock) {
        return ResponseEntity.ok("3");
    }

    @PostMapping("/uploadBulkStocks")
    public ResponseEntity<String> uploadBulkStocks(@RequestBody List<String> bulkStocksList) {
        return ResponseEntity.ok("4");
    }

}
