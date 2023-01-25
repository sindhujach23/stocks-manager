package com.beassessment.stocksmanager.repository;

import com.beassessment.stocksmanager.model.dao.StockDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockDetailsRepository extends JpaRepository<StockDetails, Long> {

    List<StockDetails> findStockDetailsByStock(String stock);

    List<StockDetails> findStockDetailsByQuarterAndStock(String quarter, String stock);
}
