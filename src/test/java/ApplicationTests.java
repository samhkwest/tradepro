package com.demo.tradepro;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.tradepro.model.PurchasedItem;
import com.demo.tradepro.model.Trade;
import com.demo.tradepro.utils.Utils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@Component
public class ApplicationTests  {
	final static String ENDPOINT_URL = "http://localhost:%s/tradepro/receipt";

	@Autowired
	private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;
	
    Map<Integer, Trade> testData;
    Map<Integer, String> expectedResult;
    
    @Before
    public void setup() {
        testData = getTestData();
        expectedResult = getExpectedResult();
    }
    
    private Map<Integer, String> getExpectedResult(){
    	    	
    	Map<Integer, String> result = new HashMap<Integer, String>();
    	
    	String receipt = 
    			"item                               price                 qty" + Utils.LBREAK +"\r"+
    		    "book                              $17.99                   1" + Utils.LBREAK +
    		    "potato chips                       $3.99                   1" + Utils.LBREAK +
    		    "subtotal:                                             $21.98" + Utils.LBREAK +
    		    "tax:                                                   $1.80" + Utils.LBREAK +
    		    "total:                                                $23.78";
    	result.put(1, receipt);
    	receipt = 
    			"item                               price                 qty" + Utils.LBREAK +"\r"+
    		    "book                              $17.99                   1" + Utils.LBREAK +
    		    "pencil                             $2.99                   3" + Utils.LBREAK +
    		    "subtotal:                                             $26.96" + Utils.LBREAK +
    		    "tax:                                                   $2.40" + Utils.LBREAK +
    		    "total:                                                $29.36";
    	result.put(2, receipt);
    	receipt = 
    			"item                               price                 qty" + Utils.LBREAK +"\r"+
    		    "pencil                             $2.99                   2" + Utils.LBREAK +
    		    "shirt                             $29.99                   1" + Utils.LBREAK +
    		    "subtotal:                                             $35.97" + Utils.LBREAK +
    		    "tax:                                                   $0.55" + Utils.LBREAK +
    		    "total:                                                $36.52";
    	result.put(3, receipt);
    	
    	//System.out.println("expected result:\n"+result.get(1));
    	
    	return result;
    }
        
    private Map<Integer, Trade> getTestData() {
    	Map<Integer, Trade> data = new HashMap<>();
    	
		List<PurchasedItem> itemList = new ArrayList<PurchasedItem>();
		itemList.add(new PurchasedItem("book", "17.99", "1"));
		itemList.add(new PurchasedItem("potato chips", "3.99", "1"));
		data.put(1, new Trade("CA", itemList));

		itemList = new ArrayList<PurchasedItem>();
		itemList.add(new PurchasedItem("book", "17.99", "1"));
		itemList.add(new PurchasedItem("pencil", "2.99", "3"));
		data.put(2, new Trade("NY", itemList));
		
		itemList = new ArrayList<PurchasedItem>();
		itemList.add(new PurchasedItem("pencil", "2.99", "2"));
		itemList.add(new PurchasedItem("shirt", "29.99", "1"));
		data.put(3, new Trade("NY", itemList));
		
        return data;
    }

    @Test
    public void testTradeReceiptCase1() throws Exception {
    	    	
    	for (int i=1;i<=testData.size();i++) {
    		runTest(i);
    	}
    }
    
    private void runTest(int index) throws Exception {
    	Trade trade = testData.get(index);
    	
        URI uri = new URI(String.format(ENDPOINT_URL, randomServerPort));    	
    	HttpEntity<Trade> request = new HttpEntity<>(trade, new HttpHeaders());
    	    	
        ResponseEntity<String> resp = this.restTemplate.postForEntity(uri, request, String.class);
    	
    	
    	String actual = resp.getBody();    	
    	String expected = expectedResult.get(index);
    	
    	System.out.println("\nCase  "+index+":....................................................");
    	System.out.println("\nExpected Result:\n"+expected);
    	System.out.println("\nActual Result:\n"+actual);
    	
    	Assert.assertEquals(200, resp.getStatusCodeValue());
    	Assert.assertEquals(expected, actual);
    }
  
}
