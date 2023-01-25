package com.beassessment.stocksmanager.model.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Stock_Detail")
@Table(name = "Stock_Details")
public class StockDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quarter")
    private String quarter;

    @Column(name = "stock")
    private String stock;

    @Column(name = "date")
    private String date;

    @Column(name = "open")
    private String open;

    @Column(name = "high")
    private String high;

    @Column(name = "low")
    private String low;

    @Column(name = "close")
    private String close;

    @Column(name = "volume")
    private String volume;

    @Column(name = "percent_change_price")
    private String percentChangePrice;

    @Column(name = "percent_change_volume_over_last_wk")
    private String percentChangeVolumeOverLastWk;

    @Column(name = "previous_weeks_volume")
    private String previousWeeksVolume;

    @Column(name = "next_weeks_open")
    private String nextWeeksOpen;

    @Column(name = "next_weeks_close")
    private String nextWeeksClose;

    @Column(name = "percent_change_next_weeks_price")
    private String percentChangeNextWeeksPrice;

    @Column(name = "days_to_next_dividend")
    private String daysToNextDividend;

    @Column(name = "percent_return_next_dividend")
    private String percentReturnNextDividend;
}
