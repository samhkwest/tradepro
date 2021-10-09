package com.demo.tradepro.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.tradepro.model.PurchasedItem;
import com.demo.tradepro.model.Trade;
import com.demo.tradepro.utils.Utils;

@Service
public class TradeServiceImpl implements TradeService{
	
	public String getReceipt(Trade trade) {
		String location = trade.getLocation();
		List<PurchasedItem> purchasedItem = trade.getPurchasedItem();
		
		String receiptStr = getReceipt(purchasedItem, location);
		return receiptStr;
	}
	
	private String getHeader() {
		return Utils.rightPad("item", Utils.PAD_LEN)
				+Utils.leftPad("price", Utils.PAD_LEN)
				+Utils.leftPad("qty", Utils.PAD_LEN)+Utils.LBREAK+"\r";		
	}
	
	private double getTaxRate(String location, String productName) {
		double taxRate = 0;
		String city = location.toUpperCase();
		
		switch (city) {
		case "CA":
			taxRate = productName.equalsIgnoreCase("potato chips") ? 0.0 : 9.75/100;
			break;
		case "NY":
			taxRate = (productName.equalsIgnoreCase("shirt") || productName.equalsIgnoreCase("potato chips")) ? 0.0 : 8.875/100;
			break;
		default:
			taxRate = 0.0;
		}
				
		return taxRate;
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
		
		System.out.println("location: "+location);
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
	
    public static BigDecimal roundUp(BigDecimal amount, double nearestNum)
    {
    	Double val = 10/(nearestNum*100);
    	int nearest = val.intValue();
    	String dividsor = String.valueOf(nearest);
    	int scale = Integer.parseInt(dividsor);
    	
    	amount = amount.multiply(new BigDecimal(dividsor));
    	amount = amount.setScale(1, RoundingMode.UP);
    	amount = amount.divide(new BigDecimal(dividsor), scale, RoundingMode.UP);
    	return amount;
    }
}
