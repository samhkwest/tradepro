package com.demo.tradepro.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.tradepro.configuration.AppConfig;
import com.demo.tradepro.configuration.TestConfig;
import com.demo.tradepro.model.PurchasedItem;
import com.demo.tradepro.model.Trade;
import com.demo.tradepro.utils.Utils;

@Service
public class TradeServiceImpl implements TradeService{
	
	@Autowired
	AppConfig appConfig;
	
	/*
	private void checkData() {
		System.out.println("appConfig: "+Arrays.toString(appConfig.getSalestax().toArray()));
		
		appConfig.getProductcat().forEach((key, value) -> System.out.println(key + ":" + value));
				
		getTaxRate("CA", "book");
		getTaxRate("CA", "potato chips");		
		getTaxRate("NY", "book");
		getTaxRate("NY", "potato chips");
		getTaxRate("NY", "pencil");		
		getTaxRate("NY", "shirt");		
	}*/
	
	public String getReceipt(Trade trade) {	
		//checkData();
		
		String location = trade.getLocation();
		List<PurchasedItem> purchasedItem = trade.getPurchasedItem();
		
		String receiptStr = getReceipt(purchasedItem, location);
		return receiptStr;
	}
	
	private String getHeader() {
		return Utils.rightPad("item") + Utils.leftPad("price") + Utils.leftPad("qty")+Utils.LBREAK+"\r";		
	}
		
	private double getTaxRate(String location, String productName) {	
		//find product category
		Optional<Map.Entry<String, String>> first = appConfig.getProductcat()
		            .entrySet()
		            .stream()
		            .filter(entry -> entry.getValue().contains(productName))
		            .findFirst();
	            
		String productCat = first.isPresent() ? first.get().getKey() : "other";
		
		//find tax rate by location and product cat.
		Optional<Double> rate = appConfig.getSalestax()
				.stream()
				.filter(p -> p.getLocation().equalsIgnoreCase(location) && !p.getExempt().contains(productCat))
				.findFirst()
				.map(p -> p.getRate());
		
		return rate.isPresent() ? rate.get() : 0;
	}
	
	private String getDerivedRow(String label, String valStr) {
		return Utils.rightPad(label+":", Utils.HALF_LEN) + Utils.leftPad("$" + valStr, Utils.HALF_LEN);
	}
	
	private String getReceipt(List<PurchasedItem> purchasedItem, String location) {		
		BigDecimal subTotalAmt = new BigDecimal(0);
		BigDecimal totalTaxAmt = new BigDecimal(0);
		BigDecimal amount = new BigDecimal(0);
		StringBuilder itemList = new StringBuilder("");
		StringBuilder receipt = new StringBuilder("");
		
		for (PurchasedItem item : purchasedItem) {	
			amount = new BigDecimal(item.getPrice()).multiply(new BigDecimal(item.getQty()));
			subTotalAmt = subTotalAmt.add(amount);
			
			double taxRate = getTaxRate(location, item.getItem());
			totalTaxAmt = totalTaxAmt.add(amount.multiply(new BigDecimal(taxRate)));	
			
			/*
			 System.out.println(String.format("product: %s, price: %s, qty: %s, price*qty: %s, taxRate: %s, totalTaxAmt: %s", 			 
					item.getItem(), item.getPrice(), item.getQty(), subTotalAmt.doubleValue(), 
					String.valueOf(taxRate), totalTaxAmt.doubleValue()));
					*/
						
			//purchased items list
			String itemCol = Utils.rightPad(item.getItem());
			String priceCol = Utils.leftPad("$" + item.getPrice());
			String qtyCol = Utils.leftPad(item.getQty());
			itemList.append(itemCol).append(priceCol).append(qtyCol).append(Utils.LBREAK);
		}
				
		totalTaxAmt = Utils.roundUp(totalTaxAmt, Utils.RUP_NEAREST);
		BigDecimal totalAmt = subTotalAmt.add(totalTaxAmt);
		
		//receipt string
		String subTotalAmtRow = getDerivedRow("subtotal", String.valueOf(subTotalAmt));
		String totalTaxAmtRow = getDerivedRow("tax", totalTaxAmt.toString());		
		String totalAmtRow = getDerivedRow("total", String.valueOf(totalAmt));
		
		receipt.append(getHeader())
				.append(itemList.toString())
				.append(subTotalAmtRow).append(Utils.LBREAK)
				.append(totalTaxAmtRow).append(Utils.LBREAK)
				.append(totalAmtRow);
		
		return receipt.toString();		
	}	
}
