package com.demo.tradepro.model;

import java.util.Arrays;
import java.util.List;

public class Trade {
	String location;
	List<PurchasedItem> purchasedItem;
	
	public Trade(String location, List<PurchasedItem> purchasedItem) {
		this.location = location;
		this.purchasedItem = purchasedItem;
	}
	
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public List<PurchasedItem> getPurchasedItem() {
        return purchasedItem;
    }

    public void setPurchasedItem(List<PurchasedItem> purchasedItem) {
        this.purchasedItem = purchasedItem;
    }
    
    @Override
    public String toString() {
		return "location: "+location+", purchasedItem: "+Arrays.toString(purchasedItem.toArray());
    }
}
