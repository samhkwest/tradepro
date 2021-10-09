package com.demo.tradepro.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {
	public final static int PAD_LEN = 20;
	public final static int HALF_LEN = PAD_LEN*3/2;
	public final static String LBREAK = "\r\n";
	public final static double RUP_NEAREST = 0.05;
	
	public static String rightPad(String str, int num) {
	    return String.format("%-"+num+"s", str);
	}
		
	public static String leftPad(String str, int num) {
	    return String.format("%1$" + num + "s", str);
	}
	
	public static String rightPad(String str) {
	    return String.format("%-"+PAD_LEN+"s", str);
	}
		
	public static String leftPad(String str) {
	    return String.format("%1$" + PAD_LEN + "s", str);
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
