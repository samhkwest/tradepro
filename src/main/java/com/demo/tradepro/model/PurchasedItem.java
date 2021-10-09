package com.demo.tradepro.model;

public class PurchasedItem {
	private String item;
	private String price;
	private String qty;
	
	public PurchasedItem(String item,  String price, String qty){
		this.item = item;
		this.price = price;
		this.qty = qty;
	}
	
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
    
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
    
    @Override
    public String toString() {
		return String.format("(item: %s, price: %s, qty: %s)", this.item, this.price, this.qty);
    }
}
