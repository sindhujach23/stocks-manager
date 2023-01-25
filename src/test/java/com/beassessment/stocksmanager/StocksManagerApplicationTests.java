package com.beassessment.stocksmanager;

import com.beassessment.stocksmanager.model.dao.StockDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class StocksManagerApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void allEndpointsTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/stocks/uploadBulkStocks")
                        .content(this.getBulkStocksString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Thread.sleep(5000);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/stocks/getStockDetails/AA")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        List<StockDetails> stockDetailsList = this.objectMapper.readValue(result, List.class);
        Assertions.assertEquals(2, stockDetailsList.size());

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/stocks/addStock")
                        .content(this.getStockString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Thread.sleep(5000);

        mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/stocks/getQuarterlyStockDetails/1/AA")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        result = mvcResult.getResponse().getContentAsString();
        stockDetailsList = this.objectMapper.readValue(result, List.class);
        Assertions.assertEquals(1, stockDetailsList.size());
    }

    private String getBulkStocksString() throws JsonProcessingException {
        List<String> stocksList = new ArrayList<>();
        stocksList.add("2,AA,4/1/2011,$17.13,$17.80,$17.02,$17.47,103320396,1.98482,8.131838957,95550392,$17.42,$17.92,2.87026,41,0.171723");
        stocksList.add("2,AA,4/8/2011,$17.42,$18.47,$17.42,$17.92,129237024,2.87026,25.08374823,103320396,$18.06,$16.52,-8.52713,34,0.167411");
        return this.objectMapper.writeValueAsString(stocksList);
    }

    private String getStockString() {
        return "1,AA,1/7/2011,$15.82,$16.72,$15.78,$16.42,239655616,3.79267,,,$16.71,$15.97,-4.42849,26,0.182704";
    }

}
