package com.demo.tradepro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.tradepro.model.Trade;
import com.demo.tradepro.service.TradeService;

@Component
@RestController
public class TradeController {
	
  @Autowired
  private TradeService tradeService;
      
  @PostMapping(path="/tradepro/receipt", produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<Object> createReceipt(@RequestBody Trade trade){
	  String receipt = tradeService.getReceipt(trade);	  
	  return new ResponseEntity<Object>(receipt, HttpStatus.OK);
  }
  
 
}
