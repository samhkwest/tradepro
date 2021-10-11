# tradepro

1. This is a app of RESTful API backend services to product sales receipt for a list of purchased products.

2. The service endpoint is http://[Domain]/tradepro/receipt

3. It accepts request messages in format:
      
      ## Sample 1
      
      Method: POST
      
      Header: 
            Content-type, application/json
            
      Payload:
```json            
          {
            "location":"CA",
            "purchasedItem":[
                {"item":"book","price":17.99,"qty":1},
                {"item":"potato chips","price":3.99,"qty":1}
              ]
          }
```

      Response: a plain text of sales receipt:
```json
      item                               price                 qty

      book                              $17.99                   1
      potato chips                       $3.99                   1
      subtotal:                                             $21.98
      tax:                                                   $1.80
      total:                                                $23.78
```
   
      ## Sample 2
      
      Method: POST
      
      Header: 
            Content-type, application/json
            
      Payload:
```json            
      {
            "location":"NY",
            "purchasedItem":[
                        {"item":"book","price":17.99,"qty":1},
                        {"item":"pencil","price":2.99,"qty":3}
                  ]
      }
```

      Response: a plain text of sales receipt:
```json
      item                               price                 qty

      book                              $17.99                   1
      pencil                             $2.99                   3
      subtotal:                                             $26.96
      tax:                                                   $2.40
      total:                                                $29.36
```
      
      
5. The sales tax rates are calculated by this formula:

      Sales tax = roundup(price * quantity * sales tax rate).
      
6. Certain product categories are exempt from sales tax (means tax will be 0), and sales tax amount should be rounded up to the nearest 0.05 (e.g. 1.13->1.15, 1.16->1.20, 1.151->1.20)

7. Soome products are exempted from tax rate:

      In California (CA), sales tax rate is 9.75%, food is exempted.
      
      In New York (NY), sales tax rate is 8.875%, food and clothing are exempted.
